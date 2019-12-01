package com.javase.java_nio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolHttpServer {

	private static Logger logger = LoggerFactory
			.getLogger(ThreadPoolHttpServer.class);

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(10);

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {

			int number = 1;

			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(2345));

			logger.debug("开始接受来自客户端的请求...");

			while (true) {
				final Socket socket = serverSocket.accept();
				final int clientId = number++;
				logger.debug("客户端[" + clientId + "]连接上来，开始处理请求:");

				threadPool.submit(new Runnable() {

					@Override
					public void run() {
						try {
							BufferedReader bufferedReader = new BufferedReader(
									new InputStreamReader(socket
											.getInputStream()));

							while (true) {
								String receiveMessage = bufferedReader
										.readLine();
								if (receiveMessage != null
										&& !receiveMessage.equals("")) {
									logger.info("接受到来自客户端的消息 {}",
											receiveMessage + "\n");
								}

								if (receiveMessage.equals("bye")) {
									logger.warn("再见客户端[" + clientId + "]!");
									socket.close();
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});

			}
		} catch (IOException ex) {
			IOUtils.closeQuietly(serverSocket);
			logger.error("Read message failed", ex);
		}

	}

}
