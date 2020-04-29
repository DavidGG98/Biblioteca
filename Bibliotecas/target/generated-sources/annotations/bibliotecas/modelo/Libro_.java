package bibliotecas.modelo;

import bibliotecas.modelo.Autor;
import bibliotecas.modelo.Biblioteca;
import bibliotecas.modelo.Editorial;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-28T17:25:04")
@StaticMetamodel(Libro.class)
public class Libro_ { 

    public static volatile SingularAttribute<Libro, Editorial> editorial;
    public static volatile SingularAttribute<Libro, Integer> tiempoPrestamo;
    public static volatile SingularAttribute<Libro, Integer> idLibro;
    public static volatile SingularAttribute<Libro, Biblioteca> biblioteca;
    public static volatile SingularAttribute<Libro, String> genero;
    public static volatile SingularAttribute<Libro, String> titulo;
    public static volatile SingularAttribute<Libro, String> imagen;
    public static volatile SingularAttribute<Libro, String> resumen;
    public static volatile SingularAttribute<Libro, String> idioma;
    public static volatile SingularAttribute<Libro, Autor> autor;

}