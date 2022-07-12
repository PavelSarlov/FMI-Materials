package com.fmi.materials.repository;

import java.util.List;

import com.fmi.materials.model.Section;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {
    List<Section> findAll();
}
