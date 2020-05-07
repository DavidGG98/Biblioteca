/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.LibroFacadeLocal;
import bibliotecas.EJB.PrestamoFacadeLocal;
import bibliotecas.EJB.UsuarioFacadeLocal;
import bibliotecas.modelo.Autor;
import bibliotecas.modelo.Editorial;
import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Prestamo;
import bibliotecas.modelo.Usuario;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class PrestamoControl implements Serializable{
    
    private Prestamo prestamo;
    private Calendar c;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    List <Prestamo> listaPrestamos;
    @EJB
    PrestamoFacadeLocal prestamoEJB;
    
    @PostConstruct //le mandamos ejecutarse antes, ya que el constructor debe estar vacio
    public void reserva() {
        c = Calendar.getInstance();
        prestamo=new Prestamo(); //reserva la memoria
        listaPrestamos = prestamoEJB.findAll();
    }
    
    public void nuevoPrestamo() { //Inserta en la base de datos
        //Comprobar que el libro esté libre primero
        if (prestamo.getLibro().getEstado()==0) {
            try {
                System.out.println("Creando un nuevo prestamo...");
                //Añadimos al prestamo el usuario actual en caso de que aun no exista
                if (prestamo.getUsuario() == null ) {
                    Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                    prestamo.setUsuario(u);
                }
                System.out.println("Prestamo para el usuario" + prestamo.getUsuario().getIdUsuario() 
                        + " del libro " + prestamo.getLibro().getTitulo());
                //Establecemos la fecha de inicio del prestamo en el momento actual
                c.setTime(new Date());
                prestamo.setFechaInicio(c.getTime());
                System.out.println("Fecha de inicio: " + dateFormat.format(c.getTime()));
                //Establecemos la fecha final del prestamo como fecha actual + numero de dias que el libro está 
                c.add(Calendar.DATE, prestamo.getLibro().getTiempoPrestamo());
                prestamo.setFechaFin(c.getTime());
                System.out.println("Fecha de fin de prestamo: " + dateFormat.format(c.getTime()));
                //Actualizamos el estado del libro a reservado
                prestamo.getLibro().setEstado(1);
                //Introducimos el libro
                prestamoEJB.create(prestamo);

                System.out.println("Insertando Prestamo...");
            } catch (Exception e) {
                System.out.println("Error al crear el prestamo " + e.getMessage());
            }
        } else {
            System.out.println("EL LIBRO SELECCIONADO YA HA SIDO RESERVADO");
        }
    }
    
    public void finPrestamo () {
        try {
            for (Prestamo p:listaPrestamos) {
                if(p.getIdPrestamo() == prestamo.getIdPrestamo()) {
                    prestamo=p; //Recuperamos la categoria al completo, no solo su id
                    break; //Sale del bucle
                }
            }
            //Añadimos la fecha de finalización
            c.setTime(new Date ()); 
            prestamo.setFechaDevolucion(c.getTime());
            //Realizamos la modificación en BD
            prestamoEJB.edit(prestamo);
            System.out.println("Prestamo finalizado con exito");
        } catch (Exception e) {
            System.out.println("Error al finalizar el prestamo "+ e.getMessage());
        }
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void setPrestamoEJB(PrestamoFacadeLocal prestamoEJB) {
        this.prestamoEJB = prestamoEJB;
    }

    public void setListaPrestamos(List<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }


}
