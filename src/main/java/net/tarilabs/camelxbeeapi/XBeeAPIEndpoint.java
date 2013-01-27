package net.tarilabs.camelxbeeapi;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.XBee;

/**
 * Represents a XBeeAPI endpoint.
 * 
 * It's a singleton.
 * URI parameters:
 * tty: provide the tty address (system dependent)
 * baud: transmission baud on the tty address
 * 
 * TODO use URI path for describing Xbee addess mesh on transmitting, or find another mechanism.
 * 
 */
public class XBeeAPIEndpoint extends DefaultEndpoint {
    private static final transient Logger log = LoggerFactory.getLogger(XBeeAPIEndpoint.class);
	private XBee xbee;
	private String tty;
	private int baud;
	
    public XBeeAPIEndpoint() {
    }

    public XBeeAPIEndpoint(String uri, XBeeAPIComponent component) {
        super(uri, component);
    }

    /**
     * This is deprecated and came from the Maven archetype generation of Camel 2.10
     */
    @SuppressWarnings("deprecation")
	public XBeeAPIEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new XBeeAPIProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new XBeeAPIConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }

	@Override
	protected void doStart() throws Exception {
		log.info("do start");
		super.doStart();
		xbee = new XBee();
		try {
			xbee.open(tty, baud);
		} catch (Exception e) {
			throw new Exception("Unable to open() Xbee", e);
		}
		log.info("Finished do start");
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		if (xbee.isConnected()) {
			xbee.close();
		}
	}

	public XBee getXbee() {
		return xbee;
	}

	public String getTty() {
		return tty;
	}

	public void setTty(String tty) {
		this.tty = tty;
	}

	public int getBaud() {
		return baud;
	}

	public void setBaud(int baud) {
		this.baud = baud;
	}
    
}
