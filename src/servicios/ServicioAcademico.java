package servicios;

import estructuras.ArbolGeneral;
import estructuras.Condicion;
import estructuras.ElementoNoEncontradoException;
import estructuras.ListaEnlazada;
import modelo.Estudiante;
import modelo.TipoUnidad;
import modelo.UnidadAcademica;

//M3: JERARQUIA Universidad -> Facultad -> Carrera -> Estudiantes en un arbol general
public class ServicioAcademico {

    private final ArbolGeneral<UnidadAcademica> arbol = new ArbolGeneral<UnidadAcademica>();

    public ServicioAcademico(String codigoUniversidad, String nombreUniversidad) {
        arbol.crearRaiz(new UnidadAcademica(codigoUniversidad, nombreUniversidad, TipoUnidad.UNIVERSIDAD));
    }

    public void agregarFacultad(String codigo, String nombre) {
        validarCodigoDeUnidad(codigo);
        arbol.agregarHijo(arbol.getRaiz(), new UnidadAcademica(codigo, nombre, TipoUnidad.FACULTAD));
    }

    public void agregarCarrera(String codigoFacultad, String codigo, String nombre) {
        validarCodigoDeUnidad(codigo);
        ArbolGeneral.Nodo<UnidadAcademica> facultad = nodoDeTipo(codigoFacultad, TipoUnidad.FACULTAD);
        arbol.agregarHijo(facultad, new UnidadAcademica(codigo, nombre, TipoUnidad.CARRERA));
    }

    //los codigos "E" + numero quedan reservados para los nodos de estudiantes (E50, E30...)
    private void validarCodigoDeUnidad(String codigo) {
        if (codigo != null && codigo.trim().matches("[Ee][0-9]+")) {
            throw new IllegalArgumentException("El codigo " + codigo.trim()
                    + " esta reservado para estudiantes (E + matricula)");
        }
    }

    public boolean existeCarrera(String codigo) {
        ArbolGeneral.Nodo<UnidadAcademica> n = buscar(codigo);
        return n != null && n.getDato().getTipo() == TipoUnidad.CARRERA;
    }

    public void vincularEstudiante(Estudiante e) {
        vincularEstudianteEn(e, -1);
    }

    //posicion = lugar entre los hijos de la carrera (-1 = al final)
    public void vincularEstudianteEn(Estudiante e, int posicion) {
        if (e == null) {
            throw new IllegalArgumentException("El estudiante no puede ser null");
        }
        ArbolGeneral.Nodo<UnidadAcademica> carrera = nodoDeTipo(e.getCodigoCarrera(), TipoUnidad.CARRERA);
        arbol.agregarHijoEn(carrera, new UnidadAcademica(codigoEstudiante(e.getMatricula()),
                e.getNombres(), TipoUnidad.ESTUDIANTE), posicion);
    }

    //solo borra nodos de tipo ESTUDIANTE, nunca una carrera o facultad
    public boolean desvincularEstudiante(int matricula) {
        final String codigo = codigoEstudiante(matricula);
        return arbol.eliminar(new Condicion<UnidadAcademica>() {
            public boolean cumple(UnidadAcademica u) {
                return u.getTipo() == TipoUnidad.ESTUDIANTE && u.getCodigo().equals(codigo);
            }
        });
    }

    //posicion del estudiante entre los hijos de su carrera, -1 si no esta
    public int posicionEstudiante(int matricula) {
        final String codigo = codigoEstudiante(matricula);
        ArbolGeneral.Nodo<UnidadAcademica> n = arbol.buscar(new Condicion<UnidadAcademica>() {
            public boolean cumple(UnidadAcademica u) {
                return u.getTipo() == TipoUnidad.ESTUDIANTE && u.getCodigo().equals(codigo);
            }
        });
        if (n == null) {
            return -1;
        }
        final ArbolGeneral.Nodo<UnidadAcademica> objetivo = n;
        return n.getPadre().getHijos().indiceDe(new Condicion<ArbolGeneral.Nodo<UnidadAcademica>>() {
            public boolean cumple(ArbolGeneral.Nodo<UnidadAcademica> x) {
                return x == objetivo;
            }
        });
    }

    public ListaEnlazada<String> estudiantesDeCarrera(String codigoCarrera) {
        ArbolGeneral.Nodo<UnidadAcademica> carrera = nodoDeTipo(codigoCarrera, TipoUnidad.CARRERA);
        ListaEnlazada<String> salida = new ListaEnlazada<String>();
        for (ArbolGeneral.Nodo<UnidadAcademica> hijo : carrera.getHijos()) {
            salida.agregarAlFinal(hijo.getDato().getCodigo());
        }
        return salida;
    }

    //cuenta unidades de un tipo recorriendo el arbol en preorden
    public int contar(TipoUnidad tipo) {
        int total = 0;
        for (UnidadAcademica u : arbol.preorden()) {
            if (u.getTipo() == tipo) {
                total++;
            }
        }
        return total;
    }

    public ListaEnlazada<String> codigosPreorden() {
        ListaEnlazada<String> salida = new ListaEnlazada<String>();
        for (UnidadAcademica u : arbol.preorden()) {
            salida.agregarAlFinal(u.getCodigo());
        }
        return salida;
    }

    public String dibujar() {
        return arbol.dibujar();
    }

    public int altura() {
        return arbol.altura();
    }

    public static String codigoEstudiante(int matricula) {
        return "E" + matricula;
    }

    private ArbolGeneral.Nodo<UnidadAcademica> buscar(final String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede ser null ni vacio");
        }
        return arbol.buscar(new Condicion<UnidadAcademica>() {
            public boolean cumple(UnidadAcademica u) {
                return u.getCodigo().equals(codigo.trim());
            }
        });
    }

    private ArbolGeneral.Nodo<UnidadAcademica> nodoDeTipo(String codigo, TipoUnidad tipo) {
        ArbolGeneral.Nodo<UnidadAcademica> n = buscar(codigo);
        if (n == null || n.getDato().getTipo() != tipo) {
            throw new ElementoNoEncontradoException("No existe la " + tipo.name().toLowerCase() + " " + codigo);
        }
        return n;
    }
}
