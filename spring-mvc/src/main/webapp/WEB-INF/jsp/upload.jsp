<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
	<form action="<%=request.getContextPath()%>/upload/uploadFile" method="post" enctype="multipart/form-data">
		<input type="hidden" name="_method" value="post">	
		文件1：<input type="file" name="file"><br>
		文件2：<input type="file" name="file"><br>
		<input type="submit" value=" 提 交 "><br>
	</form>
</center>
</body>
</html>