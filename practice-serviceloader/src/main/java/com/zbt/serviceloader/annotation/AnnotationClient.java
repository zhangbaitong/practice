package com.zbt.serviceloader.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationClient {
	
	public static void main(String[] args) throws Exception {
		//通过反射获得注解信息
		Class<AnnotationTest> c = AnnotationTest.class;
		//得到不带参数的类的方法
		Method method = c.getMethod("output", new Class[]{});
		//判断方法前是否存在某个注解
		if(method.isAnnotationPresent(ZBT.class)){
			//通过反射执行类方法
			AnnotationTest test = new AnnotationTest();
			method.invoke(test, new Object[]{});
			//通过反射获得指定注解
			ZBT zbt = method.getAnnotation(ZBT.class);
			//打印注解包含的值
			System.out.println(zbt.name());
			System.out.println(zbt.type());
		}
		//获得所有注解
		Annotation[] annotations = method.getAnnotations();
		//打印注解的类名称,RESOURCE不被打印，因为没有被编译到class
		for(Annotation ans : annotations){
			System.out.println(ans.annotationType().getName());
		}
	}

}
