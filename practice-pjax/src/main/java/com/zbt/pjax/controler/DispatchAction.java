package com.zbt.pjax.controler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理servlet分发的基础类
 * 根据参数method内容进行方法名的分发
 */
public class DispatchAction extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		dispachMethod(request, response);
	}

	public void dispachMethod(HttpServletRequest request,
			HttpServletResponse response) {
		String methodName = request.getParameter("method");
		//this将会指向继承此类的具体类
		Class dispachAction = this.getClass();
		try {
			Class[] parametersTypes = new Class[] { HttpServletRequest.class,
					HttpServletResponse.class };
			Method method = dispachAction
					.getMethod(methodName, parametersTypes);

			Object[] parameterVlaues = new Object[] { request, response };
			method.invoke(this, parameterVlaues);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
