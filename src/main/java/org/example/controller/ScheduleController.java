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

    // These are the ONLY two variables you need for policy.
    // They must be public so VenueResponseBehavior can see them.
    public static String globalBlacklist = "";
    public static String globalVenue = "Unassigned Hall";

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private StudentRepository studentRepo;

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

    @PostMapping("/constraints")
    public String updateConstraints(@RequestBody Map<String, String> payload) {
        // FIX: Update the global variables that the Agents actually use
        globalBlacklist = payload.get("prohibitedDays");
        globalVenue = payload.get("venueName");
        System.out.println("Lecturer Policy Updated: Venue is " + globalVenue);
        return "SUCCESS";
    }

    @PostMapping("/negotiate")
    public String negotiate(@RequestBody Map<String, String> payload) {
        String matric = payload.get("matricNumber");
        String requestedCourse = payload.get("courseCode").trim();
        String day = payload.get("preferredDay");
        String requestedVenue = payload.get("preferredVenue");

        Student student = studentRepo.findByMatricNumber(matric);
        if (student == null) return "ERROR: Matric number not recognized.";

        String registeredCourse = student.getCarryOverCourse().trim();

        // 1. Validation: Is it the student's actual course?
        if (!registeredCourse.equalsIgnoreCase(requestedCourse)) {
            return "REJECTED: Unauthorized course.";
        }

        // 2. Validation: Is the day blocked?
        if (globalBlacklist.contains(day)) {
            return "REJECTED: Venue Agent says " + day + " is blacklisted.";
        }

        // 3. Validation: Did they pick the venue the lecturer chose?
        if (!requestedVenue.equalsIgnoreCase(globalVenue)) {
            return "REJECTED: Access Denied. You must select the authorized venue: " + globalVenue;
        }

        // If all checks pass, we tell the frontend to trigger the JADE agents
        // We return globalVenue to ensure the frontend displays the CORRECT venue
        return "AGENT_SPAWNED:" + requestedCourse + ":" + student.getName() + ":" + day + ":" + globalVenue;
    }
}