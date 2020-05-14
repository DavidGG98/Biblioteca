/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.EJB;

import bibliotecas.modelo.Reserva;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author david
 */
@Local
public interface ReservaFacadeLocal {

    void create(Reserva reservas);

    void edit(Reserva reservas);

    void remove(Reserva reservas);

    Reserva find(Object id);

    List<Reserva> findAll();

    List<Reserva> findRange(int[] range);

    int count();
    
}
