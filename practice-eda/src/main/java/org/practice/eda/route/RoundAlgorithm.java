package org.practice.eda.route;

/**
 * 根据给定数据的定长进行轮循路由的算法
 * @author zhangbaitong
 *
 */
public class RoundAlgorithm implements RouteAlgorithm{
	
	public static int _index = -1;

	public static void main(String[] args) {
		RoundAlgorithm route = new RoundAlgorithm();
		for(int i=0;i<20;i++){
			System.out.println(route.getIndex(20));
		}
	}

	@Override
	public int getIndex(int sum) {
		//System.out.println("_index is " + _index);
		if(sum <= 0)return 0;
		if(sum == 1)return 1;
		//TODO:目前实现总是从第二个开始取，可以改进从第一个
		_index = _index + 1;
		if(_index>=sum){
			_index=0;
		}
		//System.out.println("_index is " + _index);
		return _index;
	}

}
