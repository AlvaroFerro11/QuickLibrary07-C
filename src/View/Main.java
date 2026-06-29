package View;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaBiblioteca ventana = new VentanaBiblioteca();
            ventana.setVisible(true);
        });
    }
}