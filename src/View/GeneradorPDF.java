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