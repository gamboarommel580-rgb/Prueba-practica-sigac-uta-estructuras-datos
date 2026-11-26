package pruebas;

import estructuras.ABB;
import estructuras.Cola;
import estructuras.EstructuraVaciaException;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import estructuras.Vector;
import servicios.ServicioRedSocial;
import servicios.SistemaSIGAC;

//CASOS LIMITE: vacios, un solo elemento, aislados, primero/ultimo/inexistente, peor caso del ABB
public class PruebasExtremos {

    private static final String T = "Extremo";

    public static void ejecutar(Verificador v) {
        v.igual("E-01", "M3", T, "recorridos de un ABB vacio", "[] [] [] 0", () -> {
            ABB<Integer, String> a = new ABB<Integer, String>();
            return a.preorden() + " " + a.inorden() + " " + a.posorden() + " " + a.altura();
        });
        v.igual("E-02", "M3", T, "ABB con un solo elemento: eliminarlo deja el arbol vacio", "true true 0", () -> {
            ABB<Integer, String> a = new ABB<Integer, String>();
            a.insertar(1, "x");
            return a.eliminar(1) + " " + a.estaVacio() + " " + a.tamanio();
        });
        v.lanza("E-03", "M1", T, "eliminarEn(0) en lista vacia", EstructuraVaciaException.class,
                () -> new ListaEnlazada<Integer>().eliminarEn(0));
        v.lanza("E-04", "M1", T, "eliminarEn(0) en vector vacio", EstructuraVaciaException.class,
                () -> new Vector<Integer>().eliminarEn(0));
        v.lanza("E-05", "M2", T, "desapilar pila vacia", EstructuraVaciaException.class,
                () -> new Pila<String>().desapilar());
        v.lanza("E-06", "M2", T, "desencolar cola vacia", EstructuraVaciaException.class,
                () -> new Cola<String>().desencolar());
        v.lanza("E-07", "M2", T, "deshacer con historial vacio", EstructuraVaciaException.class,
                () -> SistemaSIGAC.conCasoIntegrador().deshacer());
        v.lanza("E-08", "M2", T, "deshacer 3 veces cuando solo hay 2 operaciones", EstructuraVaciaException.class, () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.atenderSolicitud();
            s.atenderSolicitud();
            s.deshacer();
            s.deshacer();
            s.deshacer();
        });
        v.lanza("E-09", "M2", T, "atender una cuarta solicitud con la cola vacia", EstructuraVaciaException.class, () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.atenderSolicitud();
            s.atenderSolicitud();
            s.atenderSolicitud();
            s.atenderSolicitud();
        });
        v.igual("E-10", "M4", T, "grafo sin aristas: BFS solo visita el origen", "[A] [B, C]", () -> {
            Grafo<String> g = new Grafo<String>(true);
            g.agregarVertice("A", "A");
            g.agregarVertice("B", "B");
            g.agregarVertice("C", "C");
            return g.bfs("A") + " " + g.noAlcanzables("A");
        });
        v.igual("E-11", "M4", T, "BFS y DFS desde PC4 aislado, y cantidad de no alcanzables", "[PC4] [PC4] 5", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.getLaboratorio().bfs("PC4") + " " + s.getLaboratorio().dfs("PC4") + " "
                    + s.getLaboratorio().noAlcanzables("PC4").tamanio();
        });
        v.igual("E-12", "M5", T, "usuario sin amigos no recibe recomendaciones", "[]", () -> {
            ServicioRedSocial r = SistemaSIGAC.conCasoIntegrador().getSocial();
            r.registrarUsuario("Solo");
            return r.recomendar("Solo");
        });
        v.igual("E-13", "M5", T, "usuario con amigos pero sin contactos de segundo grado", "[]", () -> {
            ServicioRedSocial r = new ServicioRedSocial();
            r.registrarUsuario("Ana");
            r.registrarUsuario("Beto");
            r.registrarAmistad("Ana", "Beto");
            return r.recomendar("Ana");
        });
        v.igual("E-14", "M1", T, "buscar el primero (50): secuencial 1 comparacion, ABB 1", "1 1", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.getEstudiantes().buscarSecuencial(50).getComparaciones() + " "
                    + s.getEstudiantes().buscarEnABB(50).getComparaciones();
        });
        v.igual("E-15", "M1", T, "buscar el ultimo registrado (80): secuencial 7, ABB 3", "7 3", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.getEstudiantes().buscarSecuencial(80).getComparaciones() + " "
                    + s.getEstudiantes().buscarEnABB(80).getComparaciones();
        });
        v.igual("E-16", "M1", T, "buscar inexistente (99): no encontrado, secuencial 7, ABB 3", "false 7 false 3", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.getEstudiantes().buscarSecuencial(99).encontrado() + " "
                    + s.getEstudiantes().buscarSecuencial(99).getComparaciones() + " "
                    + s.getEstudiantes().buscarEnABB(99).encontrado() + " "
                    + s.getEstudiantes().buscarEnABB(99).getComparaciones();
        });
        v.igual("E-17", "M3", T, "insertar 1..5 ordenado: altura 5 y buscar 5 cuesta 5 comparaciones", "5 5", () -> {
            ABB<Integer, String> a = new ABB<Integer, String>();
            for (int i = 1; i <= 5; i++) {
                a.insertar(i, "x");
            }
            a.buscar(5);
            return a.altura() + " " + a.getUltimasComparaciones();
        });
        v.lanza("E-18", "M1", T, "eliminar despues de vaciar el registro de estudiantes", EstructuraVaciaException.class, () -> {
            SistemaSIGAC s = new SistemaSIGAC();
            s.registrarEstudiante(1, "Uno", "SW");
            s.eliminarEstudiante(1);
            s.eliminarEstudiante(1);
        });
        v.igual("E-19", "M3", T, "ABB con un nodo: preorden, inorden y posorden coinciden", "[7] [7] [7]", () -> {
            ABB<Integer, String> a = new ABB<Integer, String>();
            a.insertar(7, "x");
            return a.preorden() + " " + a.inorden() + " " + a.posorden();
        });
        v.igual("E-20", "M4", T, "eliminar el unico vertice de un grafo", "true true", () -> {
            Grafo<String> g = new Grafo<String>(true);
            g.agregarVertice("A", "A");
            return g.eliminarVertice("A") + " " + g.estaVacio();
        });
        v.igual("E-21", "M1", T, "lista con un elemento: eliminarlo actualiza cabeza y cola", "true [8] 8 8", () -> {
            ListaEnlazada<Integer> l = new ListaEnlazada<Integer>();
            l.agregarAlFinal(5);
            l.eliminarEn(0);
            boolean vacia = l.estaVacia();
            l.agregarAlFinal(8);
            return vacia + " " + l + " " + l.primero() + " " + l.ultimo();
        });
    }
}
