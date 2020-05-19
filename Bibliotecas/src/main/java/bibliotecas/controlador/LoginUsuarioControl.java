/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.UsuarioFacadeLocal;
import bibliotecas.modelo.Libro;
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

public class LoginUsuarioControl implements Serializable{
    
    @EJB
    UsuarioFacadeLocal usuarioEJB;
    Usuario usuario;
    

    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        usuario=new Usuario(); //reserva la memoria
    }
    
    public String verificarUsuario () {       
        usuario = usuarioEJB.verificarUsuario(usuario);
        if (usuario == null) {
            return "loginError.xhtml?faces-redirect=true";
        } else {
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
            return "private/user/home.xhtml?faces-redirect=true";
        }
    }
    

    
    public UsuarioFacadeLocal getUsuarioEJB() {
        return usuarioEJB;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuarioEJB(UsuarioFacadeLocal usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
        
}
