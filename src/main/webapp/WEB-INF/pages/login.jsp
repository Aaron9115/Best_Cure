<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Login - PharmaBestCure</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>
	<div class="container">
		<div class="login-section">
			<div class="logo">
				<i class="fas fa-first-aid"></i> BestCure
			</div>


			<c:if test="${not empty errorMessage}">
				<div class="error-message">${errorMessage}</div>
			</c:if>

			<form action="${pageContext.request.contextPath}/login" method="post" class="form form-login">
				<div class="form-field">
					<label class="user" for="email"> <i class="fas fa-envelope"></i>
						<span class="hidden">Email</span>
					</label> <input id="email" type="email" class="form-input"
						placeholder="Email" name="user_email" required />
				</div>

				<div class="form-field">
					<label class="lock" for="password"> <i class="fas fa-lock"></i>
						<span class="hidden">Password</span>
					</label> <input id="password" type="password" class="form-input"
						placeholder="Password" name="user_password" required />
				</div>

				<div class="form-field forget-password">
					<a href="#" class="forget-text">Forget Password?</a>
				</div>

				<div class="form-field">
					<input type="submit" value="Login" />
				</div>

				<div class="form-field">
					Not Registered Yet? <a
						href="${pageContext.request.contextPath}/register"
						class="register-text">Register Here</a>
				</div>
			</form>
		</div>

		<div class="motivation-section">
			<img
				src="https://www.umary.edu/sites/default/files/styles/optimized/public/2021-09/HealthSciences-Pharmacy-CustomerPharmacist.jpg?itok=8VQODnpg"
				class="motivation-image" alt="Pharmacist" />
		</div>
	</div>
</body>
</html>
