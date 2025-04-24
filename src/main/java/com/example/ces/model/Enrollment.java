package com.example.ces.model;

import java.sql.Timestamp;

public class Enrollment {
    private int studentId;
    private String courseCode;
    private Timestamp enrollmentDate;

    public Enrollment(int studentId, String courseCode, Timestamp enrollmentDate) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters and setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Timestamp getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Timestamp enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
