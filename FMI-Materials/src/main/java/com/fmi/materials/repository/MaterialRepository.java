package com.fmi.materials.repository;

import com.fmi.materials.model.Material;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Long> {
    List<Material> findAll();

    @Query(value = "SELECT m.* FROM materials m\n" +
            "JOIN sections s ON s.id = m.section_id\n" +
            "WHERE file_name = ?1 AND course_id = ?2\n" +
            "LIMIT 1", nativeQuery = true)
    Optional<Material> findByName(String name, Long courseId);
}
