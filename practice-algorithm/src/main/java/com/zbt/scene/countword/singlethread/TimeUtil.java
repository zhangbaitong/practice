package com.zbt.scene.countword.singlethread;


import java.util.Date;

public class TimeUtil {
	
	public static void calc(Date old){
		System.out.println((new Date().getTime() - old.getTime())/1000.0 + " s");
	}
	
	public static void calc(String msg,Date old){
		System.out.println((msg + ":" + (new Date().getTime() - old.getTime())/1000.0000) + " s");
	}

}
