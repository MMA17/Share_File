<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String username = (String) request.getSession(false).getAttribute("user");
if (username == null) {
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
	
	<%
	out.print("<h1> Xin chào " + username + "</h1>");
	%>

	<a href="/ShareFile/login.jsp">dang nhap</a>
</body>
</html>