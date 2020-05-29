/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.EJB;

import bibliotecas.modelo.Prestamo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author david
 */
@Stateless
public class PrestamoFacade extends AbstractFacade<Prestamo> implements PrestamoFacadeLocal {

    @PersistenceContext(unitName = "biblioteca_Bibliotecas_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrestamoFacade() {
        super(Prestamo.class);
    }
    
    @Override
    public int getLastId () {
        int id=0;
        List <Prestamo> lista = findAll();
        for (Prestamo p:lista) {
            if (p.getIdPrestamo()>id) {
                id=p.getIdPrestamo();
            }
        }
        return id;
    }
}
