package com.biblioteca.Biblioteca.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.Biblioteca.model.Administradores;
import com.biblioteca.Biblioteca.repository.AdministradoresRepository;
import com.biblioteca.Biblioteca.utils.CustomResponse;
import com.biblioteca.Biblioteca.utils.UUIDGenerator;

@RestController
@RequestMapping("/administradores")
public class AdministradoresController {
    @Autowired
    private AdministradoresRepository administradoresRepository;

    @GetMapping
    public CustomResponse<LinkedList<Administradores>> getAllAdministradores() {
        CustomResponse<LinkedList<Administradores>> response = new CustomResponse<>();
        LinkedList<Administradores> administradores = new LinkedList<>();
        try {
            administradoresRepository.findAll().forEach(administradores::add);
            if (!administradores.isEmpty()) {
                response.setData(administradores);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("No hay administradores");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al obtener los administradores");
            response.setError(true);
        }
        return response;
    }

    @PostMapping
    public CustomResponse<Administradores> addAdministrador(@RequestBody Administradores administrador) {
        CustomResponse<Administradores> response = new CustomResponse<>();
        try {
            if (administrador.getIdAdministrador() != null) {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("El administrador no debe llevar id");
                response.setError(true);
            } else {
                administrador.setIdAdministrador(UUIDGenerator.getId());
                administrador.setLogged(false);
                administradoresRepository.save(administrador);
                response.setData(administrador);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al agregar el administrador");
            response.setError(true);
        }
        return response;
    }

    @DeleteMapping("/{idAdministrador}")
    public CustomResponse<Administradores> deleteAdministrador(@RequestBody Administradores administrador) {
        CustomResponse<Administradores> response = new CustomResponse<>();
        try {
            Administradores admin = administradoresRepository.findById(administrador.getIdAdministrador()).orElse(null);
            if (admin != null) {
                if (admin.isLogged()) {
                    administradoresRepository.delete(admin);
                    response.setData(admin);
                    response.setStatusCode(200);
                    response.setMessage("OK");
                    response.setError(false);
                } else {
                    response.setData(null);
                    response.setStatusCode(400);
                    response.setMessage("El administrador no se encontro");
                    response.setError(true);
                }
            } else {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("El administrador no existe");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al eliminar el administrador");
            response.setError(true);
        }
        return response;
    }

    @PostMapping("/login")
    public CustomResponse<Administradores> login(@RequestBody Administradores administrador) {
        CustomResponse<Administradores> response = new CustomResponse<>();
        try {
            Administradores admin = administradoresRepository.findByCorreo(administrador.getCorreo());
            if (admin != null) {
                if (admin.getContrasena().equals(administrador.getContrasena())) {
                    admin.setLogged(true);
                    administradoresRepository.save(admin);
                    response.setData(admin);
                    response.setStatusCode(200);
                    response.setMessage("Sesion iniciada");
                    response.setError(false);
                } else {
                    response.setData(null);
                    response.setStatusCode(400);
                    response.setMessage("Credenciales incorrectas");
                    response.setError(true);
                }
            } else {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("El administrador no existe");
                response.setError(true);
            }
        } catch (Exception e) {
            
        }
        return response;
    }

    @PostMapping("/logout")
    public CustomResponse<Administradores> logout(@RequestBody Administradores administrador) {
        CustomResponse<Administradores> response = new CustomResponse<>();
        try {
            Administradores admin = administradoresRepository.findByCorreo(administrador.getCorreo());
            if (admin != null) {
                if (admin.isLogged()) {
                    admin.setLogged(false);
                    administradoresRepository.save(admin);
                    response.setData(admin);
                    response.setStatusCode(200);
                    response.setMessage("Sesion cerrada");
                    response.setError(false);
                } else {
                    response.setData(null);
                    response.setStatusCode(400);
                    response.setMessage("El administrador no está logueado");
                    response.setError(true);
                }
            } else {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("El administrador no existe");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al cerrar sesión");
            response.setError(true);
        }
        return response;
    }
}
