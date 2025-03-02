/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
/**
 *
 * @author david
 */
@Entity
@Table (name="prestamos")

public class Prestamo implements Serializable {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int idPrestamo;
    
    @JoinColumn (name="idLibro")
    @ManyToOne
    private Libro libro;
    
    @JoinColumn (name="idUsuario") 
    @ManyToOne 
    private Usuario usuario;
    
    @Column(name="fechaInicio")
    @NotNull
    @Temporal (TemporalType.DATE)
    private Date fechaInicio;
    
    @Column (name="fechaFin")
    @NotNull
    @Temporal (TemporalType.DATE)
    private Date fechaFin;
    
    @Column (name="fechaDevolucion")
    @Temporal (TemporalType.DATE)
    private Date fechaDevolucion;
    
    @Column (name="comentario")
    private String comentario;
    
    @Column (name="estado")
    @NotNull
    private int estado=0; //default 0
    
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getComentario() {
        return comentario;
    }
    
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public String getStringEstado() {           
        switch (estado) {
            case 0: return "Activa";
            case 1: return "Finalizado";
            case -1: return "Cancelado";
            default: return "ERROR";
        }
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }
    
    @Override
    public String toString() {
        return "Prestamo{" + "idPrestamo=" + idPrestamo + ", libro=" + libro + ", usuario=" + usuario + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", fechaDevolucion=" + fechaDevolucion + ", comentario=" + comentario + '}';
    }

}
