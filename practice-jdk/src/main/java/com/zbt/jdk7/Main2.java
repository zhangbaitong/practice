package com.zbt.jdk7;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main2 {
	
	public void mockMethod1(String arg1,int arg2 ,int[] arg3){
		System.out.println(arg1 + arg2 + arg3);
	}
	
	/**
	 * method handle(MethodHandle类不可改变，有点想String对象)
	 * @throws Throwable 
	 */
	
	public static void testGenerateGenericMethodType() throws Throwable{
		//object 参数个数
		MethodType mt1 = MethodType.genericMethodType(3);
		//object 参数个数，并是否在参数列表后面添加一个Object[]类型的参数
		MethodType mt2 = MethodType.genericMethodType(2, true);
		
		ClassLoader cl = Main.class.getClassLoader();
		//String descriptor = "(Ljava/lang/String)Ljava/lang/String";
		String descriptor = "(II[CI)V";//String.getChars的方法参数字节码表示
		MethodType mt3 = MethodType.fromMethodDescriptorString(descriptor, cl);
		
		//change method type，先指定返回值，后面跟0到多个参数类型
		//返回值可以指定void.class or java.lang.Void.class
		MethodType mt4 = MethodType.methodType(String.class,int.class,int.class);
		mt4 = mt4.appendParameterTypes(float.class);
		mt4 = mt4.insertParameterTypes(1,double.class, long.class);
		mt4 = mt4.dropParameterTypes(2, 3);
		mt4 = mt4.changeParameterType(2, String.class);
		mt4 = mt4.changeReturnType(void.class);
		//wrap unwrap generic erase
		MethodType mt5 = MethodType.methodType(Integer.class,int.class,double.class);
		//基本类型和包装类型转换
		MethodType wrapped = mt5.wrap();
		MethodType unwrapped = mt5.unwrap();
		//泛化，把所有的参数和返回值变为object
		MethodType generic = mt5.generic();
		//把引用类型变为Object，不处理基本类型
		MethodType erased = mt5.erase();
		
		//method invoke
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class,int.class,int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		String str = (String)mh.invokeExact("Hello World",1,3);
		System.out.println("invoke mehtod substring:"+str);
		
		//可变长度参数
		MethodHandle mh2 = lookup.findVirtual(Main2.class, "mockMethod1", MethodType.methodType(void.class,String.class,int.class,int[].class));
		//收集指定长度的参数
		//mh3 = mh2.asCollector(int[].class, 2);
		mh2 = mh2.asVarargsCollector(int[].class);
		mh2.invoke(new Main2(),"Hello",2,3,4,5);
		//参数长度变方法数组
		//public void mockMethod2(String arg1,int arg2,int arg3,int arg4);
		//MethodHandle mh4 = lookup.findVirtual(Main.class, "mockMethod2", MethodType.methodType(void.class,String.class,int.class,int.class,int.class));
		//mh4 = mh2.asSpreader(int[].class, 3);
		//mh4.invoke(new Main2(),"Hello",new int[]{2,3,4});
		//参数长度可变变为数组
		//public void mockMethod3(String arg1,int... args)
		//MethodHandle mh5 = lookup.findVirtual(Main.class, "mockMethod3", MethodType.methodType(void.class,String.class,int[].class));
		//mh5.asFixedArity();
		//mh5.invoke(new 2(),"Hello",new int[]{2,4});
		
		//事先绑定
		MethodHandle mh6 = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
		int len = (int)mh6.invoke("Hello");
		System.out.println("mh6:" + len);
		//注意赋值
		mh6 = mh6.bindTo("Hello world!");
		len = (int)mh6.invoke();
		System.out.println("mh6:" + len);
		//多参数绑定
		MethodHandle mh7 = lookup.findVirtual(String.class, "indexOf", MethodType.methodType(int.class,String.class,int.class));
		mh7 = mh7.bindTo("baitong").bindTo("t");
		System.out.println("mh7:" + mh7.invoke(2));
		//参数绑定只能对引用类型进行绑定，无法对int和float的基本类型进行绑定（可以通过wrap先转换类型，再通过方法句柄的asType转换为新的句柄，然后再bindTo）
		MethodHandle mh8 = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class,int.class,int.class));
		mh8 = mh8.asType(mh8.type().wrap());
		mh8 = mh8.bindTo("Hello World").bindTo(3);
		System.out.println("mh8:" + mh8.invoke(5));
	}
	
	private void mockPrivateMethod(){
		System.out.println("I am a private method.");
	}
	
	public class MockPOJO {
		
		public String name = "baitong";
		
		private void privateMethod(){
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	public static void testGetMethodType() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		//三种不同的方法查找
		lookup.findConstructor(String.class, MethodType.methodType(void.class,byte[].class));
		lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class,int.class,int.class));
		lookup.findStatic(String.class, "format", MethodType.methodType(String.class,String.class,Object[].class));
		//特殊方法，比如私有
		lookup.findSpecial(Main2.class, "mockPrivateMethod", MethodType.methodType(void.class), Main2.class);
		//值方法
		lookup.findGetter(MockPOJO.class, "name", String.class);
		lookup.findSetter(MockPOJO.class, "name", String.class);
		//lookup.findStaticSetter(MockPOJO.class, "name", String.class);
		//lookup.findStaticGetter(MockPOJO.class, "name", String.class);
		//通过反射获得
		Constructor constructor = String.class.getConstructor(byte[].class);
		lookup.unreflectConstructor(constructor);
		
		Method method = String.class.getMethod("substring", int.class,int.class);
		Method privateMethod = MockPOJO.class.getDeclaredMethod("privateMethod");
		//lookup.unreflectSpecial(privateMethod, MockPOJO.class);
		
		Field field = MockPOJO.class.getField("name");
		lookup.unreflectGetter(field);
		lookup.unreflectSetter(field);
		
		//通过静态方法创建
		int[] array = new int[]{1,2,3,4,5};
		MethodHandle setter = MethodHandles.arrayElementSetter(int[].class);
		setter.invoke(array,3,6);
		MethodHandle getter = MethodHandles.arrayElementGetter(int[].class);
		int value = (int)getter.invoke(array,3);
		System.out.println("array:" + value);
		
		//总返回常量值
		MethodHandle mh = MethodHandles.identity(String.class);
		String value2 = (String)mh.invoke("Hello");
		System.out.println(value2);
		
		MethodHandle mh2 = MethodHandles.constant(String.class, "Hello");
		String name = (String)mh2.invoke();
	}
	
	/**
	 * 方法句柄的转换，适用于参数类型和返回类型不同的场景进行适应
	 * @throws Throwable 
	 * @throws  
	 */
	public static void testMethodTypeChange() throws Throwable{
		
		//添加无用参数
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class,int.class,int.class);
		MethodHandle mhOld = lookup.findVirtual(String.class, "substring", type);
		String value = (String)mhOld.invoke("Hello",2,4);
		System.out.println(value);
		MethodHandle mhNew = MethodHandles.dropArguments(mhOld, 0, float.class,String.class);
		value = (String)mhNew.invoke(0.5f,"ignore","Hello",2,4);
		System.out.println(value);
		
		//预先置入参数
		MethodType type2 = MethodType.methodType(String.class,String.class);
		MethodHandle mhOld2 = lookup.findVirtual(String.class, "concat", type2);
		String value2 = (String)mhOld2.invoke("Hello","World");
		System.out.println(value2);
		MethodHandle mhNew2 = MethodHandles.insertArguments(mhOld2, 1, "--");
		value2 = (String)mhNew2.invoke("Hello");
		System.out.println(value2);
		
		//通过其他方法句柄进行预处理参数
		MethodType type3 = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhGetLength = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
		MethodHandle mhTarget = lookup.findStatic(Math.class, "max", type3);
		//对math的max函数做getlength的预处理(filterArguments会替换到原有的值)
		MethodHandle mhNew3 = MethodHandles.filterArguments(mhTarget, 0, mhGetLength,mhGetLength);
		int value3 = (int)mhNew3.invoke("Hello","New World");
		System.out.println(value3);
		
		//（foldArguments不会替换原有值而是再原有列表前添加一个新的参数,如果是void则不添加，并且它是用一个方法句柄处理所有可处理的参数
		MethodType typeCombiner = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhCombiner = lookup.findStatic(Math.class, "max", typeCombiner);
		MethodType typeTarget = MethodType.methodType(int.class,int.class,int.class,int.class);
		MethodHandle mhTarget2 = lookup.findStatic(Main2.class, "mockMethod2", typeTarget);
		//max先对三个参数求最大值，然后传递给目标函数
		MethodHandle mhResult = MethodHandles.foldArguments(mhTarget2, mhCombiner);
		int value4 = (int)mhResult.invoke(3,2);
		System.out.println(value4);
		
		//permuteArguments重新排列参数
		MethodType type5 = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhCompare = lookup.findStatic(Integer.class, "compare", type5);
		int value5 = (int)mhCompare.invoke(3,4);
		System.out.println("compare result:" + value5);
		//调换参数顺序
		MethodHandle mhNew5 = MethodHandles.permuteArguments(mhCompare, type5, 1,0);
		value5 = (int)mhNew5.invoke(3,4);
		System.out.println("compare result:" + value5);
		
		MethodHandle mhDuplicateArgs = MethodHandles.permuteArguments(mhCompare, type5, 1,1);
		value5 = (int)mhDuplicateArgs.invoke(3,4);
		System.out.println("compare result:" + value5);
	}
	
	public static int mockMethod2(int arg1,int arg2,int arg3){
		return arg1 + arg2 + arg3;
	}

	public static void testCatchMethodHandleException() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		//先定义方法形式：返回为int参数是string
		MethodType typeTarget = MethodType.methodType(int.class,String.class);
		MethodHandle mhParseInt = lookup.findStatic(Integer.class, "parseInt", typeTarget);
		//定义handle得形式
		MethodType typeHandler = MethodType.methodType(int.class,Exception.class,String.class);
		MethodHandle mhHandler = lookup.findVirtual(Main2.class, "handleException", typeHandler).bindTo(new Main2());
		//用handle对应原方法
		MethodHandle mh = MethodHandles.catchException(mhParseInt, NumberFormatException.class, mhHandler);
		//如果有异常，handleexception方法捕获，然后处理，并提供返回值，如果正常，直接返回
		System.out.println(mh.invoke("hello"));
		
	}
	
	public int handleException(Exception e,String msg){
		System.out.println("handle exception---------");
		e.printStackTrace();
		return 0;
	}
	
	/**
	 * 方法句柄得条件判断逻辑
	 * @throws Throwable 
	 * @throws  
	 */
	public static void testMethodHandleTest() throws Throwable{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhTest = lookup.findStatic(Main2.class, "guardTest", MethodType.methodType(boolean.class));
		
		MethodType type = MethodType.methodType(int.class,int.class,int.class);
		MethodHandle mhTarget = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhFallback = lookup.findStatic(Math.class, "min", type);
		//根据条件方法选择后面得两个类型和参数相同得方法
		MethodHandle mh = MethodHandles.guardWithTest(mhTest, mhTarget, mhFallback);
		int value = (int)mh.invoke(3,5);
		System.out.println("guard test:" + value);
		
		//方法过滤（链式调用）
		MethodHandle mhSubstring = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class,int.class));
		MethodHandle mhUperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
		//先执行filter方法mhUperCase然后返回值给mhSubstring
		MethodHandle mhResult = MethodHandles.filterReturnValue(mhSubstring, mhUperCase);
		
		System.out.println((String)mhResult.invoke("Hello World",5));
	}
	
	public static boolean guardTest(){
		return Math.random() > 0.5;
	}
	
	
	public static void testSpecialMethodHandleInvoke() throws Throwable{
		//定义一个通用得调用方法类型（相当于抽象层得接口定义）
		MethodType typeInvoker = MethodType.methodType(String.class,Object.class,int.class,int.class);
		MethodHandle invoker = MethodHandles.invoker(typeInvoker);
		
		//定义需要调用得实现方法得类型
		MethodType typefind = MethodType.methodType(String.class,int.class,int.class);
		//获得具体实现得两个方法
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh1 = lookup.findVirtual(String.class, "substring", typefind);
		MethodHandle mh2 = lookup.findVirtual(Main2.class, "mockSpecialMethod", typefind);
		
		//使用抽象定义得接口调用
		String result = (String)invoker.invoke(mh1,"Hello",2,4);
		System.out.println("Special method : " + result);
		result = (String)invoker.invoke(mh2,new Main2(),2,3);
		System.out.println("Special method : " + result);
		
		
		//最大得好处就是当对抽象接口进行句柄转换后不需要对每个具体实现进行单独应用（其实就是做了个代理抽象层）
		MethodType typeInvoker2 = MethodType.methodType(String.class,String.class,int.class,int.class);
		MethodHandle invoker2 = MethodHandles.exactInvoker(typeInvoker2);
		
		MethodHandle mhUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
		//对invoker2进行变换,之后对下面得逻辑没有影响
		invoker2 = MethodHandles.filterReturnValue(invoker2, mhUpperCase);
		//寻找实现类型进行调用
		MethodType typefind2 = MethodType.methodType(String.class,int.class,int.class);
		MethodHandle mh11 = lookup.findVirtual(String.class, "substring", typefind2);
		result = (String)invoker2.invoke(mh11,"Hello",1,4);
		System.out.println("Special method : " + result);
		
	}
	
	public String mockSpecialMethod(int a,int b){
		return ""+ a + b;
	}
	
	public static void main(String[] args) throws Throwable {
		testGenerateGenericMethodType();
		testGetMethodType();
		testMethodTypeChange();
		testCatchMethodHandleException();
		testMethodHandleTest();
		testSpecialMethodHandleInvoke();
	}

}
