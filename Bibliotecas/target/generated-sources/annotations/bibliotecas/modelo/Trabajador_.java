package bibliotecas.modelo;

import bibliotecas.modelo.Biblioteca;
import bibliotecas.modelo.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-29T19:56:44")
@StaticMetamodel(Trabajador.class)
public class Trabajador_ { 

    public static volatile SingularAttribute<Trabajador, String> apellidos;
    public static volatile SingularAttribute<Trabajador, Biblioteca> biblioteca;
    public static volatile SingularAttribute<Trabajador, Integer> idTrabajador;
    public static volatile SingularAttribute<Trabajador, String> contrasena;
    public static volatile SingularAttribute<Trabajador, String> nombre;
    public static volatile SingularAttribute<Trabajador, String> dni;
    public static volatile SingularAttribute<Trabajador, Rol> rol;

}