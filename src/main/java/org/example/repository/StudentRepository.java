package org.example.repository;

import org.example.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Spring now sees 'password' in the Entity, so this works!
    Student findByMatricNumberAndPassword(String matricNumber, String password);

    Student findByMatricNumber(String matricNumber);
}