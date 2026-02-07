package org.example.model;

public class Student {
    private String name;
    private String matricNumber;
    private String carryOverCourse;

    // REQUIRED CONSTRUCTOR: This fixes the "found: String, String, String" error
    public Student(String name, String matricNumber, String carryOverCourse) {
        this.name = name;
        this.matricNumber = matricNumber;
        this.carryOverCourse = carryOverCourse;
    }

    // Default constructor (Needed by some frameworks)
    public Student() {}

    // GETTERS
    public String getName() { return name; }
    public String getMatricNumber() { return matricNumber; }
    public String getCarryOverCourse() { return carryOverCourse; }

    // SETTERS
    public void setName(String name) { this.name = name; }
    public void setMatricNumber(String matricNumber) { this.matricNumber = matricNumber; }
    public void setCarryOverCourse(String carryOverCourse) { this.carryOverCourse = carryOverCourse; }
}