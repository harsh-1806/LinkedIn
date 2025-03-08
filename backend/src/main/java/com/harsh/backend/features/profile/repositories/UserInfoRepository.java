package com.harsh.backend.features.profile.repositories;

import com.harsh.backend.features.profile.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByUser_Email(String username);
}
