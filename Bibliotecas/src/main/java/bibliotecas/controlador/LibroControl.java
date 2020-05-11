/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.LibroFacadeLocal;
import bibliotecas.modelo.Autor;
import bibliotecas.modelo.Editorial;
import bibliotecas.modelo.Libro;
import java.io.Serializable;
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
    List <Libro> listaLibros;
    private Autor autor;
    private Editorial editorial;
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
   public Libro getLibro(int id){
        
        try{
            System.out.println(id);
            System.out.println("Seleccionado "+ id);
            for(Libro b:listaLibros){
                if(b.getIdLibro()==id){
                    System.out.println("Libro "+ b.getTitulo()+" tiene el mismo id");
                    libro=b;
                    break;
                }
            }
            return libro;
        } catch (Exception e){
            System.out.println("Error al seleccionar el Libro "+ e.getMessage());
        }
        return null;
    } 
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        libro=new Libro(); //reserva la memoria
        listaLibros = libroEJB.findAll();
    }
    
        public void insertar() { //Inserta en la base de datos
        try {
            libroEJB.create(libro);
            System.out.println("Insertando libro...");
        } catch (Exception e) {
            System.out.println("Error al insertar el libro " + e.getMessage());
        }
    }
    
    public void eliminar(int id) {
        try {
            System.out.println();
            for (Libro l:listaLibros) {
                if(l.getIdLibro() == id) {
                    libro=l; //Recuperamos la categoria al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            libroEJB.remove(libro);
        } catch (Exception e) {
            System.out.println("Erroooooor al eliminar el libro "+ e.getMessage());
        }
    }
    
    public void modificar(int id) {
        try {
            String n=libro.getTitulo();
            for (Libro c:listaLibros) {
                if(c.getIdLibro() == id) {
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

    public Libro getLibro() {
        return libro;
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
