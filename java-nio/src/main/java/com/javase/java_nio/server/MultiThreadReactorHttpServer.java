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

import com.javase.java_nio.Processor;

/**
 * 多线程Reactor模式：
 * 	1） 主线程负责接受请求，即accept</p>
 *  2）主线程分发请求至线程池处理请求</p>
 * @author kefan
 *
 */
public class MultiThreadReactorHttpServer {

	private static Logger logger = LoggerFactory
			.getLogger(MultiThreadReactorHttpServer.class);

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
					int clientId = 0;
					iterator.remove();
					if (key.isAcceptable()) {
						ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key
								.channel();
						SocketChannel socketChannel = acceptServerSocketChannel
								.accept();
						socketChannel.configureBlocking(false);
						clientId = number++;
						logger.debug("客户端[" + clientId + "]连接上来，开始处理请求:");
						SelectionKey selectionKey = socketChannel.register(
								selector, SelectionKey.OP_READ);

						selectionKey.attach(new Processor());

					} else if (key.isReadable()) {
						Processor processor = (Processor) key.attachment();
						processor.process(key);
					}
					keys.remove(key);
				}
			}
		} catch (Exception ex) {
			logger.error("Read message failed", ex);
		}

	}

}
