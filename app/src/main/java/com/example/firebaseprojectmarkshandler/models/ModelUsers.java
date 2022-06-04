package com.example.firebaseprojectmarkshandler.models;

public class ModelUsers  {
    private String name;
    private String email ;
    private String userType ;
    private String doctorUid ;

    public ModelUsers(){}

    public ModelUsers(String name, String email, String userType, String doctorUid) {
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.doctorUid = doctorUid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public String getDoctorUid() {
        return doctorUid;
    }
}
