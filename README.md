# java-location-cachecontrol-varnish-example
Example of caching geo locations with a Java REST service and Varnish.

Run a docker container with the java app first.

```
  docker run -d -it -p 8080:8080 -v $PWD/autodeploy:/opt/payara41/glassfish/domains/payaradomain/autodeploy --name payara payara:4.1.152.1.oraclejdk8
```

A .war file of the app must be placed in ./autodeploy. Next, run Varnish with varnish.vcl in ./data.

```
docker run -d --link payara:payara -p 8181:80 -v $PWD:/data --env 'VCL_CONFIG=/data/varnish.vcl' million12/varnish
```

Varnish is configured as reverse proxy. You can now access the sample.

```
http://<your-docker-ip>:8181/samplecache/cachecontrol?latitude=40.7142700&longitude=-74.0059700
```

The Varnish configuration does not cache exactly because caching geo locations exactly makes less sense. Therefore, the cache key cuts latitude and longitude after two digits after the point. The call is modified by Varnish to the app server.

```
http://<your-docker-ip>:8181/samplecache/cachecontrol?latitude=40.71&longitude=-74.00
```
