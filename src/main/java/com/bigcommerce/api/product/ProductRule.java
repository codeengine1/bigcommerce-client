package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRule extends BigCommerceEntity {

	private PriceAdjuster _priceAdjuster;
	private List<Condition> _conditions;

	@JsonProperty("price_adjuster")
	public PriceAdjuster getPriceAdjuster() {
		return _priceAdjuster;
	}

	public ProductRule setPriceAdjuster(PriceAdjuster priceAdjuster) {
		_priceAdjuster = priceAdjuster;
		return this;
	}

	@JsonProperty("conditions")
	public List<Condition> getConditions() {
		return _conditions;
	}

	public ProductRule setConditions(List<Condition> conditions) {
		_conditions = conditions;
		return this;
	}

	@JsonIgnore
	public List<Condition> getSkuConditions() {
		if (getConditions() == null || getConditions().size() == 0) {
			return null;
		}

		List<Condition> conditions = new ArrayList<>();

		for (Condition condition : getConditions()) {
			if (condition.getSkuId() != null) {
				conditions.add(condition);
			}
		}

		return conditions;
	}

	@Override
	public String toString() {
		return "ProductRule{" +
				"_priceAdjuster=" + _priceAdjuster +
				"} " + super.toString();
	}
}
