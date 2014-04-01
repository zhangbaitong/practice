package com.zbt.concurrent.collections.example1;

import java.util.ArrayList;
import java.util.List;

public class Service implements Runnable{
	
	public static List list = new ArrayList();
	
	public static void init(){
		list.add("zhang");
		list.add("baitong");
	}
	
	public static List getList(){
		return list;
	}

	public static void main(String[] args) {
		System.out.println(list.size());
	}

	@Override
	public void run() {
		System.out.println(list.size());
		
	}

}
