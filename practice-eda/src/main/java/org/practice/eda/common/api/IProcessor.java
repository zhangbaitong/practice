package org.practice.eda.common.api;

import java.util.List;

import org.practice.eda.event.Event;
import org.practice.eda.event.EventProxy;

public interface IProcessor {
	
	/**
	 * 停止监听触发事件
	 */
	public void stop();
	
	public String getRoute();

	public void setRoute(String route);

	
	/**
	 * 添加订阅者
	 * @param evt 订阅事件类型
	 * @param wat 订阅者
	 */
	public void addWatcher(Event evt,EventProxy wat);
	
	/**
	 * 返回制定事件名称的订阅者列表
	 * @param evtName
	 * @return
	 */
	public List<EventProxy> getWatchList(String evtName);
	
	/**
	 * 事件响应逻辑
	 * 1.通过事件列表消费最早事件
	 * 2.获得当前事件的订阅者列表
	 * 3.路由策略的判断
	 * 4.订阅者响应
	 */
	public void response();
	
	public void trigger(Event evt);

}
