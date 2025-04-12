package com.example.ces.dao;

import com.example.ces.model.Course;
import com.example.ces.DatabaseConnection;
import com.example.ces.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void addCourse(Course course) throws SQLException {
        // FIX: Changed "enrolled_count" to "enrolled"
        String sql = "INSERT INTO courses (code, name, capacity, enrolled) VALUES (?, ?, ?, 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getName());
            pstmt.setInt(3, course.getCapacity());
            pstmt.executeUpdate();
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        // FIX: Changed "enrolled_count" to "enrolled"
        String sql = "SELECT code, name, capacity, enrolled FROM courses";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getInt("enrolled") // Corrected column name
                );
                courses.add(course);
            }
        }
        return courses;
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students"; // Includes 'id' column
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("email")
                );
                student.setId(rs.getInt("id")); // Set the ID
                students.add(student);
            }
        }
        return students;
    }

    public void updateCourse(Course course) throws SQLException {
        // FIX: Changed "enrolled_count" to "enrolled"
        String sql = "UPDATE courses SET name = ?, capacity = ?, enrolled = ? WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getCapacity());
            pstmt.setInt(3, course.getEnrolled());
            pstmt.setString(4, course.getCode());
            pstmt.executeUpdate();
        }
    }

    public void deleteCourse(String code) throws SQLException {
        String sql = "DELETE FROM courses WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.executeUpdate();
        }
    }

    public Course getCourseByCode(String code) throws SQLException {
        // FIX: Changed "enrolled_count" to "enrolled"
        String sql = "SELECT code, name, capacity, enrolled FROM courses WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getInt("capacity"),
                            rs.getInt("enrolled") // Corrected column name
                    );
                }
            }
        }
        return null;
    }
}