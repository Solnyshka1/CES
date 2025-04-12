package com.example.ces.dao;

import com.example.ces.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    public void enrollStudent(int studentId, String courseCode) throws SQLException {

        try {
            }

                pstmt.setInt(1, studentId);
                pstmt.setString(2, courseCode);
                pstmt.executeUpdate();
            }

                pstmt.setString(1, courseCode);
            }

            conn.commit();
        } catch (SQLException e) {
                conn.rollback();
            throw e;
        } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void unenrollStudent(int studentId, String courseCode) throws SQLException {

        try {
                pstmt.setInt(1, studentId);
                pstmt.setString(2, courseCode);
            }

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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
                while (rs.next()) {
            }
        }
        return courses;
    }
        }