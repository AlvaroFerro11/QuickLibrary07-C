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
    private static void escribirPDF(List<List<String>> paginas, File destino) throws IOException {
        List<byte[]> objetos = new ArrayList<>();

        int totalPaginas = paginas.size();
        StringBuilder kids = new StringBuilder();

        for (int i = 0; i < totalPaginas; i++) {
            int idPagina = 4 + (i * 2);
            kids.append(idPagina).append(" 0 R ");
        }

        objetos.add("<< /Type /Catalog /Pages 2 0 R >>".getBytes(StandardCharsets.ISO_8859_1));
        objetos.add(("<< /Type /Pages /Count " + totalPaginas + " /Kids [ " + kids + "] >>").getBytes(StandardCharsets.ISO_8859_1));
        objetos.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>".getBytes(StandardCharsets.ISO_8859_1));

        for (int i = 0; i < totalPaginas; i++) {
            int idContenido = 5 + (i * 2);

            String pagina = "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 " + ANCHO_PAGINA + " " + ALTO_PAGINA + "] " +
                    "/Resources << /Font << /F1 3 0 R >> >> /Contents " + idContenido + " 0 R >>";

            byte[] contenido = construirContenidoPagina(paginas.get(i));
            String cabeceraStream = "<< /Length " + contenido.length + " >>\nstream\n";
            String cierreStream = "\nendstream";

            ByteArrayOutputStream streamObjeto = new ByteArrayOutputStream();
            streamObjeto.write(cabeceraStream.getBytes(StandardCharsets.ISO_8859_1));
            streamObjeto.write(contenido);
            streamObjeto.write(cierreStream.getBytes(StandardCharsets.ISO_8859_1));

            objetos.add(pagina.getBytes(StandardCharsets.ISO_8859_1));
            objetos.add(streamObjeto.toByteArray());
        }

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>();

        escribir(pdf, "%PDF-1.4\n");
        escribir(pdf, "%âãÏÓ\n");

        for (int i = 0; i < objetos.size(); i++) {
            offsets.add(pdf.size());
            escribir(pdf, (i + 1) + " 0 obj\n");
            pdf.write(objetos.get(i));
            escribir(pdf, "\nendobj\n");
        }

        int inicioXref = pdf.size();

        escribir(pdf, "xref\n");
        escribir(pdf, "0 " + (objetos.size() + 1) + "\n");
        escribir(pdf, "0000000000 65535 f \n");

        for (Integer offset : offsets) {
            escribir(pdf, String.format("%010d 00000 n \n", offset));
        }

        escribir(pdf, "trailer\n");
        escribir(pdf, "<< /Size " + (objetos.size() + 1) + " /Root 1 0 R >>\n");
        escribir(pdf, "startxref\n");
        escribir(pdf, inicioXref + "\n");
        escribir(pdf, "%%EOF");

        File carpeta = destino.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (FileOutputStream salida = new FileOutputStream(destino)) {
            pdf.writeTo(salida);
        }
    }

    private static byte[] construirContenidoPagina(List<String> lineas) throws IOException {
        ByteArrayOutputStream contenido = new ByteArrayOutputStream();

        escribir(contenido, "BT\n");
        escribir(contenido, "/F1 " + TAMANIO_FUENTE + " Tf\n");
        escribir(contenido, INTERLINEADO + " TL\n");
        escribir(contenido, MARGEN_IZQUIERDO + " " + MARGEN_SUPERIOR + " Td\n");

        for (String linea : lineas) {
            escribir(contenido, "(" + escaparTexto(linea) + ") Tj\n");
            escribir(contenido, "T*\n");
        }

        escribir(contenido, "ET");
        return contenido.toByteArray();
    }

    private static String escaparTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("—", "-")
                .replace("–", "-")
                .replace("“", "\"")
                .replace("”", "\"")
                .replace("‘", "'")
                .replace("’", "'");
    }

    private static void escribir(ByteArrayOutputStream salida, String texto) throws IOException {
        salida.write(texto.getBytes(StandardCharsets.ISO_8859_1));
    }
}