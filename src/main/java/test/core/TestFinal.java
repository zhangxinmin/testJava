package test.core;
/*
 * 总结四种用法：
 * 1、局部变量  不能被重新赋值， 只能被赋值一次 生命时 或声明后 。
 * 2、修饰成员变量 有且只有两个地方可以给它赋值，一个是声明该成员时赋值，另一个是在构造方法中赋值，在这两个地方我们必须给它们赋初始值，并且只能在其中一个地方赋值
 * 3、修饰方法参数 标识 在方法中不会改变参数的值
 * 4、方法  不能被覆盖重写
 * 5、修饰类不能被继承
 * 
 * */
//final修饰的类是无法被继承的
public final class TestFinal {
	//final修饰的成员变量，我们有且只有两个地方可以给它赋值，一个是声明该成员时赋值，另一个是在构造方法中赋值，在这两个地方我们必须给它们赋初始值
	private final  int  age;
	public String name;
	
	//编写方法时，可以在参数前面添加final关键字，它表示在整个方法中，我们不会（实际上是不能）改变参数的值
	//final 修饰方法表示不能被子类覆盖。 
	public final int add(final int i,final int j){
		//i=2;
		return i+j;
	}
	
	
	public TestFinal(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}



	public static void main(String[] args) {
		//修饰变量 只能被 赋值一次     a 在声明时赋值  b 在声明后 赋值一次
		final int a = 4;
		//a =5;
		
		final int b;
		b=1;
		//修饰引用类型时 是 引用不可变 引用中的内容可以修改
		final TestFinal test= new TestFinal(1,"22");
		test.name ="123";
		//test=new TestFile();
		
	}

}
