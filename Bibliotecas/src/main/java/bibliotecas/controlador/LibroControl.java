/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.AutorFacadeLocal;
import bibliotecas.EJB.EditorialFacadeLocal;
import bibliotecas.EJB.LibroFacadeLocal;
import bibliotecas.modelo.Autor;
import bibliotecas.modelo.Biblioteca;
import bibliotecas.modelo.Editorial;
import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Trabajador;
import bibliotecas.modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
    EditorialFacadeLocal editorialEJB;
    @EJB
    AutorFacadeLocal autorEJB;
    List <Libro> listaLibros;
    List <Editorial> listaEditoriales;
    List <Autor> listaAutores;
    private Autor autor;
    private Editorial editorial;
    String context;
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        libro=new Libro(); //reserva la memoria
        autor = new Autor();
        editorial = new Editorial();
        listaLibros = libroEJB.findAll();
        listaAutores = autorEJB.findAll();
        listaEditoriales=editorialEJB.findAll();
        context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
    }
    
        public void insertar() { //Inserta en la base de datos             
        //recuperamos el autor completo        
        for (Autor a:listaAutores) {
            if (a.getIdAutor()==autor.getIdAutor()) {
                autor=a;
                break;
            }
        }
  
        libro.setAutor(autor);
        //recuperamos la editorial completa
        
        for (Editorial e:listaEditoriales) {
            if (e.getIdEditorial() == editorial.getIdEditorial()) {
                editorial=e;
                break;
            }
        }
        libro.setEditorial(editorial);
        //Seteamos la biblioteca
        Trabajador t =(Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        libro.setBiblioteca(t.getBiblioteca());
        //Seteamos los defaults 
        if(libro.getIdioma() == null || libro.getIdioma().equals("")) {
                libro.setIdioma("Espanol"); //IDIOMA DEFAULT
        }
        libro.setTiempoPrestamo(15); //Dias que peude estar prestado
        try {
            libroEJB.create(libro);
            System.out.println("Insertando libro...");
             try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaLibros/tablaLibros.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LibroControl.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            System.out.println("Libro eliminado de la base de datos");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaLibros/tablaLibros.xhtml");

        } catch (Exception e) {
            System.out.println("Erroooooor al eliminar el libro "+ e.getMessage());
        }
    }
    
    public void modificar(Libro l) {
        //String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();       
        try {
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            //libro.setTitulo(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            libroEJB.edit(l);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaLibros/tablaLibros.xhtml");

        } catch (Exception e) {
            System.out.println("Erroooooor al modificarl el libro"+ e.getMessage());
        }
    }

    public Libro getLibro() {
        return libro;
    }

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

    public List<Editorial> getListaEditoriales() {
        return listaEditoriales;
    }

    public List<Autor> getListaAutores() {
        return listaAutores;
    }

    public void setListaEditoriales(List<Editorial> listaEditoriales) {
        this.listaEditoriales = listaEditoriales;
    }

    public void setListaAutores(List<Autor> listaAutores) {
        this.listaAutores = listaAutores;
    }
      
    //Para mostrar los libros pertenecientes a la biblioteca en la que trabaja el trabajador
    public List <Libro> getLibrosBiblioteca () {
        Trabajador t =(Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        List <Libro> lista = new ArrayList ();
        for (Libro l:listaLibros) {
            if (l.getBiblioteca().getIdBiblioteca() == t.getBiblioteca().getIdBiblioteca()) {
                lista.add(l);
            }
        }
        
        return lista;
    }
    
}
