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
