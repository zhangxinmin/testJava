package test.core.enumt;


public class TestEnum {
	public interface Ttt{
		void doPrint();
	}
	
	//定义最简单 枚举
	public enum Signal {
		RED,GREEN,YELLOW
	}
	

	
	//定义属性 一定要在  枚举值后一定要加 分号 否则报格式错误 
	//如果是常量 就取消 set方法 否则 值会被修改
	//感觉 枚举类像是接口 而值是实现类 只不过接口的方法没有暴露出来
	enum Color2{
		RED("红色",1),GREEN("绿色",2),YELLOW("黄色",3);
		
		private String name;
		private int value;
		private Color2(String name,int value){
			this.name=name;
			this.value =value;
		}
		public String getName() {
			return name;
		}
		//public void setName(String name) {
		//	this.name = name;
		//}
		public int getValue() {
			return value;
		}
		//public void setValue(int value) {
		//	this.value = value;
		//}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			
			return super.toString()+name+"="+value;
		}
		
		
		
	}
	
	//枚举也可以实现接口 定义操作  
	//
	public enum Signal2  implements Ttt{
		RED("红色",1),GREEN("绿色",2),YELLOW("黄色",3);;
		private String name;
		private int value;
		private Signal2(String name,int value){
			this.name=name;
			this.value =value;
		}
		public String getName() {
			return name;
		}
		public int getValue() {
			return value;
		}
		@Override
		public void doPrint() {
			System.out.println(this.name+this.value);
			
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Color.GREEN.equals("GREEN"));
		 test1();
		 test2();
	}
	//测试调用接口方法
	public static void test2(){
		Signal2.GREEN.doPrint();
	}
	//用作 switch
	public static void test1(){
		Signal color = Signal.RED;
		switch(color){
		case RED:
			System.out.println("red");
			break;
		case GREEN:
			System.out.println("green");
			break;
		case YELLOW:
			System.out.println("yellow");
			break;
		}
		
	}
	

}
