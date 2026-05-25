package com.example.demo.auth.service;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.LoginResponse;
import com.example.demo.auth.entity.Role;
import com.example.demo.auth.repository.RoleRepository;
import com.example.demo.auth.util.JwtUtil;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().getNome());
        
        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getNome(),
                user.getRole().getNome()
        );
    }

    public User registerUser(String email, String nome, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado");
        }

        Role defaultRole = roleRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role padrão não encontrada"));

        User user = new User();
        user.setEmail(email);
        user.setNome(nome);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(defaultRole);
        user.setAtivo(true);

        return userRepository.save(user);
    }

    public boolean validateToken(String token) {
        return jwtUtil.isTokenValid(token);
    }

    public String getEmailFromToken(String token) {
        return jwtUtil.getEmailFromToken(token);
    }

    public String getRoleFromToken(String token) {
        return jwtUtil.getRoleFromToken(token);
    }

    public Long getUserIdFromToken(String token) {
        return jwtUtil.getUserIdFromToken(token);
    }
}
