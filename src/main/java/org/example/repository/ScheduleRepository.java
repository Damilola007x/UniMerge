package org.example.repository;

import org.example.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Used for Lecturer Login
    Schedule findByLecturerLoginId(String lecturerLoginId);

    // FIX: Add this for the VenueAgent to check course existence
    boolean existsByCourseCode(String courseCode);
}