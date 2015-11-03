package com.bigcommerce.api;

import com.bigcommerce.api.brand.Brand;
import com.bigcommerce.api.category.Category;
import com.bigcommerce.api.product.Product;
import com.bigcommerce.api.product.ProductRule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
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


	@Test
	public void updateProductTitle() throws TimeoutException {
		List<Product> products = _bigCommerceClient.findAll(Product.class);
//		List<Product> products = _bigCommerceClient.find(Product.class, "sku", "2A0-004O0-6406/200P");

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
}
