package com.fmi.materials.repository;

import com.fmi.materials.model.WorkerJob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerJobRepository extends JpaRepository<WorkerJob, Long> {
}
