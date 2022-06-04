package com.example.firebaseprojectmarkshandler.models;

public class ModelMember {
    String email ;
    String courseId ;
    double attendanceGrade ;
    double quizGrade ;

    public ModelMember (){}



    public ModelMember(String email,
                       String courseId, double attendanceGrade, double quizGrade) {
        this.email = email;
        this.courseId = courseId;
        this.attendanceGrade = attendanceGrade;
        this.quizGrade = quizGrade;
    }

    public String getEmail() {
        return email;
    }


    public String getCourseId() {
        return courseId;
    }

    public double getAttendanceGrade() {
        return attendanceGrade;
    }

    public double getQuizGrade() {
        return quizGrade;
    }
}
