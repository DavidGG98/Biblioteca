 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.UsuarioFacadeLocal;
import bibliotecas.modelo.Usuario;
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
public class UsuarioControl implements Serializable {
    private Usuario usuario;
    
    @EJB
    UsuarioFacadeLocal usuarioEJB;
    List <Usuario> listaUsuarios;
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        usuario=new Usuario(); //reserva la memoria
        listaUsuarios = usuarioEJB.findAll();
    }
    
    public void insertar() { //Inserta en la base de datos
        try {
            usuarioEJB.create(usuario);
            System.out.println("Insertando Usuario...");
        } catch (Exception e) {
            System.out.println("Error al insertar el Usuario " + e.getMessage());
        }
    }
    

    public Usuario getUsuario() {
        return usuario;
    }

    public UsuarioFacadeLocal getUsuarioEJB() {
        return usuarioEJB;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuarioEJB(UsuarioFacadeLocal usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    
}
