package com.biblioteca.Biblioteca.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.Biblioteca.model.Prestamos;
import com.biblioteca.Biblioteca.repository.LibrosRepository;
import com.biblioteca.Biblioteca.repository.PrestamosRepository;
import com.biblioteca.Biblioteca.utils.CustomResponse;
import com.biblioteca.Biblioteca.utils.UUIDGenerator;

@RestController
@RequestMapping("/prestamos")
public class PrestamosController {
    @Autowired
    private PrestamosRepository prestamosRepository;

    @Autowired
    private LibrosRepository librosRepository;

    @PostMapping
    public CustomResponse<Prestamos> addPrestamo(@RequestBody Prestamos prestamo) {
        CustomResponse<Prestamos> response = new CustomResponse<>();
        try {
            if (prestamo.getIdPrestamo() != null) {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("El prestamo no debe llevar id");
                response.setError(true);
            } else {
                prestamo.setIdPrestamo(UUIDGenerator.getId());
                prestamo.setLibro(librosRepository.findByIdLibro(prestamo.getIdLibro()));
                prestamosRepository.save(prestamo);
                response.setData(prestamo);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al agregar el prestamo");
            response.setError(true);
        }
        return response;
    }

    @GetMapping
    public CustomResponse<LinkedList<Prestamos>> getAllPrestamos() {
        CustomResponse<LinkedList<Prestamos>> response = new CustomResponse<>();
        LinkedList<Prestamos> prestamos = new LinkedList<>();
        try {
            prestamosRepository.findAll().forEach(prestamos::add);
            if (!prestamos.isEmpty()) {
                response.setData(prestamos);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontraron prestamos");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al obtener los prestamos");
            response.setError(true);
        }
        return response;
    }

    @GetMapping("/{idPrestamo}")
    public CustomResponse<Prestamos> getPrestamoById(@PathVariable String idPrestamo) {
        CustomResponse<Prestamos> response = new CustomResponse<>();
        try {
            Prestamos prestamo = prestamosRepository.findByIdPrestamo(idPrestamo);
            if (prestamo != null) {
                response.setData(prestamo);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontro el prestamo");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al obtener el prestamo");
            response.setError(true);
        }
        return response;
    }

    @PutMapping("/{idPrestamo}")
    public CustomResponse<Prestamos> updatePrestamo(@PathVariable String idPrestamo, @RequestBody Prestamos prestamo) {
        CustomResponse<Prestamos> response = new CustomResponse<>();
        try {
            Prestamos prestamoDB = prestamosRepository.findByIdPrestamo(idPrestamo);
            if (prestamoDB != null) {
                prestamoDB.setIdLibro(prestamo.getIdLibro());
                prestamoDB.setLibro(librosRepository.findByIdLibro(prestamo.getIdLibro()));
                prestamoDB.setNombrePersona(prestamo.getNombrePersona());
                prestamosRepository.save(prestamoDB);
                response.setData(prestamoDB);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontro el prestamo");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al actualizar el prestamo");
            response.setError(true);
        }
        return response;
    }

    @DeleteMapping("/{idPrestamo}")
    public CustomResponse<Prestamos> deletePrestamo(@PathVariable String idPrestamo) {
        CustomResponse<Prestamos> response = new CustomResponse<>();
        try {
            Prestamos prestamoDB = prestamosRepository.findByIdPrestamo(idPrestamo);
            if (prestamoDB != null) {
                prestamosRepository.delete(prestamoDB);
                response.setData(prestamoDB);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontro el prestamo");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al eliminar el prestamo");
            response.setError(true);
        }
        return response;
    }
}
