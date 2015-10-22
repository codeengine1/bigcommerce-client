package com.bigcommerce.api.product;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sku extends BigCommerceEntity {

	private BigInteger _id;
	private BigInteger _productId;
	private String _sku;
	private BigDecimal _costPrice;
	private String _upc;
	private Integer _inventoryLevel;
	private Integer _inventoryWarningLevel;
	private String _binPickingNumber;

	@JsonProperty("id")
	public BigInteger getId() {
		return _id;
	}

	public Sku setId(BigInteger id) {
		_id = id;
		return this;
	}

	@JsonProperty("product_id")
	public BigInteger getProductId() {
		return _productId;
	}

	public Sku setProductId(BigInteger productId) {
		_productId = productId;
		return this;
	}

	@JsonProperty("sku")
	public String getSku() {
		return _sku;
	}

	public Sku setSku(String sku) {
		_sku = sku;
		return this;
	}

	@JsonProperty("cost_price")
	public BigDecimal getCostPrice() {
		return _costPrice;
	}

	public Sku setCostPrice(BigDecimal costPrice) {
		_costPrice = costPrice;
		return this;
	}

	@JsonProperty("upc")
	public String getUpc() {
		return _upc;
	}

	public Sku setUpc(String upc) {
		_upc = upc;
		return this;
	}

	@JsonProperty("inventory_level")
	public Integer getInventoryLevel() {
		return _inventoryLevel;
	}

	public Sku setInventoryLevel(Integer inventoryLevel) {
		_inventoryLevel = inventoryLevel;
		return this;
	}

	@JsonProperty("inventory_warning_level")
	public Integer getInventoryWarningLevel() {
		return _inventoryWarningLevel;
	}

	public Sku setInventoryWarningLevel(Integer inventoryWarningLevel) {
		_inventoryWarningLevel = inventoryWarningLevel;
		return this;
	}

	@JsonProperty("bin_picking_number")
	public String getBinPickingNumber() {
		return _binPickingNumber;
	}

	public Sku setBinPickingNumber(String binPickingNumber) {
		_binPickingNumber = binPickingNumber;
		return this;
	}

	@Override
	public String toString() {
		return "Sku{" +
				"_id=" + _id +
				", _productId=" + _productId +
				", _sku='" + _sku + '\'' +
				", _costPrice=" + _costPrice +
				", _upc='" + _upc + '\'' +
				", _inventoryLevel=" + _inventoryLevel +
				", _inventoryWarningLevel=" + _inventoryWarningLevel +
				", _binPickingNumber='" + _binPickingNumber + '\'' +
				"} " + super.toString();
	}
}
