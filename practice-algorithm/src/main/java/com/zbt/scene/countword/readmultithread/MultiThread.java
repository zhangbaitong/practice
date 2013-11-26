package com.zbt.scene.countword.readmultithread;

import com.zbt.scene.countword.singlethread.CalcUtil;


public class MultiThread extends Thread {
	
	private ContainerMulti c;
	
	private Integer id;
	
	public MultiThread(int id,ContainerMulti c){
		this.c = c;
		this.id = new Integer(id);
	}

	public void run() {
		
		while(!c.isFinished(id)){
			try {
				//Date old = new Date();
				String lineString = c.getQueue(id).take();
				CalcUtil.countString(lineString, c.getKeys());
				//TimeUtil.calc("count string",old);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
