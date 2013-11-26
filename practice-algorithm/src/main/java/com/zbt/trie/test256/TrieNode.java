package com.zbt.trie.test256;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TrieNode {
	// Attributes
	private final int CHILD = 256;
	private String value;
	private TrieNode[] child = new TrieNode[CHILD];
	private Map<Integer, List<Integer>> positions;

	// Constructor
	public TrieNode() {
	}

	public TrieNode(String v) {
		this.value = v;
	}

	// Method (set/get)
	public void setValue(String v) {
		this.value = v;
	}

	public String getValue() {
		return this.value;
	}

	public void addChild(int index, TrieNode tn) {
		this.child[index] = tn;
	}

	public TrieNode getChildAt(int index) {
		if (index > CHILD - 1 || index < 0) {
			return null;
		}
		return this.child[index];
	}

	public void addPosition(int lineno, int pos) {
		Integer lineInt = new Integer(lineno);
		if (this.positions == null) {
			this.positions = new TreeMap<Integer, List<Integer>>();
		}
		List<Integer> posList = this.positions.get(lineInt);
		if (posList == null) {
			posList = new LinkedList<Integer>();
		}
		posList.add(new Integer(pos));
		this.positions.put(lineInt, posList);
	}

	public List<Integer> getPosition(int lineno) {
		return this.positions.get(Integer.valueOf(lineno));
	}

	public Map<Integer, List<Integer>> getPositions() {
		return this.positions;
	}

	public void dump() {
		for (Map.Entry<Integer, List<Integer>> entry : this.positions
				.entrySet()) {
			System.out.print(entry.getKey().intValue() + " - ");
			for (Integer pos : entry.getValue()) {
				System.out.print(pos.intValue() + ":");
			}
			System.out.println();
		}
	}
}