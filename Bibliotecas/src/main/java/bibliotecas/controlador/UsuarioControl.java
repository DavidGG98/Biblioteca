 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.UsuarioFacadeLocal;
import bibliotecas.modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UsuarioControl implements Serializable {
    
    private Usuario usuario;
    @EJB
    UsuarioFacadeLocal usuarioEJB;
    List<Usuario> listaUsuarios;
    String context;

    @PostConstruct
    public void reserva(){
        usuario= new Usuario();
        listaUsuarios= usuarioEJB.findAll();
        context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
    }
    public void insertar(){
        try{
            usuarioEJB.create(usuario);
            System.out.println("Anadiendo usuario...");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaUsuarios/listaUsuarios.xhtml");
        } catch (Exception e) {
            try {
                System.out.println("Error al anadir el Usuario "+ e.getMessage());
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void eliminar(int id){
        try{
            System.out.println("");
            for(Usuario t:listaUsuarios){
                if(id==t.getIdUsuario()){
                    usuario=t;
                    break;
                }            
            }
            usuarioEJB.remove(usuario);
            System.out.println("Usuario eliminado con exito");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaUsuarios/listaUsuarios.xhtml");
        } catch (Exception e) {
            try {
                System.out.println("Error al anadir el Usuario "+ e.getMessage());
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void modificar(Usuario u) {
        try {
            usuarioEJB.edit(usuario);
            System.out.println("Usuario modificado");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaUsuarios/listaUsuarios.xhtml");

        } catch (Exception e) {
            try {
                System.out.println("Error al anadir el Usuario "+ e.getMessage());
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public Usuario getUsuario() {
        return usuario;
    }

        public Usuario getUsuario(int id){
        
        try{
            System.out.println(id);
            System.out.println("Seleccionado "+ id);
            for(Usuario b:listaUsuarios){
                if(b.getIdUsuario()==id){
                    System.out.println("Usuario "+ b.getNombre()+" tiene el mismo id");
                    usuario=b;
                    break;
                }
            }
            return usuario;
        } catch (Exception e){
            System.out.println("Error al seleccionar el usuario "+ e.getMessage());
        }
        return null;
    }
    
    public UsuarioFacadeLocal getUsuariosEJB() {
        return usuarioEJB;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuariosEJB(UsuarioFacadeLocal UsuariosEJB) {
        this.usuarioEJB = UsuariosEJB;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarioses) {
        this.listaUsuarios = listaUsuarioses;
    }
}
