<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File upload page</title>
</head>
<body>
	<form action="uploads" method="post" enctype="multipart/form-data">
		<fieldset>
			<label>File1:</label><input type="file" name="file1"/><br/>
			<label>File2:</label><input type="file" name="file2"/>
		</fieldset>
		<input type="submit" name="submits" value="uploads"/>
	</form>
<hr>
<p>上传信息：</p>
<p>上传了${fn:length(fileNames) }个文件 </p>
<c:if test="${not empty fileNames }">
	<ul>
		<c:forEach var="fileName" items="${fileNames }">
			<li>${fileName}</li>
		</c:forEach>
	</ul>
</c:if>
<hr>
<p>错误信息：</p>
<p>${errMsg}</p>
</body>
</html>