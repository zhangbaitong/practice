##介绍

这是一个基于EDA思想的实践项目，暂时没有名字。它实现了一个基于事件的触发响应模型。

##功能

1.异步的事件触发响应模式

2.触发者和订阅者实现为n对1模型

3.支持广播，随机，轮循路由

4.支持多线程并发事件

5.订阅者统计功能


TODO:

1.复合事件响应

2.回调机制

##使用

示例：

//声明订阅者

ProxyDemo proxy = new ProxyDemo();

//注册事件和订阅者

Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);

//业务行为

BankPayActor actor = new BankPayActor();

actor.makeOrder();

//payAction方法触发相关事件

actor.payAction();


具体参考：org.practice.eda.event.EventTest

问题：

里面有些细小点已TODO的形式进行了标注，需要以后进行测试或完善。

====================================================
##平行版本介绍

基于0MQ实现了一个消息的总线机制

##功能

1.异步的事件触发响应模式

2.触发者和订阅者实现为n对n模型

3.支持多线程并发事件

##特点

用0MQ的功能简化队列，并发，订阅发布实现细节

##使用

示例：

//启动总线

ZProcessor.getInstance();

//启动服务

new Thread(new ZProxyDemo()).start();

//启动业务

new Thread(new ZBankPayActor()).start();

具体参考：org.practice.eda.zmq.ZEventTest

##可能实现
基于spring-integration的EDA实现

##相关产品
Mule（MuleForge实现的开源扩展）
ServiceMix

