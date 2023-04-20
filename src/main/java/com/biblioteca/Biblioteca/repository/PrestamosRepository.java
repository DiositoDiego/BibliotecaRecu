package com.biblioteca.Biblioteca.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.biblioteca.Biblioteca.model.Prestamos;

public interface PrestamosRepository extends MongoRepository<Prestamos, String>{
    public Prestamos findByIdPrestamo(String idPrestamo);
}
