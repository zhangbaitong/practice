package com.zbt.scene.countword.readwitethread;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.zbt.scene.countword.singlethread.TimeUtil;

public class Container {
	
	private BlockingQueue<String>  queue = new LinkedBlockingQueue<String>();
	
	private static Hashtable<String,Integer> keys = new Hashtable<String,Integer>();
	
	private boolean finished = false;
	
	private Date old = new Date();
	
	public Hashtable<String,Integer> getKeys(){
		return this.keys;
	}
	
	public void finished(){
		finished = true;
	}
	
	public boolean isFinished(){
		return finished && queue.size() == 0;
	}
	
	public void set(String str){
		try {
			queue.put(str);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String get(){
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void print(){
		List<Entry<String,Integer>> retList = new ArrayList<Entry<String,Integer>>(keys.entrySet());
		Collections.sort(retList, new Comparator<Map.Entry<String, Integer>>() {    
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {    
                return (o2.getValue() - o1.getValue());    
            }
        });
		for(int i=0;i<retList.size();i++){
			Entry<String,Integer> e = retList.get(i);
			System.out.println(e.getKey() + " : " + e.getValue());
			if(i == 9 )break;
		}
		TimeUtil.calc(old);
	}

}
