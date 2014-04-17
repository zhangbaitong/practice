package org.practice.eda.zmq;


/**
 * 订阅者接受信息执行接口
 * @author zhangbaitong
 *
 */
public interface ZProxy {
	
	public void action(String msg);

}
