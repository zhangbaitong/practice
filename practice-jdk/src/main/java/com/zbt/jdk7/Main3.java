package com.zbt.jdk7;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

public class Main3 {
	
	/**
	 * 方法句柄实现接口
	 * @throws Throwable
	 */
	public static void testMethodHandleInterface() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(Main3.class, "mockInterfaceImpl", MethodType.methodType(void.class));
		mh = mh.bindTo(new Main3());
		Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
		new Thread(runnable).start();
	}
	
	public void mockInterfaceImpl(){
		System.out.println("I am mockInterfaceImpl");
	}
	
	/**
	 * 调用时不进行访问权限查找，但是反射API每次都需要去验证，性能稍差点
	 * @throws Throwable
	 */
	public static void testMethodHandleAccessControl() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findSpecial(Main3.class, "privateMethod", MethodType.methodType(void.class),Main3.class);
		mh = mh.bindTo(new Main3());
		mh.invoke();
	}
	
	private void privateMethod(){
		System.out.println("I am private method");
	}
	
	public static void testMethodHandleSwitchPoint() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		//定义两个实现方法
		MethodType methodType = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhMax = lookup.findStatic(Math.class, "max", methodType);
		MethodHandle mhMin = lookup.findStatic(Math.class, "min", methodType);
		//定义一个交换点
		SwitchPoint sp = new SwitchPoint();
		MethodHandle mhNew = sp.guardWithTest(mhMax, mhMin);
		System.out.println("sp : " + mhNew.invoke(3,4));
		SwitchPoint.invalidateAll(new SwitchPoint[]{sp});
		System.out.println("sp : " + mhNew.invoke(3,4));
	}
	

	public static void main(String[] args) throws Throwable {
		testMethodHandleInterface();
		testMethodHandleAccessControl();
		testMethodHandleSwitchPoint();
	}

}
