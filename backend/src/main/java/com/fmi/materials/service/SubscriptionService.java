package com.fmi.materials.service;

import java.util.List;

import com.fmi.materials.dto.subscription.SubscriptionDto;

public interface SubscriptionService {
    SubscriptionDto createSubscription(Long userId, SubscriptionDto subscriptionDto);

    void deleteSubscriptionById(Long userId, Long id);

    List<SubscriptionDto> findSubscriptionsByUserId(Long userId);
}
