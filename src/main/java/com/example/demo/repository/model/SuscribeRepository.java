package com.example.demo.repository.model;

import com.example.demo.entity.Suscribe;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscribeRepository extends JpaRepository<Suscribe, UUID> {}
