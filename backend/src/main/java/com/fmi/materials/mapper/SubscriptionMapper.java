package com.fmi.materials.mapper;

import com.fmi.materials.dto.subscription.SubscriptionDto;
import com.fmi.materials.model.Subscription;
import com.fmi.materials.model.User;

import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public Subscription convertToEntity(User user,
            SubscriptionDto subscriptionDto) {
        if (subscriptionDto == null) {
            return null;
        }

        return new Subscription(
                user,
                subscriptionDto.getTargetId(),
                subscriptionDto.getType()

        );
    }

    public SubscriptionDto convertToDto(Subscription subscription) {
        if (subscription == null) {
            return null;
        }

        return new SubscriptionDto(
                subscription.getId(),
                subscription.getUser().getId(),
                subscription.getTargetId(),
                subscription.getType());
    }
}
