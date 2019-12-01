package com.javase.java_nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorHttpServer {

	private static Logger logger = LoggerFactory
			.getLogger(ReactorHttpServer.class);

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(10);

	public static void main(String[] args) throws IOException {
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel
					.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.bind(new InetSocketAddress(2345));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			logger.debug("开始接受来自客户端的请求...");
			int number = 1;
			while (selector.select() > 0) {
				

				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					int clientId=0;
					iterator.remove();
					if (key.isAcceptable()) {
						ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key
								.channel();
						SocketChannel socketChannel = acceptServerSocketChannel
								.accept();
						socketChannel.configureBlocking(false);
						
						clientId = number++;
						logger.debug("客户端[" + clientId + "]连接上来，开始处理请求:");
						socketChannel.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						SocketChannel socketChannel = (SocketChannel) key
								.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int count = socketChannel.read(buffer);
						if (count <= 0) {
							socketChannel.close();
							key.cancel();
							logger.info("Received invalide data, close the connection");
							continue;
						}
						logger.info("接受到来自客户端[" + clientId + "]的消息 {}",
								new String(buffer.array()) + "\n");

					}
					keys.remove(key);
				}
			}
		} catch (Exception ex) {
			logger.error("Read message failed", ex);
		}

	}

}
