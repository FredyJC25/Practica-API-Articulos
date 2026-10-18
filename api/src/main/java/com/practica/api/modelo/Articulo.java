package com.practica.api.modelo;

import java.math.BigDecimal;

public record Articulo(
        long id,
        String codigo,
        String nombre,
        String descripcion,
        BigDecimal precio,
        int existencia
) {
}