package com.bigcommerce.api.category;

import com.bigcommerce.api.BigCommerceEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends BigCommerceEntity {

	private BigInteger _id;
	private BigInteger _parentId;
	private String _name;
	private String _description;
	private Integer _sortOrder;
	private String _pageTitle;
	private String _metaKeywords;
	private String _metaDescription;
	private String _layoutFile;
	private List<BigInteger> _parentCategoryList;
	private String _imageFile;
	private boolean _isVisible;
	private String _searchKeywords;
	private String _url;

	@JsonProperty("id")
	public BigInteger getId() {
		return _id;
	}

	public Category setId(BigInteger id) {
		_id = id;
		return this;
	}

	@JsonProperty("parent_id")
	public BigInteger getParentId() {
		return _parentId;
	}

	public Category setParentId(BigInteger parentId) {
		_parentId = parentId;
		return this;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	public Category setName(String name) {
		_name = name;
		return this;
	}

	@JsonProperty("description")
	public String getDescription() {
		return _description;
	}

	public Category setDescription(String description) {
		_description = description;
		return this;
	}

	@JsonProperty("sortOrder")
	public Integer getSortOrder() {
		return _sortOrder;
	}

	public Category setSortOrder(Integer sortOrder) {
		_sortOrder = sortOrder;
		return this;
	}

	@JsonProperty("page_title")
	public String getPageTitle() {
		return _pageTitle;
	}

	public Category setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
		return this;
	}

	@JsonProperty("meta_keywords")
	public String getMetaKeywords() {
		return _metaKeywords;
	}

	public Category setMetaKeywords(String metaKeywords) {
		_metaKeywords = metaKeywords;
		return this;
	}

	@JsonProperty("meta_description")
	public String getMetaDescription() {
		return _metaDescription;
	}

	public Category setMetaDescription(String metaDescription) {
		_metaDescription = metaDescription;
		return this;
	}

	@JsonProperty("layout_file")
	public String getLayoutFile() {
		return _layoutFile;
	}

	public Category setLayoutFile(String layoutFile) {
		_layoutFile = layoutFile;
		return this;
	}

	@JsonProperty("parent_category_list")
	public List<BigInteger> getParentCategoryList() {
		return _parentCategoryList;
	}

	public Category setParentCategoryList(List<BigInteger> parentCategoryList) {
		_parentCategoryList = parentCategoryList;
		return this;
	}

	@JsonProperty("image_file")
	public String getImageFile() {
		return _imageFile;
	}

	public Category setImageFile(String imageFile) {
		_imageFile = imageFile;
		return this;
	}

	@JsonProperty("is_visible")
	public boolean isVisible() {
		return _isVisible;
	}

	public Category setIsVisible(boolean isVisible) {
		_isVisible = isVisible;
		return this;
	}

	@JsonProperty("search_keywords")
	public String getSearchKeywords() {
		return _searchKeywords;
	}

	public Category setSearchKeywords(String searchKeywords) {
		_searchKeywords = searchKeywords;
		return this;
	}

	@JsonProperty("url")
	public String getUrl() {
		return _url;
	}

	public Category setUrl(String url) {
		_url = url;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		if (_isVisible != category._isVisible) return false;
		if (_id != null ? !_id.equals(category._id) : category._id != null) return false;
		if (_parentId != null ? !_parentId.equals(category._parentId) : category._parentId != null) return false;
		if (_name != null ? !_name.equals(category._name) : category._name != null) return false;
		if (_description != null ? !_description.equals(category._description) : category._description != null)
			return false;
		if (_sortOrder != null ? !_sortOrder.equals(category._sortOrder) : category._sortOrder != null) return false;
		if (_pageTitle != null ? !_pageTitle.equals(category._pageTitle) : category._pageTitle != null) return false;
		if (_metaKeywords != null ? !_metaKeywords.equals(category._metaKeywords) : category._metaKeywords != null)
			return false;
		if (_metaDescription != null ? !_metaDescription.equals(category._metaDescription) : category._metaDescription != null)
			return false;
		if (_layoutFile != null ? !_layoutFile.equals(category._layoutFile) : category._layoutFile != null)
			return false;
		if (_parentCategoryList != null ? !_parentCategoryList.equals(category._parentCategoryList) : category._parentCategoryList != null)
			return false;
		if (_imageFile != null ? !_imageFile.equals(category._imageFile) : category._imageFile != null) return false;
		if (_searchKeywords != null ? !_searchKeywords.equals(category._searchKeywords) : category._searchKeywords != null)
			return false;
		return !(_url != null ? !_url.equals(category._url) : category._url != null);

	}

	@Override
	public int hashCode() {
		int result = _id != null ? _id.hashCode() : 0;
		result = 31 * result + (_parentId != null ? _parentId.hashCode() : 0);
		result = 31 * result + (_name != null ? _name.hashCode() : 0);
		result = 31 * result + (_description != null ? _description.hashCode() : 0);
		result = 31 * result + (_sortOrder != null ? _sortOrder.hashCode() : 0);
		result = 31 * result + (_pageTitle != null ? _pageTitle.hashCode() : 0);
		result = 31 * result + (_metaKeywords != null ? _metaKeywords.hashCode() : 0);
		result = 31 * result + (_metaDescription != null ? _metaDescription.hashCode() : 0);
		result = 31 * result + (_layoutFile != null ? _layoutFile.hashCode() : 0);
		result = 31 * result + (_parentCategoryList != null ? _parentCategoryList.hashCode() : 0);
		result = 31 * result + (_imageFile != null ? _imageFile.hashCode() : 0);
		result = 31 * result + (_isVisible ? 1 : 0);
		result = 31 * result + (_searchKeywords != null ? _searchKeywords.hashCode() : 0);
		result = 31 * result + (_url != null ? _url.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Category{" +
				"_id=" + _id +
				", _parentId=" + _parentId +
				", _name='" + _name + '\'' +
				", _description='" + _description + '\'' +
				", _sortOrder=" + _sortOrder +
				", _pageTitle='" + _pageTitle + '\'' +
				", _metaKeywords='" + _metaKeywords + '\'' +
				", _metaDescription='" + _metaDescription + '\'' +
				", _layoutFile='" + _layoutFile + '\'' +
				", _parentCategoryList=" + _parentCategoryList +
				", _imageFile='" + _imageFile + '\'' +
				", _isVisible=" + _isVisible +
				", _searchKeywords='" + _searchKeywords + '\'' +
				", _url='" + _url + '\'' +
				"} " + super.toString();
	}
}
