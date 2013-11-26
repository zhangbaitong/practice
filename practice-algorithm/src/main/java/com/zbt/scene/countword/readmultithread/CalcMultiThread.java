package com.zbt.scene.countword.readmultithread;


public class CalcMultiThread extends Thread{
	
	private ContainerMulti c;
	
	public CalcMultiThread(ContainerMulti c){
		this.c = c;
	}
	
	
	public void run() {
		for(int i=0;i<ContainerMulti.threadNum;i++){
			new MultiThread(i,c).start();
		}
		while(!c.isFinished()){
		}
		c.print();
	}
	
	public static void main(String[] args) {
		new CalcMultiThread(new ContainerMulti()).start();
	}
}
	
