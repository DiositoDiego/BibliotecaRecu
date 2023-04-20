package com.biblioteca.Biblioteca.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.biblioteca.Biblioteca.model.Libros;

public interface LibrosRepository extends MongoRepository<Libros, String> {
    public Libros findByIdLibro(String idLibro);
}
