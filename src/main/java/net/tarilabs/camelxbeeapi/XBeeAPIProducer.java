package net.tarilabs.camelxbeeapi;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The XBeeAPI producer.
 * 
 * TODO implement transmitting to XBee mesh on the producer.
 * 
 */
public class XBeeAPIProducer extends DefaultProducer {
    private static final transient Logger log = LoggerFactory.getLogger(XBeeAPIProducer.class);
    private XBeeAPIEndpoint endpoint;

    public XBeeAPIProducer(XBeeAPIEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	log.info("Sorry not implemented yet. "+endpoint.toString()+" "+exchange.getIn().getBody());
        throw new Exception("not implemented yet, sorry.");
    }

}
