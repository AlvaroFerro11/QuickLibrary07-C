package Model;
public class Libro implements Comparable<Libro> {
    private int codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int anioPublicacion;
    private String estado;
    public Libro(int codigo, String titulo, String autor,
                 String categoria, int anioPublicacion, String estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anioPublicacion = anioPublicacion;
        this.estado = estado;
    }
    public Libro(int codigo) {
        this.codigo = codigo;
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
    public String getEstado() {
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
    public void setEstado(String estado) {
        this.estado = estado;
    }
    @Override
    public int compareTo(Libro otroLibro) {
        return Integer.compare(this.codigo, otroLibro.codigo);
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
}