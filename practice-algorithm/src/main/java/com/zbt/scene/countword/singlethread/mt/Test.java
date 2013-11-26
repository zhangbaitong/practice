package com.zbt.scene.countword.singlethread.mt;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zbt.scene.countword.singlethread.CalcUtil;

public class Test {
	static final String[] stopWords = { ".", ",", ";", "-", "~", "\"", "?", "'", "!", "the", "and", "i", "to", "of", "a", "in", "was", "that", "had",
			"he", "you", "his", "my", "it", "as", "with", "her", "for", "on" };
	static final String[] stopWordsNew = { "the", "and", "i", "to", "of", "a", "in", "was", "that", "had", "he", "you", "his", "my", "it", "as",
			"with", "her", "for", "on" };
	static final String strip1 = "\\.|\\,|\\;|\\-|\\~|\\\"|\\?|\\'|\\!|\\s+";
	static final String strip2 = "the|and|i|to|of|a|in|was|that|had|he|you|his|my|it|as|with|her|for|on";
	static final String fileName = CalcUtil.FILENAME_S;
	static final Map<String, Word> map = new HashMap<String, Word>(20000);
	static final String empty = "\\s+";
	private final static int MAX_WORD_LEN = 255;

	public static void main(String[] args) throws IOException {
		long begin = System.currentTimeMillis();
		Set<String> stopSet = new HashSet<String>();
		for (int i = 0; i < stopWordsNew.length; i++) {
			stopSet.add(stopWordsNew[i].toLowerCase());
		}
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line = null;
		while ((line = in.readLine()) != null) {
			char[] cList = line.toLowerCase().trim().toCharArray();
			char[] bf = new char[MAX_WORD_LEN];
			int pointer = 0;
			for (int i = 0; i < cList.length; i++) {
				switch (Character.toLowerCase(cList[i])) {
				case '.':
				case ',':
				case ';':
				case '-':
				case '~':
				case '"':
				case '?':
				case '!':
				case '\'':
					break;
				case ' ':
					if (pointer == 0)
						break;
					String word = new String(bf, 0, pointer).trim().toLowerCase();
					if (stopSet.contains(word)) {
						bf = new char[MAX_WORD_LEN];
						pointer = 0;
						break;
					}
					Word w = map.get(word);
					if (null == w)
						map.put(word, new Word(word, 1));
					else
						map.get(word).setNum(w.getNum() + 1);
					bf = new char[MAX_WORD_LEN];
					pointer = 0;
					break;
				default:
					if (Character.getType(cList[i]) != Character.FORMAT) {
						bf[pointer] = cList[i];
						pointer++;
					}
				}
				if (i == cList.length - 1) {
					if (pointer != 0) {
						String word = new String(bf, 0, pointer).trim().toLowerCase();
						if (stopSet.contains(word)) {
							bf = new char[MAX_WORD_LEN];
							pointer = 0;
							continue;
						}
						Word w = map.get(word);
						if (null == w)
							map.put(word, new Word(word, 1));
						else
							map.get(word).setNum(w.getNum() + 1);
					}
				}
			}

		}
		System.out.println("build Map time=" + (System.currentTimeMillis() - begin) / 1000.0 + "s");
		Collection<Word> c = map.values();
		List<Word> a = new ArrayList<Word>(c);
		Collections.sort(a);
		System.out.println("sort time=" + (System.currentTimeMillis() - begin) / 1000.0 + "s");
		for (int index = 0; index < 10; index++) {
			Word b = a.get(index);
			System.out.println(b.getWord() + " num=" + b.getNum());
		}
		System.out.println(a.size());
	}
}
