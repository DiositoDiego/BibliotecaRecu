package com.biblioteca.Biblioteca.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libros {
    @Id
    private String idLibro;
    private String imagen;
    private String titulo;
    private String autor;
    private String descripcion;
    private int cantidad;
}
