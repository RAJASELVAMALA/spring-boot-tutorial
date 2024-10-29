package com.example.demo.controller;

import com.example.demo.model.Books;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping ("/departments")
//    public List<Department> getAllDepartments(
//            @RequestParam(defaultValue = "name") String sortBy,
//            @RequestParam(defaultValue = "asc") String sortDirection,
//            @RequestParam(required = false) String search) {
//        return departmentService.getAllDepartments(sortBy, sortDirection, search);
//    }
    public Page<Department> getAllDepartments(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return departmentService.getAllDepartments(sortBy, sortDirection, search, page, size);
    }
    //creating a get mapping that retrieves the detail of a specific book
    @GetMapping("/departments/{departmentid}")
    private Department getDepartment(@PathVariable("departmentid") int departmentId)
    {
        return departmentService.getDepartmentId(departmentId);
    }


    //creating a delete mapping that deletes a specified book
    @DeleteMapping("/departments/{departmentid}")
    private void deleteDepartment(@PathVariable("departmentid") int departmentId)
    {
        departmentService.delete(departmentId);
    }


    //creating post mapping that post the book detail in the database
    @PostMapping("/departments")
    private int saveDepartment(@RequestBody Department department)
    {
        departmentService.saveOrUpdate(department);
        return department.getId();
    }


    //creating put mapping that updates the book detail
    @PutMapping("/departments/{departmentid}")
    private Department update(@RequestBody Department department)
    {
        departmentService.saveOrUpdate(department);
        return department;
    }

}
