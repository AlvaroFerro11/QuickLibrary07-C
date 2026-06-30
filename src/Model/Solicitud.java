package Model;

import java.time.LocalDate;

public class Solicitud {
    private int codEst;
    private String nomEst;
    private int codigoLibro;
    private LocalDate fechaSolicitud;

    public Solicitud(int codEst,String nomEst,int codigoLibro,LocalDate fechaSolicitud){
        this.codEst= codEst;
        this.nomEst = nomEst;
        this.codigoLibro = codigoLibro;
        this.fechaSolicitud = fechaSolicitud;
    }
    // Metodo de lectura para obtener el codigo del libro
    public int getCodigoLibro() {
        return codigoLibro;
    }
    public int getCodEst() {
        return codEst;
    }
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    public String getNomEst() {
        return nomEst;
    }
    public void setCodEst(int codEst) {
        this.codEst = codEst;
    }
    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    public void setNomEst(String nomEst) {
        this.nomEst = nomEst;
    }
}