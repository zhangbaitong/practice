##简介

对servlet3的一些特性实践

##使用

http://localhost:8080/practice-servlet3/

servlet（filter）异步配置：

	<servlet>
	    <servlet-name>DemoServlet</servlet-name> 
	    <servlet-class>footmark.servlet.Demo Servlet</servlet-class> 
	    <async-supported>true</async-supported> 
	</servlet>

	@WebServlet(urlPatterns = "/demo",asyncSupported = true)
	@WebFilter(urlPatterns = "/demo",asyncSupported = true)




##小结

1.chrome下同一个URL进行多请求，第一个请求不结束，第二个请求无法响应，但safari可以，不同浏览器同时请求可以。

猜测可能由于浏览器对同一个地址请求的线程进行了不同策略的管理，这个问题暂时不关心了。

2.out多线程异步输出的时候需要输出一个HTML标签内容，不然无法异步动态显示，chrome添加HTML标签即可，safari不需要也可以。

但是对于单线程异步输出chrome如果有HTML标签可以达到异步动态效果，但safari会同步显示所有信息。

猜测可能由于浏览器对异步请求返回的处理方式不同，暂时不深究，做记录。