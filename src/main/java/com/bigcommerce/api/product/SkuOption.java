package com.bigcommerce.api.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class SkuOption {

	private BigInteger _optionValueId;
	private BigInteger _productOptionId;

	@JsonProperty("option_value_id")
	public BigInteger getOptionValueId() {
		return _optionValueId;
	}

	public SkuOption setOptionValueId(BigInteger optionValueId) {
		_optionValueId = optionValueId;
		return this;
	}

	@JsonProperty("product_option_id")
	public BigInteger getProductOptionId() {
		return _productOptionId;
	}

	public SkuOption setProductOptionId(BigInteger productOptionId) {
		_productOptionId = productOptionId;
		return this;
	}

	@Override
	public String toString() {
		return "SkuOption{" +
				"_optionValueId=" + _optionValueId +
				", _productOptionId=" + _productOptionId +
				'}';
	}
}
