package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.bigcommerce.api.Resource;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BigCommerceEntity {

	private String _sku;
	private Resource _options;
	private Resource _skus;
	private Resource _rules;
	private Resource _brand;
	private Resource _configurableFields;
	private Resource _customFields;
	private PrimaryImage _primaryImage;
	private String _customUrl;
	private BigDecimal _retailPrice;
	private BigDecimal _price;
	private String _name;
	private String _pageTitle;
	private Boolean _freeShipping;
	private String _description;
	private String _upc;
	private String _availability;

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

	@JsonProperty("retail_price")
	public BigDecimal getRetailPrice() {
		return _retailPrice;
	}

	public Product setRetailPrice(BigDecimal retailPrice) {
		if (retailPrice == null) {
			_retailPrice = null;
			return this;
		}

		_retailPrice = retailPrice.setScale(2, RoundingMode.HALF_EVEN);
		return this;
	}

	@JsonProperty("price")
	public BigDecimal getPrice() {
		return _price;
	}

	public Product setPrice(BigDecimal price) {
		if (price == null) {
			_price = null;
			return this;
		}

		_price = price.setScale(2, RoundingMode.HALF_EVEN);
		return this;
	}

	@JsonProperty("rules")
	public Resource getRules() {
		return _rules;
	}

	public Product setRules(Resource rules) {
		_rules = rules;
		return this;
	}

	@JsonProperty("brand")
	public Resource getBrand() {
		return _brand;
	}

	public Product setBrand(Resource brand) {
		_brand = brand;
		return this;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	public Product setName(String name) {
		_name = name;
		return this;
	}

	@JsonProperty("page_title")
	public String getPageTitle() {
		return _pageTitle;
	}

	public Product setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
		return this;
	}

	@JsonProperty("is_free_shipping")
	public Boolean getFreeShipping() {
		return _freeShipping;
	}

	public Product setFreeShipping(Boolean freeShipping) {
		_freeShipping = freeShipping;
		return this;
	}

	@JsonProperty("configurable_fields")
	public Resource getConfigurableFields() {
		return _configurableFields;
	}

	public Product setConfigurableFields(Resource configurableFields) {
		_configurableFields = configurableFields;
		return this;
	}

	@JsonProperty("custom_fields")
	public Resource getCustomFields() {
		return _customFields;
	}

	public Product setCustomFields(Resource customFields) {
		_customFields = customFields;
		return this;
	}

	@JsonProperty("description")
	public String getDescription() {
		return _description;
	}

	public Product setDescription(String description) {
		_description = description;
		return this;
	}

	@JsonProperty("upc")
	public String getUpc() {
		if (_upc == null) {
			return null;
		}

		_upc = _upc.replaceAll("\\D+","");

		if (_upc.length() < 10) {
			return null;
		}

		return StringUtils.leftPad((_upc), 12, "0");
	}

	public Product setUpc(String upc) {
		_upc = upc;
		return this;
	}

	@JsonProperty("primary_image")
	public PrimaryImage getPrimaryImage() {
		return _primaryImage;
	}

	public Product setPrimaryImage(PrimaryImage primaryImage) {
		_primaryImage = primaryImage;
		return this;
	}

	@JsonProperty("availability")
	public String getAvailability() {
		return _availability;
	}

	public Product setAvailability(String availability) {
		_availability = availability;
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
