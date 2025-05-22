<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="BestCure - Your trusted healthcare partner. Meet our dedicated team of professionals committed to providing quality healthcare products and services.">
    <title>About Us - BestCure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css">
</head>

<body>

    <!-- Header: Restored directly inside about.jsp -->
    <header>
        <div class="logo">
            <a href="index.jsp">
                <img src="${pageContext.request.contextPath}/resources/about/logo.png" alt="Medicines" height="80">
            </a>
        </div>
        <div class="tagline">
            Your Health, Our Priority
        </div>
        <div class="contact">
            <div class="phone">
                <img src="${pageContext.request.contextPath}/resources/about/call.png" alt="Phone icon" height="12" width="12">
                <a href="tel:+9779765224677">+977 9765 224 677</a>
            </div>
            <div class="email">
                <img src="${pageContext.request.contextPath}/resources/about/gmail.png" alt="Email icon" height="12" width="12">
                <a href="mailto:BestCure2323@gmail.com">BestCure2323@gmail.com</a>
            </div>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="hero-section">
        <h1 class="hero-title">About BestCure</h1>
        <p class="hero-text">
            At <strong>BestCure</strong>, we are more than just a pharmacy — we are your trusted healthcare partner.
            Our platform offers a wide range of <strong>medicines</strong>, <strong>medical equipment</strong>,
            <strong>skin care products</strong>, and <strong>baby products</strong> to support your daily well-being.
            We aim to make healthcare more accessible, reliable, and convenient through digital innovation and a dedicated team.
        </p>
    </section>

    <!-- Mission Section -->
    <section class="mission-section">
        <div class="mission-card">
            <div class="mission-icon"></div>
            <h2 class="mission-title">Our Mission</h2>
            <p>To provide accessible, affordable, and quality health products to all and enrich life with reliable pharma services and health care innovation.</p>
        </div>
        <div class="mission-card">
            <div class="mission-icon"></div>
            <h2 class="mission-title">Our Vision</h2>
            <p>To be the finest digital health company, changing individuals' access to healthcare goods and services through excellence, confidence, and ease.</p>
        </div>
        <div class="mission-card">
            <div class="mission-icon"></div>
            <h2 class="mission-title">Our Promise</h2>
            <p>We guarantee original products, prompt delivery, expert advice, and personal attention to all your health requirements, always giving your well-being top priority</p>
        </div>
    </section>

    <!-- Core Values -->
    <section class="values-section">
        <h2 class="section-title">Our Core Values</h2>
        <div class="values-container">
            <div class="value-item">
                <div class="value-icon"></div>
                <h3 class="value-title">Quality</h3>
                <p>We never compromise on the quality of our products and services.</p>
            </div>
            <div class="value-item">
                <div class="value-icon"></div>
                <h3 class="value-title">Integrity</h3>
                <p>We operate with honesty, transparency, and ethical standards.</p>
            </div>
            <div class="value-item">
                <div class="value-icon"></div>
                <h3 class="value-title">Trust</h3>
                <p>We build lasting relationships based on reliability and consistency.</p>
            </div>
            <div class="value-item">
                <div class="value-icon"></div>
                <h3 class="value-title">Innovation</h3>
                <p>We continuously enhance our services to address evolving healthcare needs.</p>
            </div>
        </div>
    </section>

    <!-- Team Section -->
    <section class="team-section">
        <h2 class="section-title">Meet Our Team</h2>
        <div class="team-grid">
            <div class="team-member">
                <img src="${pageContext.request.contextPath}/resources/about/Ichchharawal.jpg" alt="Ichchha Rawal" class="member-img">
                <div class="member-info">
                    <h3 class="member-name">Ichchha Rawal</h3>
                    <p class="member-role">Chief Executive Officer</p>
                    <p class="member-desc">Leading our team with vision and expertise to deliver exceptional healthcare solutions.</p>
                </div>
            </div>
            <div class="team-member">
                <img src="${pageContext.request.contextPath}/resources/about/AayushaBhandari.jpg" alt="Aayusha Bhandari" class="member-img">
                <div class="member-info">
                    <h3 class="member-name">Aayusha Bhandari</h3>
                    <p class="member-role">Operations Director</p>
                    <p class="member-desc">Ensuring smooth operations and efficient delivery of all our services.</p>
                </div>
            </div>
            <div class="team-member">
                <img src="${pageContext.request.contextPath}/resources/about/Prastutiguragain.jpg" alt="Prastuti Guragain" class="member-img">
                <div class="member-info">
                    <h3 class="member-name">Prastuti Guragain</h3>
                    <p class="member-role">Marketing Manager</p>
                    <p class="member-desc">Creatively connecting our solutions with the people who need them most.</p>
                </div>
            </div>
            <div class="team-member">
                <img src="${pageContext.request.contextPath}/resources/about/Anishfauzadr.jpg" alt="Anish Fauzadr" class="member-img">
                <div class="member-info">
                    <h3 class="member-name">Anish Fauzadr</h3>
                    <p class="member-role">Technical Director</p>
                    <p class="member-desc">Driving innovation through technology to improve healthcare accessibility.</p>
                </div>
            </div>
            <div class="team-member">
                <img src="${pageContext.request.contextPath}/resources/about/Surajsah.jpg" alt="Suraj Sah" class="member-img">
                <div class="member-info">
                    <h3 class="member-name">Suraj Sah</h3>
                    <p class="member-role">Customer Relations</p>
                    <p class="member-desc">Dedicated to ensuring customer satisfaction and building lasting relationships.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer Include -->
    <%@ include file="footer.jsp" %>

</body>
</html>
