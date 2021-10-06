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
		<ul class="list-group">
			<li class="list-group-item active">Input detail information</li>
			<li class="list-group-item">
				<form method="post" action="/ShareFile/register">
					<div class="form-group">
						<label for="usr">User</label> <input type="text"
							class="form-control" name="username" />
					</div>

					<div class="form-group">
						<label for="email">Name</label> <input type="text"
							class="form-control" name="name" />
					</div>
					
					
					<div class="form-group">
						<label for="email">Telephone</label> <input type="tel"
							class="form-control" name="tel" />
					</div>
					
					<div class="form-group">
						<label for="pwd">Password</label> <input type="password"
							class="form-control" name="password" />
					</div>

					<button type="submit" class="btn btn-success">Register</button>
				</form>
			</li>
		</ul>
	</div>

</body>
</html>