package org.practice.eda.event;

/**
 * 事件类型定义
 * 1.通过事件名称区分事件具体类型
 * 2.通过message传递事件消息，可以使用json等复合格式
 * @author zhangbaitong
 *
 */
public class Event {
	
	public Event(String name){
		this.name = name;
	}

	private String name;
	
	//事件信息载体
	//TODO:实现更加复杂的数据结构，是否有这个必要
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}
	
//	@Override
//	/**
//	 * 暂时不需要这个方法，因为事件的比对通过map的key（事件本身的类名）来处理
//	 * 这样的话建议类名规范化：com.name.app.func.event
//	 */
//	public boolean equals(Object obj) {
//		if(obj instanceof Event){
//			return ((Event)obj).getName().equals(this.name);
//		}else{
//			return obj.equals(this);
//		}
//	}
}
