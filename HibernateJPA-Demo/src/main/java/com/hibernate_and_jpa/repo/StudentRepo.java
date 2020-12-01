package com.hibernate_and_jpa.repo;

import com.hibernate_and_jpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    Student findByName(String name);
}
