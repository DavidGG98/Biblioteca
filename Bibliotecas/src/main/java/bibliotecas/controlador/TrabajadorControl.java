/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.BibliotecaFacadeLocal;
import bibliotecas.EJB.RolFacadeLocal;
import bibliotecas.EJB.TrabajadorFacadeLocal;
import bibliotecas.modelo.Biblioteca;
import bibliotecas.modelo.Rol;
import bibliotecas.modelo.Trabajador;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author david, alex
 */
@Named 
@ViewScoped
public class TrabajadorControl implements Serializable{
    
    private Trabajador trabajador;
    
    @EJB
    TrabajadorFacadeLocal trabajadorEJB;
    @EJB
    BibliotecaFacadeLocal bicliotecaEJB;
    @EJB
    RolFacadeLocal rolEJB;
    List <Trabajador> listaTrabajadores;
    List <Biblioteca> listaBibliotecas;
    List <Rol> listaRoles;
    private Biblioteca biblioteca;
    private Rol rol;
    public Trabajador getTrabajador(int id){
        try{
            System.out.println(id);
            System.out.println("Seleccionado "+ id);
            for(Trabajador b:listaTrabajadores){
                if(b.getIdTrabajador()==id){
                    System.out.println("Trabajador "+ b.getNombre()+" tiene el mismo id");
                    trabajador=b;
                    break;
                }
            }
            return trabajador;
        } catch (Exception e){
            System.out.println("Error al seleccionar el Trabajador "+ e.getMessage());
        }
        return null;
    }
    
    @PostConstruct
    public void reserva(){
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador") != null) {
            trabajador = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        } else {
            trabajador= new Trabajador();
        }
        biblioteca = new Biblioteca();
        rol = new Rol();
        listaTrabajadores= trabajadorEJB.findAll();
        listaBibliotecas=bicliotecaEJB.findAll();
        listaRoles = rolEJB.findAll();        
    }
    public void insertar(){
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        for (Biblioteca b:listaBibliotecas) {
            if (b.getIdBiblioteca() == biblioteca.getIdBiblioteca()) {
                biblioteca = b;
                break;
            }
        }
        trabajador.setBiblioteca(biblioteca);
        for (Rol r:listaRoles) {
            if (r.getIdRol()==rol.getIdRol()) {
                rol = r;
                break;
            }
        }
        trabajador.setRol(rol);
        try{
            trabajadorEJB.create(trabajador);
            System.out.println("Anadiendo trabajador...");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaTrabajadores/listaTrabajadores.xhtml");
        } catch (Exception e) {
            System.out.println("Error al anadir el Trabajador "+ e.getMessage());
        }
    }
    public void eliminar(int id){
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        System.out.println("Eliminado trabajador con id= "+id);
        try{
            for(Trabajador t:listaTrabajadores){
                if(t.getIdTrabajador()==id){
                    trabajador=t;
                    break;
                }
            }
            trabajadorEJB.remove(trabajador);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaTrabajadores/listaTrabajadores.xhtml");       
            
        } catch (Exception e) {
            System.out.println("Error al eliminar el trabajador "+ e.getMessage());
        }
    }
    public void modificar(Trabajador t) {
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        try {
            trabajador.setRol(rol);
            trabajador.setBiblioteca(biblioteca);
            trabajadorEJB.edit(trabajador);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaTrabajadores/listraTrabajadores.xhtml");
        
        } catch (Exception e) {
            System.out.println("Error al modificar el trabajador"+ e.getMessage());
        }
    }
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public TrabajadorFacadeLocal getTrabajadorEJB() {
        return trabajadorEJB;
    }

    public List<Trabajador> getListaTrabajadores() {
        return listaTrabajadores;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setTrabajadorEJB(TrabajadorFacadeLocal TrabajadorEJB) {
        this.trabajadorEJB = TrabajadorEJB;
    }

    public void setListaTrabajadores(List<Trabajador> listaTrabajadores) {
        this.listaTrabajadores = listaTrabajadores;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }


    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    public Rol getRol() {
        return rol;
    }


    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Biblioteca> getListaBibliotecas() {
        return listaBibliotecas;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaBibliotecas(List<Biblioteca> listaBibliotecas) {
        this.listaBibliotecas = listaBibliotecas;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    
}
