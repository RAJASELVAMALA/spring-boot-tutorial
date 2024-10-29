package com.example.demo.service;

import com.example.demo.model.Books;
import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
//public List<Department> getAllDepartments(String sortBy, String sortDirection, String search) {
//    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
//    List<Department> departments = new ArrayList<>();
//
//    if (search != null && !search.isEmpty()) {
//        departmentRepository.findByNameContainingIgnoreCase(search, sort).forEach(departments::add);
//    } else {
//        departmentRepository.findAll(sort).forEach(departments::add);
//    }
//    return departments;
//}
public Page<Department> getAllDepartments(String sortBy, String sortDirection, String search, int page, int size) {
//    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
//    Pageable pageable = PageRequest.of(page, size, sort);
//
//    if (search != null && !search.isEmpty()) {
//        return departmentRepository.findByNameContainingIgnoreCase(search, pageable);
//    } else {
//        return departmentRepository.findAll(pageable);
//
//    }




    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection != null ? sortDirection : "ASC"), sortBy != null ? sortBy : "id");
    Pageable pageable = PageRequest.of(page, size, sort);

    if (search != null && !search.isEmpty()) {
        System.out.println("search: " + search);
        return departmentRepository.findByNameContainingIgnoreCase(search, pageable);
    } else {
        System.out.println("else search : " + search);

        return departmentRepository.findAll(pageable);
    }



}

    public Department getDepartmentId(int id){
        return departmentRepository.findById(id).get();
    }
    public void saveOrUpdate(Department departments){
        departmentRepository.save(departments);
    }
    public void delete(int id)
    {
        departmentRepository.deleteById(id);
    }
    //updating a record
    public void update(Department departments, int departmentId)
    {
        departmentRepository.save(departments);
    }
}
