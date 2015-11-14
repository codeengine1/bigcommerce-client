package com.bigcommerce.api;

import com.bigcommerce.api.brand.Brand;
import com.bigcommerce.api.category.Category;
import com.bigcommerce.api.product.*;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class BigCommerceClientTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceClientTest.class);
	private final BigCommerceClient _bigCommerceClient;

	public BigCommerceClientTest() {
		Config config = ConfigFactory.load("bigcommerce.conf");
		Settings credentials = new Settings(config.getString("bigcommerce.storeid"),
				config.getString("bigcommerce.api.baseUrl"),
				config.getString("bigcommerce.api.user"),
				config.getString("bigcommerce.api.secret"));
		_bigCommerceClient = new BigCommerceClient(credentials);
	}

	@Ignore
	@Test
	public void testFindBySku() throws TimeoutException {
		List<Product> products = _bigCommerceClient.find(Product.class, "sku", "2A0-004O0-6406/200P");
		LOGGER.debug(products.toString());
	}

	@Ignore
	@Test
	public void testFindById() throws TimeoutException {
		Category category = _bigCommerceClient.findById(Category.class, BigInteger.valueOf(443));
		LOGGER.debug(category.toString());
	}

	@Ignore
	@Test
	public void updateRetailPrice() throws TimeoutException {
		List<Product> products = _bigCommerceClient.findAll(Product.class);

		for (Product product : products) {
			product.setRetailPrice(BigDecimal.ZERO);
			_bigCommerceClient.update(product, Arrays.asList("retailPrice"));
		}
	}

	@Ignore
	@Test
	public void updatePriceForOptions() throws TimeoutException {
		List<Product> products = _bigCommerceClient.findAll(Product.class);

		for (Product product : products) {
			boolean isProductWithPriceRules = false;
			BigDecimal minimumPrice = null;

			if (product.getRules() == null) {
				continue;
			}

			List<ProductRule> productRules = _bigCommerceClient.resources(ProductRule.class, product.getRules());

			if (productRules == null || productRules.size() == 0) {
				continue;
			}

			for (ProductRule productRule : productRules) {
				if (productRule.getPriceAdjuster() != null
						&& productRule.getPriceAdjuster().getAdjusterType() != null
						&& productRule.getPriceAdjuster().getAdjusterValue() != null
						&& productRule.getPriceAdjuster().getAdjusterType().equals("absolute")
						&& productRule.getPriceAdjuster().getAdjusterValue().compareTo(BigDecimal.ZERO) > 0) {

					isProductWithPriceRules = true;

					if (minimumPrice == null) {
						minimumPrice = productRule.getPriceAdjuster().getAdjusterValue();
					}

					if (minimumPrice.compareTo(productRule.getPriceAdjuster().getAdjusterValue()) > 0) {
						minimumPrice = productRule.getPriceAdjuster().getAdjusterValue();
					}
				}
			}

			if (isProductWithPriceRules) {
				product.setPrice(minimumPrice);
				_bigCommerceClient.update(product, Arrays.asList("price"));
			}
		}
	}

	@Ignore
	@Test
	public void sortDesignerCategoriesByPrice() throws TimeoutException {
		Category category = _bigCommerceClient.findById(Category.class, BigInteger.valueOf(400));
		System.out.println(category.getJson());
//		List<Category> categories = _bigCommerceClient.findAll(Category.class);
//
//		for (Category category : categories) {
//			if (category.getParentCategoryList().contains(BigInteger.valueOf(51))) {
//				System.out.println(category.getName() + " | " + category.getSortOrder());
//			}
//		}
	}


	@Ignore
	@Test
	public void updateProductTitle() throws TimeoutException {
//		List<Product> products = _bigCommerceClient.findAll(Product.class);
		List<Product> products = _bigCommerceClient.find(Product.class, "sku", "2A0-004O0-6406/200P");

		for (Product product : products) {

			if (product.getBrand() == null) {
				continue;
			}

			Brand brand = _bigCommerceClient.resource(Brand.class, product.getBrand());

			if (brand == null || brand.getName() == null || brand.getName().isEmpty()) {
				continue;
			}

			final String pageTitle = String.format("%s %s", brand.getName(), product.getName());
			product.setPageTitle(pageTitle);
			_bigCommerceClient.update(product, Arrays.asList("pageTitle"));
		}
	}

	@Ignore
	@Test
	public void markDuvets() throws TimeoutException {
//		List<Product> products = _bigCommerceClient.findAll(Product.class);
		List<Product> products = _bigCommerceClient.find(Product.class, "name", "Izmir Duvet");

		for (Product product : products) {
			boolean hasExistingItemType = false;
			CustomField itemType = new CustomField().setProductId(product.getId()).setName("Item Type");

			List<CustomField> customFields =
					_bigCommerceClient.resources(CustomField.class, product.getCustomFields());

			if (customFields != null && customFields.size() >= 0) {

				System.out.println(customFields);

				for (CustomField customField : customFields) {
					if (customField.getName().equals("Item Type")) {
						hasExistingItemType = true;
						itemType = customField;
					}
				}
			}

			if (product.getName().contains("Duvet")) {
				itemType.setText("Duvets");
			}

			if (!hasExistingItemType) {
//				_bigCommerceClient.update();
			}
		}
	}

	@Ignore
	@Test
	public void addBedSizes() throws TimeoutException, IOException {
		List<Product> products = _bigCommerceClient.findAll(Product.class);

		for (Product product : products) {

			if (product.getName().toLowerCase().contains("bedspread")) {

				List<String> sizeOptions = new ArrayList<String>();

				RequestBuilder optionsRequestBuilder = _bigCommerceClient.getRequestBuilder().setMethod("GET");
				final String optionsUrl = String.format("/api/v2/products/%d/options", product.getId());
				Response optionsResponse = _bigCommerceClient.getResponse(optionsUrl, optionsRequestBuilder);

				if (optionsResponse.getStatusCode() != 200) {
					continue;
				}

				List<Option> options =
						BigCommerceEntityMapper.readValues(optionsResponse.getResponseBody(), Option.class);

				for (Option option : options) {
					if (option.getDisplayName().equalsIgnoreCase("size")) {
						// ok -- we've got a size option

						final String optionValuesUrl = String.format("/api/v2/options/%d/values", option.getOptionId());
						RequestBuilder optionValuesRequestBuilder =
								_bigCommerceClient.getRequestBuilder().setMethod("GET");
						Response optionValuesResponse =
								_bigCommerceClient.getResponse(optionValuesUrl, optionValuesRequestBuilder);
						List<OptionValue> optionValues =
								BigCommerceEntityMapper.readValues(
										optionValuesResponse.getResponseBody(), OptionValue.class);

						for (OptionValue optionValue : optionValues) {
							sizeOptions.add(optionValue.getLabel());
						}
					}
				}

				if (sizeOptions.size() > 0) {
					List<CustomField> customFields =
							_bigCommerceClient.resources(CustomField.class, product.getCustomFields());

					if (customFields != null && customFields.size() > 0) {
						for (CustomField customField : customFields) {
							if (customField.getName().equalsIgnoreCase("Bedding Size")) {
								if (sizeOptions.contains(customField.getText())) {
									sizeOptions.remove(customField.getText());
								}
							}

							if (customField.getName().equalsIgnoreCase("Size")) {
								final String removalUrl =
										String.format("/api/v2/products/%d/custom_fields/%d",
												product.getId(), customField.getId());
								RequestBuilder sizeRemoverBuilder =
										_bigCommerceClient.getRequestBuilder().setMethod("DELETE");
								_bigCommerceClient.getResponse(removalUrl, sizeRemoverBuilder);
							}
						}
					}

					if (sizeOptions.size() > 0) {
						for (String sizeOption : sizeOptions) {
							// create a custom field
							final String customFieldUrl =
									String.format("/api/v2/products/%d/custom_fields", product.getId());
							RequestBuilder customFieldCreatorBuilder =
									_bigCommerceClient.getRequestBuilder().setMethod("POST");
							CustomField customField = new CustomField().setName("Bedding Size").setText(sizeOption);
							customFieldCreatorBuilder.setBody(customField.toJson());
							_bigCommerceClient.getResponse(customFieldUrl, customFieldCreatorBuilder);
						}
					}
				}
			}
		}
	}

	@Ignore
	@Test
	public void markItemTypes() throws TimeoutException, IOException {
		final String key = "Item Type";
		final String matcher = "bedskirt";
		final String marker = "Bedskirts";
		List<Product> products = _bigCommerceClient.findAll(Product.class);
//		List<Product> products = new ArrayList<Product>();
//		products.add(_bigCommerceClient.findById(Product.class, BigInteger.valueOf(11600)));

		for (Product product : products) {

			if (product.getName().toLowerCase().contains(matcher)) {

				List<CustomField> customFields =
						_bigCommerceClient.resources(CustomField.class, product.getCustomFields());

				boolean hasItemType = false;

				if (customFields != null && customFields.size() > 0) {
					for (CustomField customField : customFields) {
						if (customField.getName().equalsIgnoreCase(key)) {
							hasItemType = true;
							break;
						}
					}
				}

				if (!hasItemType) {
					final String customFieldUrl =
							String.format("/api/v2/products/%d/custom_fields", product.getId());
					RequestBuilder customFieldCreatorBuilder =
							_bigCommerceClient.getRequestBuilder().setMethod("POST");
					CustomField customField = new CustomField().setName(key).setText(marker);
					customFieldCreatorBuilder.setBody(customField.toJson());
					_bigCommerceClient.getResponse(customFieldUrl, customFieldCreatorBuilder);
				}
			}
		}
	}

	@Test
	public void markStyles() throws TimeoutException, IOException {
		final String key = "Style";
		final String matcher = "tropical";
		final String marker = "Tropical";
		List<Product> products = _bigCommerceClient.findAll(Product.class);
//		List<Product> products = new ArrayList<Product>();
//		products.add(_bigCommerceClient.findById(Product.class, BigInteger.valueOf(11600)));

		for (Product product : products) {

			if (product.getName().toLowerCase().contains(matcher)) {

				List<CustomField> customFields =
						_bigCommerceClient.resources(CustomField.class, product.getCustomFields());

				boolean hasStyle = false;

				if (customFields != null && customFields.size() > 0) {
					for (CustomField customField : customFields) {
						if (customField.getName().equalsIgnoreCase(key)
								&& customField.getText().equalsIgnoreCase(marker)) {
							hasStyle = true;
							break;
						}
					}
				}

				if (!hasStyle) {
					final String customFieldUrl =
							String.format("/api/v2/products/%d/custom_fields", product.getId());
					RequestBuilder customFieldCreatorBuilder =
							_bigCommerceClient.getRequestBuilder().setMethod("POST");
					CustomField customField = new CustomField().setName(key).setText(marker);
					customFieldCreatorBuilder.setBody(customField.toJson());
					_bigCommerceClient.getResponse(customFieldUrl, customFieldCreatorBuilder);
				}
			}
		}
	}
}
