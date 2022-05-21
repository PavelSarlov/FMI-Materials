package com.fmi.materials.repository;

import com.fmi.materials.model.FacultyDepartment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyDepartmentRepository extends CrudRepository<FacultyDepartment, Long> {
    List<FacultyDepartment> findAll();
}
