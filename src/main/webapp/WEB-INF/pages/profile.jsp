<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css" />

<div class="container">
  <div class="profile-section">

    <c:if test="${not empty message}">
      <div class="message success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
      <div class="message error">${error}</div>
    </c:if>

    <div class="profile-header">
      <div class="profile-pic">
        <img id="profile-pic-preview"
             src="${user.profilePicture != null ? user.profilePicture : 'https://www.canbind.ca/wp-content/uploads/2025/01/placeholder-image-person-jpg.jpg'}"
             alt="Profile Picture" />
        <input type="file" id="profile-pic-upload" name="profilePicture" accept="image/*" style="display: none" />
        <button type="button" class="upload-pic-btn" onclick="document.getElementById('profile-pic-upload').click()">
          <i class="fas fa-camera"></i>
        </button>
      </div>
      <div class="profile-info">
        <h2>${user.firstName} ${user.lastName}</h2>
        <p>${user.email}</p>
      </div>
      <button type="button" class="edit-btn" id="edit-btn">Edit</button>
    </div>

    <div class="profile-form">
      <form id="profile-form" action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
        <div class="form-wrap">
          <div class="input-group">
            <div class="input-wrap">
              <label>First Name</label>
              <input type="text" name="firstName" id="firstName" value="${user.firstName}" required readonly />
            </div>
            <div class="input-wrap">
              <label>Last Name</label>
              <input type="text" name="lastName" id="lastName" value="${user.lastName}" required readonly />
            </div>
          </div>

          <div class="input-group">
            <div class="input-wrap">
              <label>Username</label>
              <input type="text" name="user_name" id="user_name" value="${user.username}" required readonly />
            </div>
            <div class="input-wrap">
              <label>Phone Number</label>
              <input type="tel" name="phoneNumber" value="${user.phoneNumber}" placeholder="Enter Phone Number" readonly />
            </div>
            <div class="input-wrap">
              <label>Gender</label>
              <select name="gender" id="gender" disabled>
                <option value="none" ${user.gender == 'none' ? 'selected' : ''}>None</option>
                <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
                <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
                <option value="other" ${user.gender == 'other' ? 'selected' : ''}>Other</option>
              </select>
            </div>
          </div>

          <div class="input-group action-buttons" style="display: none">
            <div class="input-wrap">
              <button type="submit" class="submit-btn">Save Changes</button>
            </div>
            <div class="input-wrap">
              <button type="button" class="cancel-btn" id="cancel-btn">Cancel</button>
            </div>
          </div>
        </div>
      </form>
    </div>

  </div>
</div>

<%@ include file="footer.jsp" %>

<script>
  const editBtn = document.getElementById("edit-btn");
  const cancelBtn = document.getElementById("cancel-btn");
  const profileForm = document.getElementById("profile-form");
  const profilePicUpload = document.getElementById("profile-pic-upload");
  const profilePicPreview = document.getElementById("profile-pic-preview");

  // Enable form fields
  editBtn.addEventListener("click", () => {
    const inputs = profileForm.querySelectorAll("input, select");
    const actionButtons = profileForm.querySelector(".action-buttons");

    inputs.forEach((input) => {
      if (input.hasAttribute("readonly")) input.removeAttribute("readonly");
      if (input.hasAttribute("disabled")) input.removeAttribute("disabled");
    });

    actionButtons.style.display = "flex";
    editBtn.style.display = "none";
  });

  // Cancel editing
  cancelBtn.addEventListener("click", () => {
    const inputs = profileForm.querySelectorAll("input, select");
    const actionButtons = profileForm.querySelector(".action-buttons");

    inputs.forEach((input) => {
      if (input.tagName === "SELECT") {
        input.setAttribute("disabled", true);
      } else {
        input.setAttribute("readonly", true);
      }
    });

    actionButtons.style.display = "none";
    editBtn.style.display = "block";
    profileForm.reset();
  });

  // Profile picture preview
  profilePicUpload.addEventListener("change", (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        profilePicPreview.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });

  profileForm.addEventListener("submit", function () {
    console.log("🚀 Form is submitting");
  });
</script>
