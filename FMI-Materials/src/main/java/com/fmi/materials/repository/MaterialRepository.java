package com.fmi.materials.repository;

import com.fmi.materials.model.Material;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Long> {
    List<Material> findAll();
}
