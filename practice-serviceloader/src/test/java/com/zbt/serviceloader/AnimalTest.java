package com.zbt.serviceloader;

import static org.junit.Assert.*;

import java.util.ServiceLoader;

import org.junit.Test;

public class AnimalTest {

	@Test
	public void test() {
		ServiceLoader<IAnimal> serviceLoader = ServiceLoader.load(IAnimal.class);
		System.out.println("running ...");
		String result = "";
		for(IAnimal animal : serviceLoader){
			result = animal.call();
			System.out.println(animal.call());
		}
		if(result.equals("")){
			System.out.println("can't find ...");
		}
		assertEquals("喵~喵~", result);
	}

}
