package com.practica.vista;

import com.practica.modelo.Articulo;
import com.practica.servicio.ArticuloServicio;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VentanaArticulos extends JFrame {

    private static final String[] COLUMNAS = {"ID", "Código", "Nombre", "Descripción", "Precio (Q)", "Existencia"};
    private static final int[] ANCHOS = {50, 80, 180, 320, 90, 90};

    private final ArticuloServicio servicio = new ArticuloServicio();

    private final DefaultTableModel modelo = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int columna) {
            return switch (columna) {
                case 0 -> Long.class;
                case 4 -> BigDecimal.class;
                case 5 -> Integer.class;
                default -> String.class;
            };
        }
    };

    private final JTable tabla = new JTable(modelo);
    private final JButton botonCargar = new JButton("Cargar artículos");
    private final JLabel etiquetaEstado = new JLabel("Presione el botón para cargar los artículos desde la API.");

    public VentanaArticulos() {
        super("Artículos - Consumo de API REST");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);

        tabla.setAutoCreateRowSorter(true);
        tabla.setRowHeight(24);
        for (int i = 0; i < ANCHOS.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(ANCHOS[i]);
        }

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.add(botonCargar);
        panelSuperior.add(etiquetaEstado);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        botonCargar.addActionListener(evento -> cargarArticulos());
    }

    private void cargarArticulos() {
        botonCargar.setEnabled(false);
        etiquetaEstado.setText("Consultando la API...");
        new SwingWorker<List<Articulo>, Void>() {
            @Override
            protected List<Articulo> doInBackground() throws Exception {
                return servicio.listar();
            }

            @Override
            protected void done() {
                try {
                    mostrar(get());
                } catch (ExecutionException e) {
                    etiquetaEstado.setText("No se pudieron cargar los artículos.");
                    JOptionPane.showMessageDialog(VentanaArticulos.this, e.getCause().getMessage(),
                            "Error al consultar la API", JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    botonCargar.setEnabled(true);
                }
            }
        }.execute();
    }

    private void mostrar(List<Articulo> articulos) {
        modelo.setRowCount(0);
        for (Articulo articulo : articulos) {
            modelo.addRow(new Object[]{
                    articulo.getId(),
                    articulo.getCodigo(),
                    articulo.getNombre(),
                    articulo.getDescripcion(),
                    articulo.getPrecio(),
                    articulo.getExistencia()
            });
        }
        etiquetaEstado.setText(articulos.size() + " artículo(s) cargado(s) desde la API.");
    }
}