package com.zbt.scene.countword.singlethread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunOneThread2 {

	public static void main(String[] args) throws Exception {
		BufferedReader buf = new BufferedReader(new FileReader("english.txt"));
		System.out.println("通过读取 English.txt");
		StringBuffer sbuf = new StringBuffer();
		String line = null;
		while ((line = buf.readLine()) != null) {
			sbuf.append(line);
		}
		buf.close();
		Pattern expression = Pattern.compile("[a-zA-Z]+");
		String string1 = sbuf.toString().toLowerCase();
		Matcher matcher = expression.matcher(string1);
		TreeMap myTreeMap = new TreeMap();
		int n = 0;
		Object word = null;
		Object num = null;
		while (matcher.find()) {
			word = matcher.group();
			n++;
			if (myTreeMap.containsKey(word)) {
				num = myTreeMap.get(word);
				Integer count = (Integer) num;
				myTreeMap.put(word, new Integer(count.intValue() + 1));
			} else {
				myTreeMap.put(word, new Integer(1));
			}
		}
		System.out.println("统计分析如下：");
		System.out.println("文章中单词总数" + n + "个");
		System.out.println("具体的信息在当前目录的result.txt文件中");
		BufferedWriter bufw = new BufferedWriter(new FileWriter("result.txt"));
		Iterator iter = myTreeMap.keySet().iterator();
		Object key = null;
		while (iter.hasNext()) {
			key = iter.next();
			bufw.write((String) key + ":" + myTreeMap.get(key));
			bufw.newLine();
		}
		bufw.write("english.txt中单词的出现次数" + n + "个");
		bufw.newLine();
		bufw.write("english.txt中单词的个数" + myTreeMap.size() + "个");
		bufw.close();
	}

}
