/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.LibroFacadeLocal;
import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Reserva;
import bibliotecas.modelo.Usuario;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import bibliotecas.EJB.ReservaFacadeLocal;
import bibliotecas.EJB.UsuarioFacadeLocal;
import java.io.IOException;
import javax.faces.application.FacesMessage;

/**
 *
 * @author david
 */
@Named
@ViewScoped
public class ReservaControl implements Serializable {
    
    private Reserva reserva;
    private Usuario usuario;
    private Libro libro;
    private Calendar c;
    private Date d;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private final int dias = 2; //Dias por defecto que estará activa la reserva
    @EJB
    ReservaFacadeLocal reservaEJB;
    @EJB
    LibroFacadeLocal libroEJB;
    @EJB
    UsuarioFacadeLocal usuarioEJB;
    
    List <Reserva> listaReservas;
    List <Usuario> listaUsuarios;
    List <Libro> listaLibros;
    
    @PostConstruct 
    public void reservaEspacio() {
        c=Calendar.getInstance();
        d=new Date();
        reserva = new Reserva();
        libro = new Libro();
        usuario = new Usuario();
        listaReservas = reservaEJB.findAll();
        listaUsuarios = usuarioEJB.findAll();
        listaLibros = libroEJB.findAll();
    }
    public void cancelaReserva (int idReserva) {
        reserva = getReserva(idReserva);
        libro = reserva.getLibro();
        //CANCELAMOS LA RESERVA
        reserva.setEstado(-1);
        reservaEJB.edit(reserva);
        //LIBERAMOS EL LIBRO
        libro.setEstado(0);
        libroEJB.edit(libro);
    }
    
    public void nuevaReserva (int idLibro) throws IOException {
        String context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();       
        System.out.println("Creando reserva");
        c=Calendar.getInstance();
        //utilizamos el usuario de la sesión
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        libro = libroEJB.find(idLibro);
        if (libro.getEstado()==0) {
            libro.setEstado(1);
            libroEJB.edit(libro);
            
            reserva.setLibro(libro);
            reserva.setUsuario(usuario);
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR)-1900);
            System.out.println(d.toString());
            System.out.println("-----------------------------------------------------------------");
            reserva.setFechaInicio(d); //Fecha inicio de la reserva
            c.add(Calendar.DAY_OF_MONTH, 3);
            d = new Date();
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR)-1900);
            System.out.println(d.toString());
            reserva.setFechaFin(d); //Fecha final reserva
            reserva.setEstado(0); //Estado activo
            try {
                reservaEJB.create(reserva);
                System.out.println("Reserva añadida a la base de datos");
                try {
                FacesContext.getCurrentInstance().getExternalContext()
                .redirect(context+"/faces/private/user/reservas/info.xhtml?id=" + getNuevaReserva().getIdReserva());            
                } catch (IOException e) {
                    System.out.println("Error al redireccionar" + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error al crear una nueva reserva "+ e.getMessage());
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/faces/private/user/genericError.xhtml");
            }
        } else {
            addMessage("El libro ya está alquilado!!");
            System.out.println("No se puede reservar un libro reservado/alquilado");
        }

    }

    public void addMessage(String summary) {
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aviso:", "El libro seleccionado no está disponible"));
    }
    
    public String getFormatDate (Date d) {
        return df.format(d);
    }
    
    public void insertar () {
        if (reserva.getUsuario()==null) {
        usuario = usuarioEJB.find(usuario.getIdUsuario());
        reserva.setUsuario(usuario);
        } 
        if (reserva.getLibro() == null) {
        libro = libroEJB.find(libro.getIdLibro());
        reserva.setLibro(libro);
        }
        try {
            reservaEJB.create(reserva);
        } catch (Exception e) {
            System.out.println("Error al crear la reserva " + e.getMessage());
        }
    }
    
    public void editar(){
        
    }
    public void eliminar() {
        
    }

    public Reserva getReserva() {
        return reserva;
    }
    public Reserva getNuevaReserva() {
        listaReservas=reservaEJB.findAll();

        return listaReservas.get(listaReservas.size()-1);
    }
    public Reserva getReserva(int id) {
        return reservaEJB.find(id);
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public ReservaFacadeLocal getReservaEJB() {
        return reservaEJB;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
    public void setReserva (int id) {
        this.reserva=reservaEJB.find(id);
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setReservaEJB(ReservaFacadeLocal reservaEJB) {
        this.reservaEJB = reservaEJB;
    }

    public void setListaReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }
    
    
    
}
