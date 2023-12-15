package com.example.demo.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@IdClass(RegistrationCompositeId.class)
public class UserEntity {

    @Id
    private String registrationId;
    @Id
    private String registrationOpenId;
    private String name;
    private String email;

}
