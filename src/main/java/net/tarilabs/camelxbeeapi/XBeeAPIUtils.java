package net.tarilabs.camelxbeeapi;

import com.rapplogic.xbee.api.XBeeAddress;

public class XBeeAPIUtils {
	public static String addressAsMacFormat(XBeeAddress address) {
		StringBuilder sb = new StringBuilder();
		for (int i : address.getAddress()) {
			if ( sb.length()>0 ) {
				sb.append(":");
			}
			if ( i < 16 ) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(i).toUpperCase());
		}
		return sb.toString();
	}
}
