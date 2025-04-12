package com.example.ces.dao;

import com.example.ces.DatabaseConnection;
import com.example.ces.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students"; // Includes 'id'
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("email")
                );
                student.setId(rs.getInt("id")); // Set ID from database
                students.add(student);
            }
        }
        return students;
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}