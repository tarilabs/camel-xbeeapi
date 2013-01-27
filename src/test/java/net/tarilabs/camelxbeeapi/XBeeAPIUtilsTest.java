package net.tarilabs.camelxbeeapi;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rapplogic.xbee.api.XBeeAddress64;
import static net.tarilabs.camelxbeeapi.XBeeAPIUtils.addressAsMacFormat;

public class XBeeAPIUtilsTest {

	@Test
	public void testAddressAsMacFormat() {
		XBeeAddress64 addr = new XBeeAddress64(0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x10);
		assertEquals("09:0A:0B:0C:0D:0E:0F:10", addressAsMacFormat(addr));
		addr = new XBeeAddress64(0x11,0x22,0xAA,0xCD,0xEF,0x47,0x99,0x00);
		assertEquals("11:22:AA:CD:EF:47:99:00", addressAsMacFormat(addr));
	}

}
