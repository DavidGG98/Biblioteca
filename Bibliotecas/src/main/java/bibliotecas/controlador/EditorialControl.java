/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.AutorFacadeLocal;
import bibliotecas.EJB.EditorialFacadeLocal;
import bibliotecas.modelo.Autor;
import bibliotecas.modelo.Editorial;
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

public class EditorialControl implements Serializable{
    
    private Editorial editorial;
    
    @EJB
    EditorialFacadeLocal editorialEJB;
    List <Editorial> listaEditoriales;
 
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        editorial=new Editorial(); //reserva la memoria
        listaEditoriales = editorialEJB.findAll();

    }

    public Editorial getEditorial() {
        return editorial;
    }

    public EditorialFacadeLocal getEditorialEJB() {
        return editorialEJB;
    }

    public List<Editorial> getListaEditoriales() {
        return listaEditoriales;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public void setEditorialEJB(EditorialFacadeLocal editorialEJB) {
        this.editorialEJB = editorialEJB;
    }

    public void setListaEditoriales(List<Editorial> listaEditoriales) {
        this.listaEditoriales = listaEditoriales;
    }
  public void insertar(){
        try{
          editorialEJB.create(editorial);
            System.out.println("Anadiendo editorial...");
        } catch (Exception e) {
            System.out.println("Error al anadir el editorial "+ e.getMessage());
        }
    }
    public void eliminar(int id){
        try{
            System.out.println("");
            for(Editorial t:listaEditoriales){
                if(t.getIdEditorial()== id){
                    editorial=t;
                    break;
                }
            editorialEJB.remove(editorial);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el editorial "+ e.getMessage());
        }
    }
    public void modificar(int id) {
        try {
            String n=editorial.getNombre();
            for (Editorial c:listaEditoriales) {
                if(c.getIdEditorial()== id) {
                    editorial=c; //Recuperamos el objeto al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            editorial.setNombre(n); //Actualizamos el nombre que hemos puesto y lo guardamos
            editorialEJB.edit(editorial);
        } catch (Exception e) {
            System.out.println("Error al modificar el editorial"+ e.getMessage());
        }
    }  
}
