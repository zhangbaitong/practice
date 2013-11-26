package com.zbt.trie.test256;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zbt.scene.countword.singlethread.CalcUtil;

public class Trie {
	private String fileName;
	private TrieNode root;

	public Trie(String fileName) {
		this.fileName = fileName;
		root = new TrieNode();
	}

	public void parse() {
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(this.fileName));
			String line = null;
			int lineno = 0;

			Pattern pattern = Pattern.compile("\\w*", Pattern.UNICODE_CASE);
			Matcher m;

			while ((line = br.readLine()) != null) {
				lineno++;
				m = pattern.matcher(line);
				while (m.find()) {
					int startPos = m.start();
					String subStr = m.group();
					if (subStr.length() == 0) {
						continue;
					}
					this.insert(subStr, lineno, startPos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.root = null;
		}
	}

	/** */
	/**
     * 
     */
	private TrieNode search(byte[] strBytes, int index, TrieNode node) {
		if (index >= strBytes.length) {
			// System.out.println("查找次数：" + index);
			return node;
		}
		//对strBytes[index]使用255做位运算，清除高八位，保留低八位，限制数字在256
		int indexInNode = strBytes[index] & 0xFF;
		TrieNode trieNode = node.getChildAt(indexInNode);
		if (trieNode == null) {
			trieNode = new TrieNode();
			node.addChild(indexInNode, trieNode);
		}
		return search(strBytes, ++index, trieNode);
	}

	public TrieNode search(String search) {
		return search(search.getBytes(), 0, this.root);
	}

	// 应该首先查找，直到找到该字符串中没有走到但是还需要走的路
	private void insert(String str, int lineno, int pos) {
		byte[] strBytes = str.getBytes();

		TrieNode trieNode = search(strBytes, 0, this.root);
		trieNode.setValue(str);
		trieNode.addPosition(lineno, pos);
	}

	public void dump() {
		if (this.root != null) {
			System.out.println("-----");
			String searchStr = "girl";
			TrieNode trieNode = this.search(searchStr);
			if (trieNode.getValue() != null
					&& trieNode.getValue().equals(searchStr)) {
				trieNode.dump();
			}
		}
	}

	// 深度优先
	private void dumpAll(TrieNode trieNode) {
		String value = trieNode.getValue();
		if (value != null) {
			System.out.println("##" + value + "##");
			trieNode.dump();
		}
		TrieNode node;
		for (int i = 0; i < 256; i++) {
			node = trieNode.getChildAt(i);
			if (node != null) {
				dumpAll(node);
			}
		}
	}

	public void dumpAll() {
		this.dumpAll(this.root);

	}

	public static void main(String[] args) {
		Trie trie = new Trie(CalcUtil.FILENAME);
		trie.parse();
		trie.dump();
		// trie.dumpAll();
	}
}