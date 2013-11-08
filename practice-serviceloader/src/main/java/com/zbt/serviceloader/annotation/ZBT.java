package com.zbt.serviceloader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义Annotation

//定义注解的保留级别
@Retention(RetentionPolicy.RUNTIME)
/**
 * class：保存在class中，但不加载到vm，运行时无法获取，也是默认设置
 * runtime:保存在class中，运行时也可获取
 * source:编译时舍弃
 */
//定义注解的目标，默认是全部支持
@Target(ElementType.METHOD)
public @interface ZBT {
	//定义属性
	//格式：属性类型+属性名称+括号  
	String name() default "zbt";
	
	String type();

}
