package com.javase.Lambda;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LambdaDemo {

	
	
	
	/**
	 * 使用函数式编程代替匿名内部类
	 */
	@Test
	public void demo01(){
		
		
		/*
		 * 1，使用匿名内部类实现
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("匿名内部类实现");
			}
		}).start();
		
		
		
		/*
		 * 使用lambda表达式实现
		 */
		new Thread(() -> {System.out.println("lambda表达式实现");}).start();
	}
	
	
	/**
	 * 优化循环
	 */
	
	@Test
	public void demo02(){
		
		List<String> dataList=new ArrayList<String>();
		dataList.add("1");
		dataList.add("2");
		dataList.add("3");
		
		//传统的循环遍历数组
		
		for(String tempString:dataList){
			System.out.println("增强for遍历:"+tempString);
		}
		
		//使用lambda表达式格式遍历
		dataList.forEach((tempString)->{System.out.println("lambda表达式遍历集合:"+tempString);});
	}
	
	
	/**
	 * 支持函数式编程
	 */
	/*
	 * @Test public void demo03(){
	 * 
	 * List<String> dataList=new ArrayList<String>(); dataList.add("1");
	 * dataList.add("2"); dataList.add("3");
	 * 
	 * filter(dataList, (str)->str.startsWith("J"));
	 * 
	 * }
	 */
}
