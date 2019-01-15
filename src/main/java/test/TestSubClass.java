package test;

public class TestSubClass extends Base {
	private String name = "dervied";
	{
		System.out.println("1111111111Dervied tell name:"+ name);
		this.name = "dervied1";
	}
	public TestSubClass(){
		//super();
		tellName();
		printName();
	}
	
	private void test (){
		System.out.println("Dervied private ");
	}
	
	public void tellName(){
		System.out.println("Dervied tell name:"+ name);
	}
	public void printName(){
		System.out.println("Dervied print name:" + name);
	}
	public static void main(String[] a){
		new TestSubClass();
	}
}

class Base{
	private  String name="base";
	public Base(){
		test();
		this.tellName();
		this.printName();
	}
	private void test (){
		System.out.println("base private ");
	}
	public void tellName(){
		System.out.println("Base tell name:"+ name);
	}
	public void printName(){
		System.out.println("Base print name:" + name);
	}
}

