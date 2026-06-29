package estructuras_auxiliares;

public class Libro implements Comparable<Libro>{
    private int code;
    private String title;
    private String author;
    private String category;
    private String yearPubli;
    private String  State;
    }
    public Libro(int codigo, String titulo, String autor, String categoria, int anioPublicacion, Estado estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anioPublicacion = anioPublicacion;
        this.estado = estado;
    }

}