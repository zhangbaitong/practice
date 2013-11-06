package com.zbt.serviceloader;

import java.util.ServiceLoader;

public class AnimalFinder {

	public static void main(String[] args) {
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

	}

}
