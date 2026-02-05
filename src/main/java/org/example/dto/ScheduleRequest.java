package org.example.dto;

public class ScheduleRequest {
    private String studentId;
    private String courseCode;
    private String preferredDay;

    // Getters and Setters are MANDATORY for Postman to work
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getPreferredDay() { return preferredDay; }
    public void setPreferredDay(String preferredDay) { this.preferredDay = preferredDay; }
}