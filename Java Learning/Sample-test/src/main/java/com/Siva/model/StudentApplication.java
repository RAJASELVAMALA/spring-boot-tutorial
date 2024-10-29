//package com.Siva.model;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//
//@SpringBootApplication
//@RestController
//public class StudentApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(StudentApplication.class, args);
//    }
//
//    @GetMapping("/welcome")
//    public String SayHi() {
//        return "Hello Sivakami";
//    }
//
//    @GetMapping("/student")
//    public StudentApp getStudentDetails() {
//        System.out.println("Retrieving student details");
//        LocalDate dob = LocalDate.of(2000, 5, 13); // Correctly formatted date
//
//        return new StudentApp("Sivakami", 20, "A" , dob);
//    }
//}
//package com.Siva.model;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootApplication
//@RestController
//public class StudentApplication {
//
//    private List<StudentApp> studentList = new ArrayList<>();
//
//    public static void main(String[] args) {
//        SpringApplication.run(StudentApplication.class, args);
//    }
//
//    @GetMapping("/welcome")
//    public String sayHi() {
//        return "Hello Sivakami";
//    }
//
//    @GetMapping("/student")
//    public StudentApp getStudentDetails() {
//        System.out.println("Retrieving student details");
//        LocalDate dob = LocalDate.of(2000, 5, 13); // Correctly formatted date
//        return new StudentApp("Sivakami", 20, "A", dob);
//    }
//
//    @PostMapping("/students")
//    public String addStudent(@RequestBody StudentApp student) {
//        studentList.add(student);
//        return "Student added successfully";
//    }
//
//    @GetMapping("/students")
//    public List<StudentApp> getAllStudents() {
//        return studentList;
//    }
//}
//
package com.Siva.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class StudentApplication {

    private List<StudentApp> studentList = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

    @GetMapping("/welcome")
    public String sayHi() {
        return "Hello Sivakami";
    }

    @GetMapping("/student")
    public StudentApp getStudentDetails() {
        System.out.println("Retrieving student details");
        LocalDate dob = LocalDate.of(2000, 5, 13);
        return new StudentApp("Sivakami", 20, "A", dob);
    }

    @PostMapping("/students")
    public String addStudents(@RequestBody List<StudentApp> students) {
        studentList.addAll(students);
        return "Students added successfully";
    }

    @GetMapping("/students")
    public List<StudentApp> getAllStudents() {
        return studentList;
    }

}
