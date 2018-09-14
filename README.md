# saml20-clj

this is a saml 2.0 clojure library for sso.

## installation

need to download http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html or similar

prepare JKS

```
cat cert_file.crt idp_sso.crt > tmp.pem
openssl pkcs12 -export -inkey private_key.pem -in tmp.pem -name demo -out keystore.p12
rm tmp.pem
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype pkcs12 -destkeystore keystore.jks
keytool -list  -keystore keystore.jks
```

other usefull
```
keytool -genkey -alias demo -keyalg RSA -keystore keystore.jks -storepass password
keytool -list -v -keystore keystore.jks -storepass password
keytool -list -keystore keystore.jks -storepass password
keytool -certreq -alias mydomain -keystore keystore.jks -storepass password -file mydomain.csr
keytool -import  -keystore keystore.jks -file mydomain.csr
```

add ```[saml20-clj "0.1.2"]``` to your project dependencies.

## usage

see dev source files to see how the library works.

## resources

http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-binding-simplesign-cd-04.html vs https://en.wikipedia.org/wiki/SAML_2.0 ?
http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0.html
http://stackoverflow.com/questions/8150096/construct-a-signed-saml2-logout-request
http://stackoverflow.com/questions/25598788/signature-as-parameter-in-the-authentication-request
https://svn.apache.org/repos/asf/santuario/xml-security-java/trunk/samples/org/apache/xml/security/samples/signature/CreateSignature.java
http://stackoverflow.com/questions/2052251/is-there-an-easier-way-to-sign-an-xml-document-in-java
http://www.di-mgt.com.au/xmldsig2.html
https://apache.googlesource.com/santuario-java/+/eab39986c1e3a4586e921960546727b6903f9273/src/main/java/org/apache/xml/security/encryption/XMLCipher.java
https://git.shibboleth.net/view/?p=java-opensaml2.git;a=log;h=refs/tags/2.6.4
https://worace.works/2016/06/05/rsa-cryptography-in-clojure/
https://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption
https://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters/6481658#6481658

## License

Copyright © 2013 VLACS <jdoane@vlacs.org>

Distributed under the Eclipse Public License, the same as Clojure.

