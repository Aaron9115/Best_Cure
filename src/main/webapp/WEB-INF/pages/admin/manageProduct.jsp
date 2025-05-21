<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Manage Products</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/manageProduct.css">
</head>
<body>
<div class="dashboard-container">
    <aside class="sidebar">
        <h2>Admin</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-home"></i> Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/user"><i class="fas fa-user"></i> Users</a></li>
            <li class="active"><a href="${pageContext.request.contextPath}/admin/manageProduct"><i class="fas fa-box"></i> Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orderList"><i class="fas fa-list"></i> Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </aside>

    <main class="main-content">
        <div class="top-bar">
            <h1>Manage Products</h1>
        </div>

        <%-- Display success/error messages --%>
        <c:if test="${not empty success}">
            <div class="alert alert-success">
                <i class="fas fa-check-circle"></i> ${success}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>

        <div class="form-container">
            <h2>${not empty product ? 'Edit Product' : 'Add New Product'}</h2>
            <form action="${pageContext.request.contextPath}/admin/manageProduct" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="${not empty product ? 'update' : 'add'}">
                <input type="hidden" name="productId" value="${not empty product ? product.productId : ''}">

                <div class="form-row">
                    <div class="form-group">
                        <label for="productName">Product Name</label>
                        <input type="text" id="productName" name="productName" 
                               value="${not empty product ? product.productName : ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="productCategory">Category</label>
                        <select id="productCategory" name="productCategory" required>
                            <option value="">Select Category</option>
                            <option value="BabyProducts" ${not empty product && product.productCategory == 'BabyProducts' ? 'selected' : ''}>Baby Products</option>
                            <option value="SkinCare" ${not empty product && product.productCategory == 'SkinCare' ? 'selected' : ''}>Skin Care</option>
                            <option value="Medicine" ${not empty product && product.productCategory == 'Medicine' ? 'selected' : ''}>Medicine</option>
                            <option value="Equipments" ${not empty product && product.productCategory == 'Equipments' ? 'selected' : ''}>Equipments</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="productPrice">Price ($)</label>
                        <input type="number" id="productPrice" name="productPrice" step="0.01" min="0" 
                               value="${not empty product ? product.productPrice : ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="productQuantity">Quantity</label>
                        <input type="number" id="productQuantity" name="productQuantity" min="0" 
                               value="${not empty product ? product.productQuantity : ''}" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="productDescription">Description</label>
                    <textarea id="productDescription" name="productDescription">${not empty product ? product.productDescription : ''}</textarea>
                </div>

                <div class="form-group">
                    <label for="imageInput">Product Image</label>
                    <input type="file" id="imageInput" name="image" accept="image/*">
                    <div class="image-upload-container">
                        <c:choose>
                            <c:when test="${not empty product && not empty product.image}">
                                <img src="${pageContext.request.contextPath}/resources/products/${product.image}" 
                                     class="product-image-preview" id="imagePreview">
                                <input type="hidden" name="existingImage" value="${product.image}">
                            </c:when>
                            <c:otherwise>
                                <div id="imagePreview"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="form-actions">
                    <c:if test="${not empty product}">
                        <a href="${pageContext.request.contextPath}/admin/manageProduct" class="btn btn-secondary">Cancel</a>
                    </c:if>
                    <button type="submit" class="btn btn-primary">
                        ${not empty product ? 'Update Product' : 'Add Product'}
                    </button>
                </div>
            </form>
        </div>

        <div class="product-section">
            <div class="section-header">
                <h2>Product List</h2>
                <a href="${pageContext.request.contextPath}/admin/manageProduct?action=add" class="add-product-btn">
                    <i class="fas fa-plus"></i> Add Product
                </a>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Image</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td>${product.productId}</td>
                            <td>${product.productName}</td>
                            <td>${product.productCategory}</td>
                            <td>$${product.productPrice}</td>
                            <td>${product.productQuantity}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty product.image}">
                                        <img src="${pageContext.request.contextPath}/resources/products/${product.image}" 
                                             width="50" alt="${product.productName}">
                                    </c:when>
                                    <c:otherwise>
                                        <span class="no-image">No Image</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="table-actions">
                                    <a href="${pageContext.request.contextPath}/admin/manageProduct?action=edit&id=${product.productId}" 
                                       class="btn-edit">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/manageProduct?action=delete&id=${product.productId}" 
                                       class="btn-delete"
                                       onclick="return confirm('Are you sure you want to delete this product?')">
                                        <i class="fas fa-trash-alt"></i> Delete
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
</div>

<script>
    // Image preview functionality
    document.getElementById('imageInput')?.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                let preview = document.getElementById('imagePreview');
                if (!preview) {
                    preview = document.createElement('img');
                    preview.className = 'product-image-preview';
                    preview.id = 'imagePreview';
                    document.querySelector('.image-upload-container').appendChild(preview);
                }
                preview.src = e.target.result;
                preview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });
</script>
</body>
</html>