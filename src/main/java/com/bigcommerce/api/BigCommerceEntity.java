package com.bigcommerce.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BigCommerceEntity {

	private String _json;

	@JsonIgnore
	public String getJson() {
		return _json;
	}

	@JsonIgnore
	public BigCommerceEntity setJson(String json) {
		_json = json;
		return this;
	}

	@Override
	public String toString() {
		return "BigCommerceEntity{" +
				"_json='" + _json + '\'' +
				'}';
	}
}
