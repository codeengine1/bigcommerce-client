package com.bigcommerce.api;

import com.google.common.collect.ImmutableMap;
import com.ning.http.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class BigCommerceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceClient.class);
	private static final int DEFAULT_TIMEOUT_SECONDS = 10;
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
	public <T extends BigCommerceEntity> T findById(Class<T> entityType, final Object id)
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
	 *
	 * @param entityType
	 * @param resource
	 * @param <T> entityClass
	 * @return entity
	 * @throws TimeoutException
	 */
	public <T extends BigCommerceEntity> List<T> resource(Class<T> entityType, Resource resource)
			throws TimeoutException {
		return findEntities(entityType, getRequestBuilder(), resource.getUrl());
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
			return BigCommerceProductMapper.readValue(response.getResponseBody(), entityType);
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

		if (response == null || response.getStatusCode() >= 300) {
			return null;
		}

		try {
			return BigCommerceProductMapper.readValues(response.getResponseBody(), entityType);
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
	private Response getResponse(String url, RequestBuilder requestBuilder) throws TimeoutException {
		try {
			requestBuilder.setUrl(url);
			Request request = requestBuilder.build();
			return _asyncHttpClient.executeRequest(request).get(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} catch (ExecutionException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return null;
	}

	/**
	 * @param entityClass
	 * @return restUrl
	 */
	private String getQueryUrl(Class<? extends BigCommerceEntity> entityClass) {
		return String.format("https://store-%s.mybigcommerce.com/api/v2/%s", _settings.getStoreId(),
				_scopes.getQueryScope(entityClass));
	}

	/**
	 * @param id
	 * @param entityClass
	 * @return restUrl
	 */
	private String getIdUrl(Class<? extends BigCommerceEntity> entityClass, final Object id) {
		return String.format("https://store-%s.mybigcommerce.com/api/v2/%s/%s", _settings.getStoreId(),
				_scopes.getQueryScope(entityClass), id.toString());
	}
}
