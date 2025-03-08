package com.harsh.backend.features.profile.services;

import com.harsh.backend.features.profile.dtos.UserInfoDto;

public interface ProfileService {
    UserInfoDto updateProfile(UserInfoDto userInfoDto, String username);
}
