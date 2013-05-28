package net.tarilabs.camelxbeeapi;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.util.AsyncProcessorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBeeResponse;

/**
 * The XBeeAPI consumer.
 */
public class XBeeAPIConsumer extends DefaultConsumer implements PacketListener {
    
	private static final transient Logger log = LoggerFactory.getLogger(XBeeAPIConsumer.class);
	private final XBeeAPIEndpoint endpoint;
    

    public XBeeAPIConsumer(XBeeAPIEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		endpoint.getXbee().addPacketListener(this);
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		endpoint.getXbee().removePacketListener(this);
	}

	@Override
	public void processResponse(XBeeResponse response) {
		final Exchange exchange = endpoint.createExchange();
		exchange.getIn().setBody(response);
		log.debug("about to async process");
		AsyncProcessorHelper.process(getAsyncProcessor(), exchange, new AsyncCallback() {		
			@Override
			public void done(boolean arg0) {
				log.debug("callback done.");
			}
		});
		log.debug("processResponse finished after having sent asynch");
	}
}
