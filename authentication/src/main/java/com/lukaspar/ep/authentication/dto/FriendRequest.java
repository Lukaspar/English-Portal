package com.lukaspar.ep.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {

    @Positive
    private Long ownerId;
    @Positive
    private Long friendId;
}
