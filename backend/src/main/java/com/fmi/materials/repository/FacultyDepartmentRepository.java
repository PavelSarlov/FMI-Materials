package com.fmi.materials.repository;

import java.util.List;

import com.fmi.materials.model.FacultyDepartment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyDepartmentRepository extends CrudRepository<FacultyDepartment, Long> {
    List<FacultyDepartment> findAll();
}
