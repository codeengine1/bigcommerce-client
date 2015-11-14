package com.bigcommerce.api;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.ning.http.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class BigCommerceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceClient.class);
	private static final int DEFAULT_TIMEOUT_SECONDS = 10;
	private static final JsonNodeFactory JSON_NODE_FACTORY = new JsonNodeFactory(false);
	private final AsyncHttpClient _asyncHttpClient;
	private final Settings _settings;
	private final BigCommerceScopes _scopes;

	public BigCommerceClient(Settings settings) {
		this(new AsyncHttpClient(new AsyncHttpClientConfig.Builder().build()), settings);
	}

	public BigCommerceClient(AsyncHttpClient asyncHttpClient,
							 Settings settings) {
		_settings = settings;
		_asyncHttpClient = asyncHttpClient;
		_scopes = new BigCommerceScopes();
	}

	/**
	 * @return requestBuilder
	 */
	public RequestBuilder getRequestBuilder() {
		return new RequestBuilder()
				.setHeader("Authorization", _settings.getAuthorizationHeader())
				.setHeader("Accept", "application/json")
				.setHeader("Content-Type", "application/json");
	}

	/**
	 *
	 * @param entityType
	 * @param id
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> T findById(Class<T> entityType, final BigInteger id)
			throws TimeoutException {
		final String idUrl = getIdUrl(entityType, id);
		final RequestBuilder requestBuilder = getRequestBuilder();
		return findEntity(idUrl, requestBuilder, entityType);
	}

	/**
	 *
	 * @param entityType
	 * @param criteria
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> List<T> find(Class<T> entityType, Map<String, String> criteria)
			throws TimeoutException {
		final String queryUrl = getQueryUrl(entityType);
		final RequestBuilder requestBuilder = getRequestBuilder();

		for (Map.Entry<String, String> criterion : criteria.entrySet()) {
			requestBuilder.addQueryParam(criterion.getKey(), criterion.getValue());
		}

		return findEntities(entityType, requestBuilder, queryUrl);
	}

	/**
	 *
	 * @param entityType
	 * @param criteria
	 * @param page
	 * @param limit
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> List<T> find(Class<T> entityType,
													  Map<String, String> criteria,
													  int page,
													  int limit)
			throws TimeoutException {
		final String queryUrl = getQueryUrl(entityType);
		final RequestBuilder requestBuilder = getRequestBuilder();

		if (criteria != null && criteria.size() > 0) {
			for (Map.Entry<String, String> criterion : criteria.entrySet()) {
				requestBuilder.addQueryParam(criterion.getKey(), criterion.getValue());
			}
		}

		requestBuilder.addQueryParam("page", new Integer(page).toString());
		requestBuilder.addQueryParam("limit", new Integer(limit).toString());

		return findEntities(entityType, requestBuilder, queryUrl);
	}

	/**
	 *
	 * @param entityType
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> List<T> findAll(Class<T> entityType)
			throws TimeoutException {
		Set<T> entities = new HashSet<T>();
		int page = 1;
		int limit = 250;

		while (true) {
			List<T> items = find(entityType, null, page, limit);

			if (items == null || items.size() == 0) {
				break;
			}

			entities.addAll(items);
			page++;
		}

		return new ArrayList<T>(entities);
	}

	/**
	 *
	 * @param entityType
	 * @param fieldName
	 * @param value
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> List<T> find(Class<T> entityType, String fieldName, String value)
			throws TimeoutException {
		Map<String, String> criteria = ImmutableMap.of(fieldName, value);
		return find(entityType, criteria);
	}

	/**
	 * Applies an update to the product in question
	 *
	 * @param entity
	 * @param fieldNames
	 * @param <T>
	 */
	public <T extends BigCommerceEntity> void update(T entity, List<String> fieldNames) throws TimeoutException {
		String url = getIdUrl(entity.getClass(), entity.getId());
		final RequestBuilder requestBuilder = getRequestBuilder();
		ObjectNode objectNode = JSON_NODE_FACTORY.objectNode();

		for (String fieldName : fieldNames) {
			String jsonFieldName = entity.getJsonFieldName(fieldName);
			String value = entity.getFieldValue(fieldName, String.class);

			if (value == null) {
				objectNode.putNull(jsonFieldName);
			} else {
				objectNode.put(jsonFieldName, value);
			}
		}

		requestBuilder.setUrl(url);
		requestBuilder.setMethod("PUT");
		requestBuilder.setBody(objectNode.toString());
		getResponse(url, requestBuilder);
	}

	/**
	 *
	 * @param entityType
	 * @param resource
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> List<T> resources(Class<T> entityType, Resource resource)
			throws TimeoutException {
		return findEntities(entityType, getRequestBuilder(), resource.getUrl());
	}

	/**
	 *
	 * @param entityType
	 * @param resource
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> T resource(Class<T> entityType, Resource resource)
			throws TimeoutException {
		return findEntity(resource.getUrl(), getRequestBuilder(), entityType);
	}

	/**
	 *
	 * @param url
	 * @param requestBuilder
	 * @param entityType
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	private <T extends BigCommerceEntity> T findEntity(String url,
													   RequestBuilder requestBuilder,
													   Class<T> entityType) throws TimeoutException {
		Response response = getResponse(url, requestBuilder);

		if (response == null || response.getStatusCode() >= 300) {
			return null;
		}

		try {
			return BigCommerceEntityMapper.readValue(response.getResponseBody(), entityType);
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 *
	 * @param entityType
	 * @param requestBuilder
	 * @param url
	 * @param <T>
	 * @return
	 * @throws TimeoutException
	 */
	private <T extends BigCommerceEntity> List<T> findEntities(Class<T> entityType,
															   RequestBuilder requestBuilder,
															   String url) throws TimeoutException {
		Response response = getResponse(url, requestBuilder);

		try {
			if (response == null
					|| response.getResponseBody() == null
					|| response.getResponseBody().isEmpty()
					|| response.getStatusCode() >= 300) {
				return null;
			}

			return BigCommerceEntityMapper.readValues(response.getResponseBody(), entityType);
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Base method to get to a response from the api
	 *
	 * @param url
	 * @param requestBuilder
	 * @return response
	 * @throws TimeoutException
	 */
	public Response getResponse(String url, RequestBuilder requestBuilder) throws TimeoutException {

		System.out.println(url);
		if (!url.toLowerCase().startsWith("http")) {
			url = _settings.getBaseUrl() + url;
		}

		requestBuilder.setUrl(url);
		Request request = requestBuilder.build();

		try {
			Response response =
					_asyncHttpClient.executeRequest(request).get(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);

			if (response != null) {
				LOGGER.debug(BigCommerceEntityMapper.prettify(response.getResponseBody()));
			}

			return response;
		} catch(ExecutionException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} catch (InterruptedException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return null;
	}

	/**
	 * @param entityClass
	 * @return restUrl
	 */
	private String getQueryUrl(Class<? extends BigCommerceEntity> entityClass) {
		return String.format("%s/api/v2/%s", _settings.getBaseUrl(),
				_scopes.getQueryScope(entityClass));
	}

	/**
	 * @param id
	 * @param entityClass
	 * @return restUrl
	 */
	private String getIdUrl(Class<? extends BigCommerceEntity> entityClass, final BigInteger id) {
		return String.format("%s/api/v2/%s/%s", _settings.getBaseUrl(),
				_scopes.getQueryScope(entityClass), id.toString());
	}
}
