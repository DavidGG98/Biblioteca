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
@Table (name="editoriales", uniqueConstraints={@UniqueConstraint(columnNames=
        {"Nombre", "Telefono", "email", "cif"})})
public class Editorial implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idEditorial;
    
    @Column (name="nombre")
    @NotNull
    private String nombre;
    
    @Column (name="CIF")
    @NotNull
    private String cif;
    
    @Column (name="telefono")
    private String telefono;
    
    @Column (name="email")
    private String email;

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCif() {
        return cif;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Editorial{" + "idEditorial=" + idEditorial + ", nombre=" + nombre + ", cif=" + cif + ", telefono=" + telefono + ", email=" + email + '}';
    }    
    
}
