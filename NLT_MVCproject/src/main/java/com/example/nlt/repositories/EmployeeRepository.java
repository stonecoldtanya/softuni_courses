package com.example.nlt.repositories;

import com.example.nlt.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByAgeGreaterThanOrderByProjectNameAsc(int age);

}
