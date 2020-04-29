/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.EJB;

import bibliotecas.modelo.Trabajador;
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
public class TrabajadorFacade extends AbstractFacade<Trabajador> implements TrabajadorFacadeLocal {

    @PersistenceContext(unitName = "biblioteca_Bibliotecas_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrabajadorFacade() {
        super(Trabajador.class);
    }
    
    @Override
    public Trabajador verificarTrabajador (Trabajador t) {
       String consulta = "FROM Trabajador t WHERE t.dni=:param1"
               + " and t.contrasena=:param2";
       //Creamos la consulta
       Query q = em.createQuery(consulta); 
       //Cargamos los parametros
       q.setParameter("param1", t.getDni());
       q.setParameter("param2", t.getContrasena());
       
       List <Trabajador> result = q.getResultList();
       
       if (result.isEmpty()) {
           return null;
       } else {
           return result.get(0);
       }
    }
    
}
