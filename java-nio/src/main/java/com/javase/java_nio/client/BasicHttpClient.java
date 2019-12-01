package com.javase.java_nio.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javase.java_nio.server.BasicHttpServer;

public class BasicHttpClient {

	private static Logger logger = LoggerFactory
			.getLogger(BasicHttpClient.class);

	private static int threadNum = 1;

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(threadNum);

	public static void main(String[] args) throws IOException {

		for (int i = 0, len = threadNum; i < len; i++) {
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						sendMessage();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	public static void sendMessage() throws Exception {

		Socket socket = null;
		Scanner keyboard = null;
		BufferedWriter bw = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("127.0.0.1", 2345));
			keyboard = new Scanner(System.in);
			bw = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			while (true) {
				logger.debug("请输入内容");
				String message = keyboard.nextLine();
				if (message != null && !message.equals("")) {
					logger.debug("您已输入内容" + message + ",开始发送内容");
					bw.write(message + "\n");
					bw.flush();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.close();
			}

			if (keyboard != null) {
				keyboard.close();
			}

			if (socket != null) {
				socket.close();
			}
		}
	}

}
