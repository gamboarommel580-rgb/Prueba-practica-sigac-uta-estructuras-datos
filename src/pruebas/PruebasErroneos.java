package pruebas;

import estructuras.ABB;
import estructuras.ElementoDuplicadoException;
import estructuras.ElementoNoEncontradoException;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import estructuras.Vector;
import servicios.SistemaSIGAC;

//CASOS ERRONEOS: duplicados, null, inexistentes, entradas invalidas y estructuras vacias
public class PruebasErroneos {

    private static final String T = "Erroneo";

    public static void ejecutar(Verificador v) {
        v.lanza("ER-01", "M1", T, "registrar matricula duplicada 50", ElementoDuplicadoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().registrarEstudiante(50, "Otro", "SW"));
        v.lanza("ER-02", "M1", T, "registrar matricula 0", IllegalArgumentException.class,
                () -> new SistemaSIGAC().registrarEstudiante(0, "Cero", "SW"));
        v.lanza("ER-03", "M1", T, "registrar matricula negativa -5", IllegalArgumentException.class,
                () -> new SistemaSIGAC().registrarEstudiante(-5, "Neg", "SW"));
        v.lanza("ER-04", "M1", T, "registrar con nombres null", IllegalArgumentException.class,
                () -> new SistemaSIGAC().registrarEstudiante(10, null, "SW"));
        v.lanza("ER-05", "M1/M3", T, "registrar en carrera inexistente XX", ElementoNoEncontradoException.class,
                () -> new SistemaSIGAC().registrarEstudiante(10, "Ana", "XX"));
        v.lanza("ER-06", "M1/M3", T, "registrar en FISEI (es facultad, no carrera)", ElementoNoEncontradoException.class,
                () -> new SistemaSIGAC().registrarEstudiante(10, "Ana", "FISEI"));
        v.lanza("ER-07", "M1", T, "eliminar matricula inexistente 99", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().eliminarEstudiante(99));
        v.lanza("ER-08", "M1", T, "actualizar matricula inexistente 99", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().actualizarEstudiante(99, "X", "SW"));
        v.lanza("ER-09", "M1/M2", T, "eliminar estudiante 50 con solicitud pendiente", IllegalStateException.class,
                () -> SistemaSIGAC.conCasoIntegrador().eliminarEstudiante(50));
        v.lanza("ER-10", "M2", T, "solicitud con matricula inexistente 99", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().registrarSolicitud(99, "PC1", "x"));
        v.lanza("ER-11", "M2/M4", T, "solicitud para equipo inexistente PC9", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().registrarSolicitud(50, "PC9", "x"));
        v.lanza("ER-12", "M2", T, "solicitud con motivo vacio", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().registrarSolicitud(50, "PC1", "  "));
        v.lanza("ER-13", "M4", T, "eliminar arista inexistente PC1->PC2", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().desconectarEquipos("PC1", "PC2"));
        v.lanza("ER-14", "M4", T, "conectar con vertice inexistente PC1->PC9", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC1", "PC9", 1));
        v.lanza("ER-15", "M4", T, "lazo PC1->PC1", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC1", "PC1", 1));
        v.lanza("ER-16", "M4", T, "peso negativo", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC2", "PC4", -3));
        v.lanza("ER-17", "M4", T, "peso 0", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC2", "PC4", 0));
        v.lanza("ER-18", "M4", T, "peso NaN", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC2", "PC4", Double.NaN));
        v.lanza("ER-19", "M4", T, "peso infinito", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC2", "PC4", Double.POSITIVE_INFINITY));
        v.lanza("ER-20", "M4", T, "conexion duplicada PC1->SW1", ElementoDuplicadoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().conectarEquipos("PC1", "SW1", 9));
        v.lanza("ER-21", "M4", T, "equipo duplicado PC1", ElementoDuplicadoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().agregarEquipo("PC1", "PC"));
        v.lanza("ER-22", "M4", T, "BFS desde vertice inexistente", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().getLaboratorio().bfs("PC9"));
        v.lanza("ER-23", "M4", T, "eliminar arista en grafo vacio", ElementoNoEncontradoException.class,
                () -> new Grafo<String>(true).eliminarArista("A", "B"));
        v.lanza("ER-24", "M4", T, "vertice con id vacio", IllegalArgumentException.class,
                () -> new Grafo<String>(true).agregarVertice(" ", "x"));
        v.lanza("ER-25", "M4", T, "eliminar equipo inexistente", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().eliminarEquipo("PC9"));
        v.lanza("ER-26", "M2/M4", T, "eliminar equipo PC1 con solicitud pendiente", IllegalStateException.class,
                () -> SistemaSIGAC.conCasoIntegrador().eliminarEquipo("PC1"));
        v.lanza("ER-27", "M5", T, "amistad duplicada Ana-Beto (en orden inverso)", ElementoDuplicadoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().getSocial().registrarAmistad("Beto", "Ana"));
        v.lanza("ER-28", "M5", T, "amistad consigo mismo", IllegalArgumentException.class,
                () -> SistemaSIGAC.conCasoIntegrador().getSocial().registrarAmistad("Ana", "Ana"));
        v.lanza("ER-29", "M5", T, "recomendar a usuario inexistente", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().getSocial().recomendar("Zoe"));
        v.lanza("ER-30", "M5", T, "usuario duplicado", ElementoDuplicadoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().getSocial().registrarUsuario("Ana"));
        v.lanza("ER-31", "M5", T, "eliminar amistad inexistente Ana-Elena", ElementoNoEncontradoException.class,
                () -> SistemaSIGAC.conCasoIntegrador().getSocial().eliminarAmistad("Ana", "Elena"));
        v.lanza("ER-32", "M1", T, "agregar null al vector", IllegalArgumentException.class,
                () -> new Vector<String>().agregar(null));
        v.lanza("ER-33", "M1", T, "agregar null a la lista", IllegalArgumentException.class,
                () -> new ListaEnlazada<String>().agregarAlFinal(null));
        v.lanza("ER-34", "M2", T, "apilar null", IllegalArgumentException.class,
                () -> new Pila<String>().apilar(null));
        v.lanza("ER-35", "M3", T, "insertar clave null en ABB", IllegalArgumentException.class,
                () -> new ABB<Integer, String>().insertar(null, "x"));
        v.lanza("ER-36", "M3", T, "agregar carrera a facultad inexistente", ElementoNoEncontradoException.class,
                () -> new SistemaSIGAC().getAcademico().agregarCarrera("FXX", "C1", "Carrera"));
        v.lanza("ER-37", "M3", T, "agregar facultad duplicada FISEI", ElementoDuplicadoException.class,
                () -> new SistemaSIGAC().getAcademico().agregarFacultad("FISEI", "Otra"));
        v.lanza("ER-38", "M1", T, "obtener posicion fuera de rango del vector", IndexOutOfBoundsException.class,
                () -> PruebasUnitarias.vectorCon(1, 2).obtener(5));
        v.lanza("ER-39", "M3", T, "agregar carrera con codigo reservado E90 (choca con el nodo del estudiante 90)",
                IllegalArgumentException.class, () -> new SistemaSIGAC().getAcademico().agregarCarrera("FISEI", "E90", "Trampa"));
        v.lanza("ER-40", "M3", T, "agregar hijo a un nodo que ya fue eliminado del arbol general", IllegalArgumentException.class, () -> {
            estructuras.ArbolGeneral<String> a = new estructuras.ArbolGeneral<String>();
            estructuras.ArbolGeneral.Nodo<String> r = a.crearRaiz("R");
            estructuras.ArbolGeneral.Nodo<String> b = a.agregarHijo(a.agregarHijo(r, "A"), "B");
            a.eliminar(x -> x.equals("A"));
            a.agregarHijo(b, "Z");
        });
        v.lanza("ER-41", "M2", T, "solicitud con matricula negativa directo en el servicio", IllegalArgumentException.class,
                () -> new servicios.ServicioAtencion().registrarSolicitud(-1, "PC1", "x"));
        v.igual("ER-42", "M3", T, "eliminar del ABB una clave inexistente no lo altera", "false 7 true", () -> {
            ABB<Integer, String> a = PruebasUnitarias.abbCaso();
            return a.eliminar(99) + " " + a.tamanio() + " " + a.esValido();
        });
        v.igual("ER-43", "M6", T, "un registro fallido (duplicado) no deja rastro en ningun modulo", "7 0 true", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            try {
                s.registrarEstudiante(40, "Repetido", "SW");
            } catch (ElementoDuplicadoException ok) {
                //se espera
            }
            return s.getEstudiantes().cantidad() + " " + s.getAtencion().getHistorial().tamanio() + " "
                    + s.estadoConsistente();
        });
        v.igual("ER-44", "M6", T, "un registro con carrera invalida no deja rastro", "7 true", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            try {
                s.registrarEstudiante(91, "X", "NOEXISTE");
            } catch (ElementoNoEncontradoException ok) {
                //se espera
            }
            return s.getEstudiantes().cantidad() + " " + s.estadoConsistente();
        });
    }
}
