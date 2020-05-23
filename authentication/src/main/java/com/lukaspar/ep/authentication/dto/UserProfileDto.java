package com.lukaspar.ep.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String name;
    private String surname;
    private Long age;
    private String country;
}
