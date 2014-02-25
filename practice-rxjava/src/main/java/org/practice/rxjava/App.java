package org.practice.rxjava;

import rx.Observable;
import rx.Observable.OnSubscribeFunc;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        hello("zhang","baitong");
        
        customObservableBlocking().subscribe(new Action1(){
			public void call(Object s) {
				System.out.println(s);
			}
        });
        
        customObservableBlocking().skip(10).take(5).map(new Func1(){
			public Object call(Object s) {
				return s+"_zhang";
			}
        }).subscribe(new Action1(){
			public void call(Object s) {
				System.out.println("onNext => " + s);
			}
        });
    }
    
    public static void hello(String... names) {
    	//action1表示有1个参数的call，其他同理
        Observable.from(names).subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });
    }
    
    /**
     * Synchronous Observable
     */
    public static Observable customObservableBlocking(){
    	return Observable.create(new OnSubscribeFunc<String>() {

            public Subscription onSubscribe(Observer<? super String> Observer) {
            	for(int i=0; i<50; i++) {
            		Observer.onNext("value_" + i);
                }
            	Observer.onCompleted();
                return Subscriptions.empty();
            }

        });
    }
}
