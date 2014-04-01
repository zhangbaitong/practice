package org.practice.eda.event;


/**
 * 事件代理类即订阅者
 * 1.通过实现EventProxy的recive接口来进行适配
 * 2.在Processor上注册使用
 * @author zhangbaitong
 *
 */
public interface EventProxy {
	public void recive(Event evt);
}
