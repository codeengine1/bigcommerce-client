package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class ConfigurableField extends BigCommerceEntity {

	private BigInteger _productId;
	private String _name;
	private String _type;
	private String _allowedFileTypes;
	private BigInteger _maxSize;
	private String _selectOptions;
	private boolean _required;
	private Integer sortOrder;

	@JsonProperty("product_id")
	public BigInteger getProductId() {
		return _productId;
	}

	public ConfigurableField setProductId(BigInteger productId) {
		_productId = productId;
		return this;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	public ConfigurableField setName(String name) {
		_name = name;
		return this;
	}

	@JsonProperty("type")
	public String getType() {
		return _type;
	}

	public ConfigurableField setType(String type) {
		_type = type;
		return this;
	}

	@JsonProperty("allowed_file_types")
	public String getAllowedFileTypes() {
		return _allowedFileTypes;
	}

	public ConfigurableField setAllowedFileTypes(String allowedFileTypes) {
		_allowedFileTypes = allowedFileTypes;
		return this;
	}

	@JsonProperty("max_size")
	public BigInteger getMaxSize() {
		return _maxSize;
	}

	public ConfigurableField setMaxSize(BigInteger maxSize) {
		_maxSize = maxSize;
		return this;
	}

	@JsonProperty("select_options")
	public String getSelectOptions() {
		return _selectOptions;
	}

	public ConfigurableField setSelectOptions(String selectOptions) {
		_selectOptions = selectOptions;
		return this;
	}

	@JsonProperty("is_required")
	public boolean isRequired() {
		return _required;
	}

	public ConfigurableField setRequired(boolean required) {
		_required = required;
		return this;
	}

	@JsonProperty("sort_order")
	public Integer getSortOrder() {
		return sortOrder;
	}

	public ConfigurableField setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}

	@Override
	public String toString() {
		return "ConfigurableField{" +
				"_productId=" + _productId +
				", _name='" + _name + '\'' +
				", _type='" + _type + '\'' +
				", _allowedFileTypes='" + _allowedFileTypes + '\'' +
				", _maxSize=" + _maxSize +
				", _selectOptions='" + _selectOptions + '\'' +
				", _required=" + _required +
				", sortOrder=" + sortOrder +
				"} " + super.toString();
	}
}
