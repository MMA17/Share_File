<%@page import="dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@page import="dao.FileDAO"%>
<%@page import="model.User"%>
<%@page import="model.File"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
User user = (User) session.getAttribute("user");
// out.print(user);
if (user == null) {
	response.sendRedirect("/ShareFile/login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bạn bè</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="images/logo.png">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="js/javascript.js"></script>
</head>
<body>
	<!-- Pre Loader -->
	<div class="load">
		<img src="images/loader.gif">
	</div>

	<div class="social">
		<a href="https://www.facebook.com/MISS-TEEN-VietNam-102237985334298"
			class="facebook" target="_blank"><i class="fa fa-facebook"></i></a> <a
			href="https://www.facebook.com/MISS-TEEN-VietNam-102237985334298"
			class="twitter" target="_blank"><i class="fa fa-twitter"></i></a> <a
			href="https://www.facebook.com/MISS-TEEN-VietNam-102237985334298"
			class="google" target="_blank"><i class="fa fa-google"></i></a> <a
			href="https://www.facebook.com/MISS-TEEN-VietNam-102237985334298"
			class="linkedin" target="_blank"><i class="fa fa-linkedin"></i></a> <a
			href="https://www.facebook.com/MISS-TEEN-VietNam-102237985334298"
			class="youtube" target="_blank"><i class="fa fa-youtube"></i></a>
	</div>
	<div
		style="position: fixed; bottom: 5px; left: 10px; background-color: #0275d8; border-radius: 4px; color: white;">
		<p>Tổng số lượt truy cập: 999</p>
	</div>
	<div class="col-2"
		style="position: fixed; bottom: 5px; right: 10px; background-color: #FFFFF; border-style: inset; '' border-radius: 6px; border-width: 2px; color: black;">
		<p>Danh sách bạn bè</p>
	</div>
	<nav class="navbar sticky-top navbar-expand-lg px-5"
		style="background-color: #e9ecef">
		<a href="dashboard.jsp"><img src="images/logo.png"
			class="navbar-brand" style="height: 100px; width: auto"></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarContent" aria-controls="navbarContent"
			aria-expanded="false" aria-label="Toggle navigation">
			<i class="fa fa-bars fa-2x"></i>
		</button>
		<div class="collapse navbar-collapse" id="navbarContent">
			<ul class="navbar-nav mr-auto" style="font-size: 1.5rem">
				<li class="nav-item active"><a
					class="nav-link text-primary p-2" href="dashboard.jsp"><b>Trang
							Chủ</b></a></li>
				<li class="nav-item"><a class="nav-link text-primary p-2"
					href="account.jsp"><b>Tài khoản</b></a></li>
				<li class="nav-item"><a class="nav-link text-primary p-2"
					href="friend.jsp"><b>Bạn bè</b></a></li>
				<li class="nav-item"><a class="nav-link text-primary p-2"
					href="logout"><b>Đăng xuất</b></a></li>
			</ul>
			<form action="" class="form-inline my-2 my-lg-0" method="POST">
				<input class="form-control mr-sm-2" id="searchBox" name="searchBox"
					type="search" placeholder="Nhập tên bạn bè" aria-label="Search"
					required>
				<button class="btn btn-outline-primary my-2 my-sm-0" type="submit"
					name="btn_submit">Tìm kiếm</button>
			</form>
		</div>
	</nav>
	<div class="container p-5">
		<h4 class="text-primary">Nhập ID người muốn kết bạn</h4>
		<form method="GET" action="/ShareFile/addFriend">
			<div class="form-group">
				<input type="text" class="form-control" name="user_id"
					aria-describedby="emailHelp" placeholder="Nhập User ID">
			</div>
			<button type="submit" class="btn btn-primary">Gửi lời mời
				kết bạn</button>
		</form>
		<div class="col-10 mt-5">
			<h3>Lời mời kết bạn</h3>
			<table class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>User ID</th>
						<th>Tên đăng nhập</th>
						<th>Họ tên</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<%
					try {
						UserDAO userdao = new UserDAO();
						ArrayList<User> users = (ArrayList<User>) userdao.searchAddFriendRequest(user.getUser_id());
						for (User u : users) {
							out.print("<tr><td>" + u.getUser_id() + "</td><td>" + u.getUser_name() + "</td><td>" + u.getName()
							+ "</td><td><a href='/ShareFile/acceptFriend?userid=" + u.getUser_id() +"'>" + " Đồng ý </a>" 
							+ "<a href='/ShareFile/ignoreFriend?userid=" + u.getUser_id() +"'> Từ chối </a></td>");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					%>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>