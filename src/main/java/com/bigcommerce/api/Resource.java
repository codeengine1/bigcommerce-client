package com.bigcommerce.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource {

	private String _url;
	private String _resource;

	public String getUrl() {
		return _url;
	}

	public Resource setUrl(String url) {
		_url = url;
		return this;
	}

	public String getResource() {
		return _resource;
	}

	public Resource setResource(String resource) {
		_resource = resource;
		return this;
	}

	@Override
	public String toString() {
		return "Resource{" +
				"_url='" + _url + '\'' +
				", _resource='" + _resource + '\'' +
				'}';
	}
}
