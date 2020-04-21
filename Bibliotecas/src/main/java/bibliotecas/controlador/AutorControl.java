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
import javax.faces.bean.ViewScoped;
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
