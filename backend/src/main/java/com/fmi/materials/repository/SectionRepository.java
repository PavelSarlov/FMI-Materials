package com.fmi.materials.repository;

import com.fmi.materials.model.Section;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {
    List<Section> findAll();
}
