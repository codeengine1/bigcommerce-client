package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class PriceAdjuster extends BigCommerceEntity {

	private String _adjusterType;
	private BigDecimal _adjusterValue;

	@JsonProperty("adjuster")
	public String getAdjusterType() {
		return _adjusterType;
	}

	public PriceAdjuster setAdjusterType(String adjusterType) {
		_adjusterType = adjusterType;
		return this;
	}

	@JsonProperty("adjuster_value")
	public BigDecimal getAdjusterValue() {
		return _adjusterValue;
	}

	public PriceAdjuster setAdjusterValue(BigDecimal adjusterValue) {
		if (adjusterValue == null) {
			_adjusterValue = null;
		}

		_adjusterValue = adjusterValue.setScale(2, RoundingMode.HALF_EVEN);
		return this;
	}

	@Override
	public String toString() {
		return "PriceAdjuster{" +
				"_adjusterType='" + _adjusterType + '\'' +
				", _adjusterValue=" + _adjusterValue +
				"} " + super.toString();
	}
}
