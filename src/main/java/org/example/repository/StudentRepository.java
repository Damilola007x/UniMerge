package org.example.repository;

import org.example.model.Student;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    private static final List<Student> mockStudents = new ArrayList<>();

    static {
        mockStudents.add(new Student("Onikosi Quadri Olajuwon", "22/11438", "CSC301"));
        mockStudents.add(new Student("NEMIEBOKA TAMUNOMIEBAKA FORTUNE", "22/11419", "CSC302"));
        mockStudents.add(new Student("Odunaiya Matthew Olarenwaju", "22/10588", "CSC303"));
        mockStudents.add(new Student("Song David", "22/11000", "CSC304"));
        mockStudents.add(new Student("Adeoshun Olayemi", "22/9753", "CSC305"));
        mockStudents.add(new Student("Ijale Freeman Ade", "23/13247", "CSC306"));
        mockStudents.add(new Student("Obada Abraham OGHENERUKEWE", "22/11155", "CSC307"));
        mockStudents.add(new Student("Olomowewe Fathia Omowunmi", "22/10502", "CSC301"));
        mockStudents.add(new Student("OLABIYI", "22210083", "CSC302"));
        mockStudents.add(new Student("Echemazu Bianca", "22/11317", "CSC303"));
        mockStudents.add(new Student("Unagha Jessica Ugochi", "22/11177", "CSC304"));
        mockStudents.add(new Student("OSILAMA JOSEPH ESIEMOKHAI", "22/11481", "CSC305"));
        mockStudents.add(new Student("Tijani Emmanuel Ayotomiwa", "22/10200", "CSC306"));
        mockStudents.add(new Student("Enyi David Chibueze", "22/10376", "CSC307"));
        mockStudents.add(new Student("OKERE AMANZE IKENNA", "22/11273", "CSC301"));
    }

    public Student findByMatricNumber(String matric) {
        return mockStudents.stream()
                .filter(s -> s.getMatricNumber().equalsIgnoreCase(matric))
                .findFirst()
                .orElse(null);
    }
}