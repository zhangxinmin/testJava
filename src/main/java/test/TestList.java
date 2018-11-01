package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestList {
	public static void main(String[] args) {
		testRemove(); 
		//testAsList();
		//testToArray();
		//testSublist();
	}
	
	
	/**
	 * 不要在 foreach 循环里进行元素的 remove / add 操作。 remove 元素请使用 Iterator
		方式，如果并发操作，需要对 Iterator 对象加锁。
	 */
	public static void testRemove(){
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String item = iterator.next();
			iterator.remove();
		}
		System.out.println(list);//[]
		
		list.add("1");
		list.add("2");
		
		for (String item : list) {
			if ("1".equals(item)) {
		        list.remove(item);
			}
		}
		System.out.println(list);//[2]
		
		list.set(0, "1");list.add( "2");
		System.out.println(list);
		for (String item : list) { //java.util.ConcurrentModificationException
			if ("2".equals(item)) {
		        list.remove(item);
			}
		}
		System.out.println(list);
	}
	
	/**
	 * 使用工具类 Arrays . asList() 把数组转换成集合时，不能使用其修改集合相关的方
		法，它的 add / remove / clear 方法会抛出 UnsupportedOperationException 异常。
		说明： asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。 Arrays . asList
		体现的是适配器模式，只是转换接口，后台的数据仍是数组。
		String[] str = new String[] { "you", "wu" };
		List list = Arrays.asList(str);
		第一种情况： list.add("yangguanbao");  运行时异常。
		第二种情况： str[0] = "gujin"; 那么 list.get(0) 也会随之修改
	 */
	public static void testAsList(){
		String[] arr = new String[]{"1","2","3"};
		List list = Arrays.asList(arr);
		
		arr[0]="10";
		System.out.println(list.get(0));//10 
		
		list.add("4");//java.lang.UnsupportedOperationException
	}
	
	/**
	 * 使用集合转数组的方法，必须使用集合的 toArray(T[] array) ，传入的是类型完全
		一样的数组，大小就是 list . size() 。
		说明：使用 toArray 带参方法，入参分配的数组空间不够大时， toArray 方法内部将重新分配
		内存空间，并返回新数组地址 ； 如果数组元素个数大于实际所需，下标为 [ list . size() ]
		的数组元素将被置为 null ，其它数组元素保持原值，因此最好将方法入参数组大小定义与集
		合元素个数一致。
	 */
	public static void testToArray(){
		List list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		String[] res1=new String[list.size()];
		res1=(String[]) list.toArray(res1);
		System.out.println(res1.length);//4
		
		String[] res2=new String[list.size()-1];
		String[] res3=(String[]) list.toArray(res2);
		System.out.println(res2.length);//3
		System.out.println(res3.length);//4
		System.out.println(res3==res2);//false
		
		String[] res4=new String[list.size()+1];
		String[] res5=(String[]) list.toArray(res4);
		System.out.println(res4.length);//5
		System.out.println(res5.length);//5
		System.out.println(res4==res5);//true
		
		String[] res6 = (String[])list.toArray();//java.lang.ClassCastException:
	}
	
	
	/**
	 *  ArrayList 的 subList 结果不可强转成 ArrayList ，否则会抛出 ClassCastException
		异常，即 java . util . RandomAccessSubList cannot be cast to java . util . ArrayList 。
		说明： subList 返回的是  ArrayList 的内部类  SubList ，并不是  ArrayList 而是 ArrayList
		的一个视图，对于 SubList 子列表的所有操作最终会反映到原列表上
	 */
	public static void testSublist(){
		List list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		System.out.println(list.subList(2, 3));  //[3]
		
		//ArrayList list2= (ArrayList) list.subList(2, 3);//.ArrayList$SubList cannot be cast to java.util.ArrayList
		List list3 = list.subList(2, 3);
		System.out.println(list3);
		list3.add("5");
		System.out.println(list3);//[3, 5]
		System.out.println(list);//[1, 2, 3, 5, 4]
		
		for(int i =0 ;i<list3.size();i++){
			
			
			//list.remove(i);
			//System.out.println(list3.get(i));
			//java.util.ConcurrentModificationException 删除了元素 便利会抛出异常
			
			//list.add("6");
			//System.out.println(list3.get(i));
			//java.util.ConcurrentModificationException   添加了元素 便利会抛出异常
			
			//list.remove(0);
			//System.out.println(list3.get(i));
			//java.util.ConcurrentModificationException   添加了元素 便利会抛出异常
			
			list.set(2, "6");
			System.out.println(list3.get(i));//6   这里输出了6 表示长度不变只是修改元素值的话可以直接输出
			
		}

	}
}
