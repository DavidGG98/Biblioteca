/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.TrabajadorFacadeLocal;
import bibliotecas.modelo.Biblioteca;
import bibliotecas.modelo.Trabajador;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author david, alex
 */
@Named
@ViewScoped
public class TrabajadorControl implements Serializable{
    
    private Trabajador trabajador;
    
    @EJB
    TrabajadorFacadeLocal trabajadorEJB;
    List <Trabajador> listaTrabajadores;
    private Biblioteca biblioteca;
    
    @PostConstruct
    public void reserva(){
        trabajador= new Trabajador();
        listaTrabajadores= trabajadorEJB.findAll();
        
    }
    public void insertar(){
        try{
          trabajadorEJB.create(trabajador);
            System.out.println("Anadiendo trabajador...");
        } catch (Exception e) {
            System.out.println("Error al anadir el Trabajador "+ e.getMessage());
        }
    }
    public void eliminar(){
        try{
            System.out.println("");
            for(Trabajador t:listaTrabajadores){
                if(t.getIdTrabajador()==(trabajador.getIdTrabajador())){
                    trabajador=t;
                    break;
                }
            trabajadorEJB.remove(trabajador);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el trabajador "+ e.getMessage());
        }
    }
    public void modificar() {
        try {
            String n=trabajador.getNombre();
            for (Trabajador c:listaTrabajadores) {
                if(c.getIdTrabajador()== trabajador.getIdTrabajador()) {
                    trabajador=c; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            trabajador.setNombre(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            trabajadorEJB.edit(trabajador);
        } catch (Exception e) {
            System.out.println("Error al modificar el trabajador"+ e.getMessage());
        }
    }
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public TrabajadorFacadeLocal getTrabajadorEJB() {
        return trabajadorEJB;
    }

    public List<Trabajador> getListaTrabajadors() {
        return listaTrabajadores;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setTrabajadorEJB(TrabajadorFacadeLocal TrabajadorEJB) {
        this.trabajadorEJB = TrabajadorEJB;
    }

    public void setListaTrabajadors(List<Trabajador> listaTrabajadors) {
        this.listaTrabajadores = listaTrabajadors;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }


    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

}
