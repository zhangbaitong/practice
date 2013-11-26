package com.zbt.trie.abc;

/**
 * Trie的节点类
 * @author zbt
 *
 */
public class Node {
	
	//单词个数
    protected int words;
    //前缀个数
    protected int prefixes;
    //这里包含26个字母节点
    protected Node[] childs;
    Node() {
        words = 0;
        prefixes = 0;
        childs = new Node[26];
        for (int i = 0; i < childs.length; i++) {
        	childs[i] = null;
        }
    }

}
