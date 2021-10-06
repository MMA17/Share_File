<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String username = (String) request.getSession(false).getAttribute("name");
	if(username == null){
		username = "";
	}
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>Hello <%=username%></h1>


	<a href="/ShareFile/login.jsp">dang nhap</a>
</body>
</html>