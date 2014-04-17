package org.practice.eda.common;

public class Log {
	
	
	private static String LEVEL = "DEBUG";
	
	public static String getLEVEL() {
		return LEVEL;
	}

	public static void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}

	public static String LEVEL_INFO = "INFO";
	
	public static String LEVEL_DEBUG = "DEBUG";
	
	
	public static void log(String msg){
		System.out.println(msg);
	}
	
	public static void log(String msg1,String msg2){
		log(msg1 + " : " + msg2);
	}
	
	public static void debug(String msg){
		if(LEVEL.equals(LEVEL_DEBUG)){
			log(msg);
		}
	}
	
	public static void debug(String msg1,String msg2){
		if(LEVEL.equals(LEVEL_DEBUG)){
			log(msg1,msg2);
		}
	}

}
