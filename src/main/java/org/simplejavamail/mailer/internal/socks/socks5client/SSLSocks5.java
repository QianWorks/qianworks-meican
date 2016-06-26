

package org.simplejavamail.mailer.internal.socks.socks5client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SSLSocks5 extends Socks5 {

	private final SSLConfiguration configuration;

	public SSLSocks5(final InetSocketAddress address, final SSLConfiguration configuration) {
		super(address);
		this.configuration = configuration;
	}

	private SSLSocks5(final InetAddress address, final int port, final SSLConfiguration configuration) {
		super(address, port);
		this.configuration = configuration;
	}

	@Override
	public Socket createProxySocket(final InetAddress address, final int port)
			throws IOException {
		return configuration.getSSLSocketFactory().createSocket(address, port);
	}

	@Override
	public Socket createProxySocket()
			throws IOException {
		return configuration.getSSLSocketFactory().createSocket();
	}

	@Override
	public Socks5 copy() {
		return copyWithoutChainProxy().setChainProxy(getChainProxy());
	}

	private Socks5 copyWithoutChainProxy() {
		final SSLSocks5 socks5 = new SSLSocks5(getInetAddress(), getPort(), configuration);
		socks5.setAlwaysResolveAddressLocally(isAlwaysResolveAddressLocally()).setCredentials(getCredentials())
				.setInetAddress(getInetAddress()).setPort(getPort()).setSocksAuthenticationHelper(getSocksAuthenticationHelper());
		return socks5;
	}

}
