package com.test.guava.collection.Immutable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;


/**
 * 构造不可变集合
 * @author kefan
 *
 */
public class CollectionDemo {

	@Test
	public void unmodifyList() {
		//使用jdk的api构造不可变集合
		List<String> unmodifyList= Collections.unmodifiableList(Arrays.asList("aaa","bbb"));
		System.out.println(unmodifyList);
		
		//是否guava构造不可变集合
		unmodifyList=ImmutableList.of("aaa","bbb");
		System.out.println(unmodifyList);
		
		unmodifyList=ImmutableList.<String>builder().addAll(unmodifyList).add("ccc").build();
		System.out.println(unmodifyList);
	}
	
	

	
	/**
	 * guava中的multiset是一个无序可重复的集合：
	 */
	@Test
	public void multisetTest() {
		
		
		Set<String> newSet=new HashSet<String>();

		for(int i=0,len=10;i<len;i++) {
			newSet.add(i+"");
		}
		
		System.out.println(newSet);
		
		 newSet=new TreeSet<String>();

		for(int i=0,len=10;i<len;i++) {
			newSet.add(i+"");
		}
		
		System.out.println(newSet);
		
		
		Multiset<String> multiSet=HashMultiset.create();
		multiSet.addAll(Lists.newArrayList("a","b","c","d","a","a"));
		System.out.println(multiSet.count("a"));
		System.out.println(multiSet);
		
		
		
		//顺序集合
		LinkedHashMultiset<String> linkedHashMultiset=LinkedHashMultiset.create();
		linkedHashMultiset.addAll(Lists.newArrayList("a","b","c","d","a","a"));
		System.out.println("linkedHashMultiset->"+linkedHashMultiset.count("a"));
		System.out.println("linkedHashMultiset->"+linkedHashMultiset);
		
		
	}
	
	
	/**
	 * guava中comparisonChain,根据多个条件排序
	 */
	@Test
	public void comparisonChainTest() {
		List<InnerUser> testUserList=new ArrayList<InnerUser>();
		Random idRandom=new Random();
		for(int i=0,len=10;i<len;i++) {
			testUserList.add(new InnerUser(
					1000, 
					Math.abs(idRandom.nextInt())+"_username", 
					Math.abs(idRandom.nextInt())+"_email", 
					Math.abs(idRandom.nextInt())));
		}
		
		System.out.println("排序前打印=======================");
		testUserList.forEach((tempInnerUser)->{
			System.out.println(tempInnerUser);
		});
		
		
		
		
		//集合排序。先使用id升序排列,如果多字段排序，则需要嵌套一堆is else
		Collections.sort(testUserList,(tempUser1,tempUser2)->{
			return tempUser1.getId()-tempUser2.getId();
		});
		
		System.out.println("使用lamda表达式排序后打印=======================");
		testUserList.forEach((tempInnerUser)->{
			System.out.println(tempInnerUser);
		});
		
		
		
		//集合排序。先使用id升序排列,如果多字段排序，则需要嵌套一堆is else
		System.out.println("使用guava ComparisonChain排序后打印=======================");
		Collections.sort(testUserList,(tempUser1,tempUser2)->{
			return ComparisonChain.start()
					.compare(tempUser1.getId(), tempUser2.getId())
					.compare(tempUser1.getOrderNo(), tempUser2.getOrderNo())
					.result();
		});
		
		testUserList.forEach((tempInnerUser)->{System.out.println(tempInnerUser);});
		
	}
	
	@Test
	public void orderingTest() {
		
		List<InnerUser> testUserList=new ArrayList<InnerUser>();
		Random idRandom=new Random();
		for(int i=0,len=10;i<len;i++) {
			testUserList.add(new InnerUser(
					1000, 
					Math.abs(idRandom.nextInt())+"_username", 
					Math.abs(idRandom.nextInt())+"_email", 
					Math.abs(idRandom.nextInt())));
		}
		
		
		testUserList.forEach((testUser)->{
			System.out.println("排序前:"+testUser);
		});
		
		Ordering<InnerUser>  innerUserOrder=Ordering.compound(Lists.<Comparator<InnerUser>>newArrayList(
				(o1, o2)->{return o1.getId()-o1.getId();},
				
				(o1, o2)->{return o1.getId()-o1.getId();}
				)) ;
		
		
		Collections.sort(testUserList, innerUserOrder);
		
		testUserList.forEach((testUser)->{
			System.out.println("排序后:"+testUser);
		});
		
		
		
	}
	
	
	
	/**
	 * 集合过滤：过滤后不能添加过滤不符合的数据
	 */
	@Test
	public void filterTest() {
		Random intRandom=new Random();
		
		List<Integer> intList=Lists.newArrayList();
		
		for(int i=0,len=100;i<len;i++) {
			intList.add(intRandom.nextInt(100));
		}
		
		intList.forEach((temp)->{
			System.out.println("未过滤以前"+temp);
		});
		
		
		Collection<Integer> fileredList= Collections2.filter(intList, new Predicate<Integer>() {
			@Override
			public boolean apply(Integer input) {
				return input<50?Boolean.TRUE:Boolean.FALSE;
			}
		});
		
		fileredList.forEach((temp)->{
			System.out.println("过滤以后的"+temp);
		});
		
		fileredList=Collections2.filter(intList, (input)->{
			return input<50?Boolean.TRUE:Boolean.FALSE;
		});
		
		fileredList.forEach((temp)->{
			System.out.println("过滤以后的"+temp);
		});
		
		
	}
	
	
	
	/**
	 * 集合转换transform
	 */
	@Test
	public void transformTest() {
		Random intRandom=new Random();
		List<Integer> intList=Lists.newArrayList();
		
		for(int i=0,len=100;i<len;i++) {
			intList.add(intRandom.nextInt(100));
		}
		
		Collection<String> newColection= Collections2.transform(intList, new Function<Integer, String>() {
			@Override
			public String apply(Integer input) {
				return input+"转换后的";
			}
		});
		newColection.forEach((tempString)->{
			System.out.println(tempString);
		});
		
		
		
		newColection=Collections2.transform(intList, (tempInt)->{
			return tempInt+"转换后的";
		});
		
		newColection.forEach((tempString)->{
			System.out.println(tempString);
		});
	}
	
	
	
	public static class InnerUser{
		private int id;
		private String username;
		private String email;
		
		
		private int orderNo;
		
		public InnerUser() {
			super();
		}
		
		
		public InnerUser(int id, String username, String email, int orderNo) {
			super();
			this.id = id;
			this.username = username;
			this.email = email;
			this.orderNo = orderNo;
		}





		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}

		
		


		public int getOrderNo() {
			return orderNo;
		}



		public void setOrderNo(int orderNo) {
			this.orderNo = orderNo;
		}



		@Override
		public String toString() {
			return "InnerUser [id=" + id + ", username=" + username + ", email=" + email + ", orderNo=" + orderNo + "]";
		}



		
		
		
	}
	
	
}
