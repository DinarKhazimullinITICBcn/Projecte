package com.example.demo.repository;

import com.example.demo.bean.Oferta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends CrudRepository<Oferta, Long> {
}
