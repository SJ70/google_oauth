package com.example.demo.user.service;

import com.example.demo.auth.dto.RegistrationDTO;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(RegistrationDTO registrationDTO) {
        UserEntity user = new UserEntity();
        user.setName(registrationDTO.name());
        user.setEmail(registrationDTO.email());
        user.setRegistrationId(registrationDTO.registrationId());
        user.setRegistrationOpenId(registrationDTO.registrationOpenId());
        return userRepository.save(user);
    }

    public UserEntity updateUser(RegistrationDTO registrationDTO) {
        UserEntity user = userRepository.findByRegistrationIdAndRegistrationOpenId(
                        registrationDTO.registrationId(),
                        registrationDTO.registrationOpenId()
                )
                .orElse(createUser(registrationDTO));
        user.setName(registrationDTO.name());
        user.setEmail(registrationDTO.email());
        return userRepository.save(user);
    }

}
