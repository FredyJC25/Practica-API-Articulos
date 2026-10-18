package com.practica.principal;

import com.practica.vista.VentanaArticulos;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
                System.err.println("No se pudo aplicar el estilo del sistema: " + e.getMessage());
            }
            new VentanaArticulos().setVisible(true);
        });
    }
}