package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.Empresa;
import com.example.demo.bean.Oferta;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.OfertaRepository;


@RestController
public class Controlador {

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private OfertaRepository ofertaRepository;

    // Consultar totes les ofertes
    @GetMapping("/ofertas")
    public Iterable<Oferta> getAllOfertas() {
        return ofertaRepository.findAll();
    }
    // Afegir una oferta d’una empresa
    @PostMapping("/empresa/{empresaId}/ofertas")
    public Oferta addOferta(@PathVariable Long empresaId, @RequestBody Oferta oferta) {
        final Oferta[] result = new Oferta[1];
        empresaRepository.findById(empresaId).ifPresent(empresa -> {
            oferta.setEmpresa(empresa);
            result[0] = ofertaRepository.save(oferta);
        });
        return result[0];
    }

    // Consultar ofertes d’una empresa
    @GetMapping("/empresa/{empresaId}/ofertas")
    public List<Oferta> getOfertasByEmpresa(@PathVariable Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        if (empresa != null) {
            return empresa.getOfertas();
        } else {
            return new ArrayList<>();
        }
    }

    // Actualitzar una oferta d’una empresa
    @PutMapping("/empresa/{empresaId}/ofertas/{ofertaId}")
    public Oferta updateOferta(@PathVariable Long empresaId, @PathVariable Long ofertaId, @RequestBody Oferta ofertaRequest) {
        if(!empresaRepository.existsById(empresaId)) {
            return null;  // or you can return a default value
        }

        final Oferta[] result = new Oferta[1];
        ofertaRepository.findById(ofertaId).ifPresent(oferta -> {
            oferta.setTitul(ofertaRequest.getTitul());
            result[0] = ofertaRepository.save(oferta);
        });
        return result[0];
    }

    // Eliminar una oferta d’una empresa
    @DeleteMapping("/empresa/{empresaId}/ofertas/{ofertaId}")
    public void deleteOferta(@PathVariable Long empresaId, @PathVariable Long ofertaId) {
        Oferta oferta = ofertaRepository.findById(ofertaId).orElse(null);
        if (oferta != null && oferta.getEmpresa().getId().equals(empresaId)) {
            ofertaRepository.delete(oferta);
        }
    }
    
////////////
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
    @DeleteMapping("/empresaEliminar/{id}")
    public void deleteEmpresa(@PathVariable Long id) {
        empresaRepository.deleteById(id);
    }
}
