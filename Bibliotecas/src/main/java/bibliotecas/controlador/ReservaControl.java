/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecas.controlador;

import bibliotecas.EJB.LibroFacadeLocal;
import bibliotecas.EJB.PrestamoFacadeLocal;
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
import bibliotecas.modelo.Prestamo;
import bibliotecas.modelo.Trabajador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @EJB
    PrestamoFacadeLocal prestamoEJB;

    List<Reserva> listaReservas;
    List<Usuario> listaUsuarios;
    List<Libro> listaLibros;
    String context;

    @PostConstruct
    public void reservaEspacio() {
        c = Calendar.getInstance();
        d = new Date();
        reserva = new Reserva();
        libro = new Libro();
        usuario = new Usuario();
        listaReservas = reservaEJB.findAll();
        listaUsuarios = usuarioEJB.findAll();
        listaLibros = libroEJB.findAll();
        context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
    }

    public void cancelaReserva(int id, int i) { //i = 0 => Admin i=1 => User
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");

        System.out.println("Cancelando reserva " + id);
        Reserva r = reservaEJB.find(id);
        if (t.getBiblioteca().getIdBiblioteca() == r.getLibro().getBiblioteca().getIdBiblioteca()) {

            if (r.getEstado() == 0) {
                r.setEstado(-1); //Estado cancelado
                Libro l = r.getLibro();
                l.setEstado(0); //estado libre
                try {
                    libroEJB.edit(l);
                    try {
                        reservaEJB.edit(r);
                        if (i == 1) {
                            FacesContext.getCurrentInstance().getExternalContext()
                                    .redirect(context + "/faces/private/user/reservas/lista.xhtml");
                        } else if (i == 0) {
                            FacesContext.getCurrentInstance().getExternalContext()
                                    .redirect(context + "/faces/private/worker/vistaReservas/listaReservas.xhtml");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al editar la reserva");
                    }
                } catch (Exception e) {
                    System.out.println("Error al liberar el libro");
                }

            } else {
                addMessage("La reserva ya ha sido recogida o cancelada");
            }
        } else {
            addMessage("No tienes privilegios para editar esta reserva: No perteneces a la biblioteca");
        }
    }

    public void nuevaReserva(int idLibro) throws IOException {

        System.out.println("Creando reserva");
        c = Calendar.getInstance();
        //utilizamos el usuario de la sesión
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        libro = libroEJB.find(idLibro);
        if (libro.getEstado() == 0) {
            reserva.setLibro(libro);
            reserva.setUsuario(usuario);
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR) - 1900);

            System.out.println(d.toString());
            System.out.println("-----------------------------------------------------------------");
            reserva.setFechaInicio(d); //Fecha inicio de la reserva
            c.add(Calendar.DAY_OF_MONTH, 3);
            d = new Date();
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR) - 1900);

            System.out.println(d.toString());
            reserva.setFechaFin(d); //Fecha final reserva
            reserva.setEstado(0); //Estado activo
            try {
                reservaEJB.create(reserva);
                System.out.println("Reserva añadida a la base de datos");

                libro.setEstado(1);
                libroEJB.edit(libro);
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(context + "/faces/private/user/reservas/info.xhtml?id=" + getNuevaReserva().getIdReserva());
                } catch (IOException e) {
                    System.out.println("Error al redireccionar" + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error al crear una nueva reserva " + e.getMessage());
                FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/user/genericError.xhtml");

            }
        } else {
            addMessage("El libro ya está alquilado!!");
            System.out.println("No se puede reservar un libro reservado/alquilado");
        }

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getFormatDate(Date d) {
        if (d != null) {
            return df.format(d);
        } else {
            return "";
        }
    }

    public void insertar() {
        if (reserva.getUsuario() == null) {
            usuario = usuarioEJB.find(usuario.getIdUsuario());
            reserva.setUsuario(usuario);
        }
        if (reserva.getLibro() == null) {
            libro = libroEJB.find(libro.getIdLibro());
            libro.setEstado(1);
            libroEJB.edit(libro);
            reserva.setLibro(libro);
        }
        try {
            reservaEJB.create(reserva);
        } catch (Exception e) {
            System.out.println("Error al crear la reserva " + e.getMessage());
        }
    }

    public void editar() {

    }

    public void eliminar(int id) {

        reserva = reservaEJB.find(id);
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        if (t.getBiblioteca().getIdBiblioteca() == reserva.getLibro().getBiblioteca().getIdBiblioteca()) {
            libro = reserva.getLibro();
            libro.setEstado(0);
            libroEJB.edit(libro);

            reservaEJB.remove(reserva);
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(context + "/faces/private/worker/vistaReservas/listaReserva.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ReservaControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            addMessage("No se puede eliminar la reserva. No perteneces a la misma biblitoeca");
        }
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Reserva getNuevaReserva() {
        listaReservas = reservaEJB.findAll();

        return listaReservas.get(listaReservas.size() - 1);
    }

    public Reserva getReserva(int id) {
        System.out.println("recuperando la reserva con id = " + id);

        reserva = reservaEJB.find(id);
        return reserva;
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

    public void setReserva(int id) {
        this.reserva = reservaEJB.find(id);
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

    public void caducarReservas() {
        for (Reserva r : listaReservas) {
            if (r.getFechaFin().before(new Date()) && r.getEstado() == 0) {
                System.out.println("La reserva " + r.getIdReserva() + " con fecha de caducidad"
                        + r.getFechaFin().toString() + " ha caducado " + new Date().toString());
                r.setEstado(-1);
                reservaEJB.edit(r);
            }
        }
        listaReservas = reservaEJB.findAll();
    }

    public List<Reserva> getReservasUsuario() {
        List<Reserva> lista = new ArrayList();
        Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        int id = u.getIdUsuario();
        for (Reserva r : listaReservas) {
            if (r.getUsuario().getIdUsuario() == id) {
                lista.add(r);
            }
        }
        return lista;
    }

    //Muestra los prestamos relacionados con la biblioteca del trabajador
    public List<Reserva> getReservasBiblioteca() {
        List<Reserva> lista = new ArrayList();
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        int id = t.getBiblioteca().getIdBiblioteca();
        for (Reserva r : listaReservas) {
            if (r.getLibro().getBiblioteca().getIdBiblioteca() == id) {
                lista.add(r);
            }
        }
        return lista;
    }

    public void nuevoPrestamo(int id) {
        System.out.println("Creando un prestamo a partir de la reserva nº" + id);
        Reserva r = reservaEJB.find(id);
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        if (t.getBiblioteca().getIdBiblioteca() == r.getLibro().getBiblioteca().getIdBiblioteca()) {
            if (r.getEstado() == 0) {

                Prestamo p = new Prestamo();
                p.setLibro(r.getLibro());
                p.setUsuario(r.getUsuario());
                p.setEstado(0);
                d = new Date();
                p.setFechaInicio(d);
                c.add(Calendar.DAY_OF_MONTH, libro.getTiempoPrestamo());
                d.setDate(c.get(Calendar.DAY_OF_MONTH));
                d.setMonth(c.get(Calendar.MONTH));
                d.setYear(c.get(Calendar.YEAR) - 1900);
                p.setFechaFin(d); //Fecha final reserva
                try {
                    //p.setIdPrestamo(prestamoEJB.getLastId()+1);
                    System.out.println(p.toString());
                    prestamoEJB.create(p);
                    try {
                        r.setEstado(1);
                        r.setFechaRecogida(new Date());
                        reservaEJB.edit(r);
                    } catch (Exception e) {
                        System.out.println("Error e");
                    }
                } catch (Exception e) {
                    System.out.println("Error al crear el prestamo " + e.getMessage());
                }

                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(context + "/faces/private/worker/vistaReservas/listaReservas.xhtml?id=" + getNuevaReserva().getIdReserva());
                } catch (IOException ex) {
                    Logger.getLogger(ReservaControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                addMessage("La reserva ya ha sido recogida o cancelada");
            }
        } else {
            System.out.println("Biblioteca libro " + r.getLibro().getBiblioteca().getIdBiblioteca() + " biblioteca usuario " + t.getBiblioteca().getIdBiblioteca());
            addMessage("No tienes los privilegios para editar esta reserva: No perteneces a la biblioteca del libro");
        }
    }

}
