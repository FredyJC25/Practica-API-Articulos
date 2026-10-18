package com.practica.servicio;

import com.practica.modelo.Articulo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ArticuloServicio {

    private static final String URL_API = "http://localhost:8080/api/articulos";

    private final HttpClient cliente = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public List<Articulo> listar() throws IOException, InterruptedException {
        HttpRequest solicitud = HttpRequest.newBuilder(URI.create(URL_API))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> respuesta;
        try {
            respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (ConnectException | HttpTimeoutException e) {
            throw new IOException("No se pudo conectar con la API en " + URL_API
                    + ". Verifique que ApiApplication esté en ejecución.", e);
        }
        if (respuesta.statusCode() != 200) {
            throw new IOException(mensajeError(respuesta));
        }
        return convertir(respuesta.body());
    }

    private List<Articulo> convertir(String json) throws IOException {
        try {
            JSONArray arreglo = new JSONArray(json);
            List<Articulo> articulos = new ArrayList<>();
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject objeto = arreglo.getJSONObject(i);
                articulos.add(new Articulo(
                        objeto.getLong("id"),
                        objeto.getString("codigo"),
                        objeto.getString("nombre"),
                        objeto.optString("descripcion", ""),
                        objeto.getBigDecimal("precio").setScale(2, RoundingMode.HALF_UP),
                        objeto.getInt("existencia")
                ));
            }
            return articulos;
        } catch (JSONException e) {
            throw new IOException("La respuesta de la API no tiene el formato esperado: " + e.getMessage(), e);
        }
    }

    private String mensajeError(HttpResponse<String> respuesta) {
        try {
            return new JSONObject(respuesta.body()).getString("mensaje");
        } catch (JSONException e) {
            return "La API respondió con el código " + respuesta.statusCode();
        }
    }
}