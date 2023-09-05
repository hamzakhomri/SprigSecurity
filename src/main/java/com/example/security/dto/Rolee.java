package com.example.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Rolee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Role;
    @ManyToOne
    @JoinColumn(name = "userr_id")
    private Userr userr;

    // Constructors, getters, setters, etc.
}
