package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    CacheManager cacheManager;

    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @GetMapping("/student")
    public List<Student> getStudent(){
        return studentService.getStudent();
    }

    @GetMapping("/cache")
    public Object getCache(){
        return cacheManager.getCache("students");
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable String id){
       return studentService.getStudentById(id);
    }


    @GetMapping("/student/search")
    public Student searchStudentName(@RequestParam String name){
        return studentService.searchByStudentName(name);
    }


    @PutMapping("/student")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }


    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable String id){
        return studentService.deleteStudent(id);
    }
}
