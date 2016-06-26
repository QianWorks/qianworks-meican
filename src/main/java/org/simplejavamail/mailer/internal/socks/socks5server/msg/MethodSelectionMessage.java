package org.simplejavamail.mailer.internal.socks.socks5server.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.simplejavamail.mailer.internal.socks.socks5server.msg.StreamUtil.checkEnd;

public final class MethodSelectionMessage {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodSelectionMessage.class);

	public static int readVersion(final InputStream inputStream)
			throws IOException {
		LOGGER.trace("MethodSelectionMessage.read");
		final int version = checkEnd(inputStream.read());
		final int methodNum = checkEnd(inputStream.read());
		for (int i = 0; i < methodNum; i++) {
			checkEnd(inputStream.read()); // read method byte
		}
		return version;
	}
}
