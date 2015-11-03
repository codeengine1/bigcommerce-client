example usage:

``` java

final String storeId = "8jlg16m";
final String baseUrl="https://mybigcommerce-site.com"
final String userName = "apiuser";
final String secret = "yoursecret";

Settings credentials = new Settings(storeId, baseUrl, userName, secret);
BigCommerceClient bigCommerceClient = new BigCommerceClient(credentials);

// get a category by the big commerce id
Category category = _bigCommerceClient.findById(Category.class, BigInteger.valueOf(443));
LOGGER.debug(category.toString());

// get a list of products by sku
List<Product> products = _bigCommerceClient.find(Product.class, "sku", "2A0-004O0-6406/200P");
LOGGER.debug(products.toString());

// get the brand resource object associated with a Product instance
Brand brand = _bigCommerceClient.resource(Brand.class, product.getBrand());

// now let's format a pageTitle from the brand name + product name
final String pageTitle = String.format("%s %s", brand.getName(), product.getName());
product.setPageTitle(pageTitle);

// now let's push that updated product to BigCommerce
_bigCommerceClient.update(product, Arrays.asList("pageTitle"));

```