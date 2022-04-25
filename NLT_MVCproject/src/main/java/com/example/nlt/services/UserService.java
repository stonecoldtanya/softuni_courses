package com.example.nlt.services;

import com.example.nlt.entities.User;
import com.example.nlt.entities.dto.RegistrationDTO;
import com.example.nlt.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;

    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void register(RegistrationDTO dto) {
        User user = this.mapper.map(dto, User.class);

        this.userRepository.save(user);
    }
}
