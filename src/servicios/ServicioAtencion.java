package servicios;

import estructuras.Cola;
import estructuras.Condicion;
import estructuras.EstructuraVaciaException;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import modelo.Operacion;
import modelo.Solicitud;

//M2: COLA FIFO DE SOLICITUDES + PILA LIFO CON EL HISTORIAL REVERSIBLE
public class ServicioAtencion {

    private final Cola<Solicitud> pendientes = new Cola<Solicitud>();
    private final ListaEnlazada<Solicitud> atendidas = new ListaEnlazada<Solicitud>();
    private final Pila<Operacion> historial = new Pila<Operacion>();
    private int siguienteId = 1;

    //la validacion de matricula y equipo la hace SistemaSIGAC, que conoce los otros modulos
    public Solicitud registrarSolicitud(int matricula, String idEquipo, String motivo) {
        Solicitud s = new Solicitud(siguienteId, matricula, idEquipo, motivo);
        pendientes.encolar(s);
        siguienteId++;
        return s;
    }

    //FIFO: siempre sale la que lleva mas tiempo esperando
    public Solicitud atenderSiguiente() {
        if (pendientes.estaVacia()) {
            throw new EstructuraVaciaException("No hay solicitudes pendientes");
        }
        Solicitud s = pendientes.desencolar();
        atendidas.agregarAlFinal(s);
        return s;
    }

    //DESHACER UNA ATENCION: la solicitud sale de atendidas y vuelve a ser la primera de la cola.
    //Solo se usan encolar y desencolar: se encola al final y se rotan los n elementos que ya estaban,
    //asi la devuelta queda al frente. O(n)
    public void devolverAlFrente(final Solicitud s) {
        if (s == null) {
            throw new IllegalArgumentException("La solicitud no puede ser null");
        }
        atendidas.eliminarPrimero(new Condicion<Solicitud>() {
            public boolean cumple(Solicitud x) {
                return x.getId() == s.getId();
            }
        });
        int n = pendientes.tamanio();
        pendientes.encolar(s);
        for (int i = 0; i < n; i++) {
            pendientes.encolar(pendientes.desencolar());
        }
    }

    //DESHACER UN REGISTRO DE SOLICITUD: se rota la cola completa una vez y se deja fuera la que tiene ese id.
    //Las demas conservan su orden. O(n)
    public boolean retirarSolicitud(int id) {
        int n = pendientes.tamanio();
        boolean retirada = false;
        for (int i = 0; i < n; i++) {
            Solicitud actual = pendientes.desencolar();
            if (!retirada && actual.getId() == id) {
                retirada = true;
            } else {
                pendientes.encolar(actual);
            }
        }
        return retirada;
    }

    public boolean tienePendientesEnEquipo(final String idEquipo) {
        return pendientes.contiene(new Condicion<Solicitud>() {
            public boolean cumple(Solicitud s) {
                return s.getIdEquipo().equals(idEquipo);
            }
        });
    }

    //CUANDO SE ELIMINA UN EQUIPO, sus operaciones ya no se pueden deshacer (el vertice ya no existe).
    //Se pasan todas a una pila auxiliar y se devuelven solo las que no dependen del equipo,
    //en el mismo orden que tenian. Devuelve cuantas se descartaron. O(p), p = tamanio del historial
    public int descartarOperacionesDeEquipo(String idEquipo) {
        Pila<Operacion> auxiliar = new Pila<Operacion>();
        int descartadas = 0;
        while (!historial.estaVacia()) {
            Operacion op = historial.desapilar();
            if (op.involucraEquipo(idEquipo)) {
                descartadas++;
            } else {
                auxiliar.apilar(op);
            }
        }
        while (!auxiliar.estaVacia()) {
            historial.apilar(auxiliar.desapilar());
        }
        return descartadas;
    }

    public boolean tienePendientes(final int matricula) {
        return pendientes.contiene(new Condicion<Solicitud>() {
            public boolean cumple(Solicitud s) {
                return s.getMatricula() == matricula;
            }
        });
    }

    public void registrarOperacion(Operacion op) {
        historial.apilar(op);
    }

    //LIFO: sale la ultima operacion registrada
    public Operacion sacarUltimaOperacion() {
        if (historial.estaVacia()) {
            throw new EstructuraVaciaException("No hay operaciones para deshacer");
        }
        return historial.desapilar();
    }

    public Cola<Solicitud> getPendientes() {
        return pendientes;
    }

    public ListaEnlazada<Solicitud> getAtendidas() {
        return atendidas;
    }

    public Pila<Operacion> getHistorial() {
        return historial;
    }
}
