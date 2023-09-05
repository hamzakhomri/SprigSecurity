package com.example.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Userr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String login;
    private String password;

    @OneToMany(mappedBy = "userr", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rolee> roles = new ArrayList<>();

    // Constructors, getters, setters, etc.
}
