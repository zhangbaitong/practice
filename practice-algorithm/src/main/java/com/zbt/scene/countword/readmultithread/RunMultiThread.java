package com.zbt.scene.countword.readmultithread;



//-Xms1024m -Xmx1024m
public class RunMultiThread {
	
	public static void main(String[] args) {
		ContainerMulti c = new ContainerMulti();
		new ReadMultiThread(c).start();
		new CalcMultiThread(c).start();
	}
}
