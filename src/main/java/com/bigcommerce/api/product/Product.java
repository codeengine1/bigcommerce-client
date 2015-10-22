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
public class Product extends BigCommerceEntity {

	private BigInteger _id;
	private String _sku;
	private Resource _options;
	private Resource _skus;
	private String _customUrl;

	@JsonProperty("id")
	public BigInteger getId() {
		return _id;
	}

	public Product setId(BigInteger id) {
		_id = id;
		return this;
	}

	@JsonProperty("sku")
	public String getSku() {
		return _sku;
	}

	public Product setSku(String sku) {
		_sku = sku;
		return this;
	}

	@JsonProperty("options")
	public Resource getOptions() {
		return _options;
	}

	public Product setOptions(Resource options) {
		_options = options;
		return this;
	}

	@JsonProperty("skus")
	public Resource getSkus() {
		return _skus;
	}

	public Product setSkus(Resource skus) {
		_skus = skus;
		return this;
	}

	@JsonProperty("custom_url")
	public String getCustomUrl() {
		return _customUrl;
	}

	public Product setCustomUrl(String customUrl) {
		_customUrl = customUrl;
		return this;
	}

	@Override
	public String toString() {
		return "Product{" +
				"_id=" + _id +
				", _sku='" + _sku + '\'' +
				", _options=" + _options +
				"} " + super.toString();
	}
}
