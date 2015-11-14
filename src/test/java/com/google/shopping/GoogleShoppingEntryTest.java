package com.google.shopping;

import com.bigcommerce.api.BigCommerceClient;
import com.bigcommerce.api.BigCommerceClientTest;
import com.bigcommerce.api.BigCommerceEntityMapper;
import com.bigcommerce.api.Settings;
import com.bigcommerce.api.brand.Brand;
import com.bigcommerce.api.product.*;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class GoogleShoppingEntryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceClientTest.class);
	private final BigCommerceClient _bigCommerceClient;

	public GoogleShoppingEntryTest() {
		Config config = ConfigFactory.load("bigcommerce.conf");
		Settings credentials = new Settings(config.getString("bigcommerce.storeid"),
				config.getString("bigcommerce.api.baseUrl"),
				config.getString("bigcommerce.api.user"),
				config.getString("bigcommerce.api.secret"));
		_bigCommerceClient = new BigCommerceClient(credentials);
	}

	@Test
	public void createFeed() throws TimeoutException, IOException {

		List<GoogleShoppingMeta> googleShoppingMetas = getGoogleShoppingMetaData();
		FileOutputStream productFeedOutputStream = new FileOutputStream("google-shopping-feed.xml");

		Writer productFeedWriter = new BufferedWriter(new OutputStreamWriter(productFeedOutputStream, "UTF-8"));
		productFeedWriter.write("<?xml version='1.0' encoding='UTF-8'?>\n");
		productFeedWriter.write("<feed xmlns='http://www.w3.org/2005/Atom' xmlns:g='http://base.google.com/ns/1.0' xmlns:c='http://base.google.com/cns/1.0'>\n");

		FileOutputStream upcategorizedProducts = new FileOutputStream("google-shopping-uncategorized.txt");
		Writer uncategorizedWriter = new BufferedWriter(new OutputStreamWriter(upcategorizedProducts, "UTF-8"));

		List<Product> products = _bigCommerceClient.findAll(Product.class);
		Set<String> upcs = new HashSet<String>();

		for (Product product : products) {

			if (!product.getAvailability().equals("available")) {
				continue;
			}

			if (product.getUpc() == null) {
				continue;
			}

			Brand brand = _bigCommerceClient.resource(Brand.class, product.getBrand());

			if (brand == null) {
				continue;
			}

			GoogleShoppingMeta googleShoppingMeta =
					GoogleShoppingMeta.getMatchingMeta(googleShoppingMetas, product.getName());

			if (googleShoppingMeta == null) {
				uncategorizedWriter.write(brand.getName() + " " + product.getName() + "\n");
				continue;
			}

			final String googleCategory = googleShoppingMeta.getGoogleShoppingTaxonomyCategory();
			final String customLabel = googleShoppingMeta.getCustomLabel0();

			// get images
			PrimaryImage primaryImage = product.getPrimaryImage();

			GoogleShoppingEntry entry = new GoogleShoppingEntry();
			entry.setAgeGroup("");
			entry.setAvailability("in stock");
			entry.setBrand(brand.getName());
			entry.setCondition("New");
			entry.setDescription(product.getDescription().replaceAll("\\<.*?\\>", "").replaceAll("nbsp;", ""));
			entry.setGender("");
			entry.setGoogleProductCategory(googleCategory);
			entry.setIdentifierExists(true);
			entry.setImageLink(primaryImage.getZoomUrl());
			entry.setLink("https://www.ajmoss.com" + product.getCustomUrl());
			entry.setProductType(customLabel);
			entry.setCustomLabel0(customLabel);
			entry.setMpn(product.getSku());
			entry.setPrice(product.getPrice() + " USD");
			entry.setGtin(product.getUpc());
			entry.setId(product.getUpc());
			entry.setTitle(brand.getName() + " " + product.getName());

			// get custom fields
			List<CustomField> customFields =
					_bigCommerceClient.resources(CustomField.class, product.getCustomFields());

			if (customFields != null && customFields.size() > 0) {
				for (CustomField customField : customFields) {
					if (customField.getName().equals("Color")) {
						entry.setColor(customField.getText());
						break;
					}
				}
			}

			// handle product rules
			List<ProductRule> productRules = _bigCommerceClient.resources(ProductRule.class, product.getRules());

			if (productRules != null && productRules.size() >= 0) {
				handleProductRules(product, entry, productRules, upcs);
			} else {
				upcs.add(product.getUpc());
			}

			productFeedWriter.write(entry.toXml() + "\n");
		}

		productFeedWriter.write("</feed>");
		productFeedWriter.close();
		productFeedOutputStream.close();
		uncategorizedWriter.close();
		upcategorizedProducts.close();
	}

	/**
	 *
	 * @throws TimeoutException
	 */
	private void handleProductRules(Product product,
									GoogleShoppingEntry entry,
									List<ProductRule> productRules,
									Set<String> upcs) throws TimeoutException, IOException {
		for (ProductRule productRule : productRules) {
			if (productRule.getSkuConditions() == null || productRule.getSkuConditions().size() == 0) {
				continue;
			}

			if (productRule.getPriceAdjuster().getAdjusterValue().intValue() != product.getPrice().intValue()) {
				return;
			}

			for (Condition skuCondition : productRule.getSkuConditions()) {

				if (skuCondition.getSkuId() == null) {
					continue;
				}

				RequestBuilder skusRequestBuilder = _bigCommerceClient.getRequestBuilder().setMethod("GET");
				final String skusUrl = String.format("/api/v2/products/%d/skus", product.getId());
				Response skusResponse = _bigCommerceClient.getResponse(skusUrl, skusRequestBuilder);
				List<Sku> skus = BigCommerceEntityMapper.readValues(skusResponse.getResponseBody(), Sku.class);
				Sku sku = null;

				if (skus != null) {
					for (Sku skuTest : skus) {
						if (skuTest.getId().equals(skuCondition.getSkuId())) {
							sku = skuTest;
						}
					}
				}

				if (sku == null) {
					LOGGER.error("No matching SKU!!");
					continue;
				}

				// don't duplicate upcs
				if (upcs.contains(sku.getUpc())) {
					continue;
				}

				upcs.add(sku.getUpc());
				entry.setMpn(sku.getSku());
				entry.setPrice(productRule.getPriceAdjuster().getAdjusterValue() + " USD");
				entry.setGtin(sku.getUpc());
				entry.setId(sku.getUpc());

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
							if (sku.getOptionValueId().equals(optionValue.getId())) {
								//entry.setSize(optionValue.getValue());
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @return googleShoppingMetaData
	 */
	private List<GoogleShoppingMeta> getGoogleShoppingMetaData() {
		List<GoogleShoppingMeta> metas = new ArrayList<>();

		GoogleShoppingMeta meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Quilts & Comforters")
				.setNameMatch("comforter set")
				.setCustomLabel0("comforter sets");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Quilts & Comforters")
				.setNameMatch("comforter set")
				.setCustomLabel0("comforters");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Quilts & Comforters")
				.setNameMatch("quilt")
				.setCustomLabel0("quilts");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Quilts & Comforters")
				.setNameMatch("bedspread")
				.setCustomLabel0("bedspreads");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Duvet Covers")
				.setNameMatch("duvet")
				.setCustomLabel0("duvets");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Decor > Throw Pillows")
				.setNameMatch("pillow")
				.setCustomLabel0("pillows");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Bedskirts")
				.setNameMatch("bedskirts")
				.setCustomLabel0("bedskirts");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Decor > Window Treatments > Window Valances & Cornices")
				.setNameMatch("valance")
				.setCustomLabel0("valances");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Decor > Window Treatments > Curtains & Drapes")
				.setNameMatch("drape")
				.setCustomLabel0("drapes");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Decor > Window Treatments > Curtains & Drapes")
				.setNameMatch("panel")
				.setCustomLabel0("drapes");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Bed Sheets")
				.setNameMatch("sheet")
				.setCustomLabel0("sheets");
		metas.add(meta);

		meta = new GoogleShoppingMeta();
		meta.setGoogleShoppingTaxonomyCategory("Home & Garden > Linens & Bedding > Bedding > Pillowcases & Shams")
				.setNameMatch("sham")
				.setCustomLabel0("shams");
		metas.add(meta);

		return metas;
	}
}
