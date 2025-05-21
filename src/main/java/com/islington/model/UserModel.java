package com.islington.model;

public class UserModel {
    private int user_id;
    private String first_name;
    private String last_name;
    private String user_name;
    private String user_email;
    private String gender;
    private String phone_number;
    private String user_password;
    private String user_role;
    private String profile_picture;

    public UserModel() {}
    
    public UserModel(int user_id) {
        this.user_id = user_id;
    }

    public UserModel(String user_email, String user_password) {
        this.user_email = user_email;
        this.user_password = user_password;
    }

    public UserModel(String user_email, String user_password, String user_role) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_role = user_role != null ? user_role : "user";
    }

    public UserModel(String first_name, String last_name, String user_name, String user_email,
                     String gender, String phone_number, String user_password) {
        this(first_name, last_name, user_name, user_email, gender, phone_number, user_password, "user");
    }

    public UserModel(String first_name, String last_name, String user_name, String user_email,
                     String gender, String phone_number, String user_password, String user_role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.user_email = user_email;
        this.gender = gender;
        this.phone_number = phone_number;
        this.user_password = user_password;
        this.user_role = user_role != null ? user_role : "user";
    }

    public UserModel(int user_id, String first_name, String last_name, String user_name, String user_email,
                     String gender, String phone_number, String user_password) {
        this(first_name, last_name, user_name, user_email, gender, phone_number, user_password);
        this.user_id = user_id;
    }

    public UserModel(int user_id, String first_name, String last_name, String user_name, String user_email,
                     String gender, String phone_number, String user_password, String user_role) {
        this(first_name, last_name, user_name, user_email, gender, phone_number, user_password, user_role);
        this.user_id = user_id;
    }

    public UserModel(int user_id, String first_name, String last_name, String user_name, String user_email,
                     String gender, String phone_number, String user_password, String user_role, String profile_picture) {
        this(user_id, first_name, last_name, user_name, user_email, gender, phone_number, user_password, user_role);
        this.profile_picture = profile_picture;
    }

    // Getters and setters
    public int getUserId() { return user_id; }
    public void setUserId(int user_id) { this.user_id = user_id; }

    public String getFirstName() { return first_name; }
    public void setFirstName(String first_name) { this.first_name = first_name; }

    public String getLastName() { return last_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }

    public String getUsername() { return user_name; }
    public void setUsername(String user_name) { this.user_name = user_name; }

    public String getEmail() { return user_email; }
    public void setEmail(String user_email) { this.user_email = user_email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhoneNumber() { return phone_number; }
    public void setPhoneNumber(String phone_number) { this.phone_number = phone_number; }

    public String getPassword() { return user_password; }
    public void setPassword(String user_password) { this.user_password = user_password; }

    public String getRole() { return user_role; }
    public void setRole(String user_role) { this.user_role = user_role; }

    public String getProfilePicture() { return profile_picture; }
    public void setProfilePicture(String profile_picture) { this.profile_picture = profile_picture; }

    // Validation methods
    public boolean isValidPhoneNumber() {
        return phone_number != null && phone_number.matches("^\\+?[0-9]{10,15}$");
    }

    public boolean isValidEmail() {
        return user_email != null && user_email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public boolean updateProfile(String phone_number, String gender) {
        if (isValidPhoneNumber() && gender != null && !gender.isEmpty()) {
            this.phone_number = phone_number;
            this.gender = gender;
            return true;
        }
        return false;
    }

    // Clear password from memory
    public void clearSensitiveData() {
        this.user_password = null;
    }
}