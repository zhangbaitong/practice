package com.zbt.scene.countword.singlethread;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RunOneThread {
	
	private static Map<String,Integer> keys = new HashMap<String,Integer>();
	
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				//Date old = new Date();
				CalcUtil.countString(tempString, keys);
				//TimeUtil.calc("count string",old);
				line++;
				//if(line ==  12)break;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	


	public static void main(String[] args) {
		Date old = new Date();
		readFileByLines(CalcUtil.FILENAME);
		Date now = new Date();
		List<Entry<String,Integer>> retList = new ArrayList<Entry<String,Integer>>(keys.entrySet());
		Collections.sort(retList, new Comparator<Map.Entry<String, Integer>>() {    
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {    
                return (o2.getValue() - o1.getValue());    
            }
        });
		for(int i=0;i<retList.size();i++){
			Entry<String,Integer> e = retList.get(i);
			System.out.println(e.getKey() + " : " + e.getValue());
			if(i == 9 )break;
		}
		TimeUtil.calc(old);
	}
}
