/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.BibliotecaFacadeLocal;
import bibliotecas.modelo.Biblioteca;
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

public class BibliotecaControl implements Serializable{

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public BibliotecaFacadeLocal getBibliotecaEJB() {
        return bibliotecaEJB;
    }

    public void setBibliotecaEJB(BibliotecaFacadeLocal bibliotecaEJB) {
        this.bibliotecaEJB = bibliotecaEJB;
    }

    public List<Biblioteca> getListaBiblioteca() {
        return listaBiblioteca;
    }

    public void setListaBiblioteca(List<Biblioteca> listaBiblioteca) {
        this.listaBiblioteca = listaBiblioteca;
    }
    private Biblioteca biblioteca;
    
    @EJB
    BibliotecaFacadeLocal bibliotecaEJB;
    List <Biblioteca> listaBiblioteca;
    
    
    @PostConstruct
    public void reserva(){
        biblioteca=new Biblioteca();
        listaBiblioteca= bibliotecaEJB.findAll();
        
    }
    
    public void insertar(){
        try{
            bibliotecaEJB.create(biblioteca);
            System.out.println("Añadiendo biblioteca...");
        } catch (Exception e){
            System.out.println("Error al añadir la biblioteca "+ e.getMessage());
        }
    }
    public void eliminar(){
        try{
            System.out.println("");
            for(Biblioteca b:listaBiblioteca){
                if(b.getIdBiblioteca()==biblioteca.getIdBiblioteca()){
                    biblioteca=b;
                    break;
                }
            }
        } catch (Exception e){
            System.out.println("Error al eliminar la biblioteca "+ e.getMessage());
        }
    }
    public void modificar(){
        try {
            String nomnbre = biblioteca.getNombre();
            for (Biblioteca bbb:listaBiblioteca){
                if (bbb.getIdBiblioteca()==biblioteca.getIdBiblioteca()) {
                    biblioteca=bbb;
                    break;
                }
            }
            biblioteca.setNombre(nomnbre);
            bibliotecaEJB.edit(biblioteca);
        } catch (Exception e) {
            System.out.println("Error al modificar la biblioteca"+ e.getMessage());
        }
    }
}
