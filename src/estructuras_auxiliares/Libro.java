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
    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public Estado getEstado() {
        return estado;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    @Override
    public int compareTo(Libro otroLibro) {
        return this.codigo - otroLibro.codigo;
    }
    @Override
    public String toString() {
        return "Código: " + codigo +
                " | Título: " + titulo +
                " | Autor: " + autor +
                " | Categoría: " + categoria +
                " | Año: " + anioPublicacion +
                " | Estado: " + estado;
    }
