package com.example.demo.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private String tipo;
    private Long id;
    private String email;
    private String nome;
    private String role;
    private Long expiresIn;

    public LoginResponse(String token, Long id, String email, String nome, String role) {
        this.token = token;
        this.tipo = "Bearer";
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.role = role;
        this.expiresIn = 86400L; // 24 horas em segundos
    }
}
