<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registered Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
    <style>
        .profile-img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
        }
        .user-table {
            width: 100%;
            border-collapse: collapse;
        }
        .user-table th, .user-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .user-table th {
            background-color: #f8f9fa;
            font-weight: 600;
        }
        .user-table tr:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <!-- Sidebar -->
    <aside class="sidebar">
        <h2>Admin</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-home"></i> Dashboard</a></li>
            <li class="active"><a href="${pageContext.request.contextPath}/admin/user"><i class="fas fa-user"></i> User</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/manageProduct"><i class="fas fa-box"></i> Products</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orderList"><i class="fas fa-list"></i> Order lists</a></li>
            <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
        <div class="top-bar">
            <h1>Registered Users</h1>
            <div class="search-container">
                <input type="text" id="userSearch" placeholder="Search users..." onkeyup="searchUsers()">
            </div>
        </div>

        <div class="user-table-container">
            <table class="user-table" id="userTable">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Gender</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Phone</th>
                        <th>Profile Picture</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                   <c:forEach var="user" items="${userList}">
    <tr>
        <td>${user.userId}</td>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td>
            <span class="role-badge ${user.role.toLowerCase()}">${user.role}</span>
        </td>
        <td>${user.gender}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.phoneNumber}</td>
        <td>
            <c:choose>
                <c:when test="${not empty user.profilePicture}">
                    <img src="${pageContext.request.contextPath}/resources${user.profilePicture}" 
                         alt="Profile" class="profile-img">
                </c:when>
                <c:otherwise>
                    <span class="no-image">No Image</span>
                </c:otherwise>
            </c:choose>
        </td>
        <td class="actions">
            <a href="${pageContext.request.contextPath}/admin/editUser?id=${user.userId}" 
               class="btn-edit" title="Edit User">
                <i class="fas fa-edit"></i>
            </a>
            <a href="${pageContext.request.contextPath}/admin/deleteUser?id=${user.userId}" 
               class="btn-delete" title="Delete User"
               onclick="return confirm('Are you sure you want to delete this user?')">
                <i class="fas fa-trash-alt"></i>
            </a>
        </td>
    </tr>
</c:forEach>

                </tbody>
            </table>
        </div>
    </main>
</div>

<script>
    function searchUsers() {
        const input = document.getElementById('userSearch');
        const filter = input.value.toUpperCase();
        const table = document.getElementById('userTable');
        const tr = table.getElementsByTagName('tr');

        for (let i = 1; i < tr.length; i++) {
            let found = false;
            const td = tr[i].getElementsByTagName('td');
            
            for (let j = 0; j < td.length - 1; j++) { // Skip actions column
                if (td[j]) {
                    const txtValue = td[j].textContent || td[j].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        found = true;
                        break;
                    }
                }
            }
            
            tr[i].style.display = found ? '' : 'none';
        }
    }
</script>
</body>
</html>