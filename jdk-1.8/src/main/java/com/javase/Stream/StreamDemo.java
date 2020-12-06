package com.javase.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;


/**
 * stream操作包含中间操作和终端操作，中间操作
 * 
 * 如果Stream只有中间操作是不会执行的，当执行终端操作的时候才会执行中间操作，这种方式称为延迟加载或惰性求值
 * 
 * @author kefan
 *
 */
public class StreamDemo {

	@Test
	public void demo1() {

		List<String> arrayList = new ArrayList<String>();
		arrayList.add("1");
		arrayList.add("3");
		arrayList.add("3");
		System.out.println(arrayList);
		Stream<String> stream=arrayList.stream();
		stream.distinct().forEach((stringField -> {System.out.println(stringField);}));
	}

}
