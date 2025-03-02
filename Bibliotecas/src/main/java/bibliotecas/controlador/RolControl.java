/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.RolFacadeLocal;
import bibliotecas.modelo.Rol;
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

public class RolControl implements Serializable {
     private Rol rol;
    
    @EJB
    RolFacadeLocal RolEJB;
    List <Rol> listaRoles;

    public Rol getRol(int id){
        try{
            System.out.println(id);
            System.out.println("Seleccionado "+ id);
            for(Rol b:listaRoles){
                if(b.getIdRol()==id){
                    System.out.println("Rol "+ b.getNombre()+" tiene el mismo id");
                    rol=b;
                    break;
                }
            }
            return rol;
        } catch (Exception e){
            System.out.println("Error al seleccionar el Rol "+ e.getMessage());
        }
        return null;
    }
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        rol=new Rol(); //reserva la memoria
        listaRoles = RolEJB.findAll();

    }
    
    public void insertar() { //Inserta en la base de datos
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        try {
            RolEJB.create(rol);
            System.out.println("Rol insertado con exito");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaLibros/tablaLibros.xhtml");

        } catch (Exception e) {
            System.out.println("Error al insertar el Rol " + e.getMessage());
        }
    }
    
  
    
    public void eliminar(int id) {
        try {
            System.out.println();
            for (Rol r:listaRoles) {
                if(r.getIdRol() == id) {
                    rol=r; //Recuperamos la categoria al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            RolEJB.remove(rol);
        } catch (Exception e) {
            System.out.println("Erroooooor al eliminar el libro "+ e.getMessage());
        }
    }
    
    public void modificar(Rol r) {
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        try {
            RolEJB.edit(r);
            System.out.println("Rol editado con exito");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaLibros/tablaLibros.xhtml");

        } catch (Exception e) {
            System.out.println("Erroooooor al modificarl el libro"+ e.getMessage());
        }
    }

    public Rol getRol() {
        return rol;
    }

    public RolFacadeLocal getRolEJB() {
        return RolEJB;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setRolEJB(RolFacadeLocal RolEJB) {
        this.RolEJB = RolEJB;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }
    
}
