package test.core.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解其实比较简单  在指定对象上 定义一些附加属性 
 * 然后 通过反射可以或得到这些属性。根据属性执行不同的操作即可
 */

@Target({ElementType.FIELD,ElementType.CONSTRUCTOR})
//@Target。该注解有一个属性value，类型为ElementType[]，它是枚举类型 取值如下：
//TYPE,FIELD,METHOD,PARAMETED,CONSTRUCTOR,LOCAL_VARIABLE,ANNOCATION_TYPE,PACKAGE  
/**
 * 注解的保留策略
 * 注解是只保留在源代码上，还是保留到class文件上，再或者是类在运行时，可以被类加载器加载到内存中。
 *  它有一个value属性，类型为RetentionPolicy类型，RetentionPolicy是枚举类型
 *  SOURCE, CLASS, RUNTIME 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnno {
	String value();
	int age() default 0;
	//Integer sex(); //不支持该类型的属性
	//只支持   基本类型 、Stirng  enum  annotation Class  以上类型的一维数组类型
	Class<?> clazz();
	int[] intarr();
	//int[][] ages2(); //二维数组类型报错
	//MyAnno anno();//不能是注解自己本身
	Override anno();

}

class TestMyAnno{
	
}
