/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.EJB;

import bibliotecas.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author david
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "biblioteca_Bibliotecas_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public Usuario verificarUsuario(Usuario u) {
       String consulta = "FROM Usuario u WHERE u.numeroCarnet=:param1"
               + " and u.contrasena=:param2";
       //Creamos la consulta
       Query q = em.createQuery(consulta); 
       //Cargamos los parametros
       q.setParameter("param1", u.getNumeroCarnet());
       q.setParameter("param2", u.getContrasena());
       
       List <Usuario> result = q.getResultList();
       
       if (result.isEmpty()) {
           return null;
       } else {
           return result.get(0);
       }
    }
}
