package com.bigcommerce.api.brand;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.text.html.HTML;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class Brand extends BigCommerceEntity {

	private String _name;
	private String _pageTitle;
	private String _metaKeywords;
	private String _metaDescription;
	private String _imageFile;
	private String _searchKeywords;

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	public Brand setName(String name) {
		_name = name;
		return this;
	}

	@JsonProperty("page_title")
	public String getPageTitle() {
		return _pageTitle;
	}

	public Brand setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
		return this;
	}

	@JsonProperty("meta_keywords")
	public String getMetaKeywords() {
		return _metaKeywords;
	}

	public Brand setMetaKeywords(String metaKeywords) {
		_metaKeywords = metaKeywords;
		return this;
	}

	@JsonProperty("meta_description")
	public String getMetaDescription() {
		return _metaDescription;
	}

	public Brand setMetaDescription(String metaDescription) {
		_metaDescription = metaDescription;
		return this;
	}

	@JsonProperty("image_file")
	public String getImageFile() {
		return _imageFile;
	}

	public Brand setImageFile(String imageFile) {
		_imageFile = imageFile;
		return this;
	}

	@JsonProperty("search_keywords")
	public String getSearchKeywords() {
		return _searchKeywords;
	}

	public Brand setSearchKeywords(String searchKeywords) {
		_searchKeywords = searchKeywords;
		return this;
	}

	@Override
	public String toString() {
		return "Brand{" +
				"_name='" + _name + '\'' +
				", _pageTitle='" + _pageTitle + '\'' +
				", _metaKeywords='" + _metaKeywords + '\'' +
				", _metaDescription='" + _metaDescription + '\'' +
				", _imageFile='" + _imageFile + '\'' +
				", _searchKeywords='" + _searchKeywords + '\'' +
				"} " + super.toString();
	}
}
