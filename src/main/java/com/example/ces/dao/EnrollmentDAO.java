package com.example.ces.dao;

import com.example.ces.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    public void enrollStudent(int studentId, String courseCode) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_code) VALUES (?, ?)";
        String updateCourse = "UPDATE courses SET enrolled = enrolled + 1 WHERE code = ?";

        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            // Check capacity first
            if (!hasAvailableCapacity(courseCode, conn)) {
                throw new SQLException("Course is full");
            }

            // Create enrollment
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.setString(2, courseCode);
                pstmt.executeUpdate();
            }

            // Update course enrollment count
            try (PreparedStatement pstmt = conn.prepareStatement(updateCourse)) {
                pstmt.setString(1, courseCode);
                pstmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private boolean hasAvailableCapacity(String courseCode, Connection conn) throws SQLException {
        String sql = "SELECT capacity, enrolled FROM courses WHERE code = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int enrolled = rs.getInt("enrolled");
                return enrolled < capacity;
            }
            return false;
        }
    }

    public void unenrollStudent(int studentId, String courseCode) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_code = ?";
        String updateCourse = "UPDATE courses SET enrolled = enrolled - 1 WHERE code = ?";

        Connection conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            // Remove enrollment
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.setString(2, courseCode);
                pstmt.executeUpdate();
            }

            // Update course enrollment count
            try (PreparedStatement pstmt = conn.prepareStatement(updateCourse)) {
                pstmt.setString(1, courseCode);
                pstmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<String> getEnrolledCourses(int studentId) throws SQLException {
        List<String> courses = new ArrayList<>();
        String sql = "SELECT course_code FROM enrollments WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courses.add(rs.getString("course_code"));
            }
        }
        return courses;
    }
}