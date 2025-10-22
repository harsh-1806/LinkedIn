package com.harsh.profileservice.controllers;

import com.harsh.backend.features.auth.model.User;
import com.harsh.backend.features.profile.dtos.UserInfoDto;
import com.harsh.backend.features.profile.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PatchMapping
    public ResponseEntity<UserInfoDto> updateProfile(
            @RequestBody UserInfoDto userInfoDto,
            @RequestAttribute("authenticatedUser") User user
            ) {
        UserInfoDto updatedProfile = profileService.updateProfile(userInfoDto, user.getUsername());

        return ResponseEntity.ok(updatedProfile);
    }
}
