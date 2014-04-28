##支持模式
支持的消息模式配对，任何一方都可以做为服务端
	PUB and SUB
	REQ and REP
	REQ and XREP
	XREQ and REP
	XREQ and XREP
	XREQ and XREQ
	XREP and XREP
	PUSH and PULL
	PAIR and PAIR

##参考资料

ZeroMQ File Server

	https://github.com/thatch45/sandbox/blob/master/examples/zmq/file_serve.py

Spring Integration手册

	http://docs.spring.io/spring-integration/docs/

Enterprise Integration Pattern 官网

	http://www.eaipatterns.com/

文件传输

	https://github.com/zeromq/filemq

##相关技术

Apache Camel(FTP component) 

Tibco-SFTP
protocol-RocketStream protocol

	http://support.rocketstream.com/docs/RS12/server/index.html?the_rocketstream_protocols.html

remoting, messaging, and scheduling

##网上摘录

消息是任何Java对象连同框架处理对象时使用的元数据的一个通用包装。
它由负载和头部组成。负载可以是任何类型，头部保存一般的请求信息，例如id，时间戳，过期时间和返回地址。
头部也被用来在各种传输协议间传值。比如，从接收到的一个文件创建一个消息时，可以把文件名存储在头部，这些下游的组件就可以使用它。
同样地，如果一个消息的内容最终被一个对外接出的Mail适配器发送，各种属性（收件人，发送人，抄送人，主题等）可以被上游组件配置为消息头部值。
开发者也可以在头部存储任意的键值对。


