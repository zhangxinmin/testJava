package test.core;


/**
 *本类用于测试java 的克隆方法。
 *测试结果：
 *一个类 克隆实例中的 字符串 integer int 类型的 属性  和原对象中的属性值没有任何关系（新实例中属性取值改变不会影响原实例中的取值）
 *但是对于引用类型Test2 这种 ，新实例中的值和旧实例中的值存放的是同一个对象的引用。 修改这个属性中字段值得时候  原实例中该属性字段的值也会变。
 *这就是浅克隆的概念。即对于嵌套的引用类型没有进行克隆 而只是将引用改地址复制到了克隆对象中。
 *
 *如果要实现深度克隆怎么做？
 *两种方式:
 *1.重写clone方法时对引用类型的属性再进行克隆。如果引用类型的属性中 又包含 引用各类型属性就需要层层克隆。所以该种方法再这种情况下回比较麻烦。
 *2.使用对象流将对象序列化 然后再反序列化回来就可以了
 *
 *问题：字符串和integer类型变量也都是引用类型 为什么展现出来的现象和 Test2 这种引用类型不一致？
 *解释：代码中对象克隆后 
 *t.getAge()==t2.getAge()     
 *t.getName()==t2.getName()
 *结果都是true  证明和引用各类型对象一样   克隆只是将引用类型的 引用复制了过来。
 *但是由于 String 和Integer 都是不可变对象  所以对新克隆的实例属性 重新赋值时 实际上是 重新创建的新的对象 并赋值到对应的属性中。
 *所以不会影响原对象中属性的值
 * 
 * @author zxm
 *
 */
public class TestClone implements Cloneable{
	private String name;//字符串类型
	private Integer age; //integer 类型
	private int sex; //基本类型
	private TestClone obj; //引用各类型
	
	public TestClone getObj() {
		return obj;
	}
	public void setObj(TestClone obj) {
		this.obj = obj;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public TestClone getCloneIns() throws CloneNotSupportedException{
		return (TestClone) this.clone();
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public String toString() {
		String res=name+"="+age +"="+sex+"；obj:"+obj.getName()+"="+obj.getAge()+"="+obj.getSex();
		return  res;
	}
	public static void main(String[] args) throws CloneNotSupportedException {
		TestClone t =  new TestClone();
		t.setName("zhangsan");
		t.setSex(1);
		t.setAge(300);
		t.setObj(t);
		TestClone t2= t.getCloneIns();
		
		System.out.println("t.getAge()==t2.getAge()"+(t.getAge()==t2.getAge()));//true 
		System.out.println("t.getName()==t2.getName()"+(t.getName()==t2.getName()));//true 
		
		t.setName("zhangsan2");
		t2.setSex(2);
		//t2.getAge().
		System.out.println(t2.getObj().getName());
		System.out.println(t.toString() + t2.toString());
		System.out.println(t.getObj() == t2.getObj());
		System.out.println(t.getAge()== t2.getAge());
	}

}
