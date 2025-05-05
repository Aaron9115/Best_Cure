<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Products</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/products.css">
</head>

<body>

<!-- Banner -->
<section class="home" id="home">
	<div class="image">
		<div class="slider" id="ig">
			<img src="${pageContext.request.contextPath}/images/slider.png" style="border-radius: 20px;">
		</div>
		<div class="down">
			<p>Scroll To <span id="one">Shop</span></p>
			<div class="mouse">
				<div class="scroll"></div>
			</div>
		</div>
	</div>
</section>

<!-- Category Section -->
<section class="cat" id="cat">
	<h1>Shop By <span id="h" style="color: #FFAE42;">Category</span></h1>
	<div class="box-container">
		<div class="box">
			<img src="${pageContext.request.contextPath}/images/baby.png" />
			<p>Baby Products</p>
			<a href="${pageContext.request.contextPath}/baby.jsp"><button class="pbtn">Buy Now</button></a>
		</div>
		<div class="box">
			<img src="${pageContext.request.contextPath}/images/skin.png" />
			<p>Skin Care</p>
			<a href="${pageContext.request.contextPath}/skin.jsp"><button class="pbtn">Buy Now</button></a>
		</div>
		<div class="box">
			<img src="${pageContext.request.contextPath}/images/equipment.png" />
			<p>Equipment</p>
			<a href="${pageContext.request.contextPath}/equip.jsp"><button class="pbtn">Buy Now</button></a>
		</div>
		<div class="box">
			<img src="${pageContext.request.contextPath}/images/med.png" />
			<p>Medicines</p>
			<a href="${pageContext.request.contextPath}/med.jsp"><button class="pbtn">Buy Now</button></a>
		</div>
	</div>
</section>

<!-- Products Listing -->
<section class="products" id="products">
	<h1>Our <span style="color: #FFAE42;">Products</span></h1>
	<div class="product-container">
		<c:forEach var="product" items="${products}">
			<div class="product-box">
				<h3>${product.productName}</h3>
				<p>Category: ${product.productCategory}</p>
				<p>${product.productDescription}</p>
				<p>Price: $${product.productPrice}</p>
				<p>In Stock: ${product.productQuantity}</p>
			</div>
		</c:forEach>
	</div>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>
