package com.hibernate_and_jpa.service;

//holds utilization of repo hibernate integration

import com.hibernate_and_jpa.entity.Student;
import com.hibernate_and_jpa.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepo repo;

    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    public Student getStudentById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Student getStudentByName(String name ) {
        return repo.findByName(name);
    }

    public void deleteStudentById(int id) {
        repo.deleteById(id);
    }

    public Student updateStudent(Student student) {
        Student updatingStudent = repo.findById(student.id).orElse(null);
        assert updatingStudent != null;
        updatingStudent.name = student.name;
        updatingStudent.grade = student.grade;
        return repo.save(updatingStudent);
    }
}
