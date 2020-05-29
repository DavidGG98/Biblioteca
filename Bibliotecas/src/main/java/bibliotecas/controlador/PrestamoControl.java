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
public class PrestamoControl implements Serializable {

    private Prestamo prestamo;
    private Usuario usuario;
    private Libro libro;
    private Calendar c;
    private Date d;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private final int dias = 15; //Dias por defecto que estará activa el prestamo
    @EJB
    PrestamoFacadeLocal prestamoEJB;
    @EJB
    LibroFacadeLocal libroEJB;
    @EJB
    UsuarioFacadeLocal usuarioEJB;

    List<Prestamo> listaPrestamos;
    List<Usuario> listaUsuarios;
    List<Libro> listaLibros;

    String context;

    @PostConstruct
    public void reservaEspacio() {
        c = Calendar.getInstance();
        d = new Date();
        prestamo = new Prestamo();
        libro = new Libro();
        usuario = new Usuario();
        listaPrestamos = prestamoEJB.findAll();
        listaUsuarios = usuarioEJB.findAll();
        listaLibros = libroEJB.findAll();
        context = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
    }

    public void cancelaPrestamo(int idPrestamo) {
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        if (t.getBiblioteca().getIdBiblioteca() == prestamo.getLibro().getBiblioteca().getIdBiblioteca()) {
        try {
            prestamo = getPrestamo(idPrestamo);
            libro = prestamo.getLibro();
            //CANCELAMOS LA RESERVA
            prestamo.setEstado(-1);
            prestamoEJB.edit(prestamo);
            //LIBERAMOS EL LIBRO
            libro.setEstado(0);
            libroEJB.edit(libro);
        } catch (Exception e) {
            System.out.println("Error al cancelar el prestamo nº" + idPrestamo);
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/worker/vistaPrestamos/editarPrestamo.xhtml?id=" + prestamo.getIdPrestamo());
        } catch (IOException ex) {
            Logger.getLogger(PrestamoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
            addMessage("no tienes los permisos para interactuar con este préstamo");
        }
    }

    public void finPrestamo(int idPrestamo) {
        //Verificamos que el trabajador y el libro sean de la misma biblioteca
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        if (t.getBiblioteca().getIdBiblioteca() == prestamo.getLibro().getBiblioteca().getIdBiblioteca()) {
        try {
            prestamo = getPrestamo(idPrestamo);
            libro = prestamo.getLibro();
            libro.setEstado(0);
            libroEJB.edit(libro);
            prestamo.setEstado(1);
            prestamo.setFechaDevolucion(new Date());
            prestamoEJB.edit(prestamo);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/worker/vistaPrestamos/editarPrestamo.xhtml?id=" + prestamo.getIdPrestamo());

        } catch (Exception e) {
            System.out.println("Error al finalizar el prestamo " + e.getMessage());
        }
        } else {
            addMessage("No tienes los permisos para interactuar con este prestamo");
        }
    }

    public void nuevoPrestamo(int idLibro) throws IOException {
        System.out.println("Creando reserva");
        c = Calendar.getInstance();
        //utilizamos el usuario de la sesión
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        libro = libroEJB.find(idLibro);
        if (libro.getEstado() == 0) {
            libro.setEstado(1);
            libroEJB.edit(libro);

            prestamo.setLibro(libro);
            prestamo.setUsuario(usuario);
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR) - 1900);
            System.out.println(d.toString());
            System.out.println("-----------------------------------------------------------------");
            prestamo.setFechaInicio(d); //Fecha inicio de la reserva
            c.add(Calendar.DAY_OF_MONTH, libro.getTiempoPrestamo());
            d = new Date();
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR) - 1900);
            System.out.println(d.toString());
            prestamo.setFechaFin(d); //Fecha final reserva
            prestamo.setEstado(0); //Estado activo
            try {
                prestamoEJB.create(prestamo);
                System.out.println("Reserva añadida a la base de datos");
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(context + "/faces/private/user/prestamos/listaprestamos.xhtml?id=" + getNuevoPrestamo().getIdPrestamo());
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

    

    //Añade un mensaje al growl de la vista
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addComent() {
        try {
            prestamoEJB.edit(prestamo);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/worker/vistaPrestamos/editarPrestamo.xhtml?id=" + prestamo.getIdPrestamo());
            addMessage("Comentario guardado con éxito");
        } catch (Exception e) {
            System.out.println("Error guardar el comentario del prestamo " + e.getMessage());
            addMessage("Error guardar el comentario");

        }
    }

    //Metodo que amplia la fecha de devolución del prestamo por 15 dias
    public void amplia() {
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        if (t.getBiblioteca().getIdBiblioteca() == prestamo.getLibro().getBiblioteca().getIdBiblioteca()) {
            c.setTime(prestamo.getFechaFin());
            c.add(Calendar.DAY_OF_YEAR, 15); //Ampliamos el prestamo por 15 dias
            Date d = new Date();
            d.setDate(c.get(Calendar.DAY_OF_MONTH));
            d.setMonth(c.get(Calendar.MONTH));
            d.setYear(c.get(Calendar.YEAR) - 1900);
            prestamo.setFechaFin(d);
            try {
                prestamoEJB.edit(prestamo);
                FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/worker/vistaPrestamos/editarPrestamo.xhtml?id=" + prestamo.getIdPrestamo());
                addMessage("Fecha ampliada con éxito");

            } catch (Exception e) {
                System.out.println("Error al ampliar la fecha de devolucion del prestamo " + e.getMessage());
                addMessage("Error al ampliar la fecha de devolucion");

            }
        } else {
            //EL libro no es de la misma biblioteca que el trabajador
            addMessage("No tienes privilegios para modificar este prestamo.");
        }
    }

    public String getFormatDate(Date d) {
        if (d != null) {
            return df.format(d);
        } else {
            return "";
        }
    }

    public List<Libro> getLibrosLibres() {
        List<Libro> lista = new ArrayList();
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        int id = t.getBiblioteca().getIdBiblioteca();
        for (Libro l : listaLibros) {
            if (l.getBiblioteca().getIdBiblioteca()==id) {
                if (l.getEstado() == 0) {
                    lista.add(l);
                }
            }
        }
        return lista;
    }

    public void insertar() {
        for (Libro l : listaLibros) {
            if (l.getIdLibro() == libro.getIdLibro()) {
                libro = l;
                break;
            }
        }
        prestamo.setLibro(libro);
        for (Usuario u : listaUsuarios) {
            if (u.getIdUsuario() == usuario.getIdUsuario()) {
                usuario = u;
                break;
            }
        }
        int tiempo = libro.getTiempoPrestamo();
        if (tiempo == 0) {
            tiempo = 30;
        }
        prestamo.setUsuario(usuario);
        d.setDate(c.get(Calendar.DAY_OF_MONTH));
        d.setMonth(c.get(Calendar.MONTH));
        d.setYear(c.get(Calendar.YEAR) - 1900);
        System.out.println(d.toString());
        System.out.println("-----------------------------------------------------------------");
        prestamo.setFechaInicio(d); //Fecha inicio de la reserva
        c.add(Calendar.DAY_OF_MONTH, tiempo);
        d = new Date();
        d.setDate(c.get(Calendar.DAY_OF_MONTH));
        d.setMonth(c.get(Calendar.MONTH));
        d.setYear(c.get(Calendar.YEAR) - 1900);
        System.out.println(d.toString());
        prestamo.setFechaFin(d); //Fecha final reserva
        prestamo.setEstado(0); //Estado activo
        try {
            prestamoEJB.create(prestamo);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/worker/vistaPrestamos/listaPrestamos.xhtml");
        } catch (Exception e) {
            System.out.println("Error al crear la reserva " + e.getMessage());
        }
    }

    public void editar() {

    }

    public void eliminar(int id) {
        prestamo=prestamoEJB.find(id);
        libro = prestamo.getLibro();
        libro.setEstado(0);
        libroEJB.edit(libro);
        try {
            prestamoEJB.remove(prestamo);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/faces/private/worker/vistaPrestamos/listaPrestamos.xhtml");
        } catch (Exception e) {
            System.out.println("Error al eliminar el prestamo " + e.getMessage());
        }
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public Prestamo getNuevoPrestamo() {
        listaPrestamos = prestamoEJB.findAll();

        return listaPrestamos.get(listaPrestamos.size() - 1);
    }

    public Prestamo getPrestamo(int id) {
        prestamo = prestamoEJB.find(id);
        return prestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public List<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public void setPrestamo(int id) {
        this.prestamo = prestamoEJB.find(id);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setListaPrestamos(List<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void setListaLibros(List<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    public Date getD() {
        return d;
    }

    public int getDias() {
        return dias;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public List<Prestamo> getPrestamosUsuario() {
        List<Prestamo> lista = new ArrayList();
        Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        int id = u.getIdUsuario();
        for (Prestamo r : listaPrestamos) {
            if (r.getUsuario().getIdUsuario() == id) {
                lista.add(r);
            }
        }
        return lista;
    }

    //Muestra los prestamos relacionados con la biblioteca del trabajador
    public List<Prestamo> getPrestamosBiblioteca() {
        List<Prestamo> lista = new ArrayList();
        Trabajador t = (Trabajador) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trabajador");
        int id = t.getBiblioteca().getIdBiblioteca();
        for (Prestamo p : listaPrestamos) {
            if (p.getLibro().getBiblioteca().getIdBiblioteca() == id) {
                lista.add(p);
            }
        }
        return lista;
    }
    

}
