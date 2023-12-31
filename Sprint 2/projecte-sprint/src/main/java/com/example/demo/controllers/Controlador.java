package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.bean.Empresa;
import com.example.demo.repository.EmpresaRepository;

import java.util.List;

@RestController
public class Controlador {

    @Autowired
    private EmpresaRepository empresaRepository;

    // Crear empresa
    @PostMapping("/empresa")
    public String submitForm(@RequestBody Empresa empresa) {
        empresaRepository.save(empresa);
        return "Correcte";  
    }

    
    // Retorna empresas
    @GetMapping("/empresas")
    public Iterable<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }
    
    // Retorna una empresa
    @GetMapping("/empresa/{id}")
    public Empresa getEmpresaById(@PathVariable Long id) {
        return empresaRepository.findById(id).orElse(null);
    }
    // Modificar empresar
    @PutMapping("/empresa/{id}")
    public Empresa updateEmpresa(@RequestBody Empresa newEmpresa, @PathVariable Long id) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresa.setName(newEmpresa.getName());
                    return empresaRepository.save(empresa);
                })
                .orElseGet(() -> {
                    newEmpresa.setId(id);
                    return empresaRepository.save(newEmpresa);
                });
    }

    // Eliminar Empresa
    @DeleteMapping("/empresa/{id}")
    public void deleteEmpresa(@PathVariable Long id) {
        empresaRepository.deleteById(id);
    }
}
