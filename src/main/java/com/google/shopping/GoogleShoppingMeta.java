package com.google.shopping;

import java.util.List;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class GoogleShoppingMeta {

	private String nameMatch;
	private String googleShoppingTaxonomyCategory;
	private String customLabel0;

	public String getNameMatch() {
		return nameMatch;
	}

	public GoogleShoppingMeta setNameMatch(String nameMatch) {
		this.nameMatch = nameMatch.toLowerCase();
		return this;
	}

	public String getGoogleShoppingTaxonomyCategory() {
		return googleShoppingTaxonomyCategory;
	}

	public GoogleShoppingMeta setGoogleShoppingTaxonomyCategory(String googleShoppingTaxonomyCategory) {
		this.googleShoppingTaxonomyCategory = googleShoppingTaxonomyCategory;
		return this;
	}

	public String getCustomLabel0() {
		return customLabel0;
	}

	public GoogleShoppingMeta setCustomLabel0(String customLabel0) {
		this.customLabel0 = customLabel0;
		return this;
	}

	public static GoogleShoppingMeta getMatchingMeta(List<GoogleShoppingMeta> metas, final String name) {
		for (GoogleShoppingMeta meta : metas) {
			if (name.toLowerCase().contains(meta.getNameMatch())) {
				return meta;
			}
		}

		return null;
	}
}
