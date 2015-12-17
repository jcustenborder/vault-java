This is a rudimentary java binding for [Hashicorp Vault](http://vaultproject.io/). At this point it's only been tested 
against development mode on a local workstation. It would be good to assume that exceptions will be thrown. Use in 
production is at your own risk. That being said pull requests are more than welcome. 

The next things I'm going to look at doing is removing the http dependency during testing and going to something that is
mocked with a light integration test layer.

## Maven dependency

```bash
mvn clean install
```

This is not currently deployed to maven central.

```xml
<dependency>
  <groupId>com.github.jcustenborder</groupId>
  <artifactId>vault.client</artifactId>
  <version>0.1-SNAPSHOT</version>
</dependency>
```

## Example

```java
VaultClient client =  new HttpVaultClientBuilder()
    .withToken("361ee26c-6684-d6d2-cbf5-f264f22b0f7b")
    .withUrl("http://127.0.0.1:8200/v1/")
    .withEnableCurlRequestLogging(true)
    .withEnableRequestLogging(true)
    .build();
LogicalClient logicalClient = client.getLogical();
final String path = "cubbyhole/foo"
ImmutableMap<String, Object> data = ImmutableMap.of("a", "a", "b", "b", "c", "c");
Secret writtenSecret = this.logicalClient.write(path, data);

Secret readSecret = this.logicalClient.read(path);
```

## Request logging

Take a look at the [Google Http Client Logging Configuration Documentation](https://developers.google.com/api-client-library/java/google-http-java-client/transport).
This should never be turned on in production but allows you to see what is happening during the requests. The benefits are logging like this:

```
CONFIG: -------------- REQUEST  --------------
PUT http://127.0.0.1:8200/v1/cubbyhole/com.github.jcustenborder.vault.http.HttpLogicalClientTest.cubbyHole
Accept-Encoding: gzip
User-Agent: VaultJava/0.0.0 (+github.com/jcustenborder/vault-java)
x-vault-token: 361ee26c-6684-d6d2-cbf5-f264f22b0f7b
Content-Type: application/json; charset=UTF-8
Content-Length: 47

Dec 17, 2015 12:44:20 PM com.google.api.client.http.HttpRequest execute
CONFIG: curl -v --compressed -X PUT -H 'Accept-Encoding: gzip' -H 'User-Agent: VaultJava/0.0.0 (+github.com/jcustenborder/vault-java)' -H 'x-vault-token: 361ee26c-6684-d6d2-cbf5-f264f22b0f7b' -H 'Content-Type: application/json; charset=UTF-8' -d '@-' -- 'http://127.0.0.1:8200/v1/cubbyhole/com.github.jcustenborder.vault.http.HttpLogicalClientTest.cubbyHole' << $$$
Dec 17, 2015 12:44:20 PM com.google.api.client.util.LoggingByteArrayOutputStream close
CONFIG: Total: 47 bytes
Dec 17, 2015 12:44:20 PM com.google.api.client.util.LoggingByteArrayOutputStream close
CONFIG: {"integer":0,"string":"asdfasd","boolean":true}
Dec 17, 2015 12:44:20 PM com.google.api.client.http.HttpResponse <init>
CONFIG: -------------- RESPONSE --------------
HTTP/1.1 204 No Content
Date: Thu, 17 Dec 2015 20:44:20 GMT
Content-Type: application/json
```
