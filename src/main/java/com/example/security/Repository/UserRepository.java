package com.example.security.Repository;


import com.example.security.dto.Userr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Userr,Long> {
}
