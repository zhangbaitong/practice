package org.practice.eda.route;

import java.util.Random;

public class RandomTest {

	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		for(int i=0;i<100;i++){
			System.out.println(random.nextInt(10));
		}

	}

}
