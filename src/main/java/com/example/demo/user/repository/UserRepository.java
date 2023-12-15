package com.example.demo.user.repository;

import com.example.demo.user.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByRegistrationIdAndRegistrationOpenId(String registrationId, String registrationOpenId);

}
