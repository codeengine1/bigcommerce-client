package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class ProductRule extends BigCommerceEntity {

	private PriceAdjuster _priceAdjuster;

	@JsonProperty("price_adjuster")
	public PriceAdjuster getPriceAdjuster() {
		return _priceAdjuster;
	}

	public ProductRule setPriceAdjuster(PriceAdjuster priceAdjuster) {
		_priceAdjuster = priceAdjuster;
		return this;
	}

	@Override
	public String toString() {
		return "ProductRule{" +
				"_priceAdjuster=" + _priceAdjuster +
				"} " + super.toString();
	}
}
