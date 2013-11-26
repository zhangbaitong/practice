package com.zbt.trie.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 对26个字母进行构建 ，根据26个字母和a字母的差值作为索引
 * 
 * @author zbt
 * 
 */
public class Trie {

	private Node root;

	public Trie() {
		root = new Node();
	}

	public List<String> listWords() {

		List<String> words = new ArrayList<String>();
		Node[] childs = root.childs;
		// 循环遍历root下第一层节点
		for (int i = 0; i < childs.length; i++) {
			if (childs[i] != null) {
				String word = "" + (char) ('a' + i);
				findWordsByNode(words, childs[i], word);
			}
		}
		return words;
	}

	private void findWordsByNode(List<String> words, Node Node,
			String wordSegment) {
		Node[] childs = Node.childs;
		boolean hasChildren = false;
		for (int i = 0; i < childs.length; i++) {
			if (childs[i] != null) {
				hasChildren = true;
				String newWord = wordSegment + (char) ('a' + i);
				// 递归自节点
				findWordsByNode(words, childs[i], newWord);
			}
		}
		// 如果没有自节点，说明这个节点是一个word
		if (!hasChildren) {
			words.add(wordSegment);
		}
	}

	public int countPrefixes(String prefix) {
		return countPrefixes(root, prefix);
	}

	private int countPrefixes(Node Node, String prefixSegment) {
		// 当前缀片段长度为零时，即到达最后一个字符
		if (prefixSegment.length() == 0) {
			return Node.prefixes;
		}

		char c = prefixSegment.charAt(0);
		int index = c - 'a';
		if (Node.childs[index] == null) {
			return 0;
		} else {
			// 递归自节点直到最后一个字符，返回节点的前缀数
			return countPrefixes(Node.childs[index], prefixSegment.substring(1));

		}

	}

	public int countWords(String word) {
		return countWords(root, word);
	}

	private int countWords(Node Node, String wordSegment) {
		// 如果是最后一个字符，则返回word个数
		if (wordSegment.length() == 0) {
			return Node.words;
		}

		char c = wordSegment.charAt(0);
		int index = c - 'a';
		if (Node.childs[index] == null) {
			return 0;
		} else {
			// 递归寻找子节点的word个数
			return countWords(Node.childs[index], wordSegment.substring(1));

		}

	}

	public void addWord(String word) {
		addWord(root, word);
	}

	private void addWord(Node Node, String word) {
		System.out.println(word);
		// 如果字符长度为零，则为最后一个字符，其他都被加入，那么认为单词已经被添加完毕
		if (word.length() == 0) {
			Node.words++;
		} else {
			Node.prefixes++;
			char c = word.charAt(0);
			c = Character.toLowerCase(c);
			int index = c - 'a';
			if (Node.childs[index] == null) {
				Node.childs[index] = new Node();
			}
			// 递归添加word的每一个字符
			addWord(Node.childs[index], word.substring(1));
		}
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.addWord("helloword");
		trie.addWord("helloword");
		trie.addWord("helloword");

		trie.addWord("goodbyebye");
		trie.addWord("goodbye");
		trie.addWord("byebye");

		trie.addWord("nice");
		trie.addWord("good");
		trie.addWord("nice");

		trie.addWord("abcdefg");
		System.out.println("All prefixes:" + trie.root.prefixes);
		List<String> list = trie.listWords();
		System.out.println("All words:" + list.size());

		for (String string : list) {
			System.out.println(string);
		}

		System.out.println("the count of hell prefixes:"
				+ trie.countPrefixes("hell"));
		System.out.println("the count of helloword countWords:"
				+ trie.countWords("helloword"));

	}

}
