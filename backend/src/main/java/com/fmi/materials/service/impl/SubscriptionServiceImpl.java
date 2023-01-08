package com.fmi.materials.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.fmi.materials.dto.subscription.SubscriptionDto;
import com.fmi.materials.exception.EntityNotFoundException;
import com.fmi.materials.mapper.SubscriptionMapper;
import com.fmi.materials.repository.SubscriptionRepository;
import com.fmi.materials.repository.UserRepository;
import com.fmi.materials.service.SubscriptionService;
import com.fmi.materials.util.CustomUtils;
import com.fmi.materials.vo.ExceptionMessage;
import com.fmi.materials.model.Subscription;
import com.fmi.materials.model.User;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionsRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SubscriptionDto createSubscription(Long userId, SubscriptionDto subscriptionDto) {
        CustomUtils.authenticateCurrentUser(userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No such user"));

        Subscription newSubscription = this.subscriptionsRepository
                .save(this.subscriptionMapper.convertToEntity(user, subscriptionDto));

        return this.subscriptionMapper.convertToDto(newSubscription);
    }

    @Override
    @Transactional
    public void deleteSubscriptionById(Long userId, Long id) {
        CustomUtils.authenticateCurrentUser(userId);

        if (!this.subscriptionsRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    ExceptionMessage.NOT_FOUND.getFormattedMessage("Subscription", "id", id));
        }

        this.subscriptionsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<SubscriptionDto> findSubscriptionsByUserId(Long userId) {
        CustomUtils.authenticateCurrentUser(userId);

        return this.subscriptionsRepository.findByUserId(userId).stream()
                .map(subscription -> this.subscriptionMapper.convertToDto(subscription))
                .collect(Collectors.toList());
    }
}
