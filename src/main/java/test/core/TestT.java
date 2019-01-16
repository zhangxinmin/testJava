package test.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 泛型测试类  这里对泛型进行初步学习和测试
 * 1、泛型是什么？ 泛型就是通过不新增实现的前提下 实现参数类型可配置或可约定。比如 List 可以 List<String> 约定元素类型为 String 
 * 2、泛型的特性： 泛型仅仅是在编译期起作用，编译后对类型做了验证后   会在元素获取时增加 强制类型转换。  就是 泛型就是编译期增加 类型的验证  避免出现 类型转换异常
 * 3、泛型分为三种   接口泛型 类泛型 和 方法泛型 
 * 
 */
//=============================================泛型类示例代码===========================
/**
 * 泛型类 我这里包装了一下 Map  格式如下
 * 泛型的类型参数只能是类类型，不能是简单类型。
 * 不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错
 * 泛型不能进行任何操作 因为只是 编译验证 所以不能有任何操作 
 */
class  MyMap<T,E>{
	private Map<T,E> map=new HashMap<T,E>(); 
	public void put(T key,E value){
		map.put(key, value);
	}
	public E get(T key){
		return map.get(key);
	}
} 

//=============================================泛型接口示例代码 开始===========================

/**
 * 泛型接口
 * 泛型接口与泛型类的定义及使用基本相同。泛型接口常被用在各种类的生产器中
 *
 * 
 */
interface Generator<T> {
  public T generat();
}
/**
 * 实现接口时可以 声明泛型   类型默认 为 Object
 * @author zxm
 *
 */
class GeneratorImpl implements Generator{
	@Override
	public Object generat() {
		return null;
	}
}
/**
 * 声明了泛型但是未传入实际类型 和类泛型类似
 * @author zxm
 */
class GeneratorImpl2<T> implements Generator<T>{
	@Override
	public T generat() {
		return null;
	}
}
/**
 * 一旦传入了 实际类型 则 实现类中和原接口泛型相关的 位置  就不允许有泛型标识
 * 但是 实现类仍然可以定义自己的泛型类型 如下 的T 实际上是一个自己定义的泛型 
 * 
 * @author zxm
 *
 */
class GeneratorImpl3<T> implements Generator<String>{
	String arr=null;
	//由于传入了String类型 所以这个借口方法 只能返回String
	@Override
	public String generat() {
		return "String";
	}

}

//=============================================泛型接口示例代码 结束===========================


 class Generic<T>{ 
    //key这个成员变量的类型为T,T的类型由外部指定  
    private T key;

    public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.key = key;
    }
    /**
     * 静态方法无法访问 类定义的泛型  如果要用泛型必须自己定义
     * @return
     */
    public static<T> T getKey2(T key){
    	return key;
    }
    
    public T getKey(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
        return key;
    }
}
//==========================测试方法============================
public class TestT {


	public static void main(String[] args) {
		//testt();
		testSpe();
		testMyMap();
		testFlag(new GeneratorImpl2());
		testFlag(new GeneratorImpl2<Integer>());
		//getFirst2(new ArrayList<String>());//通配符定义了边界 后 必须 满足边界限制 
		
		//在java中是”不能创建一个确切的泛型类型的数组”的。 所以这行程序报错
		//List<String>[] ls = new ArrayList<String>[10];  

		//而使用通配符创建泛型数组是可以的，如下面这个例子：

		List<?>[] ls = new ArrayList<?>[10]; 

		//这样也是可以的：
		List<String>[] ls2 = new ArrayList[10];

	}
//=========================泛型方法开始===================================	
	/**
	 * 泛型方法
	 * 这才是一个真正的泛型方法。 其他的 泛型类中的成员方法  以及  只使用了泛型通配符的方法都不是泛型方法
     * 首先在public与返回值之间的<T>必不可少，这表明这是一个泛型方法，并且声明了一个泛型T
     * 这个T可以出现在这个泛型方法的任意位置.
     * 泛型的数量也可以为任意多个 
     *    如：public <T,K> K showKeyName(Generic<T> container){
     *        ...
     *        }

	 * @param container
	 * @return
	 */
	public <T> T showKeyName(Generic<T> container){
        System.out.println("container key :" + container.getKey());
        //当然这个例子举的不太合适，只是为了说明泛型方法的特性。
        T test = container.getKey();
        return test;
    }
	//========================泛型方法  开始===========================
	/**
	 * 自定义泛型方式实例
	 */
	public static <T> T getFirst(List<T> list){
	    if(null==list||list.size()==0)return null;
		return list.get(0);
	}
	
//	public static <T> T getFirst(List<T extends Object> list){
//	    if(null==list||list.size()==0)return null;
//		return list.get(0);
//	}
	
	/**
	 * 非泛型方法  对比  通配符？可以定义便捷
	 */
	public static Object getFirst2(List<? extends Number> list){
	    if(null==list||list.size()==0)return null;
		return list.get(0);
	}
	//========================泛型方法  结束===========================
	
	/**
	 * 测试通配符 如果方法需要一个带有泛型的参数 这个时候 可以使用 ？来代表 （如果用T这种的话必须是当前类定义的泛型 ）
	 * 
	 * @param ttt
	 * @return
	 */
	public static void testFlag(Generator<?> obj){
		System.out.println(obj.generat());  
	}
	
	/**
	 * 测试泛型类使用
	 */
	public static void testMyMap(){
		MyMap map = new MyMap();
		map.put(1,2);
		MyMap<String,String> map2 = new MyMap<String,String>();
		//map2.put(1,2);//因为指定了类型所以这里报错 
		//map instanceof MyMap<String,String>; 泛型类不能作为instanceof 对象 否则报错 
		map2.put("1","1");
		System.out.println(map2.get("1"));
		
	}
	
	
	
	/**
	 * 特性测试
	 * 不同泛型的实例 他们本质上 是同一类型   运行期是完全一致的  泛型只是一种编译期验证
	 * 但是 指定了不同泛型的类 不能相互赋值  因为存在类型转换问题。即使是有父子关系的两个泛型类型也不能相互赋值
	 * 泛型类型在逻辑上可以看成是多个不同的类型，实际上都是相同的基本类型
	 * 
	 */
	public static void testSpe(){
		List<String> stringArrayList = new ArrayList<String>();
		List<Integer> integerArrayList = new ArrayList<Integer>();
		List<Object> objArrayList = new ArrayList<Object>();
		//List<Integer> integerArrayList = new ArrayList<Integer>();
		//objArrayList=integerArrayList;//不同泛型的实例不能相互赋值  因为存在类型转换问题

		Class classStringArrayList = stringArrayList.getClass();
		Class classIntegerArrayList = integerArrayList.getClass();

		if(classStringArrayList.equals(classIntegerArrayList)){
		   System.out.println("2123123123");
		}

	}
	
	
	
	/**
	 * 演示泛型的作用
	 * 没有泛型 则 元素类型没有约束 所以 可能会存在运行时 类型转换异常
	 * 而增加了泛型就会有 编译期的验证  类型不符合 就会报错  也就避免了运行时异常
	 * 
	 */
	public static void testt(){
		List arrayList = new ArrayList();
		arrayList.add("aaaa");
		arrayList.add(100);

		for(int i = 0; i< arrayList.size();i++){
		    String item = (String)arrayList.get(i);
		    System.out.println("泛型测试"+"item = " + item);
		}
	}

}
