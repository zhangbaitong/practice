package com.zbt.jdk7;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.VolatileCallSite;

public class Main4 {
	
	
	
	private static final MethodType typeCallback = MethodType.methodType(Object.class,Object.class,int.class);
	
	public static void forEach(Object[] array,MethodHandle handle) throws Throwable{
		for(int i=0;i<array.length;i++){
			handle.invoke(array[i],i);
		}
	}
	
	public static Object[] map(Object[] array,MethodHandle handle) throws Throwable{
		Object[] result = new Object[array.length];
		for(int i=0;i<array.length;i++){
			result[i] = handle.invoke(array[i],i);
		}
		return result;
	}
	
	public static Object reduce(Object[] array,Object initalValue,MethodHandle handle) throws Throwable{
		Object result = initalValue;
		for(int i=0;i<array.length;i++){
			result = handle.invoke(result,array[i]);
		}
		return result;
	}
	
	
	/*******--------------====  ======-----------------********/
	
	
	public static MethodHandle curry(MethodHandle handle,int value){
		return MethodHandles.insertArguments(handle, 0, value);
	}
	
	public static int add(int a,int b){
		return a + b;
	}
	
	public static int add5(int a) throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhAdd = lookup.findStatic(Main4.class, "add", type);
		MethodHandle mh = curry(mhAdd,5);
		return (int)mh.invoke(a);
	}
	
	
	/*******--------------====  ======----------------- ********/
	
	/**
	 * invokedynamic指令
	 * 该指令的灵活性取决于方法句柄的灵活性
	 * 在java源码中没有直接对应产生方式，它是一个完全由java代码规范的指令
	 * 编译器不会帮助开发人员生成该指令，需要自己生成（动态语言编辑使用）
	 * 可以借助字节代码工具生成
	 * 介绍：
	 * 名称：不一定是符合java命名规范的字符串，可以任意指定。方法调用者和提供者也不需要在方法名称上达成一致。
	 * 链接：更加灵活。一个方法调用的实际方法可以在运行时再确定。已经链接的可以重新进行链接指向其他方法。
	 * 选择：不再只能在方法调用的接受者进行派发，可以考虑所有调用时的参数，即支持方法的多派发
	 * 调用（适配）：调用之前可以对参数进行各种不同的处理，包括类型转换，添加删除参数，收集和奋发可变长度参数。（以前需要双方对调用声明达成一致）
	 * invokestatic：只能固定调用静态方法的类中的方法
	 * invokespacial：调用的方法只有当前类的对象才有权，所以是固定选择
	 * invokevirtual:单派发机制，取决与具体实现
	 * invokeinterface:单派发机制，取决与具体实现
	 * 满足以上条件jdk7以前才会把控制权限交给调用方法，并把调用时的实际参数传递进去
	 * 查看方法：
	 * 写一个具有接口，静态方法，实现类的一个例子，在所在类的目录下执行：
	 * javap -verbose yourclassname
	 * 
	 */
	
	
	public static void testConstantCallSite() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class,int.class,int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		
		//ConstantCallSite创建时就指定调用方法
		ConstantCallSite callSite = new ConstantCallSite(mh);
		MethodHandle invoker = callSite.dynamicInvoker();
		String result = (String)invoker.invoke("Hello",2,3);
		System.out.println("ConstantCallSite:" + result);
		
		//MutableCallSite调用时指定
		MethodType type1 = MethodType.methodType(int.class,int.class,int.class);
		MutableCallSite callSite1 = new MutableCallSite(type1);
		MethodHandle invoker1 = callSite1.dynamicInvoker();
		
		MethodHandle mhMax = lookup.findStatic(Math.class, "max", type1);
		MethodHandle mhMin = lookup.findStatic(Math.class, "min", type1);
		
		//允许对关联对象动态修改
		callSite1.setTarget(mhMax);
		int result1 = (int)invoker1.invoke(3,5);
		System.out.println("MutableCallSite:" + result1);
		callSite1.setTarget(mhMin);
		result1 = (int)invoker1.invoke(3,5);
		System.out.println("MutableCallSite:" + result1);
		
		//保证多线程之间的MutableCallSite数组的同步，建议使用VolatileCallSite
		//MutableCallSite.syncAll(sites);
	}
	
	
	/**
	 * 动态根据对象value进行调用它字符表示的toUpperCase方法
	 * @param lookup
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static CallSite bootstrap(Lookup lookup,String name,MethodType type,String value) throws Exception{
		MethodHandle mh = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class)).bindTo(value);
		return new ConstantCallSite(mh);
	}
	

	public static void main(String[] args) throws Throwable {
		//testFunctionProgram
		testConstantCallSite();
	}

}
