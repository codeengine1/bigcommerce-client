package com.google.shopping;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@XmlRootElement(name="entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class GoogleShoppingEntry {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleShoppingEntry.class);

	@XmlElement(name = "g:age_group")
	private String _ageGroup;

	@XmlElement(name = "g:availability")
	private String _availability;

	@XmlElement(name = "g:brand")
	private String _brand;

	@XmlElement(name = "g:description")
	private String _description;

	@XmlElement(name = "g:condition")
	private String _condition;

	@XmlElement(name = "g:gender")
	private String _gender;

	@XmlElement(name = "g:google_product_category")
	private String _googleProductCategory;

	@XmlElement(name = "g:gtin")
	private String _gtin;

	@XmlElement(name = "g:id")
	private String _id;

	@XmlElement(name = "g:identifier_exists")
	private boolean _identifierExists;

	@XmlElement(name = "g:image_link")
	private String _imageLink;

	@XmlElement(name = "g:link")
	private String _link;

	@XmlElement(name = "g:material")
	private String _material;

	@XmlElement(name = "g:mpn")
	private String _mpn;

	@XmlElement(name = "g:pattern")
	private String _pattern;

	@XmlElement(name = "g:price")
	private String _price;

	@XmlElement(name = "g:product_type")
	private String _productType;

	@XmlElement(name = "g:shipping_weight")
	private String _shippingWeight;

	@XmlElement(name = "g:size")
	private String _size;

	@XmlElement(name = "g:color")
	private String _color;

	@XmlElement(name = "g:title")
	private String _title;

	@XmlElement(name = "g:custom_label_0")
	private String _customLabel0;

	public String getAgeGroup() {
		return _ageGroup;
	}

	public GoogleShoppingEntry setAgeGroup(String ageGroup) {
		_ageGroup = ageGroup;
		return this;
	}

	public String getAvailability() {
		return _availability;
	}

	public GoogleShoppingEntry setAvailability(String availability) {
		_availability = availability;
		return this;
	}

	public String getBrand() {
		return _brand;
	}

	public GoogleShoppingEntry setBrand(String brand) {
		_brand = brand;
		return this;
	}

	public String getCondition() {
		return _condition;
	}

	public GoogleShoppingEntry setCondition(String condition) {
		_condition = condition;
		return this;
	}

	public String getGender() {
		return _gender;
	}

	public GoogleShoppingEntry setGender(String gender) {
		_gender = gender;
		return this;
	}

	public String getGoogleProductCategory() {
		return _googleProductCategory;
	}

	public GoogleShoppingEntry setGoogleProductCategory(String googleProductCategory) {
		_googleProductCategory = googleProductCategory;
		return this;
	}

	public String getGtin() {
		return _gtin;
	}

	public GoogleShoppingEntry setGtin(String gtin) {
		_gtin = gtin;
		return this;
	}

	public String getId() {
		return _id;
	}

	public GoogleShoppingEntry setId(String id) {
		_id = id;
		return this;
	}

	public boolean isIdentifierExists() {
		return _identifierExists;
	}

	public GoogleShoppingEntry setIdentifierExists(boolean identifierExists) {
		_identifierExists = identifierExists;
		return this;
	}

	public String getImageLink() {
		return _imageLink;
	}

	public GoogleShoppingEntry setImageLink(String imageLink) {
		_imageLink = imageLink;
		return this;
	}

	public String getLink() {
		return _link;
	}

	public GoogleShoppingEntry setLink(String link) {
		_link = link;
		return this;
	}

	public String getMaterial() {
		return _material;
	}

	public GoogleShoppingEntry setMaterial(String material) {
		_material = material;
		return this;
	}

	public String getMpn() {
		return _mpn;
	}

	public GoogleShoppingEntry setMpn(String mpn) {
		_mpn = mpn;
		return this;
	}

	public String getPattern() {
		return _pattern;
	}

	public GoogleShoppingEntry setPattern(String pattern) {
		_pattern = pattern;
		return this;
	}

	public String getPrice() {
		return _price;
	}

	public GoogleShoppingEntry setPrice(String price) {
		_price = price;
		return this;
	}

	public String getProductType() {
		return _productType;
	}

	public GoogleShoppingEntry setProductType(String productType) {
		_productType = productType;
		return this;
	}

	public String getShippingWeight() {
		return _shippingWeight;
	}

	public GoogleShoppingEntry setShippingWeight(String shippingWeight) {
		_shippingWeight = shippingWeight;
		return this;
	}

	public String getSize() {
		return _size;
	}

	public GoogleShoppingEntry setSize(String size) {
		_size = size;
		return this;
	}

	public String getColor() {
		return _color;
	}

	public GoogleShoppingEntry setColor(String color) {
		_color = color;
		return this;
	}

	public String getTitle() {
		return _title;
	}

	public GoogleShoppingEntry setTitle(String title) {
		_title = StringEscapeUtils.escapeXml(title);
		return this;
	}

	public String getDescription() {
		return _description;
	}

	public String getCustomLabel0() {
		return _customLabel0;
	}

	public GoogleShoppingEntry setCustomLabel0(String customLabel0) {
		_customLabel0 = customLabel0;
		return this;
	}

	public GoogleShoppingEntry setDescription(String description) {
		_description = StringEscapeUtils.escapeXml(description);
		return this;
	}

	/**
	 * Marshal to XML String
	 *
	 * @return xml
	 */
	public String toXml() {
		StringWriter stringWriter = new StringWriter();

		try {
			Marshaller marshaller = getJaxBContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(this, stringWriter);
		} catch (JAXBException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return stringWriter.toString();
	}

	/**
	 *
	 */
	private static class JaxBContextHolder {
		private static JAXBContext INSTANCE;
		static {
			try {
				INSTANCE = JAXBContext.newInstance(GoogleShoppingEntry.class);
			} catch (JAXBException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Returns a threadsafe singleton instance of JAXBContext
	 *
	 * @return jaxBContext
	 */
	private static JAXBContext getJaxBContext() {
		return JaxBContextHolder.INSTANCE;
	}
}
