package com.zbt.scene.countword.singlethread;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import com.zbt.scene.countword.readmultithread.ContainerMulti;

public class CalcUtil {
	
	public static String KEY_RULE = "\\s|\\.|,|;|-|~|\"|\\?|'|!";
	
	public static Hashtable withoutKey = new Hashtable();
	
	public static String FILENAME_S = "D:/Documents and Settings/_jg/Desktop/test-s.txt";
	public static String FILENAME_M = "D:/Documents and Settings/_jg/Desktop/test-d.txt";
	public static String FILENAME_1g = "D:/Documents and Settings/zhangtao_jg/Desktop/test1g.txt";
	public static String FILENAME_S2 = "D:/Documents and Settings/zhangtao_jg/Desktop/test.txt";
	public static String FILENAME = FILENAME_1g;
	
	static{
		withoutKey.put("the", "");
		withoutKey.put("and", "");
		withoutKey.put("i", "");
		withoutKey.put("to", "");
		withoutKey.put("of", "");
		withoutKey.put("a", "");
		withoutKey.put("in", "");
		withoutKey.put("was", "");
		withoutKey.put("that", "");
		withoutKey.put("had", "");
		withoutKey.put("he", "");
		withoutKey.put("you", "");
		withoutKey.put("his", "");
		withoutKey.put("my", "");
		withoutKey.put("it", "");
		withoutKey.put("as", "");
		withoutKey.put("with", "");
		withoutKey.put("her", "");
		withoutKey.put("for", "");
		withoutKey.put("on", "");
	}
	
	public static void countString2(String str,Map<String,Integer> keys){
		if(str == null || str.length() == 0)return;
			//System.out.println(lineString);
			String[] temp = split(str);
			//95.945 s
			for (String string : temp) {
				//System.out.println("key:"+string);
				//93.587 s
				if(string == null || string.length() == 0)continue;
				string = string.toLowerCase();
				if(withoutKey.containsKey(string))continue;
				//108.661 s
				if(keys.containsKey(string)){
					Integer num = new Integer(keys.get(string)).intValue();
					num++;
					keys.put(string, num);
				}else{
					keys.put(string, new Integer(1));
				}
			}
	}
	
	public static void countString(String str,Map<String,Integer> keys){
		//if(str == null || str.length() == 0)return;
//		try {
//			StreamTokenizer token2 = new StreamTokenizer(new FileReader(new File("")));
//		} catch (FileNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
		StringTokenizer token = new StringTokenizer(str);
		while (token.hasMoreElements()) {
			String string = (String) token.nextToken(" .,;-~\"?'!");
			//if(string == null || string.length() == 0)continue;
			string = string.toLowerCase();
			//57.63 s
			//57.667 s
			if(withoutKey.containsKey(string))continue;
			if(keys.containsKey(string)){
				Integer num = keys.get(string);
				num++;
				keys.put(string, num);
			}else{
				keys.put(string, new Integer(1));
			}
		}
	}
	
	private static String[] split(String line){
		return line.split(KEY_RULE);
	}
	
	public static void countString(String lineStr){
		StringTokenizer token = new StringTokenizer(lineStr);
		while (token.hasMoreElements()) {
			String str = (String) token.nextToken(", ?!:'\n");
			System.out.println(str);
		}
	}
	
	public static void main(String[] args) {
		countString("I am a ,small by?hehe:ddd!ddd'asdf");
	}

}
