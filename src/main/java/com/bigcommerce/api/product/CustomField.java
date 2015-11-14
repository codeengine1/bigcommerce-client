package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.bigcommerce.api.BigCommerceEntityMapper;
import com.fasterxml.jackson.annotation.*;

import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomField extends BigCommerceEntity {

	private BigInteger _productId;
	private String _name;
	private String _text;

	@JsonProperty("product_id")
	public BigInteger getProductId() {
		return _productId;
	}

	public CustomField setProductId(BigInteger productId) {
		_productId = productId;
		return this;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	public CustomField setName(String name) {
		_name = name;
		return this;
	}

	@JsonProperty("text")
	public String getText() {
		return _text;
	}

	public CustomField setText(String text) {
		_text = text;
		return this;
	}

	@JsonIgnore
	public String toJson() {
		return BigCommerceEntityMapper.toJson(this);
	}

	@Override
	public String toString() {
		return "CustomField{" +
				"_productId=" + _productId +
				", _name='" + _name + '\'' +
				", _text='" + _text + '\'' +
				'}';
	}
}
