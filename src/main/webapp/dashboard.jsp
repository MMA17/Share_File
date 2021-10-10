<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<% User user =(User) session.getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="images/loader.gif">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
				<li class="nav-item active"><a class="nav-link text-primary p-2"
					href="dashboard.jsp"><b>Trang Chủ</b></a></li>
				<li class="nav-item"><a class="nav-link text-primary p-2"
					href="account.jsp"><b>Tài khoản</b></a></li>
				<li class="nav-item"><a class="nav-link text-primary p-2"
					href="friend.jsp"><b>Bạn bè</b></a></li>
				<li class="nav-item"><a class="nav-link text-primary p-2"
					href="logout.jsp"><b>Đăng xuất</b></a></li>
			</ul>
			<form action="timkiem.php" class="form-inline my-2 my-lg-0"
				method="POST">
				<input class="form-control mr-sm-2" id="searchBox" name="searchBox"
					type="search" placeholder="Nhập tên bạn bè" aria-label="Search"
					required>
				<button class="btn btn-outline-primary my-2 my-sm-0" type="submit"
					name="btn_submit">Tìm kiếm</button>
			</form>
		</div>
	</nav>
	<div class="jumbotron">

		<div class="text-center">
			<h1 class="text-primary">
				<b>Cloud Drive</b>
			</h1>
			<h4>Tải lên file của bạn không giới hạn! Lưu trữ vĩnh viễn!</h4>
			<form action="upload" method="POST" enctype="multipart/form-data">
				<input type="submit" value="Upload Now!"><br>
				<input type="text" name="userid" value=<%=user.getUser_id()%> hidden="">
				<input type="file" name="file" multiple="multiple" required="required">
			</form>
			
		</div>
	</div>
	<div class="container">
	<h3>${requestScope.message}</h3>
	<p>Xin chào, <%= user.getName() %></p>
	<table class="table table-bordered table-striped table-hover">
		<thead><tr><th>File ID</th><th>Tên file</th><th>Kích thước</th><th>Kiểu file</th><th>Tải xuống</th></tr></thead>
		<tbody>
			
		</tbody>
	</table>
	</div>
	
</body>
</html>