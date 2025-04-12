package com.example.ces;

import com.example.ces.dao.EnrollmentDAO;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentDAO enrollmentDAO;

    public EnrollmentService() {
        this.enrollmentDAO = new EnrollmentDAO();
    }

    public void enrollStudent(int studentId, String courseCode) throws SQLException {
        try {
            enrollmentDAO.enrollStudent(studentId, courseCode);
        } catch (SQLException e) {
            throw new SQLException("Failed to enroll student: " + e.getMessage());
        }
    }

    // Change this method name from dropEnrollment to unenrollStudent
    public void unenrollStudent(int studentId, String courseCode) throws SQLException {
        try {
            enrollmentDAO.unenrollStudent(studentId, courseCode);
        } catch (SQLException e) {
            throw new SQLException("Failed to unenroll student: " + e.getMessage());
        }
    }

    public List<String> getEnrolledCourses(int studentId) throws SQLException {
        try {
            return enrollmentDAO.getEnrolledCourses(studentId);
        } catch (SQLException e) {
            throw new SQLException("Failed to get enrolled courses: " + e.getMessage());
        }
    }
}