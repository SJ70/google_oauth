package com.example.demo.user.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class RegistrationCompositeId implements Serializable {

    private String registrationId;
    private String registrationOpenId;
}
