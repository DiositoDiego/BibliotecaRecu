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

import com.biblioteca.Biblioteca.model.Libros;
import com.biblioteca.Biblioteca.repository.LibrosRepository;
import com.biblioteca.Biblioteca.utils.CustomResponse;
import com.biblioteca.Biblioteca.utils.UUIDGenerator;

@RestController
@RequestMapping("/libros")
public class LibrosController {
    @Autowired
    private LibrosRepository librosRepository;

    @PostMapping
    public CustomResponse<Libros> addLibro(@RequestBody Libros libro) {
        CustomResponse<Libros> response = new CustomResponse<>();
        try {
            if (libro.getIdLibro() != null) {
                response.setData(null);
                response.setStatusCode(400);
                response.setMessage("El libro no debe llevar id");
                response.setError(true);
            } else {
                libro.setIdLibro(UUIDGenerator.getId());
                librosRepository.save(libro);
                response.setData(libro);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al agregar el libro");
            response.setError(true);
        }
        return response;
    }

    @GetMapping
    public CustomResponse<LinkedList<Libros>> getAllLibros() {
        CustomResponse<LinkedList<Libros>> response = new CustomResponse<>();
        LinkedList<Libros> libros = new LinkedList<>();
        try {
            librosRepository.findAll().forEach(libros::add);
            if (!libros.isEmpty()) {
                response.setData(libros);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No hay libros");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al obtener los libros");
            response.setError(true);
        }
        return response;
    }

    @GetMapping("/{idLibro}")
    public CustomResponse<Libros> getLibroById(@PathVariable String idLibro) {
        CustomResponse<Libros> response = new CustomResponse<>();
        try {
            Libros libro = librosRepository.findByIdLibro(idLibro);
            if (libro != null) {
                response.setData(libro);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontro el libro");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al obtener el libro");
            response.setError(true);
        }
        return response;
    }

    @PutMapping("/{idLibro}")
    public CustomResponse<Libros> updateLibro(@PathVariable String idLibro, @RequestBody Libros libro) {
        CustomResponse<Libros> response = new CustomResponse<>();
        try {
            Libros libroDB = librosRepository.findByIdLibro(idLibro);
            if (libroDB != null) {
                libroDB.setTitulo(libro.getTitulo());
                libroDB.setAutor(libro.getAutor());
                libroDB.setDescripcion(libro.getDescripcion());
                libroDB.setCantidad(libro.getCantidad());
                libroDB.setImagen(libro.getImagen());
                librosRepository.save(libroDB);
                response.setData(libroDB);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontro el libro");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al actualizar el libro");
            response.setError(true);
        }
        return response;
    }

    @DeleteMapping("/{idLibro}")
    public CustomResponse<Libros> deleteLibro(@PathVariable String idLibro) {
        CustomResponse<Libros> response = new CustomResponse<>();
        try {
            Libros libro = librosRepository.findByIdLibro(idLibro);
            if (libro != null) {
                librosRepository.delete(libro);
                response.setData(libro);
                response.setStatusCode(200);
                response.setMessage("OK");
                response.setError(false);
            } else {
                response.setData(null);
                response.setStatusCode(404);
                response.setMessage("No se encontro el libro");
                response.setError(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setStatusCode(500);
            response.setMessage("Error al eliminar el libro");
            response.setError(true);
        }
        return response;
    }
}
