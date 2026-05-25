package com.example.demo.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(length = 255)
    private String descricao;

    public Role(String nome) {
        this.nome = nome;
    }

    public Role(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
