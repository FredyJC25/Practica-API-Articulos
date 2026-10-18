package com.practica.api.controlador;

import com.practica.api.modelo.Articulo;
import com.practica.api.servicio.ArticuloServicio;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloControlador {

    private final ArticuloServicio servicio;

    public ArticuloControlador(ArticuloServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Articulo> listar() {
        return servicio.listar();
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> errorBaseDatos(DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensaje", "Error de base de datos: " + e.getMostSpecificCause().getMessage()));
    }
}