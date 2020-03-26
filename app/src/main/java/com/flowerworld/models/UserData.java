package com.flowerworld.models;

public class UserData {
    private String email;
    private String phone;
    private String password;
    private String fName;
    private String sName;
    private String patName = "";

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getfName() {
        return fName;
    }

    public String getsName() {
        return sName;
    }

    public String getPatName() {
        return patName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getFullName() {
        if (!patName.isEmpty())
            return sName + " " + fName + " " + patName;
        return sName + " " + fName;
    }
}
