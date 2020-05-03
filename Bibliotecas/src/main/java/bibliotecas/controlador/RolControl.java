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

    
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        rol=new Rol(); //reserva la memoria
        listaRoles = RolEJB.findAll();

    }
    
    public void insertar() { //Inserta en la base de datos
        try {
            RolEJB.create(rol);
            System.out.println("Insertando Rol...");
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
    
    public void modificar(int id) {
        try {
            String n=rol.getNombre();
            String d=rol.getDescripcion();
            for (Rol r:listaRoles) {
                if(r.getIdRol() == id) {
                    rol=r; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            rol.setNombre(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            rol.setDescripcion(d);
            RolEJB.edit(rol);
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
