package servicios;

import estructuras.ABB;
import estructuras.Condicion;
import estructuras.ElementoDuplicadoException;
import estructuras.ElementoNoEncontradoException;
import estructuras.EstructuraVaciaException;
import estructuras.ListaEnlazada;
import estructuras.Vector;
import modelo.Estudiante;
import modelo.ResultadoBusqueda;

//M1 Y M3 (indice): el mismo objeto Estudiante se guarda en tres estructuras
//  - Vector: registro en orden de llegada, acceso por posicion y busqueda secuencial O(n)
//  - ListaEnlazada: listado enlazado para recorrer y eliminar sin correr celdas
//  - ABB por matricula: indice para busqueda O(h)
//Las tres se actualizan juntas, asi nunca quedan desincronizadas
public class ServicioEstudiantes {

    private final Vector<Estudiante> vector = new Vector<Estudiante>();
    private final ListaEnlazada<Estudiante> lista = new ListaEnlazada<Estudiante>();
    private final ABB<Integer, Estudiante> indice = new ABB<Integer, Estudiante>();

    public void registrar(Estudiante e) {
        registrarEn(e, -1, -1);
    }

    //posiciones -1 = al final. Se usan otras posiciones al deshacer una eliminacion
    public void registrarEn(Estudiante e, int posVector, int posLista) {
        if (e == null) {
            throw new IllegalArgumentException("El estudiante no puede ser null");
        }
        if (indice.contiene(e.getMatricula())) {
            throw new ElementoDuplicadoException("Ya existe un estudiante con matricula " + e.getMatricula());
        }
        indice.insertar(e.getMatricula(), e);
        if (posVector < 0 || posVector > vector.tamanio()) {
            vector.agregar(e);
        } else {
            vector.insertarEn(posVector, e);
        }
        if (posLista < 0 || posLista > lista.tamanio()) {
            lista.agregarAlFinal(e);
        } else {
            lista.insertarEn(posLista, e);
        }
    }

    //BUSQUEDA SECUENCIAL EN EL VECTOR: compara uno por uno
    public ResultadoBusqueda buscarSecuencial(int matricula) {
        int comparaciones = 0;
        for (int i = 0; i < vector.tamanio(); i++) {
            comparaciones++;
            if (vector.obtener(i).getMatricula() == matricula) {
                return new ResultadoBusqueda("Secuencial (vector)", vector.obtener(i), comparaciones);
            }
        }
        return new ResultadoBusqueda("Secuencial (vector)", null, comparaciones);
    }

    //BUSQUEDA EN EL ABB: descarta un subarbol en cada paso
    public ResultadoBusqueda buscarEnABB(int matricula) {
        Estudiante e = indice.buscar(matricula);
        return new ResultadoBusqueda("ABB (indice)", e, indice.getUltimasComparaciones());
    }

    public Estudiante obtener(int matricula) {
        Estudiante e = indice.buscar(matricula);
        if (e == null) {
            throw new ElementoNoEncontradoException("No existe el estudiante con matricula " + matricula);
        }
        return e;
    }

    public boolean existe(int matricula) {
        return indice.contiene(matricula);
    }

    //ACTUALIZA NOMBRES Y CARRERA. Devuelve una copia del estado anterior (para el historial)
    public Estudiante actualizar(int matricula, String nombres, String codigoCarrera) {
        Estudiante e = obtener(matricula);
        Estudiante anterior = e.copia();
        //se valida todo antes de tocar el objeto
        new Estudiante(matricula, nombres, codigoCarrera);
        e.setNombres(nombres);
        e.setCodigoCarrera(codigoCarrera);
        return anterior;
    }

    public int posicionEnVector(final int matricula) {
        return vector.indiceDe(new Condicion<Estudiante>() {
            public boolean cumple(Estudiante e) {
                return e.getMatricula() == matricula;
            }
        });
    }

    public int posicionEnLista(final int matricula) {
        return lista.indiceDe(new Condicion<Estudiante>() {
            public boolean cumple(Estudiante e) {
                return e.getMatricula() == matricula;
            }
        });
    }

    //ELIMINACION SEGURA: valida vacio e inexistente antes de tocar cualquier estructura
    public Estudiante eliminar(int matricula) {
        if (indice.estaVacio()) {
            throw new EstructuraVaciaException("No hay estudiantes registrados");
        }
        Estudiante e = obtener(matricula);
        vector.eliminarEn(posicionEnVector(matricula));
        lista.eliminarEn(posicionEnLista(matricula));
        indice.eliminar(matricula);
        return e;
    }

    public Vector<Estudiante> getVector() {
        return vector;
    }

    public ListaEnlazada<Estudiante> getLista() {
        return lista;
    }

    public ListaEnlazada<Integer> preorden() {
        return indice.preorden();
    }

    public ListaEnlazada<Integer> inorden() {
        return indice.inorden();
    }

    public ListaEnlazada<Integer> posorden() {
        return indice.posorden();
    }

    public boolean indiceValido() {
        return indice.esValido();
    }

    public int alturaIndice() {
        return indice.altura();
    }

    public int cantidad() {
        return indice.tamanio();
    }

    public boolean estaVacio() {
        return indice.estaVacio();
    }

    //CONSISTENCIA ENTRE LAS TRES ESTRUCTURAS (se usa en pruebas de regresion)
    public boolean estructurasSincronizadas() {
        if (vector.tamanio() != lista.tamanio() || lista.tamanio() != indice.tamanio()) {
            return false;
        }
        for (int i = 0; i < vector.tamanio(); i++) {
            Estudiante e = vector.obtener(i);
            if (lista.obtener(i) != e || indice.buscar(e.getMatricula()) != e) {
                return false;
            }
        }
        return indice.esValido();
    }
}
