package com.example.demo.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Empresa {

    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Oferta> ofertas;

    public Empresa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empresa(Long id, String name, List<Oferta> ofertas) {
    	this.id = id;
    	this.name = name;
    	this.ofertas = ofertas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Oferta> getOfertas() { return ofertas; }
    public void setOferta(List<Oferta> ofertas) { this.ofertas = ofertas; }
}
