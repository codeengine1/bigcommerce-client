package com.bigcommerce.api.product;

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
public class Condition {

	private BigInteger _productOptionId;
	private BigInteger _optionValueId;
	private BigInteger _skuId;

	@JsonProperty("product_option_id")
	public BigInteger getProductOptionId() {
		return _productOptionId;
	}

	public Condition setProductOptionId(BigInteger productOptionId) {
		_productOptionId = productOptionId;
		return this;
	}

	@JsonProperty("option_value_id")
	public BigInteger getOptionValueId() {
		return _optionValueId;
	}

	public Condition setOptionValueId(BigInteger optionValueId) {
		_optionValueId = optionValueId;
		return this;
	}

	@JsonProperty("sku_id")
	public BigInteger getSkuId() {
		return _skuId;
	}

	public Condition setSkuId(BigInteger skuId) {
		_skuId = skuId;
		return this;
	}
}
