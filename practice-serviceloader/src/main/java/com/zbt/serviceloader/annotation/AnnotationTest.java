package com.zbt.serviceloader.annotation;

public class AnnotationTest {
	
	//使用注解标注
	
	@ZBT(name="zbt",type="test")
	@Deprecated
	@SuppressWarnings("unchecked")
	public void output(){
		System.out.println("This is AnotationTest run...");
	}

}
