package com.fmi.materials.repository;

import com.fmi.materials.model.MaterialRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRequestRepository extends CrudRepository<MaterialRequest, Long> {
    List<MaterialRequest> findAllByUserId(Long userId);

    @Query(value = "SELECT mr.* FROM material_requests mr\n" +
            "JOIN sections s ON s.id = mr.section_id\n" +
            "JOIN courses c ON c.id = s.course_id\n" +
            "JOIN users u ON u.name = c.created_by\n" +
            "WHERE u.id = ?2 AND mr.id = ?1", nativeQuery = true)
    Optional<MaterialRequest> findByIdAndAdminId(Long id, Long adminId);

    @Query(value = "SELECT mr.* FROM material_requests mr\n" +
            "JOIN sections s ON s.id = mr.section_id\n" +
            "JOIN courses c ON c.id = s.course_id\n" +
            "JOIN users u ON u.name = c.created_by\n" +
            "WHERE u.id = ?1", nativeQuery = true)
    List<MaterialRequest> findAllByAdminId(Long adminId);
}
