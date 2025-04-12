package com.example.ces.model;

public class Course {
    private String code;
    private String name;
    private int capacity;
    private int enrolled;

    public Course(String code, String name, int capacity, int enrolled) {
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

    public Course(String code, String name, int capacity) {
        this(code, name, capacity, 0);
    }

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getEnrolled() { return enrolled; }
    public void setEnrolled(int enrolled) { this.enrolled = enrolled; }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}