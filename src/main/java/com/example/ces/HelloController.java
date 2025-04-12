package com.example.ces;

import com.example.ces.dao.EnrollmentDAO;
import com.example.ces.dao.StudentDAO;
import com.example.ces.dao.CourseDAO;
import com.example.ces.model.Course;
import com.example.ces.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class HelloController {
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> studentIdCol;
    @FXML
    private TableColumn<Student, String> studentNameCol;
    @FXML
    private TableColumn<Student, String> studentEmailCol;
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentEmail;

    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseCodeCol;
    @FXML
    private TableColumn<Course, String> courseNameCol;
    @FXML
    private TableColumn<Course, Integer> capacityCol;
    @FXML
    private TableColumn<Course, Integer> enrolledCol;
    @FXML
    private TextField courseCode;
    @FXML
    private TextField courseName;
    @FXML
    private TextField courseCapacity;

    @FXML
    private ComboBox<Student> studentCombo;
    @FXML
    private ComboBox<Course> courseCombo;

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        enrollmentDAO = new EnrollmentDAO();

        // Initialize table columns
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        enrolledCol.setCellValueFactory(new PropertyValueFactory<>("enrolled"));

        refreshTables();
    }

    @FXML
    private void addStudent() {
        String name = studentName.getText().trim();
        String email = studentEmail.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        try {
            Student student = new Student(name, email);
            studentDAO.addStudent(student);
            clearStudentFields();
            refreshTables();
            showAlert("Success", "Student added successfully");
        } catch (Exception e) {
            showAlert("Error", "Failed to add student: " + e.getMessage());
        }
    }

    @FXML
    private void updateStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a student to update");
            return;
        }

        String name = studentName.getText().trim();
        String email = studentEmail.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        try {
            selected.setName(name);
            selected.setEmail(email);
            studentDAO.updateStudent(selected);
            clearStudentFields();
            refreshTables();
            showAlert("Success", "Student updated successfully");
        } catch (Exception e) {
            showAlert("Error", "Failed to update student: " + e.getMessage());
        }
    }

    @FXML
    private void deleteStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a student to delete");
            return;
        }

        try {
            studentDAO.deleteStudent(selected.getId());
            clearStudentFields();
            refreshTables();
            showAlert("Success", "Student deleted successfully");
        } catch (Exception e) {
            showAlert("Error", "Failed to delete student: " + e.getMessage());
        }
    }

    private void clearStudentFields() {
        studentName.clear();
        studentEmail.clear();
    }

    @FXML
    private void handleEnroll() {
        Student student = studentCombo.getValue();
        Course course = courseCombo.getValue();

        if (student == null || course == null) {
            showAlert("Error", "Please select both student and course");
            return;
        }

        try {
            enrollmentDAO.enrollStudent(student.getId(), course.getCode());
            refreshTables();
            showAlert("Success", "Student enrolled successfully");
        } catch (Exception e) {
            showAlert("Error", "Failed to enroll: " + e.getMessage());
        }
    }

    @FXML
    private void handleDrop() {
        Student student = studentCombo.getValue();
        Course course = courseCombo.getValue();

        if (student == null || course == null) {
            showAlert("Error", "Please select both student and course");
            return;
        }

        try {
            enrollmentDAO.unenrollStudent(student.getId(), course.getCode());
            refreshTables();
            showAlert("Success", "Student unenrolled successfully");
        } catch (Exception e) {
            showAlert("Error", "Failed to unenroll: " + e.getMessage());
        }
    }

    @FXML
    private void exportToCSV() {
        // Implement CSV export functionality
    }

    @FXML
    private void generateReport() {
        // Implement report generation functionality
    }

    private void refreshTables() {
        try {
            studentTable.getItems().setAll(studentDAO.getAllStudents());
            courseTable.getItems().setAll(courseDAO.getAllCourses());
            studentCombo.getItems().setAll(studentDAO.getAllStudents());
            courseCombo.getItems().setAll(courseDAO.getAllCourses());
        } catch (Exception e) {
            showAlert("Error", "Failed to refresh data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void addCourse() {
        String code = courseCode.getText().trim();
        String name = courseName.getText().trim();
        String capacityText = courseCapacity.getText().trim();

        if (code.isEmpty() || name.isEmpty() || capacityText.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        try {
            int capacity = Integer.parseInt(capacityText);
            Course course = new Course(code, name, capacity);
            courseDAO.addCourse(course);
            clearCourseFields();
            refreshTables(); // Refresh the UI to show the new course
            showAlert("Success", "Course added successfully");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid capacity value (must be a number)");
        } catch (SQLException e) {
            showAlert("Error", "Failed to add course: " + e.getMessage());
        }
    }

    private void clearCourseFields() {
        courseCode.clear();
        courseName.clear();
        courseCapacity.clear();
    }
}