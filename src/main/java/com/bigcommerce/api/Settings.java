package com.bigcommerce.api;

import org.apache.commons.codec.binary.Base64;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class Settings {

	private final String storeId;
	private final String baseUrl;
	private final String username;
	private final String password;

	public Settings(String storeId,
					String baseUrl,
					String username,
					String password) {
		this.baseUrl = baseUrl;
		this.storeId = storeId;
		this.username = username;
		this.password = password;
	}

	public String getAuthorizationHeader() {
		return "Basic " + new Base64().encodeAsString("apiuser:eff139a5fe85e774d4e1a11872cd9720d34cf443".getBytes());
	}

	public String getStoreId() {
		return storeId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	@Override
	public String toString() {
		return "Settings{" +
				"storeId='" + storeId + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
