package com.zbt.scene.countword.readwitethread;


import com.zbt.scene.countword.singlethread.CalcUtil;

public class CalcThread extends Thread{
	
	private Container c;
	
	public CalcThread(Container c){
		this.c = c;
	}
	
	public void run() {
		while(!c.isFinished()){
			String lineString = c.get();
			//if(lineString.length() == 0)continue;
			CalcUtil.countString(lineString, c.getKeys());
		}
		c.print();
    }
	
	public static void main(String[] args) {
		new CalcThread(new Container()).start();
	}
}
