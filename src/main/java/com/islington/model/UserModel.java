package com.islington.model;

public class UserModel {
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String gender;
    private String phoneNumber;
    private String password;
    private String role;
    private String profilePicture;

    public UserModel() {}

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role != null ? role : "user";
    }

    public UserModel(String firstName, String lastName, String username, String email,
                     String gender, String phoneNumber, String password) {
        this(firstName, lastName, username, email, gender, phoneNumber, password, "user");
    }

    public UserModel(String firstName, String lastName, String username, String email,
                     String gender, String phoneNumber, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role != null ? role : "user";
    }

    public UserModel(int userId, String firstName, String lastName, String username, String email,
                     String gender, String phoneNumber, String password) {
        this(firstName, lastName, username, email, gender, phoneNumber, password);
        this.userId = userId;
    }

    public UserModel(int userId, String firstName, String lastName, String username, String email,
                     String gender, String phoneNumber, String password, String role) {
        this(firstName, lastName, username, email, gender, phoneNumber, password, role);
        this.userId = userId;
    }

    public UserModel(int userId, String firstName, String lastName, String username, String email,
                     String gender, String phoneNumber, String password, String role, String profilePicture) {
        this(userId, firstName, lastName, username, email, gender, phoneNumber, password, role);
        this.profilePicture = profilePicture;
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    // Validation methods
    public boolean isValidPhoneNumber() {
        return phoneNumber != null && phoneNumber.matches("^\\+?[0-9]{10,15}$");
    }

    public boolean isValidEmail() {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public boolean updateProfile(String phoneNumber, String gender) {
        if (isValidPhoneNumber() && gender != null && !gender.isEmpty()) {
            this.phoneNumber = phoneNumber;
            this.gender = gender;
            return true;
        }
        return false;
    }

    // Clear password from memory
    public void clearSensitiveData() {
        this.password = null;
    }
}
