package org.example.model;

public class Schedule {
    private String lecturerId;
    private String lecturerLoginId;
    private String courseCode;

    // ADD THIS CONSTRUCTOR
    public Schedule(String lecturerId, String lecturerLoginId, String courseCode) {
        this.lecturerId = lecturerId;
        this.lecturerLoginId = lecturerLoginId;
        this.courseCode = courseCode;
    }

    // Default constructor (Optional, but good practice)
    public Schedule() {}

    // GETTERS
    public String getLecturerId() { return lecturerId; }
    public String getLecturerLoginId() { return lecturerLoginId; }
    public String getCourseCode() { return courseCode; }

    // SETTERS
    public void setLecturerId(String lecturerId) { this.lecturerId = lecturerId; }
    public void setLecturerLoginId(String lecturerLoginId) { this.lecturerLoginId = lecturerLoginId; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
}