package com.bigcommerce.api;

import com.bigcommerce.api.category.Category;
import com.bigcommerce.api.product.Product;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
				config.getString("bigcommerce.api.user"),
				config.getString("bigcommerce.api.secret"));
		_bigCommerceClient = new BigCommerceClient(credentials);
	}

	@Test
	public void testFindBySku() throws TimeoutException {
		List<Product> products = _bigCommerceClient.find(Product.class, "sku", "2A0-004O0-6406/200P");
		LOGGER.debug(products.toString());
	}

	@Test
	public void testFindById() throws TimeoutException {
		Category category = _bigCommerceClient.findById(Category.class, 443);
		LOGGER.debug(category.toString());
	}
}
