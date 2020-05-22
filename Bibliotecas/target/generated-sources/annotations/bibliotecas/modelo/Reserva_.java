package bibliotecas.modelo;

import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-22T19:13:22")
@StaticMetamodel(Reserva.class)
public class Reserva_ { 

    public static volatile SingularAttribute<Reserva, Libro> libro;
    public static volatile SingularAttribute<Reserva, Integer> estado;
    public static volatile SingularAttribute<Reserva, Date> fechaInicio;
    public static volatile SingularAttribute<Reserva, Date> fechaRecogida;
    public static volatile SingularAttribute<Reserva, Usuario> usuario;
    public static volatile SingularAttribute<Reserva, String> comentario;
    public static volatile SingularAttribute<Reserva, Date> fechaFin;
    public static volatile SingularAttribute<Reserva, Integer> idReserva;

}