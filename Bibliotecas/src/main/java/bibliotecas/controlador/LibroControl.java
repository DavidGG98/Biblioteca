/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.AutorFacadeLocal;
import bibliotecas.EJB.BibliotecaFacadeLocal;
import bibliotecas.EJB.EditorialFacadeLocal;
import bibliotecas.EJB.LibroFacadeLocal;
import bibliotecas.modelo.Autor;
import bibliotecas.modelo.Biblioteca;
import bibliotecas.modelo.Editorial;
import bibliotecas.modelo.Libro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author david
 */
@Named
@ViewScoped

public class LibroControl implements Serializable {
    
    private Libro libro;
    
    @EJB
    LibroFacadeLocal libroEJB;
    @EJB
    AutorFacadeLocal autorEJB;
    @EJB
    EditorialFacadeLocal editorialEJB;
    @EJB
    BibliotecaFacadeLocal bibliotecaEJB;
    
    List <Biblioteca> listaBibliotecas;
    List<Autor> listaAutores;
    List<Editorial> listaEditoriales;
    List <Libro> listaLibros;
    private Autor autor;
    private Editorial editorial;
    private Biblioteca biblioteca;
    
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        libro=new Libro(); //reserva la memoria
        autor= new Autor();
        editorial = new Editorial();
        biblioteca = new Biblioteca();
        
        listaLibros = libroEJB.findAll();
        listaEditoriales=editorialEJB.findAll();
        listaAutores=autorEJB.findAll();
        listaBibliotecas=bibliotecaEJB.findAll();
    }
    
    public void insertar() { //Inserta en la base de datos
        //Cargamos el objeto autor 
        autor=autorEJB.find(autor.getIdAutor());
        //Cargamos el objeto editorial
        editorial = editorialEJB.find(editorial.getIdEditorial());
        try {
            libro.setAutor(autor);
            System.out.println("Autor insertado");
            libro.setEditorial(editorial);
            System.out.println("editorial insertada" );
            libro.setBiblioteca(bibliotecaEJB.find(biblioteca.getIdBiblioteca()));
            System.out.println("Biblioteca insertada ");
            libroEJB.create(libro);
            System.out.println("Insertando libro...");
        } catch (Exception e) {
            System.out.println("Error al insertar el libro " + e.getMessage());
        }
    }
    
    public void eliminar() {
        try {
            System.out.println();
            for (Libro l:listaLibros) {
                if(l.getIdLibro() == libro.getIdLibro()) {
                    libro=l; //Recuperamos la categoria al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            libroEJB.remove(libro);
        } catch (Exception e) {
            System.out.println("Erroooooor al eliminar el libro "+ e.getMessage());
        }
    }
    
    public void modificar() {
        try {
            String n=libro.getTitulo();
            for (Libro c:listaLibros) {
                if(c.getIdLibro() == libro.getIdLibro()) {
                    libro=c; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            libro.setTitulo(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            libroEJB.edit(libro);
        } catch (Exception e) {
            System.out.println("Erroooooor al modificarl el libro"+ e.getMessage());
        }
    }
    
    /*
    public Libro comprimeLibro () {
        
    }
    
    //Agrupa todos los libros que tengan el mismo titulo y autor.
    //Si al menos un libro no está alquilado, aparecerá como libre.
    
    public List <Libro> comprimeLibroLista () {
        List <Libro> libros = new ArrayList <Libro>();
        for (Libro l : listaLibros) {
            //Creamos una copia del libro y lo introducimos en la lista
            Libro lib = new Libro ();
            lib.setAutor(l.getAutor());
            lib.setTitulo(l.getTitulo());
            if (libros.contains(l) && libros.) {
                //Si el estado es libre, actualizamos a libre
            } else {       
                lib.setEstado(l.getEstado());
                libros.add(lib);
            }
       
        }
        return libros;
    }
    
    //Dado un titulo y un autor te devuelve todos los libros
    public List <Libro> expandeLibro (String titulo, String auto) {
        
    }
    */
    public Libro getLibro() {
        return libro;
    }

    public List<Biblioteca> getListaBibliotecas() {
        return listaBibliotecas;
    }

    public List<Autor> getListaAutores() {
        return listaAutores;
    }

    public List<Editorial> getListaEditoriales() {
        return listaEditoriales;
    }

    public void setListaBibliotecas(List<Biblioteca> listaBibliotecas) {
        this.listaBibliotecas = listaBibliotecas;
    }

    public void setListaAutores(List<Autor> listaAutores) {
        this.listaAutores = listaAutores;
    }

    public void setListaEditoriales(List<Editorial> listaEditoriales) {
        this.listaEditoriales = listaEditoriales;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void setAutorEJB(AutorFacadeLocal autorEJB) {
        this.autorEJB = autorEJB;
    }

    public void setEditorialEJB(EditorialFacadeLocal editorialEJB) {
        this.editorialEJB = editorialEJB;
    }

    public void setBibliotecaEJB(BibliotecaFacadeLocal bibliotecaEJB) {
        this.bibliotecaEJB = bibliotecaEJB;
    }

    public AutorFacadeLocal getAutorEJB() {
        return autorEJB;
    }

    public EditorialFacadeLocal getEditorialEJB() {
        return editorialEJB;
    }

    public BibliotecaFacadeLocal getBibliotecaEJB() {
        return bibliotecaEJB;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }
    
    public LibroFacadeLocal getLibroEJB() {
        return libroEJB;
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setLibroEJB(LibroFacadeLocal libroEJB) {
        this.libroEJB = libroEJB;
    }

    public void setListaLibros(List<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    public Autor getAutor() {
        return autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
       
}
