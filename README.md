camel-xbeeapi
=============

Camel component serving as a wrapper for the XBee API.

GOALS:
I need a wrapper for the XBee API (http://code.google.com/p/xbee-api/) for Apache Camel for use in another project.

NON-GOALS:
Manage all the JVM native library dependencies required by the running host environment. ie: you need to setup the RXTX native library yourself in your JVM installation. 

At present this implement only on receive from XBee to the Endpoint, and via the Consumer on the Camel routes.

You will need to provide manually to your running JVM the RXTX native library, the project only imports the Java Library via Maven.