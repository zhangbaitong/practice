package com.zbt.scene.countword.singlethread.mt;


public class Word implements Comparable<Word>{
	
	private String name;
	
	private int number;
	
	public Word(String name,int number){
		this.name = name;
		this.number = number;
	}
	
	public int getNum(){
		return this.number;
	}
	
	public void setNum(int number){
		this.number = number;
	}
	
	public String getWord(){
		return this.name;
	}

	public int compareTo(Word o) {
		return o.getNum() - this.getNum();
	}

}
