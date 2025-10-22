package com.harsh.profileservice.services;

import com.harsh.backend.features.profile.dtos.UserInfoDto;
import com.harsh.backend.features.profile.models.UserInfo;

import java.util.Set;
import java.util.UUID;

public interface ProfileService {
    UserInfoDto updateProfile(UserInfoDto userInfoDto, String username);
    Set<UserInfo> getConnections(UUID userId);
    void removeConnection(UUID userId, UUID connectionId);
    public void addConnection(UUID userId, UUID connectionId);
}
