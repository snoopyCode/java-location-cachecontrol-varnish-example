# java-location-cachecontrol-varnish-example
Example of caching geo locations with a Java REST service and Varnish.

Run a docker container with the java app.

```
  docker run -d -it -p 8080:8080 -v $PWD/autodeploy:/opt/payara41/glassfish/domains/payaradomain/autodeploy --name payara payara:4.1.152.1.oraclejdk8v2
```
