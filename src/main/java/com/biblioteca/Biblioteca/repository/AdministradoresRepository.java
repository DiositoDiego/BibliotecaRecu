package com.biblioteca.Biblioteca.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.biblioteca.Biblioteca.model.Administradores;

public interface AdministradoresRepository extends MongoRepository<Administradores, String> {
    public Administradores findByCorreo(String correo);
}
