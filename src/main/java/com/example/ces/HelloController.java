package com.example.ces;

import com.example.ces.dao.EnrollmentDAO;
import com.example.ces.dao.StudentDAO;
import com.example.ces.dao.CourseDAO;
import com.example.ces.model.Course;
import com.example.ces.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class HelloController {



    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        enrollmentDAO = new EnrollmentDAO();

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
        } catch (Exception e) {
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
        } catch (Exception e) {
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
