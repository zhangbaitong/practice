##去掉JSP标签转换成html时的换行


对于大部分的应用系统来说，会遇到JSP标签换行造成问题的几率很小

当我们使用标签输出数据时，比如json数据，那么换行就有可能会造成json解释错误,于是就需要把标签的换行去掉

下面有两种方法：
第一种：利用web服务器的trimSpaces功能。
Tomcat5 以上版本都可以使用，这是最简单的方法。主要是增加一个servlet：
<servlet>
<servlet-name>jsp</servlet-name>
<servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
<init-param>
<param-name>fork</param-name>
<param-value>false</param-value>
</init-param>
<init-param>
<param-name>trimSpaces </param-name>
<param-value>true </param-value>
</init-param>
<init-param>
<param-name>xpoweredBy</param-name>
<param-value>false</param-value>
</init-param>
<load-on-startup>3</load-on-startup>
</servlet>
第二种方法：这个方案只有在支持jsp 2.1的web服务器上才可以使用，如Tomcat6：
<%@ page trimDirectiveWhespaitces=”true” %>
第一种方法没有测试过；第二种方法是本人目前使用的方法，可以使用。