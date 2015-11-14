package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.bigcommerce.api.Resource;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryImage extends BigCommerceEntity {

	private String _zoomUrl;
	private String _thumbnailUrl;
	private String _standardUrl;
	private String _tinyUrl;

	@JsonProperty("zoom_url")
	public String getZoomUrl() {
		return _zoomUrl;
	}

	public PrimaryImage setZoomUrl(String zoomUrl) {
		_zoomUrl = zoomUrl;
		return this;
	}

	@JsonProperty("thumbnail_url")
	public String getThumbnailUrl() {
		return _thumbnailUrl;
	}

	public PrimaryImage setThumbnailUrl(String thumbnailUrl) {
		_thumbnailUrl = thumbnailUrl;
		return this;
	}

	@JsonProperty("standard_url")
	public String getStandardUrl() {
		return _standardUrl;
	}

	public PrimaryImage setStandardUrl(String standardUrl) {
		_standardUrl = standardUrl;
		return this;
	}

	@JsonProperty("tiny_url")
	public String getTinyUrl() {
		return _tinyUrl;
	}

	public PrimaryImage setTinyUrl(String tinyUrl) {
		_tinyUrl = tinyUrl;
		return this;
	}

	@Override
	public String toString() {
		return "PrimaryImage{" +
				"_zoomUrl='" + _zoomUrl + '\'' +
				", _thumbnailUrl='" + _thumbnailUrl + '\'' +
				", _standardUrl='" + _standardUrl + '\'' +
				", _tinyUrl='" + _tinyUrl + '\'' +
				"} " + super.toString();
	}
}
