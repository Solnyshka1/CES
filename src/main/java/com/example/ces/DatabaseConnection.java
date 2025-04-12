package com.example.ces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/enrollment";
    private static final String USER = "postgres";
    private static final String PASSWORD = "saadat";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to initialize database tables
    public static void initializeDatabase() {
        String createStudentsTable = """
            CREATE TABLE IF NOT EXISTS students (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL
            )""";

        String createCoursesTable = """
            CREATE TABLE IF NOT EXISTS courses (
                code VARCHAR(10) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                capacity INTEGER NOT NULL,
                enrolled INTEGER DEFAULT 0
            )""";

        String createEnrollmentsTable = """
            CREATE TABLE IF NOT EXISTS enrollments (
                student_id INTEGER REFERENCES students(id),
                course_code VARCHAR(10) REFERENCES courses(code),
                enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                PRIMARY KEY (student_id, course_code)
            )""";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createStudentsTable);
            stmt.execute(createCoursesTable);
            stmt.execute(createEnrollmentsTable);

            System.out.println("Database tables created successfully!");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
}