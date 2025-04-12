package com.example.ces.model;

public class EnrollmentDetail {
    private int studentId;
    private String studentName;
    private String courseCode;
    private String courseName;


    public EnrollmentDetail(int studentId, String studentName, String courseCode, String courseName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
}
