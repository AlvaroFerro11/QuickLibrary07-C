package View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GeneradorPDF {

    private static final int ANCHO_PAGINA = 595;
    private static final int ALTO_PAGINA = 842;
    private static final int MARGEN_IZQUIERDO = 50;
    private static final int MARGEN_SUPERIOR = 790;
    private static final int MARGEN_INFERIOR = 60;
    private static final int TAMANIO_FUENTE = 11;
    private static final int INTERLINEADO = 15;
    private static final int MAX_CARACTERES_LINEA = 86;

    private GeneradorPDF() {
    }

    public static void crearReporteTexto(String titulo, String contenido, File destino) throws IOException {
        if (destino == null) {
            throw new IOException("No se seleccionó un archivo de destino.");
        }

        List<String> lineas = prepararLineas(titulo, contenido);
        List<List<String>> paginas = dividirEnPaginas(lineas);
        escribirPDF(paginas, destino);
    }

    private static List<String> prepararLineas(String titulo, String contenido) {
        List<String> resultado = new ArrayList<>();
        resultado.add(titulo.toUpperCase());
        resultado.add("============================================================");
        resultado.add("");

        if (contenido != null && !contenido.trim().isEmpty()) {
            String[] lineasOriginales = contenido.replace("\r", "").split("\n");

            for (String linea : lineasOriginales) {
                resultado.addAll(dividirLineaLarga(linea));
            }
        } else {
            resultado.add("No existe contenido para mostrar en el reporte.");
        }

        return resultado;
    }

    private static List<String> dividirLineaLarga(String linea) {
        List<String> partes = new ArrayList<>();

        if (linea == null || linea.length() <= MAX_CARACTERES_LINEA) {
            partes.add(linea == null ? "" : linea);
            return partes;
        }

        String restante = linea;

        while (restante.length() > MAX_CARACTERES_LINEA) {
            int corte = restante.lastIndexOf(' ', MAX_CARACTERES_LINEA);

            if (corte <= 0) {
                corte = MAX_CARACTERES_LINEA;
            }

            partes.add(restante.substring(0, corte));
            restante = restante.substring(corte).trim();
        }

        if (!restante.isEmpty()) {
            partes.add(restante);
        }

        return partes;
    }

    private static List<List<String>> dividirEnPaginas(List<String> lineas) {
        List<List<String>> paginas = new ArrayList<>();
        List<String> paginaActual = new ArrayList<>();

        int y = MARGEN_SUPERIOR;

        for (String linea : lineas) {
            if (y < MARGEN_INFERIOR) {
                paginas.add(paginaActual);
                paginaActual = new ArrayList<>();
                y = MARGEN_SUPERIOR;
            }

            paginaActual.add(linea);
            y -= INTERLINEADO;
        }

        if (!paginaActual.isEmpty()) {
            paginas.add(paginaActual);
        }

        return paginas;
    }