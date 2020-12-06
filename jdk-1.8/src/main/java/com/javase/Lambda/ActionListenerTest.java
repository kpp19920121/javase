package com.javase.Lambda;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import org.junit.Test;

public class ActionListenerTest {

	Button button = new Button();

	@Test
	public void testInnerClass() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("未使用lambdab表达式以前这样调用!");
			}
		});

	}

	@Test
	public void testLambdab() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("未使用lambdab表达式以前这样调用!");
			}
		});

		button.addActionListener((ActionEvent e) -> {
			System.out.print("未使用lambdab表达式以前这样调用!");
		});

	}

	@Test
	public void demo1() {
		
		/**
		 * 使用内部类进行演示
		 */
		Function<Integer, String> f = new Function<Integer, String>() {
			@Override
			public String apply(Integer t) {
				System.out.println(t);
				return null;
			}
		};
		f.apply(10);
		
		/**
		 * 使用lambda表达式进行演示
		 */
		Function<Integer, String> f2 = (Integer x) -> {System.out.println(x); return  String.valueOf(x);};		
		f2.apply(100);

		
		/**
		 * 使用jdk1.8的方法进行演示：
		 */
		Function<Integer, String> f3 = String::valueOf;
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("未使用lambda表达式以前");
			}
		}).start();
		;

		new Thread(() -> {
			System.out.println("使用lambda表达式以后");
		}).start();

	}

}
