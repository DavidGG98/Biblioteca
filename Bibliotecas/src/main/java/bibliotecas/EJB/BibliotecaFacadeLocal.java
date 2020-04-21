/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.EJB;

import bibliotecas.modelo.Biblioteca;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author david
 */
@Local
public interface BibliotecaFacadeLocal {

    void create(Biblioteca biblioteca);

    void edit(Biblioteca biblioteca);

    void remove(Biblioteca biblioteca);

    Biblioteca find(Object id);

    List<Biblioteca> findAll();

    List<Biblioteca> findRange(int[] range);

    int count();
    
}
