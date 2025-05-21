<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | BestCure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/equipment.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
</head>

<body>

<!--------------------------category------section--------starts----->

<section class="category-section">
  <h1>Medical Equipment</h1>
  <div class="product-grid">

    <div class="product-card">
      <img src="images/homep1.png" alt="Sthetoscope" />
      <h4>Product 1</h4>
      <div class="stars">★★★★★</div>
      <div class="product-title">Sthetoscope</div>
      <div class="price">$150.50</div>
      <div class="original-price">$160</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Item</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Gentle baby lotion for soft skin.</div>
    </div>

    <div class="product-card">
      <img src="images/homep2.png" alt="Thermometer" />
      <h4>Product 2</h4>
      <div class="stars">★★★★☆</div>
      <div class="product-title">Thermometer</div>
      <div class="price">$120.00</div>
      <div class="original-price">$130</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Item</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Tear-free baby shampoo.</div>
    </div>
    <div class="product-card">
      <img src="images/homep3.png" alt="Vitamin-C serum" />
      <h4>Product 3</h4>
      <div class="stars">★★★★★</div>
      <div class="product-title">Vitamin-C serum</div>
      <div class="price">$50.00</div>
      <div class="original-price">$60</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Item</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Mild soap for sensitive baby skin.</div>
    </div>
    <div class="product-card">
      <img src="images/homep4.png" alt="Medical Gloves" />
      <h4>Product 4</h4>
      <div class="stars">★★★☆☆</div>
      <div class="product-title">Medical Gloves</div>
      <div class="price">$80.00</div>
      <div class="original-price">$90</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Item</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Protects from diaper rashes.</div>
    </div>

    <div class="product-card">
      <img src="images/homep5.png" alt="Sthetoscope" />
      <h4>Product 5</h4>
      <div class="stars">★★★★★</div>
      <div class="product-title">Sthetoscope</div>
      <div class="price">$100.00</div>
      <div class="original-price">$110</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Pack</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Soft and gentle wipes for daily use.</div>
    </div>

    <div class="product-card">
      <img src="images/homep6.png" alt="Thermometer" />
      <h4>Product 6</h4>
      <div class="stars">★★★★★</div>
      <div class="product-title">Thermometer</div>
      <div class="price">$130.00</div>
      <div class="original-price">$140</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Bottle</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Massage oil for baby’s delicate skin.</div>
    </div>

    <div class="product-card">
      <img src="images/homep7.png" alt="Medical Gloves" />
      <h4>Product 7</h4>
      <div class="stars">★★★★☆</div>
      <div class="product-title">Medical Gloves</div>
      <div class="price">$90.00</div>
      <div class="original-price">$100</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Bottle</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Keeps baby dry and fresh.</div>
    </div>

    <div class="product-card">
      <img src="images/homep8.png" alt="Sthetoscope" />
      <h4>Product 8</h4>
      <div class="stars">★★★★★</div>
      <div class="product-title">Sthetoscope</div>
      <div class="price">$250.00</div>
      <div class="original-price">$270</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Item</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">BPA-free safe feeding bottle.</div>
    </div>
    <div class="product-card">
      <img src="images/homep9.png" alt="Thermometer" />
      <h4>Product 9</h4>
      <div class="stars">★★★★☆</div>
      <div class="product-title">Thermometer</div>
      <div class="price">$300.00</div>
      <div class="original-price">$320</div>
      <div class="qty-label">Qty:</div>
      <input type="number" class="qty-input" value="1" />
      <div class="unit-label">/Piece</div>
      <button class="add-to-cart-btn">Add To Cart</button>
      <div class="product-description">Soft and cozy baby blanket.</div>
    </div>

  </div>
</section>
<!----------------------------------------------Footer section starts-------------------->
<%@ include file="footer.jsp" %>
  <!---------------------------Footer section ends-------------------->
</body>
</html>
