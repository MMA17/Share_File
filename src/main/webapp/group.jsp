<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="dao.*"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nhóm</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
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

	<div class="container my-5 pt-5">
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>ID nhóm</th>
					<th>Tên nhóm</th>
				</tr>
			</thead>
			<tbody>
				<%
				try {
					User user = (User) request.getSession().getAttribute("user");
					int userId = user.getUser_id();
					UserDAO uDao = new UserDAO();

					ArrayList<Group> list = uDao.displayGroup(userId);
					for (Group group : list) {
						out.print("<tr><td>" + group.getGroup_id() + "</td><td>" + group.getGroup_name() + "</td>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				%>
			</tbody>
		</table>


		<button type="button" class="btn btn-primary" data-toggle="modal"
			data-target="#exampleModalCenter">Tạo nhóm</button>
	</div>


	<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<form action="/ShareFile/group" method="post">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLongTitle">Tạo nhóm mới</h5>
							
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<input type="text" class="form-control mb-4" placeholder="Tên nhóm" name="groupName" required/> 
						<input type="text" class="form-control mb-4" placeholder="Kiểu nhóm" name="groupType" required/>
						<input type="text" class="form-control mb-4" placeholder="Ghi chú" name="note" />
							
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Thoát</button>
						<button type="submit" class="btn btn-primary">Tạo nhóm</button>
					</div>
				</div>
			</form>

		</div>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>