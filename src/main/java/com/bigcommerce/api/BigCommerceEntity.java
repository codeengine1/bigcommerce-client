package com.bigcommerce.api;

import com.bigcommerce.api.product.Product;
import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BigCommerceEntity {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceEntity.class);
	private String _json;
	protected BigInteger _id;

	@JsonProperty("id")
	public BigInteger getId() {
		return _id;
	}

	public BigCommerceEntity setId(BigInteger id) {
		_id = id;
		return this;
	}

	@JsonIgnore
	public String getJson() {
		return _json;
	}

	@JsonIgnore
	public BigCommerceEntity setJson(String json) {
		_json = json;
		return this;
	}

	@JsonIgnore
	public String getJsonFieldName(String fieldName) {
		if (fieldName.startsWith("_")) {
			fieldName = fieldName.substring(1);
		}

		if (!fieldName.startsWith("get")) {
			fieldName = "get" + WordUtils.capitalize(fieldName);
		}

		try {
			Method getter = getGetter(fieldName);

			if (getter == null) {
				return null;
			}

			for (Annotation annotation : getter.getDeclaredAnnotations()) {
				if (annotation.annotationType().equals(JsonProperty.class)) {
					JsonProperty jsonProperty = (JsonProperty) annotation;
					return jsonProperty.value();
				}
			}
		} catch (Throwable ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return null;
	}

	/**
	 * Get the value by fieldName
	 *
	 * @param fieldName
	 * @param <T>
	 * @return fieldValue
	 */
	@JsonIgnore
	public <T> T getFieldValue(String fieldName, Class<T> clazz) {
		Method getter = getGetter(fieldName);

		if (getter == null) {
			return null;
		}

		try {
			Object value = getter.invoke(this, null);

			if (clazz.equals(String.class)) {
				return (T) value.toString();
			}

			return (T) value;
		} catch (Throwable ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return null;
	}

	/**
	 * @param fieldName
	 * @return method
	 */
	private Method getGetter(String fieldName) {
		if (fieldName.startsWith("_")) {
			fieldName = fieldName.substring(1);
		}

		if (!fieldName.startsWith("get")) {
			fieldName = "get" + WordUtils.capitalize(fieldName);
		}

		try {
			return this.getClass().getMethod(fieldName, null);
		} catch (NoSuchMethodException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return null;
	}

	@Override
	public String toString() {
		return "BigCommerceEntity{" +
				"_json='" + _json + '\'' +
				'}';
	}
}
