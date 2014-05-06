package com.zbt.jdk7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * http://www.oracle.com/technetwork/java/javase/jdk7-relnotes-429209.html
 * @author zhangbaitong
 *
 */
public class Main {
	
	
	/**
	 * switch对字符和枚举型的支持
	 */
	public static void testSwitchString(){
		String test = "男";
		
		switch (test) {
		case "\u7537":
			System.out.println("I am a");
			break;
		case "baitong":
			System.out.println("I am baitong");
			break;

		default:
			System.out.println("I am default");
			break;
		}
		
		//-----------
		
		Sex sexenum = Sex.男;
		
		switch (sexenum) {
		case 男:
			System.out.println("I am boy");
			break;

		default:
			break;
		}
		
	}
	
	public enum Sex{
		男,女;
	}
	
	/**
	 * 对二进制自面值支持
	 */
	public static void testBinary(){
		System.out.println("八进制："+011);
		System.out.println("十六进制："+0x11);
		System.out.println("二进制："+0b11);
	}
	
	/**
	 * 字面量中使用下划线
	 * 整数和浮点数可以使用下划线来分割
	 */
	public static void testUnderLine(){
		System.out.println(500_00);
		System.out.println(500_00.222_11);
		System.out.println(1_2_3_3);
	}
	
	public static void testMutilExcpCatch() {
		Properties p = new Properties();
		FileInputStream fis;
		
		try {
			String values[] = new String[2];
			System.out.println(values[5]);
			//---互换上下两句可以看到异常都正常捕获
			fis = new FileInputStream(new File("/e:/bb/bb.txt"));
			p.load(fis);
		} catch (IOException|ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在try子句创建资源，系统可以帮助自动释放
	 * 如果try出现异常则会自动释放
	 * 如果释放出现异常，则抛出try和释放资源时的异常集合（通过Throwable.addSuppressed方法实现）
	 * 这种资源都实现了java.lang.AutoCloseable接口，会自动调用close方法，自己也可以实现这样的资源
	 * 系统的默认实现的接口：
	 * java.sql.connection
	 * java.sql.ResultSet
	 * java.io.BufferReader
	 * java.io.FileReader
	 * @return
	 */
	public static String testTryWithResources() {
		StringBuilder builder = new StringBuilder();
		//这里也支持多资源使用，分号分割即可
		try (BufferedReader reader = new BufferedReader(new FileReader(""));BufferedReader reader2 = new BufferedReader(new FileReader(""))){
			String line = null;
			while ((line=reader.readLine()) != null) {
				builder.append(line);
				builder.append(String.format("%n"));
			}
		} catch (Exception e) {
		}
		return builder.toString();
	}
	
	/**
	 * 不确定是否安全会提示警告，如果确定，可以使用安全注解消除警告
	 * @param args
	 * @return
	 */
	@SafeVarargs
	public static<T> T testSafeVarargs(T... args){
		return args.length>0?args[0]:null;
	}
	
	public static void testScriptEnginer() throws ScriptException{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			engine.eval("println('Hello JavaScript');");
		}
	}
	
	public static void testScriptEnginerTalk() throws ScriptException{
		ScriptEngine engine = getScriptEngin();
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			engine.put("name", "baitong");
			engine.eval("var msg = 'talk by js : ' + name;");
			engine.eval("println(msg);");
			Object obj = engine.get("msg");
			System.out.println("talk by java:" + obj);
		}
	}
	
	public static void testScriptEnginerBounds() throws ScriptException{
		ScriptEngine engine = getScriptEngin();
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			Bindings bindings = new SimpleBindings();
			bindings.put("name", "baitong");
			engine.eval("println('bingdings name is '+name);",bindings);
		}
	}
	
	private static ScriptEngine getScriptEngin(){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		return engine;
	}
	
	/**
	 * 脚本执行上下文
	 * 输入输出（默认为java.io.reader和java.io.writer使用控制台，可以通过setWriter和setReader进行重定向）
	 * 自定义属性
	 * 语言绑定对象
	 * @throws IOException 
	 */
	public static void testScritpContextIO() throws Exception{
		ScriptEngine engine = getScriptEngin();
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			ScriptContext context = engine.getContext();
			context.setWriter(new FileWriter("/Users/zhangtao/test.txt"));
			engine.eval("println('Hello baitong');");
		}
	}
	
	/**
	 * setAttribute,getAttribute
	 * 作用域：值越小查找顺序越优先，高优先级会覆盖低优先级的同名属性
	 * 脚本执行上下文实现中的作用域是固定的
	 * getScopes可以获得所有可用作用域对象
	 * 两个预定义作用域：
	 * ScriptContext.GLOBAL_SCOPE（当前脚本引擎）优先级高
	 * ScriptContext.ENGINE_SCOPE（从同一个引擎工厂创造出的所有脚本引擎）
	 * @throws ScriptException 
	 */
	public static void testScritpContextAttribute() throws ScriptException{
		ScriptEngine engine = getScriptEngin();
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			ScriptContext context = engine.getContext();
			context.setAttribute("name", "baitong", ScriptContext.GLOBAL_SCOPE);
			context.setAttribute("name2", "baitong2", ScriptContext.ENGINE_SCOPE);
			
			engine.eval("println(name);");
		}
	}
	
	public static void testScriptContextBounds() throws ScriptException{
		ScriptEngine engine = getScriptEngin();
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			ScriptContext context = engine.getContext();
			Bindings bindings1 = engine.createBindings();
			bindings1.put("name", "baitong");
			context.setBindings(bindings1, ScriptContext.GLOBAL_SCOPE);
			
			Bindings bindings2 = engine.createBindings();
			bindings2.put("name", "baitong2");
			context.setBindings(bindings2, ScriptContext.ENGINE_SCOPE);//优先级要高
			//如果没有指定作用域，默认使用的是ENGINE_SCOPE的变量
			engine.eval("println(name);");
		}
	}
	
	/**
	 * 实现Compilable接口的脚本引擎
	 * @throws ScriptException
	 */
	public static void testComplieAndRun() throws ScriptException{
		ScriptEngine engine = getScriptEngin();
		String scriptText = "println('a');"+"println('b');";
		CompiledScript script;
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			//get complie
			if(engine instanceof Compilable){
				script = ((Compilable)engine).compile(scriptText); 
				//run complie
				for(int i=0;i<2;i++){
					script.eval();
				}
			}
		}
	}
	
	/**
	 * 实现Invocable接口的脚本引擎
	 * @throws ScriptException
	 * @throws NoSuchMethodException 
	 */
	public static void testInvokeFunction() throws ScriptException, NoSuchMethodException{
		ScriptEngine engine = getScriptEngin();
		String scriptText = "function getName(name){println('Hello '+name);}";
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			engine.eval(scriptText);
			Invocable invocable = (Invocable)engine;
			invocable.invokeFunction("getName", "baitong");
		}
	}
	
	public static void testInvokeMethod() throws ScriptException, NoSuchMethodException{
		ScriptEngine engine = getScriptEngin();
		String scriptText = "var obj = {getName:function(name){return 'Hello '+name;}};";
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			engine.eval(scriptText);
			Invocable invocable = (Invocable)engine;
			Object scope = engine.get("obj");
			Object result = invocable.invokeMethod(scope, "getName", "baitong");
			System.out.println(result);
		}
	}
	
	public interface NameTestI{
		public String getName(String name);
	}
	
	public static void testUseInterface() throws ScriptException, NoSuchMethodException{
		ScriptEngine engine = getScriptEngin();
		String scriptText = "function getName(name){return 'Hello '+name;}";
		if(engine == null){
			System.out.println("Can not find JavaScript engine");
		}else{
			engine.eval(scriptText);
			Invocable invocable = (Invocable)engine;
			NameTestI name = invocable.getInterface(NameTestI.class);
			String result = name.getName("baitong");
			System.out.println(result);
		}
	}
	
	public static void executeCMD(String cmd) throws ScriptException, NoSuchMethodException{
		ScriptEngine engine = getScriptEngin();
		//添加打印行为
		if(cmd.indexOf("println") == -1){
			cmd = "println(" + cmd + ");";
		}
		Bindings bind = engine.createBindings();
		bind.put("name", "baitong");
		bind.put("value", "baitong-value");
		engine.eval(cmd,bind);
	}
	
	public static void runCMD(){
		Scanner scanner = new Scanner(System.in);
		while(true){
			String line = scanner.nextLine();
			if("quit".equals(line)){
				break;
			}
			try{
				executeCMD(line);
			}catch(Exception e){
				System.out.println("CMD is error!!");
			}
		}
	}
	
	
	
	
	public static void testx(){
		
	}
	
	/**
	 * 简化泛型声明
	 */
	public static void testGenericSimple(){
		HashMap<String,String> map = new HashMap<>();
	}
	
	/**
	 * 有些参数类型，例如ArrayList 和 List,是非具体化的(non-reifiable).
	 * 在编译阶段，编译器会擦除该类型信息
	 * Heap pollution 指一个变量被指向另外一个不是相同类型的变量
	 */
	public static void testNonReifiable(){
		List l = new ArrayList();
		List ls = l;// unchecked warning
		l.add(0, new Integer(42)); // another unchecked warning
		String s = (String) ls.get(0);      // ClassCastException is thrown 
	}
	
	/**
	 * 为什么没有警告？
	 * 消除的三种方式
	 * 1.加 annotation @SafeVarargs    
	 * 2.加 annotation @SuppressWarnings({"unchecked", "varargs"}) 
	 * 3.使用编译器参数 –Xlint:varargs;
	 * @param listArg
	 * @param elements
	 */
	public static <T> void testWarnning (List listArg, T... elements) {
		for (T x : elements) {
			listArg.add(x);
		}
	}
	
	/**
	 * 语法层面上支持集合，不再是数组的专利(jdk7没有这个特性)
	 */
	public static void testCollectionSupport(){
		//final List<Integer> piDigits = [1,2,3,4,5,8]; 
		//List<Integer> intList = [3,4,5];
		//Set<Integer> primes = { 2, 7, 31, 127, 8191, 131071, 524287 };
		//Map<Integer, String> platonicSolids = { 4 : "tetrahedron",       6 : "cube", 8 : "octahedron", 12 : "dodecahedron", 20 : "icosahedron"      }; 
	}

	public static void main(String[] args) throws Throwable {
		testSwitchString();
		testBinary();
		testUnderLine();
		testMutilExcpCatch();
		testScriptEnginer();
		testScriptEnginerTalk();
		testScriptEnginerBounds();
		testScritpContextIO();
		testScritpContextAttribute();
		testScriptContextBounds();
		testComplieAndRun();
		testInvokeFunction();
		testInvokeMethod();
		testUseInterface();
		//runCMD();
		
	}

}
