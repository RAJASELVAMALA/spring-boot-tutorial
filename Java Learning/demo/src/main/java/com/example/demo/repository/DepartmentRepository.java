package com.example.demo.repository;
import com.example.demo.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//repository that extends CrudRepository
public interface DepartmentRepository extends CrudRepository<Department, Integer>
{


//    List<Department> findAll(Sort sort);
    List<Department> findByNameContainingIgnoreCase(String name, Sort sort);
    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Department> findAll(Pageable pageable);

}