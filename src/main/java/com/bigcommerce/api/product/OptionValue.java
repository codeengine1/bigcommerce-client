package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class OptionValue extends BigCommerceEntity {

	private BigInteger _optionId;
	private String _label;
	private Integer _sortOrder;
	private String _value;
	private boolean _default;

	@JsonProperty("option_id")
	public BigInteger getOptionId() {
		return _optionId;
	}

	public OptionValue setOptionId(BigInteger optionId) {
		_optionId = optionId;
		return this;
	}

	@JsonProperty("label")
	public String getLabel() {
		return _label;
	}

	public OptionValue setLabel(String label) {
		_label = label;
		return this;
	}

	@JsonProperty("sort_order")
	public Integer getSortOrder() {
		return _sortOrder;
	}

	public OptionValue setSortOrder(Integer sortOrder) {
		_sortOrder = sortOrder;
		return this;
	}

	@JsonProperty("value")
	public String getValue() {
		return _value;
	}

	public OptionValue setValue(String value) {
		_value = value;
		return this;
	}

	@JsonProperty("is_default")
	public boolean isDefault() {
		return _default;
	}

	public OptionValue setDefault(boolean aDefault) {
		_default = aDefault;
		return this;
	}

	@Override
	public String toString() {
		return "OptionValue{" +
				"_optionId=" + _optionId +
				", _label='" + _label + '\'' +
				", _sortOrder=" + _sortOrder +
				", _value='" + _value + '\'' +
				", _default=" + _default +
				"} " + super.toString();
	}
}
