package test.core;

import java.lang.reflect.Field;

public class TestInteger {
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	testIntegerEq();
    	Integer a=1;
    	Integer b=2;
    	System.out.println(a+"===="+b);
    	swap(a,b);
    	System.out.println(a+"===="+b);
	}
    /**
     * 交换 两个变量的值
     * @param a
     * @param b
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static void swap(Integer a,Integer b) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
    	/*1====2
			方法中：2====1
			1====2
    	 * 这种写法发现 ab的值并没有变过来
    	 * 原因：调用方法时 传入的 Integer 参数  并不是引用因为 Ingeter中的值是final 不能改变的，所以 传入的是一个变量副本 （可以理解为 传值参数）。
    	 * 在方法中修改参数引用指向的地址 不会影响方法外引用指向的地址，方法外的ab仍然指向原来的值，而方法中的引用的值交换了   所以虽然方法中的值交换了 但是方法外的值 还是原来的值
    	 */
//    	Integer temp= a;
//    	a=b;
//    	b=temp;
//    	System.out.println("方法中："+a+"===="+b);
    	/*
    	 * 如果要修改方法外的值只能用反射修改
    	 * 反射修改有坑
    	 * 1、field.set(a, b.intValue()); 方法的第二个参数 是一个 object 所以会自动装箱 Integer.valueof（）方法从常量池中取 i+128位置的Integer返回。
    	 * 如果整数的范围 在 -128-127 之间会从常量池 中 获取Integer对象，如果修改了Integer对象的值，就是说常量池冲 Integer的值改变了。常量池中的值就发生变化。下次再次使用
    	 * 常量池中的值时就会出现错误。  所以 在正常功能中 使用反射修改 Integer的值 是 不可取的。
    	 *  
    	 * 以下代码如果是 -128-127之间的值输出结果如下：
    	 * 1====2  
			1=======2  我们看到由于修改了常量池中的值 我们的 Integer.valueOf(1) 返回的值是2    这显然 不太合适
			方法中：2====2
			2====2  
    	 * 但是对于不使用常量池的情况（范围不在 -128-127之间）没有问题  比如 传入  155  255  由于每次都是新建 Integer对象所以不会有上述问题
    	 * 155====255
			155=======155
			方法中：255====155
			255====155
    	 */
//    	Field field = Integer.class.getDeclaredField("value");
//    	field.setAccessible(true);
//    	
//    	int temp = a.intValue();
//    	field.set(a, b.intValue());
//    	field.set(b, temp);
//        System.out.println(temp+"======="+Integer.valueOf(temp));
//    	System.out.println("方法中："+a+"===="+b);
    	
    	/**
    	 * 为了避免上述问题 可以通过 new 来避开 自动拆装箱 
    	 * 
    	 * 1====2
			1=======2
			方法中：2====1
			2====1

    	 */
    	Field field = Integer.class.getDeclaredField("value");
    	field.setAccessible(true);
    	
    	int temp = a.intValue();
    	field.set(a, b.intValue());
    	field.set(b, new Integer(temp));
    	System.out.println(temp+"======="+Integer.valueOf(temp));
    	System.out.println("方法中："+a+"===="+b);
    	
    	
    }
    
	public static void testIntegerEq(){
		//因为-128~127 之间的数字是从常量池中取得 所以  34 都指向常量池中的 地址  所以 为true  而 超出这个范围的为新建对象  地址不相同
		Integer int1 = 128;
		Integer int2 = 128;
		System.out.println(int1==int2);// false  
		System.out.println(int1.equals(int2));// true  
		
		Integer int3 = 12;
		Integer int4 = 12;
		System.out.println(int3==int4); //true 
		
	}
}
