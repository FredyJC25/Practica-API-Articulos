package com.practica.modelo;

import java.math.BigDecimal;

public class Articulo {

    private final long id;
    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final BigDecimal precio;
    private final int existencia;

    public Articulo(long id, String codigo, String nombre, String descripcion, BigDecimal precio, int existencia) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getExistencia() {
        return existencia;
    }
}