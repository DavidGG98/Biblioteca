/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
/**
 *
 * @author david
 */
@Entity
@Table (name="reservas")

public class Reserva implements Serializable {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int idReserva;
    
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
    
    @Column (name="fechaRecogida")
    @Temporal (TemporalType.DATE)
    private Date fechaRecogida;
    
    @Column (name="comentario")
    private String comentario;
    
    @Column (name="estado") 
    private int estado;

    public String getStringEstado () {
        int e = this.estado;
        switch (e) {
            case 0: return "Activo";
            case 1: return "Recogido";
            case -1: return "Cancelado";
            default : return "ERROR";
        }
    }
    
    public int getIdReserva() {
        return idReserva;
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

    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    public String getComentario() {
        return comentario;
    }

    public int getEstado() {
        return estado;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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

    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reserva other = (Reserva) obj;
        if (this.idReserva != other.idReserva) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.comentario, other.comentario)) {
            return false;
        }
        if (!Objects.equals(this.libro, other.libro)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaFin, other.fechaFin)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecogida, other.fechaRecogida)) {
            return false;
        }
        return true;
    }
   
    

}