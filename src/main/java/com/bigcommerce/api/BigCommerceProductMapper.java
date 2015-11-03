package com.bigcommerce.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class BigCommerceProductMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceProductMapper.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		MAPPER.setDateFormat(DATE_FORMAT);
		MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}

	/**
	 *
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T extends BigCommerceEntity> List<T> readValues(String json, Class<T> clazz) {
		try {
			if (json == null || json.trim().isEmpty()) {
				return null;
			}

			List<T> entities = new ArrayList<T>();
			JsonNode clientResponse = MAPPER.readValue(json, JsonNode.class);

			if (!clientResponse.isArray()) {
				return null;
			}

			for (int i = 0; i < clientResponse.size(); i++) {
				JsonNode jsonNode = clientResponse.get(i);
				final String rawJson = prettify(jsonNode.toString());
				T entity = MAPPER.readValue(rawJson, clazz);
				entity.setJson(rawJson);
				entities.add(entity);
			}

			return entities;
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 *
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T extends BigCommerceEntity> T readValue(String json, Class<T> clazz) {
		try {
			if (json == null || json.trim().isEmpty()) {
				return null;
			}

			JsonNode jsonNode = MAPPER.readValue(json, JsonNode.class);

			if (jsonNode.isArray()) {
				final String msg = String.format(
						"Client response contains array of %s entities!!",
						clazz.getSimpleName()
				);
				LOGGER.error(msg);
			}

			final String rawJson = prettify(jsonNode.toString());
			T entity = MAPPER.readValue(rawJson, clazz);
			entity.setJson(rawJson);
			return entity;
		} catch (IOException ex) {
			LOGGER.error("json=" + json + "\n" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Makes JSON pretty
	 *
	 * @param json
	 * @return prettyJson
	 */
	public static String prettify(String json) {
		if (json == null || json.trim().isEmpty()) {
			return null;
		}

		try {
			JsonNode jsonNode = MAPPER.readValue(json.getBytes(), JsonNode.class);
			return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
		} catch (Throwable ex) {
			LOGGER.error(ex.getMessage(), ex);
			return json;
		}
	}

}
