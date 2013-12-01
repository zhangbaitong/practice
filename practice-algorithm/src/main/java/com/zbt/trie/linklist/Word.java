package com.zbt.trie.linklist;


import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Word implements Serializable, Comparable<Word> {

	private static final long serialVersionUID = 5093838179448043660L;

	private String word;

	private int num;

	public Word() {
	}

	public Word(String word, int num) {
		super();
		this.word = word;
		this.num = num;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void add(int num) {
		this.num += num;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int compareTo(Word o) {
		if (null == o) {
			return 1;
		}
		return o.num - this.num;
	}

}