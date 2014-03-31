package org.practice.eda.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Processor {
	
	public static Map<Event,List<Watcher>> watcher = new HashMap<Event,List<Watcher>>();
	public static List<Event> eventList = new ArrayList<Event>();
	
	public static void addWacher(Event evt,Watcher wat){
		if(watcher.get(evt) == null){
			List list =  new ArrayList();
			list.add(wat);
			watcher.put(evt, list);
		}else{
			watcher.get(evt).add(wat);
			//TODO:可能会出现重复的情况
		}
	}
	
	public static void response(){
		Iterator itr = watcher.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<Event,Watcher> entry = (Entry) itr.next();
			if(watcher.get(entry.getKey())!=null){
				Event watchEvt = entry.getKey();
				for(int i=0;i<eventList.size();i++){
					Event serviceEvt = eventList.get(i);
					if(serviceEvt.equals(watchEvt)){
						List<Watcher> list = watcher.get(watchEvt);
						for(int j=0;j<list.size();j++){
							Watcher wat = list.get(j);
							wat.recive(serviceEvt);
						}
					}
				}
			}
		}
	}
	
	public static void saveEvent(Event evt){
		eventList.add(evt);
	}
	
	public static void trigger(Event evt){
		System.out.println("PROCESSOR INFO:Event " + evt.getName() + " is trigger!");
		saveEvent(evt);
		response();
	}

}
