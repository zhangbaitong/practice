package com.zbt.trie.def;

import java.util.ArrayList;
import java.util.List;


/**
 * 实现一个不限制内容的trie。
 * 其中根据单个节点字符为key做索引映射，然后对应节点，默认广度为60
 * @author zhangtao_jg
 *
 */
public class DefaultTrie{
	
	private Node root = new Node();
	
	public List<Item> listWords() {
        List<Item> words = new ArrayList<Item>();
        Node[] childs = root.childs;
       
        for (int i = 0; i < childs.length; i++) {
            if (childs[i] != null) {
                String word = childs[i].content+"";
                findChildWord(word,childs[i],words);
            }
        }        
        return words;
	}
	
	private void findChildWord(String word,Node node,List<Item> words){
		for (Node child : node.childs) {
			if(child != null){
				String newWord = word + child.content;
				findChildWord(newWord,child,words);
			}
		}
		if(node.words > 0){
			words.add(new Item(word,node.words));
		}
	}

	public int countPrefixes(String prefix) {
		return countPrefixes(prefix,root);
	}
	
	private int countPrefixes(String prefix,Node node) {
		if(prefix.length() == 0){//最后一个节点
			return node.prefixes;
		}else{
			String c = prefix.substring(0,1);
			c = c.toLowerCase();
			int index = node.getIndex(c);
			//没有这个词
			if(node.childs[index] == null)return 0;
			return countPrefixes(prefix.substring(1),node.childs[index]);
		}
	}

	public int countWords(String word) {
		return contWords(word, root);
	}
	
	private int contWords(String word,Node node){
		if(word.length() == 0){//最后一个节点
			return node.words;
		}else{
			String c = word.substring(0,1);
			c = c.toLowerCase();
			int index = node.getIndex(c);
			
			//没有这个词
			if(node.childs[index] == null)return 0;
			return contWords(word.substring(1),node.childs[index]);
		}
	}

	public void addWord(String word) {
		addWord(word,root);
	}
	
	private void addWord(String word,Node node){
		//最后一个节点
		if(word.length() == 0){
			node.words++;
		}else{
			String c = word.substring(0,1);
			c = c.toLowerCase();
			node.prefixes++;
			int index = node.getIndex(c);
			if (node.childs[index] == null) {
				node.childs[index] = new Node();
				node.childs[index].addChild(c);
			}
			addWord(word.substring(1),node.childs[index]);
		}
	}
	
	public static void main(String[] args) {
		DefaultTrie trie = new DefaultTrie();
		
		trie.addWord("hello");
		trie.addWord("zhang");
		trie.addWord("zhang");
		trie.addWord("zHangbt");
		System.out.println(trie.countWords("hello"));
		System.out.println(trie.countWords("zhang"));
		System.out.println(trie.countPrefixes("zh"));
		System.out.println(trie.listWords());
		trie.addWord("Girl");
		trie.addWord("almost");
		trie.addWord("girl");
		System.out.println(trie.listWords());
		System.out.println(trie.countWords("girl"));
	}

}