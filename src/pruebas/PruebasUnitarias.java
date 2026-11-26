package pruebas;

import estructuras.ABB;
import estructuras.ArbolGeneral;
import estructuras.Cola;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import estructuras.Vector;
import modelo.Conexion;
import servicios.ServicioAcademico;
import servicios.ServicioAtencion;
import servicios.ServicioEstudiantes;
import servicios.ServicioRedSocial;
import servicios.SistemaSIGAC;

//PRUEBAS UNITARIAS POR ESTRUCTURA Y POR MODULO (M1 a M5)
public class PruebasUnitarias {

    private static final String T = "Unitaria";

    public static void ejecutar(Verificador v) {
        vector(v);
        lista(v);
        pilaYCola(v);
        arbolGeneral(v);
        abb(v);
        grafo(v);
        modulos(v);
    }

    static Vector<Integer> vectorCon(int... datos) {
        Vector<Integer> x = new Vector<Integer>(2);
        for (int d : datos) {
            x.agregar(d);
        }
        return x;
    }

    static ListaEnlazada<Integer> listaCon(int... datos) {
        ListaEnlazada<Integer> x = new ListaEnlazada<Integer>();
        for (int d : datos) {
            x.agregarAlFinal(d);
        }
        return x;
    }

    static ABB<Integer, String> abbCaso() {
        ABB<Integer, String> a = new ABB<Integer, String>();
        int[] m = {50, 30, 70, 20, 40, 60, 80};
        for (int x : m) {
            a.insertar(x, "E" + x);
        }
        return a;
    }

    static Grafo<String> grafoCaso() {
        Grafo<String> g = new Grafo<String>(true);
        String[] ids = {"PC1", "SW1", "SRV1", "PC2", "PC3", "PC4"};
        for (String id : ids) {
            g.agregarVertice(id, id);
        }
        g.agregarArista("PC1", "SW1", 2);
        g.agregarArista("SW1", "SRV1", 1);
        g.agregarArista("SRV1", "PC2", 3);
        g.agregarArista("PC1", "PC3", 4);
        return g;
    }

    private static void vector(Verificador v) {
        v.igual("U-VEC-01", "M1", T, "agregar 1,2,3", "[1, 2, 3]", () -> vectorCon(1, 2, 3));
        v.igual("U-VEC-02", "M1", T, "insertarEn(0, 0) sobre [1,2]", "[0, 1, 2]", () -> {
            Vector<Integer> x = vectorCon(1, 2);
            x.insertarEn(0, 0);
            return x;
        });
        v.igual("U-VEC-03", "M1", T, "eliminarEn(1) (medio) sobre [1,2,3]", "2 [1, 3]", () -> {
            Vector<Integer> x = vectorCon(1, 2, 3);
            return x.eliminarEn(1) + " " + x;
        });
        v.igual("U-VEC-04", "M1", T, "eliminarEn(0) cabeza y eliminarEn(ultimo) cola sobre [1,2,3,4]", "[2, 3]", () -> {
            Vector<Integer> x = vectorCon(1, 2, 3, 4);
            x.eliminarEn(0);
            x.eliminarEn(x.tamanio() - 1);
            return x;
        });
        v.igual("U-VEC-05", "M1", T, "agregar 10 elementos con capacidad inicial 2", "10 16", () -> {
            Vector<Integer> x = vectorCon(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            return x.tamanio() + " " + x.capacidad();
        });
        v.igual("U-VEC-06", "M1", T, "indiceDe(x == 3) en [1,2,3]", 2,
                () -> vectorCon(1, 2, 3).indiceDe(x -> x == 3));
    }

    private static void lista(Verificador v) {
        v.igual("U-LIS-01", "M1", T, "agregarAlFinal 2,3 y agregarAlInicio 1", "[1, 2, 3]", () -> {
            ListaEnlazada<Integer> x = listaCon(2, 3);
            x.agregarAlInicio(1);
            return x;
        });
        v.igual("U-LIS-02", "M1", T, "eliminar cabeza de [1,2,3]", "[2, 3] primero=2", () -> {
            ListaEnlazada<Integer> x = listaCon(1, 2, 3);
            x.eliminarEn(0);
            return x + " primero=" + x.primero();
        });
        v.igual("U-LIS-03", "M1", T, "eliminar medio de [1,2,3]", "[1, 3]", () -> {
            ListaEnlazada<Integer> x = listaCon(1, 2, 3);
            x.eliminarEn(1);
            return x;
        });
        v.igual("U-LIS-04", "M1", T, "eliminar cola de [1,2,3] y luego agregarAlFinal 9", "[1, 2, 9] ultimo=9", () -> {
            ListaEnlazada<Integer> x = listaCon(1, 2, 3);
            x.eliminarEn(2);
            x.agregarAlFinal(9);
            return x + " ultimo=" + x.ultimo();
        });
        v.igual("U-LIS-05", "M1", T, "insertarEn(1, 5) sobre [1,2]", "[1, 5, 2]", () -> {
            ListaEnlazada<Integer> x = listaCon(1, 2);
            x.insertarEn(1, 5);
            return x;
        });
        v.igual("U-LIS-06", "M1", T, "eliminarPrimero(x == 2) en [1,2,3,2]", "2 [1, 3, 2]", () -> {
            ListaEnlazada<Integer> x = listaCon(1, 2, 3, 2);
            return x.eliminarPrimero(e -> e == 2) + " " + x;
        });
    }

    private static void pilaYCola(Verificador v) {
        v.igual("U-PIL-01", "M2", T, "apilar A,B,C y desapilar 3 veces", "C B A vacia=true", () -> {
            Pila<String> p = new Pila<String>();
            p.apilar("A");
            p.apilar("B");
            p.apilar("C");
            return p.desapilar() + " " + p.desapilar() + " " + p.desapilar() + " vacia=" + p.estaVacia();
        });
        v.igual("U-PIL-02", "M2", T, "cima no quita el elemento", "B 2", () -> {
            Pila<String> p = new Pila<String>();
            p.apilar("A");
            p.apilar("B");
            return p.cima() + " " + p.tamanio();
        });
        v.igual("U-COL-01", "M2", T, "encolar A,B,C y desencolar 3 veces", "A B C vacia=true", () -> {
            Cola<String> c = new Cola<String>();
            c.encolar("A");
            c.encolar("B");
            c.encolar("C");
            return c.desencolar() + " " + c.desencolar() + " " + c.desencolar() + " vacia=" + c.estaVacia();
        });
        v.igual("U-COL-02", "M2", T, "encolar A,B; desencolar; encolar C", "A [B, C]", () -> {
            Cola<String> c = new Cola<String>();
            c.encolar("A");
            c.encolar("B");
            String sale = c.desencolar();
            c.encolar("C");
            return sale + " " + c;
        });
        v.igual("U-COL-03", "M2", T, "vaciar la cola y volver a encolar (puntero fin)", "[X] frente=X", () -> {
            Cola<String> c = new Cola<String>();
            c.encolar("A");
            c.desencolar();
            c.encolar("X");
            return c + " frente=" + c.frente();
        });
    }

    //ids de la cola sin modificarla: se desencola y se vuelve a encolar n veces
    static String idsPendientes(ServicioAtencion a) {
        Cola<modelo.Solicitud> c = a.getPendientes();
        StringBuilder sb = new StringBuilder("[");
        int n = c.tamanio();
        for (int i = 0; i < n; i++) {
            modelo.Solicitud s = c.desencolar();
            sb.append(i == 0 ? "" : ", ").append(s.getId());
            c.encolar(s);
        }
        return sb.append("]").toString();
    }

    static ArbolGeneral<String> arbolEjemplo() {
        ArbolGeneral<String> a = new ArbolGeneral<String>();
        ArbolGeneral.Nodo<String> uta = a.crearRaiz("UTA");
        ArbolGeneral.Nodo<String> fisei = a.agregarHijo(uta, "FISEI");
        ArbolGeneral.Nodo<String> sw = a.agregarHijo(fisei, "SW");
        a.agregarHijo(fisei, "TI");
        a.agregarHijo(sw, "E50");
        a.agregarHijo(sw, "E30");
        return a;
    }

    private static void arbolGeneral(Verificador v) {
        v.igual("U-AG-01", "M3", T, "preorden UTA>FISEI>{SW>{E50,E30}, TI}", "[UTA, FISEI, SW, E50, E30, TI]",
                () -> arbolEjemplo().preorden());
        v.igual("U-AG-02", "M3", T, "posorden del mismo arbol", "[E50, E30, SW, TI, FISEI, UTA]",
                () -> arbolEjemplo().posorden());
        v.igual("U-AG-03", "M3", T, "altura y tamanio", "4 6", () -> {
            ArbolGeneral<String> a = arbolEjemplo();
            return a.altura() + " " + a.tamanio();
        });
        v.igual("U-AG-04", "M3", T, "eliminar subarbol SW", "true [UTA, FISEI, TI] 3", () -> {
            ArbolGeneral<String> a = arbolEjemplo();
            boolean ok = a.eliminar(x -> x.equals("SW"));
            return ok + " " + a.preorden() + " " + a.tamanio();
        });
    }

    private static void abb(Verificador v) {
        v.igual("U-ABB-01", "M3", T, "insertar 50,30,70,20,40,60,80 -> inorden", "[20, 30, 40, 50, 60, 70, 80]",
                () -> abbCaso().inorden());
        v.igual("U-ABB-02", "M3", T, "preorden del caso", "[50, 30, 20, 40, 70, 60, 80]", () -> abbCaso().preorden());
        v.igual("U-ABB-03", "M3", T, "posorden del caso", "[20, 40, 30, 60, 80, 70, 50]", () -> abbCaso().posorden());
        v.igual("U-ABB-04", "M3", T, "eliminar hoja 20", "true [50, 30, 40, 70, 60, 80] valido=true", () -> {
            ABB<Integer, String> a = abbCaso();
            return a.eliminar(20) + " " + a.preorden() + " valido=" + a.esValido();
        });
        v.igual("U-ABB-05", "M3", T, "eliminar 30 con un hijo (arbol 50,30,70,20)", "true [50, 20, 70] valido=true", () -> {
            ABB<Integer, String> a = new ABB<Integer, String>();
            a.insertar(50, "a");
            a.insertar(30, "b");
            a.insertar(70, "c");
            a.insertar(20, "d");
            return a.eliminar(30) + " " + a.preorden() + " valido=" + a.esValido();
        });
        v.igual("U-ABB-06", "M3", T, "eliminar 30 con dos hijos (sucesor inorden 40)",
                "true [50, 40, 20, 70, 60, 80] valido=true", () -> {
                    ABB<Integer, String> a = abbCaso();
                    return a.eliminar(30) + " " + a.preorden() + " valido=" + a.esValido();
                });
        v.igual("U-ABB-07", "M3", T, "eliminar la raiz 50 (dos hijos, sucesor 60)",
                "true [60, 30, 20, 40, 70, 80] valido=true", () -> {
                    ABB<Integer, String> a = abbCaso();
                    return a.eliminar(50) + " " + a.preorden() + " valido=" + a.esValido();
                });
        v.igual("U-ABB-08", "M3", T, "buscar 80 y contar comparaciones", "E80 3", () -> {
            ABB<Integer, String> a = abbCaso();
            return a.buscar(80) + " " + a.getUltimasComparaciones();
        });
        v.igual("U-ABB-09", "M3", T, "minimo, maximo, altura y tamanio", "20 80 3 7", () -> {
            ABB<Integer, String> a = abbCaso();
            return a.minimo() + " " + a.maximo() + " " + a.altura() + " " + a.tamanio();
        });
        v.igual("U-ABB-10", "M3", T, "insertar duplicado 40", "false 7", () -> {
            ABB<Integer, String> a = abbCaso();
            return a.insertar(40, "otro") + " " + a.tamanio();
        });
    }

    private static void grafo(Verificador v) {
        v.igual("U-GRA-01", "M4", T, "BFS desde PC1", "[PC1, SW1, PC3, SRV1, PC2]", () -> grafoCaso().bfs("PC1"));
        v.igual("U-GRA-02", "M4", T, "DFS desde PC1", "[PC1, SW1, SRV1, PC2, PC3]", () -> grafoCaso().dfs("PC1"));
        v.igual("U-GRA-03", "M4", T, "eliminar arista SW1->SRV1", "true 3 [PC1, SW1, PC3]", () -> {
            Grafo<String> g = grafoCaso();
            boolean ok = g.eliminarArista("SW1", "SRV1");
            return ok + " " + g.cantidadAristas() + " " + g.bfs("PC1");
        });
        v.igual("U-GRA-04", "M4", T, "eliminar vertice SRV1 quita entrante SW1->SRV1 y saliente SRV1->PC2",
                "true [] 2 5", () -> {
                    Grafo<String> g = grafoCaso();
                    boolean ok = g.eliminarVertice("SRV1");
                    return ok + " " + g.vecinos("SW1") + " " + g.cantidadAristas() + " " + g.cantidadVertices();
                });
        v.igual("U-GRA-05", "M4", T, "quitar las aristas que llegan a SW1 (solo PC1->SW1)", "1 [PC3] 0", () -> {
            Grafo<String> g = grafoCaso();
            int n = g.eliminarAristasEntrantes("SW1");
            return n + " " + g.vecinos("PC1") + " " + g.gradoEntrada("SW1");
        });
        v.igual("U-GRA-06", "M4", T, "grafo no dirigido: A-B aparece en ambos lados", "[B] [A] 1", () -> {
            Grafo<String> g = new Grafo<String>(false);
            g.agregarVertice("A", "A");
            g.agregarVertice("B", "B");
            g.agregarArista("A", "B", 1);
            return g.vecinos("A") + " " + g.vecinos("B") + " " + g.cantidadAristas();
        });
        v.igual("U-GRA-07", "M4", T, "no dirigido: eliminar A-B borra las dos copias", "[] [] 0", () -> {
            Grafo<String> g = new Grafo<String>(false);
            g.agregarVertice("A", "A");
            g.agregarVertice("B", "B");
            g.agregarArista("A", "B", 1);
            g.eliminarArista("B", "A");
            return g.vecinos("A") + " " + g.vecinos("B") + " " + g.cantidadAristas();
        });
        v.igual("U-GRA-08", "M4", T, "agregarAristaEn posicion 0 respeta orden de vecinos", "[PC3, SW1]", () -> {
            Grafo<String> g = grafoCaso();
            g.eliminarArista("PC1", "PC3");
            g.agregarAristaEn("PC1", "PC3", 4, 0);
            return g.vecinos("PC1");
        });
        v.igual("U-GRA-09", "M4", T, "ids con espacios a los lados se tratan igual (' PC1 ' y 'PC1')", "true [PC1, SW1, PC3, SRV1, PC2]", () -> {
            Grafo<String> g = grafoCaso();
            return g.existeArista(" PC1 ", "SW1 ") + " " + g.bfs(" PC1");
        });
        v.igual("U-GRA-10", "M4", T, "pesos de las aristas del caso", "2.0 1.0 3.0 4.0", () -> {
            Grafo<String> g = grafoCaso();
            return g.pesoArista("PC1", "SW1") + " " + g.pesoArista("SW1", "SRV1") + " "
                    + g.pesoArista("SRV1", "PC2") + " " + g.pesoArista("PC1", "PC3");
        });
    }

    private static void modulos(Verificador v) {
        v.igual("U-M1-01", "M1", T, "busqueda secuencial de 80 en el caso", "true 7", () -> {
            ServicioEstudiantes s = SistemaSIGAC.conCasoIntegrador().getEstudiantes();
            return s.buscarSecuencial(80).encontrado() + " " + s.buscarSecuencial(80).getComparaciones();
        });
        v.igual("U-M1-02", "M1", T, "busqueda en ABB de 80 en el caso", "true 3", () -> {
            ServicioEstudiantes s = SistemaSIGAC.conCasoIntegrador().getEstudiantes();
            return s.buscarEnABB(80).encontrado() + " " + s.buscarEnABB(80).getComparaciones();
        });
        v.igual("U-M1-03", "M1", T, "actualizar nombres de 40", "40 - Ana Lopez (SW)", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.actualizarEstudiante(40, "Ana Lopez", "SW");
            return s.getEstudiantes().obtener(40);
        });
        v.igual("U-M1-04", "M1", T, "eliminar 40 y deshacer (vuelve a su posicion), luego eliminar 60: vector, lista, ABB y arbol sincronizados", "true true 6 [50, 30, 70, 20, 40, 80]", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.eliminarEstudiante(40);
            s.deshacer();
            s.eliminarEstudiante(60);
            ServicioEstudiantes e = s.getEstudiantes();
            StringBuilder m = new StringBuilder("[");
            for (int i = 0; i < e.getVector().tamanio(); i++) {
                m.append(i == 0 ? "" : ", ").append(e.getVector().obtener(i).getMatricula());
            }
            return e.estructurasSincronizadas() + " " + s.estadoConsistente() + " " + e.cantidad() + " " + m + "]";
        });
        v.igual("U-M2-01", "M2", T, "atender 3 solicitudes del caso (FIFO)", "1 2 3", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.atenderSolicitud().getId() + " " + s.atenderSolicitud().getId() + " " + s.atenderSolicitud().getId();
        });
        v.igual("U-M2-02", "M2", T, "registrar 90, eliminar 40 y deshacer dos veces (LIFO)",
                "Eliminar 40 | Registrar 90 | [] | 7", () -> {
                    SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
                    s.registrarEstudiante(90, "Nuevo", "SW");
                    s.eliminarEstudiante(40);
                    return s.deshacer() + " | " + s.deshacer() + " | " + s.getAtencion().getHistorial()
                            + " | " + s.getEstudiantes().cantidad();
                });
        v.igual("U-M2-03", "M2", T, "devolver S1 al frente de [S2,S3] usando solo encolar/desencolar", "[1, 2, 3]", () -> {
            ServicioAtencion a = new ServicioAtencion();
            a.registrarSolicitud(1, "PC1", "a");
            a.registrarSolicitud(2, "PC1", "b");
            a.registrarSolicitud(3, "PC1", "c");
            a.devolverAlFrente(a.atenderSiguiente());
            return idsPendientes(a);
        });
        v.igual("U-M2-04", "M2", T, "retirar S2 del medio de [S1,S2,S3] conserva el orden", "true [1, 3]", () -> {
            ServicioAtencion a = new ServicioAtencion();
            a.registrarSolicitud(1, "PC1", "a");
            a.registrarSolicitud(2, "PC1", "b");
            a.registrarSolicitud(3, "PC1", "c");
            return a.retirarSolicitud(2) + " " + idsPendientes(a);
        });
        v.igual("U-M2-05", "M2", T, "registrar solicitud S4 y deshacerla", "Registrar solicitud S4 | 3", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.registrarSolicitud(40, "PC4", "Mantenimiento");
            return s.deshacer() + " | " + s.getAtencion().getPendientes().tamanio();
        });
        v.igual("U-M3-01", "M3", T, "jerarquia base en preorden", "[UTA, FISEI, SW, TI]",
                () -> new SistemaSIGAC().getAcademico().codigosPreorden());
        v.igual("U-M3-02", "M3", T, "estudiantes vinculados a SW en el caso",
                "[E50, E30, E70, E20, E40, E60, E80]",
                () -> SistemaSIGAC.conCasoIntegrador().getAcademico().estudiantesDeCarrera("SW"));
        v.igual("U-M3-03", "M3", T, "agregar facultad FCHE y carrera PSI: preorden y altura", "[UTA, FISEI, SW, TI, FCHE, PSI] 3", () -> {
            ServicioAcademico a = new SistemaSIGAC().getAcademico();
            a.agregarFacultad("FCHE", "Facultad de Ciencias Humanas");
            a.agregarCarrera("FCHE", "PSI", "Psicopedagogia");
            return a.codigosPreorden() + " " + a.altura();
        });
        v.igual("U-M4-01", "M4", T, "desconectar PC1->PC3 y deshacer (vuelve a la misma posicion)",
                "PC1->PC3(4.0) [SW1, PC3]", () -> {
                    SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
                    Conexion c = s.desconectarEquipos("PC1", "PC3");
                    s.deshacer();
                    return c + " " + s.getLaboratorio().getRed().vecinos("PC1");
                });
        v.igual("U-M4-02", "M4", T, "conectar PC3->PC4 y deshacer", "true false", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.conectarEquipos("PC3", "PC4", 5);
            boolean antes = s.getLaboratorio().hayCamino("PC1", "PC4");
            s.deshacer();
            return antes + " " + s.getLaboratorio().hayCamino("PC1", "PC4");
        });
        v.igual("U-M5-01", "M5", T, "amigos de Ana y de Carla", "[Beto, Carla] [Ana, Diego, Elena]", () -> {
            ServicioRedSocial r = SistemaSIGAC.conCasoIntegrador().getSocial();
            return r.amigos("Ana") + " " + r.amigos("Carla");
        });
        v.igual("U-M5-02", "M5", T, "recomendaciones para Diego", "[Ana (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])]",
                () -> SistemaSIGAC.conCasoIntegrador().getSocial().recomendar("Diego"));
        v.igual("U-M5-03", "M5", T, "eliminar amistad Carla-Elena y recomendar a Ana", "[Diego (2 en comun: [Beto, Carla])]", () -> {
            ServicioRedSocial r = SistemaSIGAC.conCasoIntegrador().getSocial();
            r.eliminarAmistad("Elena", "Carla");
            return r.recomendar("Ana");
        });
        v.igual("U-M5-04", "M5", T, "empate en amigos en comun se ordena por nombre", "[Ana (1 en comun: [X]), Zoe (1 en comun: [X])]", () -> {
            ServicioRedSocial r = new ServicioRedSocial();
            r.registrarUsuario("Yo");
            r.registrarUsuario("X");
            r.registrarUsuario("Zoe");
            r.registrarUsuario("Ana");
            r.registrarAmistad("Yo", "X");
            r.registrarAmistad("X", "Zoe");
            r.registrarAmistad("X", "Ana");
            return r.recomendar("Yo");
        });
    }
}
