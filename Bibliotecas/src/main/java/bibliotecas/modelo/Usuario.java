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
@Table (name="usuarios",  uniqueConstraints={@UniqueConstraint(columnNames ={"numeroCarnet"})})

public class Usuario implements Serializable {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int idUsuario;
    
    @Column (name="nombre")
    @NotNull
    private String nombre;
    @Column (name="apellidos")
    @NotNull
    private String apellidos;
    @Column (name="numeroCarnet")
    @NotNull
    private String numeroCarnet;
    @Column (name="contrasena")
    @NotNull
    private String contrasena;
    @Column (name="email")
    private String email;

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNumeroCarnet() {
        return numeroCarnet;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNumeroCarnet(String numeroCarnet) {
        this.numeroCarnet = numeroCarnet;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidos=" + apellidos + ", numeroCarnet=" + numeroCarnet + ", contrasena=" + contrasena + ", email=" + email + '}';
    }

}
