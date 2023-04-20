package com.biblioteca.Biblioteca.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamos {
    @Id
    private String idPrestamo;
    private String idLibro;
    private String nombrePersona;
    private Libros libro;
}
