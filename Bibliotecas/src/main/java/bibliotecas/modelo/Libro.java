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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author david
 */
@Entity
@Table (name="libros")

public class Libro implements Serializable {
 
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int idLibro;
    
    @Column(name="titulo")
    @NotNull
    private String titulo;
    
    @Column (name="genero")
    private String genero;
    
    @Column (name="resumen")
    private String resumen;
    
    @Column (name="imagen")
    private String imagen; //URL a la imagen en la carpeta resources
    
    @Column(name="idioma")
    private String idioma;
    
    @Column (name="tiempoPrestamo")
    @NotNull
    private int tiempoPrestamo;
    
    @JoinColumn(name="idAutor")
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Autor autor;
    
    @JoinColumn(name="idBiblioteca")
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Biblioteca biblioteca;
    
    @JoinColumn(name="idEditorial")
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Editorial editorial;

    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getResumen() {
        return resumen;
    }

    public String getImagen() {
        return imagen;
    }

    public int getTiempoPrestamo() {
        return tiempoPrestamo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setTiempoPrestamo(int tiempoPrestamo) {
        this.tiempoPrestamo = tiempoPrestamo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIdioma() {
        return idioma;
    }

    @Override
    public String toString() {
        return "Libro{" + "idLibro=" + idLibro + ", titulo=" + titulo + ", genero=" + genero + ", resumen=" + resumen + ", imagen=" + imagen + ", tiempoPrestamo=" + tiempoPrestamo + ", autor=" + autor + ", biblioteca=" + biblioteca + ", editorial=" + editorial + '}';
    }

}
