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


@DataJpaTest
public class TestsEmpresa {

    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;
    
    //Test per crear una empresa i buscar si existeix.
    @Test
    public void testSubmitForm() {
        Empresa empresa = new Empresa(3L, "Empresa 3", new ArrayList<>());
        Oferta oferta1 = new Oferta(1L, "Desenvolupament Web", empresa);
        Oferta oferta2 = new Oferta(2L, "Desenvolupament Aplicacions", empresa);
        empresa.getOfertas().add(oferta1);
        empresa.getOfertas().add(oferta2);

        when(empresaRepository.save(empresa)).thenReturn(empresa);
        when(empresaRepository.findById(3L)).thenReturn(Optional.of(empresa)); // Add this line

        String result = empresaService.submitForm(empresa);

        assertEquals("Correcte", result);

        verify(empresaRepository).save(empresa);

        Optional<Empresa> savedEmpresa = empresaService.findById(3L);

        assertEquals(true, savedEmpresa.isPresent());
        assertEquals("Empresa 3", savedEmpresa.get().getName());

        List<Oferta> savedOfertas = savedEmpresa.get().getOfertas();
        assertEquals(2, savedOfertas.size());

        Oferta savedOferta1 = savedOfertas.get(0);
        assertEquals(1L, savedOferta1.getId());
        assertEquals("Desenvolupament Web", savedOferta1.getTitul());

        Oferta savedOferta2 = savedOfertas.get(1);
        assertEquals(2L, savedOferta2.getId());
        assertEquals("Desenvolupament Aplicacions", savedOferta2.getTitul());
    }

    
    //Test per encontrar totes les empreses
    @Test
    public void testGetAllEmpresas() {
        Empresa empresa1 = new Empresa(1L, "Empresa 1", new ArrayList<>());
        Oferta oferta1 = new Oferta(1L, "Desenvolupament Web", empresa1);
        Oferta oferta2 = new Oferta(2L, "Desenvolupament Aplicacions", empresa1);
        empresa1.getOfertas().add(oferta1);
        empresa1.getOfertas().add(oferta2);

        Empresa empresa2 = new Empresa(2L, "Empresa 2", new ArrayList<>());
        Oferta oferta3 = new Oferta(3L, "Desenvolupament Web", empresa2);
        Oferta oferta4 = new Oferta(4L, "Desenvolupament Aplicacions", empresa2);
        empresa2.getOfertas().add(oferta3);
        empresa2.getOfertas().add(oferta4);

        when(empresaRepository.findAll()).thenReturn(Arrays.asList(empresa1, empresa2));

        Iterable<Empresa> result = empresaService.getAllEmpresas();

        List<Empresa> empresas = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());

        assertEquals(2, empresas.size());

        Empresa resultEmpresa1 = empresas.get(0);
        assertEquals(1L, resultEmpresa1.getId());
        assertEquals("Empresa 1", resultEmpresa1.getName());
        assertEquals(2, resultEmpresa1.getOfertas().size());

        Empresa resultEmpresa2 = empresas.get(1);
        assertEquals(2L, resultEmpresa2.getId());
        assertEquals("Empresa 2", resultEmpresa2.getName());
        assertEquals(2, resultEmpresa2.getOfertas().size());
    }
    
    
    //Test per encontrar una certa empresa
    @Test
    public void testFindById() {
        Empresa empresa = new Empresa(2L, "Empresa 2", new ArrayList<>());
        Oferta oferta1 = new Oferta(3L, "Desenvolupament Web", empresa);
        Oferta oferta2 = new Oferta(4L, "Desenvolupament Aplicacions", empresa);
        empresa.getOfertas().add(oferta1);
        empresa.getOfertas().add(oferta2);

        when(empresaRepository.findById(2L)).thenReturn(Optional.of(empresa));

        Optional<Empresa> result = empresaService.findById(2L);

        assertEquals(true, result.isPresent());
        assertEquals("Empresa 2", result.get().getName());

        List<Oferta> resultOfertas = result.get().getOfertas();
        assertEquals(2, resultOfertas.size());

        Oferta resultOferta1 = resultOfertas.get(0);
        assertEquals(3L, resultOferta1.getId());
        assertEquals("Desenvolupament Web", resultOferta1.getTitul());

        Oferta resultOferta2 = resultOfertas.get(1);
        assertEquals(4L, resultOferta2.getId());
        assertEquals("Desenvolupament Aplicacions", resultOferta2.getTitul());
    }
    
    //Test per modificar una empresa
    @Test
    public void testUpdateEmpresa() {
        Empresa empresa = new Empresa(3L, "Empresa 3", new ArrayList<>());
        Oferta oferta1 = new Oferta(1L, "Desenvolupament Web", empresa);
        Oferta oferta2 = new Oferta(2L, "Desenvolupament Aplicacions", empresa);
        empresa.getOfertas().add(oferta1);
        empresa.getOfertas().add(oferta2);

        when(empresaRepository.findById(3L)).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(empresa)).thenReturn(empresa);

        Empresa newEmpresa = new Empresa();
        newEmpresa.setName("Empresa 5");

        Empresa updatedEmpresa = empresaService.updateEmpresa(newEmpresa, 3L);

        assertEquals("Empresa 5", updatedEmpresa.getName());

        verify(empresaRepository).save(empresa);

        Optional<Empresa> savedEmpresa = empresaService.findById(3L);

        assertEquals(true, savedEmpresa.isPresent());
        assertEquals("Empresa 5", savedEmpresa.get().getName());

        List<Oferta> savedOfertas = savedEmpresa.get().getOfertas();
        assertEquals(2, savedOfertas.size());

        Oferta savedOferta1 = savedOfertas.get(0);
        assertEquals(1L, savedOferta1.getId());
        assertEquals("Desenvolupament Web", savedOferta1.getTitul());

        Oferta savedOferta2 = savedOfertas.get(1);
        assertEquals(2L, savedOferta2.getId());
        assertEquals("Desenvolupament Aplicacions", savedOferta2.getTitul());
    }
    
    //Test per eliminar una empresa
    @Test
    public void testDeleteEmpresa() {
        Empresa empresa = new Empresa(2L, "Empresa 2", new ArrayList<>());
        when(empresaRepository.findById(2L)).thenReturn(Optional.of(empresa));

        empresaService.deleteEmpresa(2L);

        verify(empresaRepository).deleteById(2L);

        when(empresaRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Empresa> deletedEmpresa = empresaService.findById(2L);

        assertEquals(false, deletedEmpresa.isPresent());
    }
}

