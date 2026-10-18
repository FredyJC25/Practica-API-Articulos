package com.practica.api.servicio;

import com.practica.api.modelo.Articulo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;

@Service
public class ArticuloServicio {

    private static final String CONSULTA =
            "SELECT id_articulo, codigo, nombre, descripcion, precio, existencia"
                    + " FROM articulo ORDER BY id_articulo";

    private final JdbcTemplate jdbcTemplate;

    public ArticuloServicio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Articulo> listar() {
        return jdbcTemplate.query(CONSULTA, (rs, numeroFila) -> new Articulo(
                rs.getLong("id_articulo"),
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBigDecimal("precio").setScale(2, RoundingMode.HALF_UP),
                rs.getInt("existencia")
        ));
    }
}