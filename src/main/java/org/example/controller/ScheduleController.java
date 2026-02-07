package org.example.controller;

import org.example.model.Schedule;
import org.example.model.Student;
import org.example.repository.ScheduleRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {

    // THE SOURCE OF TRUTH: Global variables for the Agents to read
    public static String globalBlacklist = "";
    public static String globalVenue = "Unassigned Hall";

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private StudentRepository studentRepo;

    /**
     * LOGIN ENDPOINT
     * Handles both Lecturers and Students using hardcoded logic in the repositories
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> payload) {
        String role = payload.get("role");
        String loginId = payload.get("loginId");

        if ("lecturer".equals(role)) {
            Schedule s = scheduleRepo.findByLecturerLoginId(loginId);
            return (s != null) ? "SUCCESS:" + s.getLecturerId() : "ERROR: Lecturer ID not found";
        } else {
            Student st = studentRepo.findByMatricNumber(loginId);
            return (st != null) ? "SUCCESS:" + st.getName() : "ERROR: Student not found";
        }
    }

    /**
     * LECTURER CONSTRAINTS ENDPOINT
     * This is where the lecturer sets the specific Hall and Blacklisted days
     */
    @PostMapping("/constraints")
    public String updateConstraints(@RequestBody Map<String, String> payload) {
        globalBlacklist = payload.get("prohibitedDays");
        globalVenue = payload.get("venueName");

        System.out.println("--- POLICY BROADCAST ---");
        System.out.println("Venue Locked to: " + globalVenue);
        System.out.println("Days Blacklisted: " + globalBlacklist);

        return "SUCCESS";
    }

    /**
     * STUDENT NEGOTIATION ENDPOINT
     * Validates the student's choice against the Lecturer's policy before spawning Agents
     */
    @PostMapping("/negotiate")
    public String negotiate(@RequestBody Map<String, String> payload) {
        String matric = payload.get("matricNumber");
        String requestedCourse = payload.get("courseCode").trim();
        String day = payload.get("preferredDay");
        String requestedVenue = payload.get("preferredVenue");

        // 1. Database Check (In-Memory Repository)
        Student student = studentRepo.findByMatricNumber(matric);
        if (student == null) return "ERROR: Matric number not recognized.";

        // 2. Course Authorization Check
        String registeredCourse = student.getCarryOverCourse().trim();
        if (!registeredCourse.equalsIgnoreCase(requestedCourse)) {
            return "REJECTED: You are only authorized for " + registeredCourse;
        }

        // 3. Blacklist Check (The "Preferred Day" logic)
        if (globalBlacklist.contains(day)) {
            return "REJECTED: The Venue Agent has blacklisted " + day + " per Lecturer's request.";
        }

        // 4. Venue Mismatch Check (The "Strict Venue" logic)
        if (!requestedVenue.equalsIgnoreCase(globalVenue)) {
            return "REJECTED: Unauthorized Venue! You must select: " + globalVenue;
        }

        // 5. Success - Trigger JADE Handshake
        // We return the authorized data to the frontend to start the Agent process
        return "AGENT_SPAWNED:" + requestedCourse + ":" + student.getName() + ":" + day + ":" + globalVenue;
    }
}