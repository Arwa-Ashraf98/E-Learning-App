package com.example.firebaseprojectmarkshandler.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelCourse implements Parcelable {
    String courseName ;
    double quizGrade;
    double projectGrade ;
    double attendanceGrade;
    String userUid ;
    String courseId ;

    public ModelCourse(){}

    public ModelCourse(String courseName, double quizGrade, double projectGrade,
                       double attendanceGrade, String userUid, String courseId) {
        this.courseName = courseName;
        this.quizGrade = quizGrade;
        this.projectGrade = projectGrade;
        this.attendanceGrade = attendanceGrade;
        this.userUid = userUid;
        this.courseId = courseId;
    }

    protected ModelCourse(Parcel in) {
        courseName = in.readString();
        quizGrade = in.readDouble();
        projectGrade = in.readDouble();
        attendanceGrade = in.readDouble();
        userUid = in.readString();
        courseId = in.readString();
    }

    public static final Creator<ModelCourse> CREATOR = new Creator<ModelCourse>() {
        @Override
        public ModelCourse createFromParcel(Parcel in) {
            return new ModelCourse(in);
        }

        @Override
        public ModelCourse[] newArray(int size) {
            return new ModelCourse[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public double getQuizGrade() {
        return quizGrade;
    }

    public double getProjectGrade() {
        return projectGrade;
    }

    public double getAttendanceGrade() {
        return attendanceGrade;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(courseName);
        parcel.writeDouble(quizGrade);
        parcel.writeDouble(projectGrade);
        parcel.writeDouble(attendanceGrade);
        parcel.writeString(userUid);
        parcel.writeString(courseId);
    }
}
