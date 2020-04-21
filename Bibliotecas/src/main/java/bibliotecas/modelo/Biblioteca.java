/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */

@Entity
@Table (name="bibliotecas", uniqueConstraints={@UniqueConstraint(columnNames ={"direccion"})})

public class Biblioteca implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idBiblioteca;
    
    
    @Column(name="nombre")
    @NotNull
    private String nombre;
    
    @Column (name="direccion")
    @NotNull
    private String direccion;
    
    @Column (name="localidad")
    @NotNull
    private String localidad;

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Biblioteca{" + "idBiblioteca=" + idBiblioteca + ", nombre=" + nombre + ", direccion=" + direccion + ", localidad=" + localidad + '}';
    }

}
