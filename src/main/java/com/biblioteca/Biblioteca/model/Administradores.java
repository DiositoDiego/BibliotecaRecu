package com.biblioteca.Biblioteca.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administradores {
    @Id
    private String idAdministrador;
    private String correo;
    private String contrasena;
    private boolean logged;
}
