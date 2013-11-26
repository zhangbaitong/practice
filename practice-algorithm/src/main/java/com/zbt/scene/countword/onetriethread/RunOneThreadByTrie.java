package com.zbt.scene.countword.onetriethread;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.zbt.scene.countword.singlethread.TimeUtil;
import com.zbt.trie.def.DefaultTrie;
import com.zbt.trie.def.Item;

public class RunOneThreadByTrie {
	
	public static DefaultTrie trie = new DefaultTrie();
	
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				//Date old = new Date();
				CalcUtilTrie.countString(tempString, trie);
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
		readFileByLines(CalcUtilTrie.FILENAME);
		TimeUtil.calc(old);
		List<Item> retList = trie.listWords();
		TimeUtil.calc(old);
		Collections.sort(retList);
		for(int i=0;i<retList.size();i++){
			Item e = retList.get(i);
			System.out.println(e.toString());
			if(i == 9 )break;
		}
		TimeUtil.calc(old);
		//System.out.println(trie.countWords("girl"));
	}
}
