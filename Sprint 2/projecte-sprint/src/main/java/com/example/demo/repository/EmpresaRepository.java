package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}