package com.fmi.materials.repository;

import java.util.List;

import com.fmi.materials.model.Subscription;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
}
