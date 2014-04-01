package org.practice.eda.route;

import java.util.Random;

/**
 * 根据给定长度的数据进行随机路由算法
 * @author zhangbaitong
 *
 */
public class RandomAlgorithm implements RouteAlgorithm{
	
	public static Random random = new Random(System.currentTimeMillis());
	
	public int getIndex(int sum){
		return random.nextInt(sum);
	}

	public static void main(String[] args) {
		RandomAlgorithm route = new RandomAlgorithm();
		for(int i=0;i<100;i++){
			System.out.println(route.getIndex(10));
		}
	}

}
