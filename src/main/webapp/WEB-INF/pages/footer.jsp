<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://fonts.googleapis.com/css2?family=Montserrat&family=Roboto+Condensed&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />

<footer class="footer">
  <div class="b-c">
    <div class="b">
      <a href="${pageContext.request.contextPath}/home">
        <img src="${pageContext.request.contextPath}/resources/logo.png" alt="BestCure Logo" />
      </a>
      <p>BestCure is an online‐based pharmacy website offering a wide range of medical products...</p>
      <div class="social">
        <a href="#"><img src="${pageContext.request.contextPath}/resources/face.png" alt="Facebook Page" aria-label="Facebook"></a>
        <a href="#"><img src="${pageContext.request.contextPath}/resources/insta.png" alt="Instagram Page" aria-label="Instagram"></a>
        <a href="#"><img src="${pageContext.request.contextPath}/resources/twtr.png" alt="Twitter Page" aria-label="Twitter"></a>
      </div>
    </div>

    <div class="footer-links">
      <h3>Quick Links</h3>
      <ul>
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/products">Shop</a></li>
        <li><a href="${pageContext.request.contextPath}/about">About Us</a></li>
        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
      </ul>
    </div>

    <div class="footer-contact">
      <h3>Contact Us</h3>
      <p>Email: bestcure@gmail.com</p>
      <p>Phone: 053-011989</p>
      <p>Address: Kathmandu, Nepal</p>
    </div>
  </div>

  <div class="credit">
    &copy; 2025 BestCure. All Rights Reserved.
  </div>
</footer>
