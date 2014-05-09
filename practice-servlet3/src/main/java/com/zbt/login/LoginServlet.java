package com.zbt.login;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		urlPatterns = { "/login" },
		//value = { "/login" },//与urlPatterns一致，二者只能存在一个
		//asyncSupported = true,是否支持异步模式，Servlet 3.0重要新特性
		description = "用户登录模拟",
		loadOnStartup = 1,//container启动时加载，指定加载顺序
		//largeIcon = "large icon path", 
		//smallIcon = "small icon path",
		//可登录的用户名写在initParams中（模拟登录，没用数据库）
		initParams = { 
				@WebInitParam(name = "users", value = "[{\"name\":\"test\",\"password\":\"test\"}]", description = "users and passwords")
		})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected  List<Map<String,String>> users;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		String users = config.getInitParameter("users");
		Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
		Gson gson = new Gson();
		this.users = gson.fromJson(users, type);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean loginSuccess = false;
		for (Iterator<Map<String,String>> userIter = users.iterator(); userIter.hasNext();) {
			Map<String,String> user = userIter.next();
			if(username.equalsIgnoreCase(user.get("name"))){
				if(password.equals(user.get("password"))){
					loginSuccess = true;
					break;
				}
			}
		}
		if(loginSuccess){
			//验证成功，转向欢迎页面,把参数也带过去了
			request.getRequestDispatcher("/helloworld.jsp").forward(request, response);
		}else{
			//否则转向登录页面
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}

