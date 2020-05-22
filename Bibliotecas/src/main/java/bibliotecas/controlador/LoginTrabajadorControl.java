/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.TrabajadorFacadeLocal;
import bibliotecas.EJB.UsuarioFacadeLocal;
import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Trabajador;
import bibliotecas.modelo.Usuario;
import java.io.Serializable;
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

public class LoginTrabajadorControl implements Serializable{
    
    @EJB
    TrabajadorFacadeLocal trabajadorEJB;
    Trabajador trabajador;
    

    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        trabajador=new Trabajador(); //reserva la memoria
    }
    
    public String verificarTrabajador () {
        try {
            trabajador = trabajadorEJB.verificarTrabajador(trabajador);
            if (trabajador == null) {
                return "loginError.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("trabajador", trabajador);
                return "private/worker/workerHome.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("Error inesperado " + e.getMessage());
            return "unexpectedError.xhtml?faces-redirect=true";
        }
    }

    public TrabajadorFacadeLocal getTrabajadorEJB() {
        return trabajadorEJB;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajadorEJB(TrabajadorFacadeLocal trabajadorEJB) {
        this.trabajadorEJB = trabajadorEJB;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    
}
