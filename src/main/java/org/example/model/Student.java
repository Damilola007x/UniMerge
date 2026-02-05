package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students_registry") // Matches your CREATE TABLE name
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "matric_number", unique = true, nullable = false)
    private String matricNumber;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "carry_over_course", nullable = false)
    private String carryOverCourse;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // GETTERS AND SETTERS
    public String getName() { return fullName; } // Controller uses .getName()
    public void setName(String fullName) { this.fullName = fullName; }

    public String getMatricNumber() { return matricNumber; }
    public void setMatricNumber(String matricNumber) { this.matricNumber = matricNumber; }

    public String getCarryOverCourse() { return carryOverCourse; }
    public void setCarryOverCourse(String carryOverCourse) { this.carryOverCourse = carryOverCourse; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getId() { return id; }
}