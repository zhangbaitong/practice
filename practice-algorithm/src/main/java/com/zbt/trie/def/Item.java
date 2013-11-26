package com.zbt.trie.def;

/**
 * 作为返回所有节点数据时的数据结构。
 * 包含：字符全名称，出现次数，排序规则
 * @author zbt
 *
 */
public class Item implements Comparable<Item>{
	
	private String name;
	private int num;
	
	public Item(String name,int num){
		this.name = name;
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int compareTo(Item item) {
		return item.getNum() - this.getNum();
	}
	
	public String toString() {
		return this.getName() + ":" + this.getNum();
	}

}
