package net.tarilabs.camelxbeeapi.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import com.rapplogic.xbee.api.zigbee.ZNetRxIoSampleResponse;

/**
 * This has been tested with a physical XBee module and XBee mesh (remote Router sending to Coordinator dongled to tty USB)
 * @author tari
 *
 */
public class Demo {

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				
				// TODO currently seda: is not asynch model but planned in Camel 3.0, this will fully leverage the async model in the XBeeAPIConsumer.
				from("xbeeapi://?baud=9600&tty=/dev/tty.usbserial-A4004CwJ")
					.to("seda:xbeeAsyncBuffer");
				
				// the delay is introduced in this route to simulate a long processing route, but thank to the seda: this route is actually decoupled from route which has the Xbee, above.
				from("seda:xbeeAsyncBuffer")
					.delay(3000)
					.choice()
                		.when(body().isInstanceOf(ZNetRxIoSampleResponse.class))
                			.log("route log ${body.getAnalog1()}")
                		.otherwise()
                			.log("I don't know what to do with this packet from the XBee mesh: ${body}");
					
			}

		});
		context.start();
		System.out.println("DEMO context started, type anything and hit return to stop.");
	    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String s = bufferRead.readLine();
	    System.out.println("DEMO end "+s);
	    context.stop();
	}
}
