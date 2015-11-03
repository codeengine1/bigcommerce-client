example usage:

``` java

final String storeId = "8jlg16m";
final String userName = "apiuser";
final String secret = "yoursecret";

Settings credentials = new Settings(storeId, userName, secret);
BigCommerceClient bigCommerceClient = new BigCommerceClient(credentials);

```