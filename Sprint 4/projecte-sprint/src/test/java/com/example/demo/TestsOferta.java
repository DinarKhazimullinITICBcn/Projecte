package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.bean.Empresa;
import com.example.demo.bean.Oferta;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.EmpresaService;
import com.example.demo.repository.OfertaRepository;
import com.example.demo.repository.OfertaService;

@DataJpaTest
public class TestsOferta {

    @InjectMocks
    private OfertaService ofertaService;
    
    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private OfertaRepository ofertaRepository;
    
    @Mock
    private EmpresaRepository empresaRepository;

    //Test per veure totes les ofertes
    @Test
    public void testGetAllOfertas() {
        Oferta oferta1 = new Oferta(1L, "Desenvolupament Web", null);
        Oferta oferta2 = new Oferta(2L, "Desenvolupament Aplicacions", null);
        List<Oferta> ofertas = Arrays.asList(oferta1, oferta2);

        when(ofertaRepository.findAll()).thenReturn(ofertas);

        Iterable<Oferta> result = ofertaService.getAllOfertas();

        List<Oferta> resultList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());

        assertEquals(2, resultList.size());
        assertEquals(oferta1, resultList.get(0));
        assertEquals(oferta2, resultList.get(1));
    }
    
    //Test per afegir una oferta
    @Test
    public void testAddOferta() {
        Empresa empresa = new Empresa(2L, "Empresa 2", new ArrayList<>());
        Oferta oferta = new Oferta(1L, "Desenvolupament Web", empresa);

        when(empresaRepository.findById(2L)).thenReturn(Optional.of(empresa));
        when(ofertaRepository.save(oferta)).thenReturn(oferta);

        Oferta result = ofertaService.addOferta(2L, oferta);

        assertEquals(oferta, result);

        verify(ofertaRepository).save(oferta);
    }
    
    //Test per veure una oferta d'una empresa
    @Test
    public void testGetOfertasByEmpresa() {
        Empresa empresa = new Empresa(2L, "Empresa 2", new ArrayList<>());
        Oferta oferta1 = new Oferta(1L, "Desenvolupament Web", empresa);
        Oferta oferta2 = new Oferta(2L, "Desenvolupament Aplicacions", empresa);
        empresa.getOfertas().add(oferta1);
        empresa.getOfertas().add(oferta2);

        when(empresaRepository.findById(2L)).thenReturn(Optional.of(empresa));

        List<Oferta> result = empresaService.getOfertasByEmpresa(2L);

        assertEquals(2, result.size());
        assertEquals(oferta1, result.get(0));
        assertEquals(oferta2, result.get(1));
    }
    
    //Test per modificar una oferta
    @Test
    public void testUpdateOferta() {
        Empresa empresa = new Empresa(2L, "Empresa 2", new ArrayList<>());
        Oferta oferta = new Oferta(1L, "Desenvolupament Web", empresa);
        Oferta ofertaRequest = new Oferta();
        ofertaRequest.setTitul("Desenvolupament Aplicacions");

        when(empresaRepository.existsById(2L)).thenReturn(true);
        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(oferta));
        when(ofertaRepository.save(oferta)).thenReturn(oferta);

        Oferta result = ofertaService.updateOferta(2L, 1L, ofertaRequest);

        assertEquals("Desenvolupament Aplicacions", result.getTitul());

        verify(ofertaRepository).save(oferta);
    }
    
    //Test per eliminar una oferta en cascada
    @Test
    public void testDeleteOferta() {
        Empresa empresa = new Empresa(2L, "Empresa 2", new ArrayList<>());
        Oferta oferta = new Oferta(1L, "Desenvolupament Web", empresa);

        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(oferta));
        when(empresaRepository.existsById(2L)).thenReturn(true);

        ofertaService.deleteOferta(2L, 1L);

        verify(ofertaRepository).delete(oferta);
    }
}
