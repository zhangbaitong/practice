package com.zbt.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetMaxNum {

	public static void main(String[] args) {
		secondMethod();
	}
	
	private static int getFullNum(int num,int size){
		return new Double((num+1)*Math.pow(10,size)-1).intValue();
	}
	
	/**
	 * 精简版
	 */
	public static void secondMethod(){
		List list = Arrays.asList(1, 3, 10, 22, 16, 0, 99, 100, 9, 91, 21, 32, 31);
		Collections.sort(list,new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				int asize = (""+o1).length();
				int bsize = (""+o2).length();
				if(asize > bsize){
					int size = asize - bsize;
					if(o1 < getFullNum(o2,size))return 1;
					return -1;
				}else{//相等的情况这里也可以处理
					int size = bsize - asize;
					if(getFullNum(o1,size) < o2)return 1;
					return -1;
				}
			}
		});
		for(int i=0;i<list.size();i++){
			//99991332312221116101000
			System.out.print(list.get(i));
		}
	}
	
	/**
	 * 草稿
	 */
	public static void firstMethod(){
		int[] a = {1, 3, 11, 21, 4, 6, 9, 91, 0, 6};

		List s = new ArrayList<Integer>();
		List d = new ArrayList<Integer>();

		String result = "";

		for(int i=0;i<a.length;i++){
			if(a[i] > 10){
				d.add(a[i]);
			}else{
				s.add(a[i]);
			}
		}
		

		Collections.sort(s,new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				if(o1 < o2)return 1;
				return -1;
			}
		});
		Collections.sort(d,new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				if(o1 < o2)return 1;
				return -1;
			}
		});
		
		System.out.println(s.get(0));
		System.out.println(d.get(0));
		
		
		for(int i=0;i<s.size();i++){
			int snum = (Integer) s.get(i);
			System.out.println("snum:" + snum);
			for(int j=0;j<d.size();j++){
				int dnum = (Integer) d.get(j);
				System.out.println("dnum:" + dnum);
				if(snum*10 + 9 > dnum){
					result = result + snum;
					System.out.println(result);
					break;
				}else{
					result = result + dnum;
					d.remove(j);
					System.out.println(result);
				}
			}
		}
		for(int i=0;i<d.size();i++){
			result = result + d.get(i);
		}
		System.out.println(result);

	}

}


/**

scala

package controllers

object App {
	def main(args: Array[String]) {
		val a = List(1, 3, 11, 21, 4, 6, 9, 91, 0, 6, 27, 279)
//		val a = List(1, 3, 10, 22, 16, 0, 99, 100, 9, 91, 21, 32, 31)
		val maxLength = a.max.toString.length
		val result = a.map(x => {
						 val value = math.pow(10, maxLength - x.toString.length)
						 (x.toString, x * value + value - 1)
					 })
					.sortBy(_._2)
					.reverse
					.foldLeft("")(_ + _._1)
		println("Result: " + result) 
	}
}

**/