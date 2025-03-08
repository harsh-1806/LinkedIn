package com.harsh.backend.features.profile.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String firstName;
    private String lastName;

    private String company;
    private String position;

    private String location;
    private String profilePicture;
    private String coverPicture;
    private String about;

    private String bio;


    private String phoneNumber;
    private String address;

    private Date dateOfBirth;
}
