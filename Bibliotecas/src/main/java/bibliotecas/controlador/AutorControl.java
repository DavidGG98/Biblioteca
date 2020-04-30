/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.AutorFacadeLocal;
import bibliotecas.modelo.Autor;
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


public class AutorControl implements Serializable {
    
    private Autor autor;
    
    @EJB
    AutorFacadeLocal autorEJB;
    List <Autor> listaAutores;

    
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        autor=new Autor(); //reserva la memoria
        listaAutores = autorEJB.findAll();

    }
    public void insertar(){
        try{
          autorEJB.create(autor);
            System.out.println("Anadiendo Autor...");
        } catch (Exception e) {
            System.out.println("Error al anadir el Autor "+ e.getMessage());
        }
    }
    public void eliminar(){
        try{
            System.out.println("");
            for(Autor t:listaAutores){
                if(t.getIdAutor()==(autor.getIdAutor())){
                    autor=t;
                    break;
                }
            autorEJB.remove(autor);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el Autor "+ e.getMessage());
        }
    }
    public void modificar() {
        try {
            String n=autor.getNombre();
            for (Autor c:listaAutores) {
                if(c.getIdAutor()== autor.getIdAutor()) {
                    autor=c; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            autor.setNombre(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            autorEJB.edit(autor);
        } catch (Exception e) {
            System.out.println("Error al modificar el Autor"+ e.getMessage());
        }
    }

    public Autor getAutor() {
        return autor;
    }

    public AutorFacadeLocal getAutorEJB() {
        return autorEJB;
    }

    public List<Autor> getListaAutores() {
        return listaAutores;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setAutorEJB(AutorFacadeLocal autorEJB) {
        this.autorEJB = autorEJB;
    }

    public void setListaAutores(List<Autor> listaAutores) {
        this.listaAutores = listaAutores;
    }
        
}
