package com.example.ces;

import com.example.ces.dao.EnrollmentDAO;
import com.example.ces.dao.StudentDAO;
import com.example.ces.dao.CourseDAO;
import com.example.ces.model.Course;
import com.example.ces.model.Student;
import com.example.ces.model.EnrollmentDetail;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class HelloController {
    // Student UI Components
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> studentIdCol;
    @FXML private TableColumn<Student, String> studentNameCol;
    @FXML private TableColumn<Student, String> studentEmailCol;
    @FXML private TextField studentName;
    @FXML private TextField studentEmail;

    // Course UI Components
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> courseCodeCol;
    @FXML private TableColumn<Course, String> courseNameCol;
    @FXML private TableColumn<Course, Integer> capacityCol;
    @FXML private TableColumn<Course, Integer> enrolledCol;
    @FXML private TextField courseCode;
    @FXML private TextField courseName;
    @FXML private TextField courseCapacity;

    // Enrollment UI Components
    @FXML private ComboBox<Student> studentCombo;
    @FXML private ComboBox<Course> courseCombo;
    @FXML private TableView<EnrollmentDetail> enrollmentTable;
    @FXML private TableColumn<EnrollmentDetail, Integer> enrollStudentIdCol;
    @FXML private TableColumn<EnrollmentDetail, String> enrollStudentNameCol;
    @FXML private TableColumn<EnrollmentDetail, String> enrollCourseCodeCol;
    @FXML private TableColumn<EnrollmentDetail, String> enrollCourseNameCol;

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        enrollmentDAO = new EnrollmentDAO();

        // Initialize Student Table
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Initialize Course Table
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        enrolledCol.setCellValueFactory(new PropertyValueFactory<>("enrolled"));

        // Initialize Enrollment Table
        enrollStudentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        enrollStudentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        enrollCourseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        enrollCourseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                courseCode.setText(newSelection.getCode());
                courseName.setText(newSelection.getName());
                courseCapacity.setText(String.valueOf(newSelection.getCapacity()));
            }
        });

        refreshTables();
    }

    private void refreshTables() {
        try {
            // Refresh Students
            studentTable.getItems().setAll(studentDAO.getAllStudents());
            studentCombo.getItems().setAll(studentDAO.getAllStudents());

            // Refresh Courses
            courseTable.getItems().setAll(courseDAO.getAllCourses());
            courseCombo.getItems().setAll(courseDAO.getAllCourses());

            // Refresh Enrollments
            enrollmentTable.getItems().setAll(enrollmentDAO.getAllEnrollmentDetails());

        } catch (SQLException e) {
            showAlert("Error", "Failed to refresh data: " + e.getMessage());
        }
    }

    // Student Actions
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

    // Course Actions
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
            refreshTables();
            showAlert("Success", "Course added successfully");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid capacity value");
        } catch (SQLException e) {
            showAlert("Error", "Failed to add course: " + e.getMessage());
        }
    }

    @FXML
    private void updateCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a course to update");
            return;
        }

        String code = courseCode.getText().trim();
        String name = courseName.getText().trim();
        String capacityText = courseCapacity.getText().trim();

        if (code.isEmpty() || name.isEmpty() || capacityText.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        try {
            int capacity = Integer.parseInt(capacityText);
            selected.setName(name);
            selected.setCapacity(capacity);
            courseDAO.updateCourse(selected);
            clearCourseFields();
            refreshTables();
            showAlert("Success", "Course updated successfully");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid capacity value");
        } catch (SQLException e) {
            showAlert("Error", "Failed to update course: " + e.getMessage());
        }
    }

    @FXML
    private void deleteCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a course to delete");
            return;
        }

        try {
            courseDAO.deleteCourse(selected.getCode());
            clearCourseFields();
            refreshTables();
            showAlert("Success", "Course deleted successfully");
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete course: " + e.getMessage());
        }
    }

    private void clearCourseFields() {
        courseCode.clear();
        courseName.clear();
        courseCapacity.clear();
    }

    // Enrollment Actions
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
            showAlert("Success", "Enrollment successful");
        } catch (Exception e) {
            showAlert("Error", "Enrollment failed: " + e.getMessage());
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
            showAlert("Success", "Unenrollment successful");
        } catch (Exception e) {
            showAlert("Error", "Unenrollment failed: " + e.getMessage());
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
