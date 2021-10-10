<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Register</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row pt-5 mt-5">
			
			<div class="col-lg-5 col-md-8" style="margin: 0 auto">
				<form class="card bg-light px-5 py-4 shadow-lg border-light"
					method="POST" action="/ShareFile/register">
					<div class="mb-4">
						<h2 class="text-center"><strong>Register</strong></h2>
					</div>
					<div class="mb-4">
						<input type="text" placeholder="Username" class="form-control"
							name="username" value="">
					</div>
					<div class="mb-4 mt-1">
						<input type="text" placeholder="Name" class="form-control"
							name="name" value="">
					</div>
					<div class="mb-4 mt-1 ">
						<input type="tel" placeholder="Telephone number"
							class="form-control" name="tel" value="">
					</div>
					<div class="mb-4 mt-1 ">
						<input type="password" placeholder="password" class="form-control"
							name="password" value="">
					</div>
					<div class="mb-5 mt-1 ">
						<input type="password" placeholder="Confirm password"
							class="form-control" name="cpassword"
							value="">
					</div>
					<button name="submit" class="btn btn-primary mb-4">Submit</button>
					<p class="text-center fw-bolder">
						Đã có tài khoản? <a
							class="text-primary text-capitalize text-decoration-none"
							href="login.jsp">đăng nhập</a>
					</p>
				</form>
			</div>
			
		</div>
	</div>

</body>
</html>