package com.zbt.string;

public class CharString {

	private char[] cs;
	int start;
	int end;
	private int hash;

	public CharString(char[] cs, int start, int end, int hash) {
		this.cs = cs;
		this.start = start;
		this.end = end;
		this.hash = hash;
	}

	public CharString(String str) {
		cs = str.toCharArray();
		this.start = 0;
		this.end = cs.length;
	}

	public char[] getCs() {
		return cs;
	}

	public void setCs(char[] cs) {
		this.cs = cs;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0 && end > 0) {
			for (int i = start; i < end; i++) {
				h = 31 * h + cs[i];
			}
			hash = h;
		}
		return h;
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof CharString) {
			CharString anotherString = (CharString) anObject;
			if ((this.end - this.start) != (anotherString.end - anotherString.start)) {
				return false;
			}
			for (int i = this.start; i < this.end; i++) {
				if (cs[i] != anotherString.cs[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return new String(cs, start, (end - start));
	}

	public static void main2(String[] args) {
		char[] a = new char[4];
		a[0] = 't';
		a[1] = 'e';
		a[2] = 's';
		a[3] = 't';
		CharString aa = new CharString(a, 0, 4, 0);
		int h = aa.hashCode();
		System.out.println(h);
	}
	
	public static void main(String[] args) {
		String str = "I am test for your char string with sting";
		char[] cf = str.toCharArray();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 9999999; i++) {
			//string
			//String temp = new String(cf,9,9);
			//temp.toString();
			//char string
			CharString temp = new CharString(cf,9,9,0);
			//temp.toString();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}
}
