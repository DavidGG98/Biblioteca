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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author david
 */
@Named
@ViewScoped

public class EditorialControl implements Serializable {

    private Editorial editorial;

    @EJB
    EditorialFacadeLocal editorialEJB;
    List<Editorial> listaEditoriales;
    String context;
    public Editorial getEditorial(int id) {
        try {
            for (Editorial b : listaEditoriales) {
                if (b.getIdEditorial() == id) {                  
                    editorial = b;
                    break;
                }
            }
            return editorial;
        } catch (Exception e) {
            System.out.println("Error al seleccionar el Editorial " + e.getMessage());
        }
        return null;
    }

    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        editorial = new Editorial(); //reserva la memoria
        listaEditoriales = editorialEJB.findAll();
        context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
    }

    public void insertar() {
        try {
            editorialEJB.create(editorial);
            System.out.println("Anadiendo editorial...");
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaEditoriales/listaEditoriales.xhtml");
        } catch (Exception e) {
            System.out.println("Error al anadir el editorial " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            System.out.println("Borrando editorial " + id);
            for (Editorial t : listaEditoriales) {
                if (t.getIdEditorial() == id) {
                    editorial = t;
                    break;
                }
                
            }
            editorialEJB.remove(editorial);
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaEditoriales/listaEditoriales.xhtml");
        } catch (Exception e) {
            System.out.println("Error al eliminar el editorial " + e.getMessage());
        }
    }

    public void modificar(Editorial ed) {
        try {
            editorialEJB.edit(ed);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/worker/vistaEditoriales/listaEditoriales.xhtml");
        } catch (Exception e) {
            System.out.println("Error al modificar el editorial" + e.getMessage());
        }
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
}
