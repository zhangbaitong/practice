package org.practice.rxjava;

public class Test {
	
	public Test(String myName){
		name = myName;
	}
	
	private String name;
	
	public void setName(String newName){
		name = newName;
	}
	
	
	public String getName(){
		return name;
	}
	
	public static void change(Test obj){
		System.out.println("obj in method:"+obj);
		obj = new Test("b");
		System.out.println("obj2 in method:"+obj);
		System.out.println(obj.getName());
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test a = new Test("a");
		System.out.println(a.getName());
		System.out.println("a out method:"+a);
		Test.change(a);
		System.out.println("a out method:"+a);
		System.out.println(a.getName());

	}

}
