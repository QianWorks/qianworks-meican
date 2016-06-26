package org.simplejavamail.mailer.internal.socks.socks5server.msg;

final class AddressType {

	public static final int IPV4 = 0x01;

	public static final int DOMAIN_NAME = 0x03;

	private AddressType() {
	}

	public static boolean isSupport(final int type) {
		return type == IPV4 || type == DOMAIN_NAME;
	}
}