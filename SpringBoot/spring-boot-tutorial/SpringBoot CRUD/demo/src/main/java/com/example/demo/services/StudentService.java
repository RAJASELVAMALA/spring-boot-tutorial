package com.example.demo.services;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;


    //Create Student
    @CacheEvict(value = "students", key = "#student.id")
    public Student createStudent(Student student){
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setEmailId(student.getEmailId());
        newStudent.setPhoneNumber(student.getPhoneNumber());
        return studentRepository.save(student);
    }


    //Get Student
    public List<Student> getStudent(){
        List<Student> students=studentRepository.findAll();
        return students;
    }


    //Get Student By Id
    @Cacheable(value = "students", key = "#id")
    public Student getStudentById(String id){
        Optional<Student> student=studentRepository.findById(id);
        if (student.isPresent()){
            return student.get();
        }
        return null;
    }

    //Update Student
    @CachePut(value = "students", key = "#student.id")
    public Student updateStudent(Student student){
        Optional<Student> students =studentRepository.findById(student.getId());
        if (students.isPresent()){
            Student updateStudent = students.get();
            updateStudent.setPhoneNumber(student.getPhoneNumber());
            updateStudent.setEmailId(student.getEmailId());
            updateStudent.setName(student.getName());
            return studentRepository.save(updateStudent);
        }
        return null;
    }

    //Delete Student
    public String deleteStudent(String id){
        Optional<Student> student=studentRepository.findById(id);
        if (student.isPresent()){
            studentRepository.delete(student.get());;
            return "Deleted Successfully";
        }
        return "Id not found";
    }

    //Search Student by name
    public Student searchByStudentName(String name){
        Student student= studentRepository.findByName(name);
        if (student!=null){
            return student;
        }
        return null;
    }
}
