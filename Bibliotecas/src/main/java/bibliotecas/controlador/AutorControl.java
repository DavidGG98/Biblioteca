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
import javax.faces.context.FacesContext;
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
    String context;
    public Autor getAutor(int id){
        try{
            System.out.println(id);
            System.out.println("Seleccionado "+ id);
            for(Autor b:listaAutores){
                if(b.getIdAutor()==id){
                    System.out.println("Autor "+ b.getNombre()+" tiene el mismo id");
                    autor=b;
                    break;
                }
            }
            return autor;
        } catch (Exception e){
            System.out.println("Error al seleccionar el Autor "+ e.getMessage());
        }
        return null;
    }
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        autor=new Autor(); //reserva la memoria
        listaAutores = autorEJB.findAll();
        context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
    }
    public void insertar(){
        try{
          autorEJB.create(autor);
            System.out.println("Anadiendo Autor...");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaAutores/listaAutores.xhtml");
        } catch (Exception e) {
            System.out.println("Error al anadir el Autor "+ e.getMessage());
        }
    }
    public void eliminar(int id){
        System.out.println("eliminando autor " +id);
        try{
            for(Autor t:listaAutores){
                if((id)==t.getIdAutor()){
                    autor=t;
                    break;
                }
            }
             autorEJB.remove(autor);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaAutores/listaAutores.xhtml");
        } catch (Exception e) {
            System.out.println("Error al eliminar el Autor "+ e.getMessage());
        }
    }
    public void modificar(Autor a) {
        try {
            autorEJB.edit(a);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaAutores/listaAutores.xhtml");
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
