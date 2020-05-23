/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.EJB;

import bibliotecas.modelo.Libro;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author david
 */
@Stateless
public class LibroFacade extends AbstractFacade<Libro> implements LibroFacadeLocal {

    @PersistenceContext(unitName = "biblioteca_Bibliotecas_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LibroFacade() {
        super(Libro.class);
    }
    
}
