/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.PrestamoFacadeLocal;
import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Prestamo;
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
public class PrestamoControl implements Serializable{
    
    private Prestamo prestamo;
    
    @EJB
    PrestamoFacadeLocal prestamoEJB;
    List <Prestamo> listaPrestamoes;
 
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        prestamo=new Prestamo(); //reserva la memoria
        listaPrestamoes = prestamoEJB.findAll();

    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public PrestamoFacadeLocal getPrestamoEJB() {
        return prestamoEJB;
    }

    public List<Prestamo> getListaPrestamoes() {
        return listaPrestamoes;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void setPrestamoEJB(PrestamoFacadeLocal prestamoEJB) {
        this.prestamoEJB = prestamoEJB;
    }

    public void setListaPrestamoes(List<Prestamo> listaPrestamoes) {
        this.listaPrestamoes = listaPrestamoes;
    }
  public void insertar(){
        try{
          prestamoEJB.create(prestamo);
            System.out.println("Anadiendo prestamo...");
        } catch (Exception e) {
            System.out.println("Error al anadir el prestamo "+ e.getMessage());
        }
    }
    public void eliminar(int id){
        try{
            System.out.println("");
            for(Prestamo t:listaPrestamoes){
                if(t.getIdPrestamo()==id){
                    prestamo=t;
                    break;
                }
            prestamoEJB.remove(prestamo);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el prestamo "+ e.getMessage());
        }
    }
    public void modificar(int id) {
        try {
            Libro n=prestamo.getLibro();
            for (Prestamo c:listaPrestamoes) {
                if(c.getIdPrestamo()== id) {
                    prestamo=c; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            prestamo.setLibro(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            prestamoEJB.edit(prestamo);
        } catch (Exception e) {
            System.out.println("Error al modificar el prestamo"+ e.getMessage());
        }
    }
}
