package com.acquirerx.backend.iam.service;

import com.acquirerx.backend.iam.dto.*;
import com.acquirerx.backend.iam.entity.User;
import com.acquirerx.backend.iam.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository ur) {
        this.userRepository = ur;
    }

    public UserDTO getUser(Integer id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toDto(u);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserDTO createUser(CreateUserDTO dto) {
        User u = new User();
        u.setEmail(dto.email());
        u.setName(dto.name());
        u.setPasswordHash(encoder.encode(dto.password()));
        u.setPhone(dto.phone());
        u.setRole(dto.role());
        u.setStatus(User.Status.valueOf(dto.status()));
        userRepository.save(u);
        return toDto(u);
    }

    public UserDTO updateUser(Integer id, UpdateUserDTO dto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (dto.name() != null) u.setName(dto.name());
        if (dto.phone() != null) u.setPhone(dto.phone());
        if (dto.role() != null) u.setRole(dto.role());
        if (dto.status() != null) u.setStatus(User.Status.valueOf(dto.status()));

        userRepository.save(u);
        return toDto(u);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO toDto(User u) {
        return new UserDTO(
                u.getUserId(),
                u.getName(),
                u.getRole(),
                u.getEmail(),
                u.getPhone(),
                u.getStatus().name()
        );
    }
}
