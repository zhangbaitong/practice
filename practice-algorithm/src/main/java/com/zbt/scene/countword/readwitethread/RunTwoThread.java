package com.zbt.scene.countword.readwitethread;



//-Xms1024m -Xmx1024m
public class RunTwoThread {
	
	public static void main(String[] args) {
		Container c = new Container();
		new ReadThread(c).start();
		new CalcThread(c).start();
	}
}
