package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Oferta {
	@Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String titul;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Empresa empresa;
    
    public Oferta() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public Oferta(Long id, String titul, Empresa empresa) {
    	this.id = id;
    	this.titul = titul;
    	this.empresa = empresa;
    }

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitul() { return titul; }
	public void setTitul(String titul) { this.titul = titul; }
	public Empresa getEmpresa() { return empresa; }
	public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    
}

public class OfertaTest {

	@Test
	public void testConstructorAndGetters() {
		// Arrange
		Long id = 1L;
		String titul = "Oferta de trabajo";
		Empresa empresa = new Empresa();

		// Act
		Oferta oferta = new Oferta(id, titul, empresa);

		// Assert
		assertEquals(id, oferta.getId());
		assertEquals(titul, oferta.getTitul());
		assertEquals(empresa, oferta.getEmpresa());
	}

	@Test
	public void testSetters() {
		// Arrange
		Oferta oferta = new Oferta();
		Long newId = 2L;
		String newTitul = "Nueva oferta";
		Empresa newEmpresa = new Empresa();

		// Act
		oferta.setId(newId);
		oferta.setTitul(newTitul);
		oferta.setEmpresa(newEmpresa);

		// Assert
		assertEquals(newId, oferta.getId());
		assertEquals(newTitul, oferta.getTitul());
		assertEquals(newEmpresa, oferta.getEmpresa());
	}

	@Test
	public void testDefaultConstructor() {
		// Arrange, Act
		Oferta oferta = new Oferta();

		// Assert
		assertNotNull(oferta);
	}

	@Test
	public void testJsonBackReferenceAnnotation() {
		// Arrange
		Oferta oferta = new Oferta();
		assertNull(oferta.getClass().getDeclaredField("empresa").getAnnotation(JsonBackReference.class));
	}
}