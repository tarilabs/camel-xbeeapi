package net.tarilabs.camelxbeeapi;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the component that manages {@link XBeeAPIEndpoint}.
 */
public class XBeeAPIComponent extends DefaultComponent {
    private static final transient Logger log = LoggerFactory.getLogger(XBeeAPIComponent.class);
    
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        log.info("component start creating endpoint");
    	Endpoint endpoint = new XBeeAPIEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
