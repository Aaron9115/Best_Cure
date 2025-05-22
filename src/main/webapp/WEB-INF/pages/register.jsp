<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Register - PharmaBestCure</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css" />
  </head>
  <body>
    <div class="container">
      <div class="registration-section">
        <div class="logo">
          <i class="fas fa-first-aid"></i>
          <span>BestCure</span>
        </div>

        <div class="registration_form">
          <h2 class="title">Create Account</h2>

          <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
          </c:if>

          <c:if test="${not empty success}">
            <p class="success-message">${success}</p>
          </c:if>

          <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form_wrap">
              <div class="input_grp">
                <div class="input_wrap">
                  <input type="text" id="firstName" name="firstName" placeholder="First Name" value="${firstName}" />
                </div>
                <div class="input_wrap">
                  <input type="text" id="lastName" name="lastName" placeholder="Last Name" value="${lastName}" />
                </div>
              </div>
              <div class="input_wrap">
                <input type="text" id="username" name="username" placeholder="Username" value="${username}" />
              </div>
              <div class="input_wrap">
                <input type="text" id="email" name="email" placeholder="Email Address" value="${email}" />
              </div>
        
              <div class="input_wrap">
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Phone Number" value="${phoneNumber}" />
              </div>
              <div class="input_wrap">
                <input type="password" id="password" name="password" placeholder="Password" />
              </div>
              <div class="input_wrap">
                <input type="password" id="retype-password" name="retypePassword" placeholder="Confirm Password" />
              </div>
              <div class="input_wrap gender-wrap">
                <label>Gender</label>
                <div class="gender-options">
                  <label class="gender-label">
                    <input type="radio" name="gender" value="male" ${gender == 'male' ? 'checked' : ''} />
                    <span>Male</span>
                  </label>
                  <label class="gender-label">
                    <input type="radio" name="gender" value="female" ${gender == 'female' ? 'checked' : ''} />
                    <span>Female</span>
                  </label>
                  <label class="gender-label">
                    <input type="radio" name="gender" value="other" ${gender == 'other' ? 'checked' : ''} />
                    <span>Other</span>
                  </label>
                </div>
              </div>
              <div class="input_wrap">
                <button type="submit" class="submit_btn">Register Now!</button>
              </div>
              <div class="input_wrap text-center">
                <span>Already have an account? </span>
                <a href="${pageContext.request.contextPath}/login" class="login-text">Login</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>