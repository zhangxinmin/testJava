package test.core;

public class TestInteger {
    public static void main(String[] args) {
    	testIntegerEq();
	}
	public static void testIntegerEq(){
		//因为-127~127 之间的数字是从常量池中取得 所以  34 都指向常量池中的 地址  所以 为true  而 超出这个范围的为新建对象  地址不相同
		Integer int1 = 128;
		Integer int2 = 128;
		System.out.println(int1==int2);// false  
		System.out.println(int1.equals(int2));// true  
		
		Integer int3 = 12;
		Integer int4 = 12;
		System.out.println(int3==int4); //true 
		
	}
}
