/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table (name="autores")

public class Autor implements Serializable {


    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int idAutor;
    @Column (name="nombre")
    @NotNull
    private String nombre;
    @Column (name="apellidos")
    private String apellidos;
    @Column (name="nacionalidad")
    private String pais;
    @Column (name="fechaNacimiento")
    private int fechaNacimiento;

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        if (apellidos == null) {
            return "";
        } else {
            return apellidos;
        }
    }

    public String getNombreYApellidos () {
        if (apellidos == null) {
            return nombre;
        } else {
            return nombre + " " + apellidos;
        }
    }
    
    public String getPais() {
        return pais;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Autor{" + "idAutor=" + idAutor + ", nombre=" + nombre + ", apellidos=" + apellidos + ", pais=" + pais + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
    
    
}
