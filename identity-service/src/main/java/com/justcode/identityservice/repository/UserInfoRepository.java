package com.justcode.identityservice.repository;

import com.justcode.identityservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
   public Optional<UserInfo> findByName(String username);
}
