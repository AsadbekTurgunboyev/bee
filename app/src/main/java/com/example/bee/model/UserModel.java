package com.example.bee.model;

public class UserModel {

    String name;
    String phone;
    String userName;
    String key;
    String profilePic;
    String lastTime;
    int available;

    public UserModel() {
    }

    public UserModel(String name, String phone, String userName, String key, String profilePic, String lastTime, int available) {
        this.name = name;
        this.phone = phone;
        this.userName = userName;
        this.key = key;
        this.profilePic = profilePic;
        this.lastTime = lastTime;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
