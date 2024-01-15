package com.example.demo.repository;

import org.springframework.stereotype.Service;
import com.example.demo.bean.Empresa;
import com.example.demo.bean.Oferta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Iterable<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public String submitForm(Empresa empresa) {
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return savedEmpresa != null ? "Correcte" : "Error";
    }

    public Empresa updateEmpresa(Empresa newEmpresa, Long id) {
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
    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
    public List<Oferta> getOfertasByEmpresa(Long empresaId) {
        return empresaRepository.findById(empresaId)
                .map(Empresa::getOfertas)
                .orElse(new ArrayList<>());
    }


}
