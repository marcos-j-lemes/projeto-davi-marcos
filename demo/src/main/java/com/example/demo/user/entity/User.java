package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

//import com.marcos.ecommerce.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.auth.entity.Role;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean ativo = true;

    // @OneToMany(mappedBy = "usuario")
    // @JsonIgnore
    // private List<Product> produtos;
}