/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.modelo.Trabajador;
import bibliotecas.modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author david
 */
@Named
@ViewScoped 

public class PlantillaControl implements Serializable {
    
    //Se encarga de verificar que haya un usuario loggeado en la aplicación. No lo hay redirecciona al index
    public void verificarUsuario () throws IOException{
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        Usuario usuario =(Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuario==null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/index.xhtml ");
        } else {
            
        }
    }
    
    public void verificarTrabajador () throws IOException{
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        Trabajador t =(Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        if (t==null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/index.xhtml ");
        } else {
            
        }
    }
}
