# 1 guava集合体系
   Google Guava Collections（以下都简称为 Guava Collections）是 Java Collections Framework 的增强和扩展。每个 Java 开发者都会在工作中使用各种数据结构，很多情况下 Java Collections Framework 可以帮助你完成这类工作。但是在有些场合你使用了 Java Collections Framework 的 API。

简单来说就是guava在jdk的基础上封装了一些常用的操作和实现或者对部分集合做了重写或者扩展。

## 1) 功能概览 ##

- **Immutable Collections**:<br> 
	不可修改的集合
- **Multiset:**<br>
   无序可重复的集合
- **Multimaps：**<br>
  一个key对应多个value的map
- **BiMap:**<br>
  key不重复 value也不重复
- **MapMaker：**<br>
map的构造工厂
- **Ordering :**<br>
	大家知道用 Comparator 作为比较器来对集合排序，但是对于多关键字排序 Ordering class 可以简化很多的代码 
- **filter ：**<br>
	集合过滤，并且保证过滤后的几个不能有不符合过滤值得值被添加到集合中。
- **transform：**<br>
	集合转换，将(List<T>)转换为List<E>。