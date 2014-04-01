package org.practice.eda.route;


/**
 * 事件响应分发的路由策略接口
 * @author zhangbaitong
 *
 */
public interface RouteAlgorithm {
	
	public static String ROUTE_BROADCAST = "BROADCAST";
	public static String ROUTE_RANDOM = "RANDOM";
	public static String ROUTE_ROUND = "ROUND";
	
	public int getIndex(int sum);

}
