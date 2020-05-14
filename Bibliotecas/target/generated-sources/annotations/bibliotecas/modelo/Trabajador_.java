package bibliotecas.modelo;

import bibliotecas.modelo.Editorial;
import bibliotecas.modelo.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-14T17:45:09")
@StaticMetamodel(Trabajador.class)
public class Trabajador_ { 

    public static volatile SingularAttribute<Trabajador, String> apellidos;
    public static volatile SingularAttribute<Trabajador, Editorial> editorial;
    public static volatile SingularAttribute<Trabajador, Integer> idTrabajador;
    public static volatile SingularAttribute<Trabajador, String> contrasena;
    public static volatile SingularAttribute<Trabajador, String> nombre;
    public static volatile SingularAttribute<Trabajador, String> dni;
    public static volatile SingularAttribute<Trabajador, Rol> rol;

}