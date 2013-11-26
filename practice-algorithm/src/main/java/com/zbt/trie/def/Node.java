package com.zbt.trie.def;


import java.util.HashMap;
import java.util.Map;


public class Node {
	//公共存储字符和索引映射规则的集合,也可以考虑使用位运算符
	private static final Map<Object,Integer> map = new HashMap<Object,Integer>();
	private static int index = 0;
    protected int words;
    protected int prefixes;
    protected Node[] childs;
    protected String content;
    public Node() {
        words = 0;
        prefixes = 0;
        //默认广度为60，也可以改造为通过构造函数传递，更好的可以实现为自增长
        //（空间开销变大，是否对性能有影响，应该根据实际数据来指定广度）
        childs = new Node[60];
        for (int i = 0; i < childs.length; i++) {
        	childs[i] = null;
        }
    } 
    
    public void addChild(String content){
    	this.content = content;
    }
    
    //生成映射索引
    public int getIndex(String content){
    	Integer index = map.get(content);
    	if(index == null){
    		map.put(content, this.index);
    		this.index++;
    		return this.index - 1;
    	}else{
    		return index.intValue();
    	}
    	
    }
    
    @Override
    /**
     * 重写toString为了在debug的时候更容易看到具体信息
     */
    public String toString() {
    	return "Content:"+this.content+",index:"+this.index+",words:"+this.words+",prefixes:"+this.prefixes;
    }
    
    public static void main(String[] args) {
	}
} 
