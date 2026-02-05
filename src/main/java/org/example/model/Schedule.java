package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "final_schedules")
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "venue_id")
    private String venueId;

    @Column(name = "lecturer_id")
    private String lecturerId; // The Name (e.g., Dr_Odegbesan)

    @Column(name = "lecturer_login_id", unique = true)
    private String lecturerLoginId; // The ID (e.g., LEC001)

    @Column(name = "negotiation_rounds")
    private Integer negotiationRounds;
}