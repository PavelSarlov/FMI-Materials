package com.fmi.materials.dto.subscription;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDto {

    private Long id;
    private Long userId;
    private Long targetId;
    private String type;

    public SubscriptionDto(Long id, Long userId, Long targetId, String type) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
    }
}
