package com.zbt.scene.countword.onetriethread;


import java.util.Hashtable;

import com.zbt.trie.def.DefaultTrie;

public class CalcUtilTrie {
	
	public static String KEY_RULE = "\\s|\\.|,|;|-|~|\"|\\?|'|!";
	
	public static Hashtable withoutKey = new Hashtable();
	
	public static String FILENAME_S = "your file name";
	public static String FILENAME_M = "your file name";
	public static String FILENAME = FILENAME_M;
	
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
	
	public static void countString(String str,DefaultTrie trie){
		if(str == null || str.length() == 0)return;
			//System.out.println(str);
			String[] temp = split(str);
			for (String string : temp) {
				//System.out.println("key:"+string);
				if(string == null || string.length() == 0)continue;
				string = string.toLowerCase();
				if(withoutKey.containsKey(string))continue;
				//trie.addWord(string);
				//System.out.println("ALL:"+trie.listWords());
			}
	}
	
	private static String[] split(String line){
		return line.split(KEY_RULE);
	}

}
