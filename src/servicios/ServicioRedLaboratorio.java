package servicios;

import estructuras.ElementoDuplicadoException;
import estructuras.ElementoNoEncontradoException;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import modelo.Conexion;
import modelo.Equipo;

//M4: RED DE LABORATORIO = GRAFO DIRIGIDO Y PONDERADO
//A->B significa que A puede enviar a B. El peso es el costo del enlace (por ejemplo latencia en ms)
public class ServicioRedLaboratorio {

    private final Grafo<Equipo> red = new Grafo<Equipo>(true);

    public void agregarEquipo(String id, String tipo) {
        Equipo e = new Equipo(id, tipo);
        if (!red.agregarVertice(e.getId(), e)) {
            throw new ElementoDuplicadoException("Ya existe el equipo " + id);
        }
    }

    public Conexion conectar(String origen, String destino, double peso) {
        if (!red.agregarArista(origen, destino, peso)) {
            throw new ElementoDuplicadoException("Ya existe la conexion " + origen + " -> " + destino);
        }
        return new Conexion(origen, destino, peso, red.posicionArista(origen, destino));
    }

    //se usa al deshacer: vuelve a poner la arista en la misma posicion de la lista de vecinos
    public void reconectar(Conexion c) {
        if (c == null) {
            throw new IllegalArgumentException("La conexion no puede ser null");
        }
        if (!red.agregarAristaEn(c.getOrigen(), c.getDestino(), c.getPeso(), c.getPosicion())) {
            throw new ElementoDuplicadoException("Ya existe la conexion " + c);
        }
    }

    //devuelve la conexion eliminada (con su posicion) para poder deshacer
    public Conexion desconectar(String origen, String destino) {
        if (!red.existeVertice(origen)) {
            throw new ElementoNoEncontradoException("No existe el equipo " + origen);
        }
        if (!red.existeVertice(destino)) {
            throw new ElementoNoEncontradoException("No existe el equipo " + destino);
        }
        if (!red.existeArista(origen, destino)) {
            throw new ElementoNoEncontradoException("No existe la conexion " + origen + " -> " + destino);
        }
        Conexion c = new Conexion(origen, destino, red.pesoArista(origen, destino),
                red.posicionArista(origen, destino));
        red.eliminarArista(origen, destino);
        return c;
    }

    //quita el equipo y tambien todas las conexiones que llegaban a el.
    //Es de paquete: desde fuera se usa SistemaSIGAC.eliminarEquipo, que ademas limpia el historial
    void eliminarEquipo(String id) {
        if (!red.eliminarVertice(id)) {
            throw new ElementoNoEncontradoException("No existe el equipo " + id);
        }
    }

    public int quitarConexionesEntrantes(String id) {
        return red.eliminarAristasEntrantes(id);
    }

    public boolean existeEquipo(String id) {
        return red.existeVertice(id);
    }

    public ListaEnlazada<String> bfs(String origen) {
        return red.bfs(origen);
    }

    public ListaEnlazada<String> dfs(String origen) {
        return red.dfs(origen);
    }

    public ListaEnlazada<String> noAlcanzables(String origen) {
        return red.noAlcanzables(origen);
    }

    public boolean hayCamino(String origen, String destino) {
        return red.hayCamino(origen, destino);
    }

    public String resumenConectividad(String origen) {
        return "BFS desde " + origen + ": " + bfs(origen)
                + "\nDFS desde " + origen + ": " + dfs(origen)
                + "\nNo alcanzables: " + noAlcanzables(origen);
    }

    public Grafo<Equipo> getRed() {
        return red;
    }
}
