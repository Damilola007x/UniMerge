package org.example.service;

import org.example.model.Student;
import org.example.repository.ScheduleRepository;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {

    @Autowired
    private AgentContainer jadeContainer;

    @Autowired
    private ScheduleRepository scheduleRepo;

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void spawnNegotiation(Student student) throws Exception {
        // Fix the 'Incompatible Types' error by formatting to String
        String carryOverCourse = student.getCarryOverCourse();
        String matric = student.getMatricNumber();

        // Pass the Repository and Course info as arguments
        Object[] agentArgs = new Object[] {
                carryOverCourse,
                matric,
                scheduleRepo // Passing repo so agents can check DB
        };

        AgentController ac = jadeContainer.createNewAgent(
                "Agent-" + matric.replace("/", "-"),
                "org.example.agents.StudentAgent",
                agentArgs
        );
        ac.start();
    }
}