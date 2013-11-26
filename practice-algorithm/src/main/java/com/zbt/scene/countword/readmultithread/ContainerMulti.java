package com.zbt.scene.countword.readmultithread;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ContainerMulti {
	
	private static ConcurrentHashMap<String,Integer> keys = new ConcurrentHashMap<String,Integer>();
	
	private boolean finished = false;
	
	private Date old = new Date();
	
	public static int threadNum = 2;
	
	private static HashMap<Integer,BlockingQueue<String>> manager = new HashMap<Integer,BlockingQueue<String>>(threadNum);
	
	private static Integer index = 0;
	
	static{
		for(int i=0;i<threadNum;i++){
			manager.put(new Integer(i), new LinkedBlockingQueue<String>(15000));
		}
	}
	
	public ConcurrentHashMap<String,Integer> getKeys(){
		return this.keys;
	}
	
	public void finished(){
		finished = true;
	}
	
	public boolean isFinished(){
		for(int i=0;i<threadNum;i++){
			BlockingQueue<String> queue = manager.get(new Integer(i));
			if(queue.size() > 0)return false;
		}
		return finished;
	}
	
	public boolean isFinished(Integer id){
		BlockingQueue<String> queue = manager.get(id);
		if(queue.size() > 0)return false;
		return finished;
	}
	
	public void set(String str){
		try {
			manager.get(index).put(str);
			next();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void next(){
		if(index == (threadNum-1)){
			index = 0;
		}else{
			index++;
		}
	}
	
	public BlockingQueue<String> getQueue(Integer id){
			return manager.get(id);
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
		Date now = new Date();
		System.out.println((now.getTime() - old.getTime())/1000.0 + " s");
	}

}
