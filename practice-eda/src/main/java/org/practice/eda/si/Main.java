package org.practice.eda.si;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * It is a test for si.
 * @author zhangbaitong
 *
 */
public class Main {

	public static void main(String... args) throws Exception {
        ApplicationContext ctx =
            new ClassPathXmlApplicationContext("context.xml");
        // Simple Service
        TempConverter converter =
            ctx.getBean("simpleGateway", TempConverter.class);
        System.out.println(converter.fahrenheitToCelcius(68.0f));;
    }
}
