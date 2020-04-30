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
    List<Usuario> listaUsuarios;
    
    @PostConstruct
    public void reserva(){
        usuario= new Usuario();
        listaUsuarios= usuarioEJB.findAll();
    }
     public void insertar(){
        try{
          usuarioEJB.create(usuario);
            System.out.println("Anadiendo usuario...");
        } catch (Exception e) {
            System.out.println("Error al anadir el Usuario "+ e.getMessage());
        }
    }
    public void eliminar(){
        try{
            System.out.println("");
            for(Usuario t:listaUsuarios){
                if(t.getIdUsuario()==(usuario.getIdUsuario())){
                    usuario=t;
                    break;
                }
            usuarioEJB.remove(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario "+ e.getMessage());
        }
    }
    public void modificar() {
        try {
            String n=usuario.getNombre();
            for (Usuario c:listaUsuarios) {
                if(c.getIdUsuario()== usuario.getIdUsuario()) {
                    usuario=c; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            usuario.setNombre(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            usuarioEJB.edit(usuario);
        } catch (Exception e) {
            System.out.println("Error al modificar el usuario"+ e.getMessage());
        }
    }
    public Usuario getUsuarios() {
        return usuario;
    }

    public UsuarioFacadeLocal getUsuariosEJB() {
        return usuarioEJB;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setUsuarios(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuariosEJB(UsuarioFacadeLocal UsuariosEJB) {
        this.usuarioEJB = UsuariosEJB;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarioses) {
        this.listaUsuarios = listaUsuarioses;
    }
}
