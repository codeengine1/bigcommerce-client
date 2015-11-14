package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class Option extends BigCommerceEntity {

	private BigInteger _optionId;
	private String _displayName;

	@JsonProperty("option_id")
	public BigInteger getOptionId() {
		return _optionId;
	}

	public Option setOptionId(BigInteger optionId) {
		_optionId = optionId;
		return this;
	}

	@JsonProperty("display_name")
	public String getDisplayName() {
		return _displayName;
	}

	public Option setDisplayName(String displayName) {
		_displayName = displayName;
		return this;
	}

	@Override
	public String toString() {
		return "Option{" +
				"_optionId=" + _optionId +
				", _displayName=" + _displayName +
				"} " + super.toString();
	}
}
