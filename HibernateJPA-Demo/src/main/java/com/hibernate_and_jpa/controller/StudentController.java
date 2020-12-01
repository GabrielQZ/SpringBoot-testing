package com.hibernate_and_jpa.controller;
//package com.inclassteach.crud.done.controller;

//import com.inclassteach.crud.done.entity.Student;
//import com.inclassteach.crud.done.service.StudentService;
import com.hibernate_and_jpa.entity.Student;
import com.hibernate_and_jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;

    //    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student){
        return service.saveStudent(student);
    }
//    @PostMapping("/addStudents")
//    public List<Student> addStudents(@RequestBody List<Student> students){
//        return service.saveStudents(students);
//    }

//    @GetMapping("/students")
//    public List<Student> getAllStudents(){
//        return service.getStudents();
//    }
    @GetMapping("/studentById/{id}")
    public Student getStudentById(@PathVariable int id){
        return service.getStudentById(id);
    }
    @GetMapping("/studentByName/{name}")
    public Student getStudentByName(@PathVariable String name){
        return service.getStudentByName(name);
    }

    @PutMapping("/updateStudent")
    public Student updateStudent(@RequestBody Student student){
        return service.updateStudent(student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable int id){
        service.deleteStudentById(id);
    }
}
