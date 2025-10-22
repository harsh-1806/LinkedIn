package com.harsh.profileservice.services.impl;

import com.harsh.backend.features.profile.dtos.UserInfoDto;
import com.harsh.backend.features.profile.models.UserInfo;
import com.harsh.backend.features.profile.repositories.UserInfoRepository;
import com.harsh.backend.features.profile.services.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public ProfileServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public UserInfoDto updateProfile(UserInfoDto userInfoDto, String username) {

        // Retrieve the existing UserInfo entity from the database
        // Update fields if they are not null

        // TODO : Add Checks & Validation

        UserInfo existingUserInfo = userInfoRepository.findByUser_Email(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userInfoDto.getFirstName() != null) {
            existingUserInfo.setFirstName(userInfoDto.getFirstName());
        }
        if (userInfoDto.getLastName() != null) {
            existingUserInfo.setLastName(userInfoDto.getLastName());
        }
        if (userInfoDto.getCompany() != null) {
            existingUserInfo.setCompany(userInfoDto.getCompany());
        }
        if (userInfoDto.getPosition() != null) {
            existingUserInfo.setPosition(userInfoDto.getPosition());
        }
        if (userInfoDto.getLocation() != null) {
            existingUserInfo.setLocation(userInfoDto.getLocation());
        }
        if (userInfoDto.getProfilePicture() != null) {
            existingUserInfo.setProfilePicture(userInfoDto.getProfilePicture());
        }
        if (userInfoDto.getCoverPicture() != null) {
            existingUserInfo.setCoverPicture(userInfoDto.getCoverPicture());
        }
        if (userInfoDto.getAbout() != null) {
            existingUserInfo.setAbout(userInfoDto.getAbout());
        }
        if (userInfoDto.getBio() != null) {
            existingUserInfo.setBio(userInfoDto.getBio());
        }
        if (userInfoDto.getPhoneNumber() != null && userInfoDto.getPhoneNumber().matches("\\d{10}")) {
            existingUserInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        }
        if (userInfoDto.getAddress() != null) {
            existingUserInfo.setAddress(userInfoDto.getAddress());
        }
        if (userInfoDto.getDateOfBirth() != null && userInfoDto.getDateOfBirth().before(new Date())) {
            existingUserInfo.setDateOfBirth(userInfoDto.getDateOfBirth());
        }

        userInfoRepository.save(existingUserInfo);

        return convertToDto(existingUserInfo);
    }

    @Override
    public void addConnection(UUID userId, UUID connectionId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserInfo connection = userInfoRepository.findById(connectionId)
                .orElseThrow(() -> new EntityNotFoundException("Connection user not found"));

        // Add connection bidirectionally
        user.getConnections().add(connection);
        connection.getConnections().add(user);

        // Save both users
        userInfoRepository.save(user);
        userInfoRepository.save(connection);
    }

    @Override
    public void removeConnection(UUID userId, UUID connectionId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserInfo connection = userInfoRepository.findById(connectionId)
                .orElseThrow(() -> new EntityNotFoundException("Connection user not found"));

        // Remove connection bidirectionally
        user.getConnections().remove(connection);
        connection.getConnections().remove(user);

        // Save both users
        userInfoRepository.save(user);
        userInfoRepository.save(connection);
    }

    @Override
    public Set<UserInfo> getConnections(UUID userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getConnections();
    }

    private UserInfoDto convertToDto(UserInfo userInfo) {
        UserInfoDto dto = new UserInfoDto();
        dto.setFirstName(userInfo.getFirstName());
        dto.setLastName(userInfo.getLastName());
        dto.setCompany(userInfo.getCompany());
        dto.setPosition(userInfo.getPosition());
        dto.setLocation(userInfo.getLocation());
        dto.setProfilePicture(userInfo.getProfilePicture());
        dto.setCoverPicture(userInfo.getCoverPicture());
        dto.setAbout(userInfo.getAbout());
        dto.setBio(userInfo.getBio());
        dto.setPhoneNumber(userInfo.getPhoneNumber());
        dto.setAddress(userInfo.getAddress());
        dto.setDateOfBirth(userInfo.getDateOfBirth());
        return dto;
    }
}
