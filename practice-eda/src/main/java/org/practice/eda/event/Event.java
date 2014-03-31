package org.practice.eda.event;

public class Event {
	
	public Event(String name){
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Event){
			return ((Event)obj).getName().equals(this.name);
		}else{
			return obj.equals(this);
		}
	}
}
