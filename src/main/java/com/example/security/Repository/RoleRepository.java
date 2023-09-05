package com.example.security.Repository;

import com.example.security.dto.Rolee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Rolee,Long> {
}
