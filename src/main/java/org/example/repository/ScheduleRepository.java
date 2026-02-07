package org.example.repository;

import org.example.model.Schedule;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {
    private static final List<Schedule> mockSchedules = new ArrayList<>();

    static {
        // Hardcoding your Lecturer Data here
        mockSchedules.add(new Schedule("Dr_Odegbesan", "LEC001", "CSC301"));
        mockSchedules.add(new Schedule("Prof_Adeyemi", "LEC002", "CSC302"));
        mockSchedules.add(new Schedule("Dr_Bello", "LEC003", "CSC303"));
        mockSchedules.add(new Schedule("Engr_Okoro", "LEC004", "CSC304"));
        mockSchedules.add(new Schedule("Dr_Suleiman", "LEC005", "CSC305"));
        mockSchedules.add(new Schedule("Mrs_Chukwuma", "LEC006", "CSC306"));
        mockSchedules.add(new Schedule("Mr_Abiola", "LEC007", "CSC307"));
    }

    public Schedule findByLecturerLoginId(String loginId) {
        return mockSchedules.stream()
                .filter(s -> s.getLecturerLoginId().equals(loginId))
                .findFirst()
                .orElse(null);
    }

    public boolean existsByCourseCode(String courseCode) {
        // For the demo, this checks if a course is already booked in memory
        return false;
    }
}