package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.bean.Oferta;
import java.util.Optional;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public OfertaService(OfertaRepository ofertaRepository, EmpresaRepository empresaRepository) {
        this.ofertaRepository = ofertaRepository;
        this.empresaRepository = empresaRepository;
    }

    public Optional<Oferta> findById(Long id) {
        return ofertaRepository.findById(id);
    }

    public Oferta save(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public Iterable<Oferta> getAllOfertas() {
        return ofertaRepository.findAll();
    }

    public Oferta addOferta(Long empresaId, Oferta oferta) {
		return empresaRepository.findById(empresaId)
                .map(empresa -> {
                    oferta.setEmpresa(empresa);
                    return ofertaRepository.save(oferta);
                })
                .orElse(null);
    }

    public Oferta updateOferta(Long empresaId, Long ofertaId, Oferta ofertaRequest) {
        if(!empresaRepository.existsById(empresaId)) {
            return null;
        }

        return ofertaRepository.findById(ofertaId)
                .map(oferta -> {
                    oferta.setTitul(ofertaRequest.getTitul());
                    return ofertaRepository.save(oferta);
                })
                .orElse(null);
    }
    public void deleteOferta(Long empresaId, Long ofertaId) {
        Oferta oferta = ofertaRepository.findById(ofertaId).orElse(null);
        if (oferta != null && oferta.getEmpresa().getId().equals(empresaId)) {
            ofertaRepository.delete(oferta);
        }
    }


}
