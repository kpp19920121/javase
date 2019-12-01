package com.javase.java_nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 多线程Reactor模式，主线程接受请求后，交给线程池处理请求
 * @author kefan
 *
 */
public class Processor {

	private static Logger logger = LoggerFactory.getLogger(Processor.class);

	private static final ExecutorService executorService = Executors
			.newFixedThreadPool(16);

	public void  process(final SelectionKey selectionKey) {
		try {

			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						SocketChannel socketChannel = (SocketChannel) selectionKey
								.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int count = socketChannel.read(buffer);

						//
						// if (count <= 0) {
						// socketChannel.close();
						// selectionKey.cancel();
						// logger.info("Received invalide data, close the connection");
						// }

						if (count <= 0) {
							return  ;
						}
						String message = new String(buffer.array());

						logger.info("接受到来自客户的消息 {}", message + "\n");

						if (message.equals("bye")) {
							socketChannel.close();
							selectionKey.cancel();
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

		}

	}

}
