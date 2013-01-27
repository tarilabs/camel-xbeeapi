package net.tarilabs.camelxbeeapi;

import javax.naming.Context;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.zigbee.ZNetRxBaseResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxIoSampleResponse;

/**
 * TODO this test is good for general purposed, but not very representative, in fact here what is needed is a mock of the Endpoint in replacement of the physical XBee.
 * 
 * @author tari
 *
 */
public class XBeeAPITest extends CamelTestSupport {

    @Test
    public void testdummy() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.setAssertPeriod(5000);
        mock.expectedMessageCount(10);
        
        mock.assertIsSatisfied();
    }
    
    @Test
    public void testFiltering() throws Exception {
    	MockEndpoint mockOK = getMockEndpoint("mock:filterOK");
    	mockOK.setAssertPeriod(5000);
        mockOK.expectedMinimumMessageCount(1);
        MockEndpoint mockOtherwise = getMockEndpoint("mock:filterOtherwise");
        mockOtherwise.setAssertPeriod(5000);
        mockOtherwise.expectedMinimumMessageCount(1);
        
        ZNetRxIoSampleResponse r = new ZNetRxIoSampleResponse();
		r.setRemoteAddress64(new XBeeAddress64(0x00,0x13,0xa2,0x00,0x40,0x68,0xe0,0x00));
		r.setAnalog1(30);
        template.sendBody("direct:filterTest", r);
        
        ZNetRxBaseResponse r2 = new ZNetRxBaseResponse();
		template.sendBody("direct:filterTest", r2);
        
		mockOK.assertIsSatisfied(10000);
		mockOtherwise.assertIsSatisfied(10000);
    }
    
    @Override
	protected Context createJndiContext() throws Exception {
		Context ctx = super.createJndiContext();
		ZNetRxIoSampleResponse r = new ZNetRxIoSampleResponse();
		r.setRemoteAddress64(new XBeeAddress64(0x00,0x13,0xa2,0x00,0x40,0x68,0xe0,0x00));
		r.setAnalog1(30);
		SimpleDataSet sds = new SimpleDataSet(10);
		sds.setDefaultBody(r);
		ctx.bind("dummyXbeeResponseGenerator", sds);
		return ctx;
	}

	@Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:filterTest")
                	.choice()
                		.when(body().isInstanceOf(ZNetRxIoSampleResponse.class))
                			.log("is expected packet").to("mock:filterOK")
                		.otherwise()
                			.log("not expected packet").to("mock:filterOtherwise")
                ;
                
                from("dataset:dummyXbeeResponseGenerator")
                	.log("route log ${body.getAnalog1()}")
                	.to("mock:result");
                
            }
        };
    }
}