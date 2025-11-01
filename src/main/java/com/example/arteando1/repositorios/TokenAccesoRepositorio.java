package com.example.arteando1.repositorios;

import com.example.arteando1.modelos.TokenAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenAccesoRepositorio extends JpaRepository<TokenAcceso, Integer> {


}
