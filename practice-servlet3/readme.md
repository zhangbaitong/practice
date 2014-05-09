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