package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
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
public class ProductOption extends BigCommerceEntity {

	private BigInteger _id;
	private BigInteger _optionId;
	private String _displayName;
	private Integer _sortOrder;
	private boolean _isRequired;

	@JsonProperty("id")
	public BigInteger getId() {
		return _id;
	}

	public ProductOption setId(BigInteger id) {
		_id = id;
		return this;
	}

	@JsonProperty("option_id")
	public BigInteger getOptionId() {
		return _optionId;
	}

	public ProductOption setOptionId(BigInteger optionId) {
		_optionId = optionId;
		return this;
	}

	@JsonProperty("display_name")
	public String getDisplayName() {
		return _displayName;
	}

	public ProductOption setDisplayName(String displayName) {
		_displayName = displayName;
		return this;
	}

	@JsonProperty("sort_order")
	public Integer getSortOrder() {
		return _sortOrder;
	}

	public ProductOption setSortOrder(Integer sortOrder) {
		_sortOrder = sortOrder;
		return this;
	}

	@JsonProperty("is_required")
	public boolean isRequired() {
		return _isRequired;
	}

	public ProductOption setIsRequired(boolean isRequired) {
		_isRequired = isRequired;
		return this;
	}

	@Override
	public String toString() {
		return "ProductOption{" +
				"_id=" + _id +
				", _optionId=" + _optionId +
				", _displayName='" + _displayName + '\'' +
				", _sortOrder=" + _sortOrder +
				", _isRequired=" + _isRequired +
				"} " + super.toString();
	}
}
