package bibliotecas.modelo;

import bibliotecas.modelo.Libro;
import bibliotecas.modelo.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-24T22:40:45")
@StaticMetamodel(Prestamo.class)
public class Prestamo_ { 

    public static volatile SingularAttribute<Prestamo, Libro> libro;
    public static volatile SingularAttribute<Prestamo, Integer> estado;
    public static volatile SingularAttribute<Prestamo, Date> fechaDevolucion;
    public static volatile SingularAttribute<Prestamo, Date> fechaInicio;
    public static volatile SingularAttribute<Prestamo, Integer> idPrestamo;
    public static volatile SingularAttribute<Prestamo, Usuario> usuario;
    public static volatile SingularAttribute<Prestamo, String> comentario;
    public static volatile SingularAttribute<Prestamo, Date> fechaFin;

}