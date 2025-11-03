package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepositorio extends JpaRepository<Test, Integer> {
}
