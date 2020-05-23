/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.modelo;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 *
 * @author david
 */

@Entity
@Table (name="trabajadores",  uniqueConstraints={@UniqueConstraint(columnNames ={"dni"})})

public class Trabajador implements Serializable {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int idTrabajador;
    
    @Column (name="nombre")
    @NotNull
    private String nombre;
    
    @Column (name="apellidos")
    @NotNull
    private String apellidos;
    
    @Column (name="dni", length=9)
    @NotNull
    private String dni;
    
    @Column (name="contrasena")
    @NotNull
    private String contrasena;
    
    @JoinColumn (name="idBiblioteca")
    @ManyToOne
    private Biblioteca biblioteca;
    
    @JoinColumn (name="idRol")
    @ManyToOne
    private Rol rol;

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public Rol getRol() {
        return rol;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Trabajador{" + "idTrabajador=" + idTrabajador + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", contrasena=" + contrasena + ", biblioteca=" + biblioteca + ", rol=" + rol + '}';
    }
    
    
}
