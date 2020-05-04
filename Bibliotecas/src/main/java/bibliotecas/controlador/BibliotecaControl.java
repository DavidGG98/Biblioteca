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
    private int sele;
    public int getsele(){
        return sele;
    }
    public Biblioteca getBiblioteca(int id){
        sele=id;
        try{
            System.out.println(id);
            System.out.println("Seleccionado "+ id);
            for(Biblioteca b:listaBiblioteca){
                if(b.getIdBiblioteca()==id){
                    System.out.println("Biblioteca "+ b.getNombre()+" tiene el mismo id");
                    biblioteca=b;
                    break;
                }
            }
            return biblioteca;
        } catch (Exception e){
            System.out.println("Error al seleccionar la biblioteca "+ e.getMessage());
        }
        return null;
    }
    
    
    
    
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
    public void eliminar(int id){
        try{
            Biblioteca aux = new Biblioteca();
            System.out.println(id);
            System.out.println("borrando ");
            for(Biblioteca b:listaBiblioteca){
                if(b.getIdBiblioteca()==id){
                    aux=b;
                    break;
                }
            }
            bibliotecaEJB.remove(aux);
        } catch (Exception e){
            System.out.println("Error al eliminar la biblioteca "+ e.getMessage());
        }
    }
    
    
    
    public void modificar(int id){
        try {
            System.out.println("modifico "+ id+","+biblioteca.getNombre());
            System.out.println("modifico "+ biblioteca.getIdBiblioteca()+","+biblioteca.getNombre());
            Biblioteca aux = new Biblioteca();
            
            for (Biblioteca bbb:listaBiblioteca){
                if (bbb.getIdBiblioteca()==id) {
                    aux=bbb;
                    break;
                }
            }
            aux.setIdBiblioteca(biblioteca.getIdBiblioteca());
            aux.setNombre(biblioteca.getNombre());
            aux.setDireccion(biblioteca.getDireccion());
            aux.setLocalidad(biblioteca.getLocalidad());
            
            bibliotecaEJB.edit(biblioteca);
        } catch (Exception e) {
            System.out.println("Error al modificar la biblioteca"+ e.getMessage());
        }
    }
    
    
    
}
