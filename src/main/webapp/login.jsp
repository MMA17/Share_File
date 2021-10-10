<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
</head>
<body>
	
	<div class="container">

		<div class="row pt-5 mt-5">
			<div class="col-lg-5 col-md-8" style="margin: 0 auto">
				<form class="card bg-light px-5 py-4 shadow-lg border-light"
					method="POST" action="/ShareFile/login">
					<div class="mb-4">
						<h2 class="text-center"><strong>Login</strong></h2>
					</div>
					<div class="mb-4 input-group-lg">
						<input type="text" placeholder="Username" class="form-control"
							name="username" value="">
					</div>
					<div class="mt-1 mb-3 input-group-lg">
						<input type="password" placeholder="Password" class="form-control"
							name="password" value="">
					</div>
					<div class="mb-4 form-check">
						<input type="checkbox" class="form-check-input" name=""> <label
							class="form-check-label fw-bold">Remember me</label>
					</div>
					<button type="submit" class="btn btn-primary mb-4" name="submit">Submit</button>
					<p class="text-center fw-bolder">
						Chưa có tài khoản? <a
							class="text-primary text-capitalize text-decoration-none"
							href="register.jsp">đăng ký</a>
					</p>
				</form>
			</div>
			
		</div>
	</div>
	

</body>
</html>