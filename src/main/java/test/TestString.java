package test;

public class TestString {
  
	public static void main(String[] args) {
		testSplit();
	}
	
	public static void testSplit(){
		String str="1,2,,";
		System.out.println(str.split(",").length); // 2
		String str2="1,2,,,1";
		System.out.println(str2.split(",").length); // 5
	}
	
}
