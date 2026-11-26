package estructuras;

//ARBOL GENERAL (N-ARIO): cada nodo guarda sus hijos en una ListaEnlazada propia,
//asi los hijos se recorren en el mismo orden en que se agregaron
public class ArbolGeneral<T> {

    public static class Nodo<T> {
        private final T dato;
        private final ListaEnlazada<Nodo<T>> hijos = new ListaEnlazada<Nodo<T>>();
        private Nodo<T> padre;

        Nodo(T dato, Nodo<T> padre) {
            this.dato = dato;
            this.padre = padre;
        }

        public T getDato() {
            return dato;
        }

        public Nodo<T> getPadre() {
            return padre;
        }

        public ListaEnlazada<Nodo<T>> getHijos() {
            return hijos;
        }

        public boolean esHoja() {
            return hijos.estaVacia();
        }
    }

    private Nodo<T> raiz;
    private int tamanio;

    public Nodo<T> crearRaiz(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("La raiz no puede ser null");
        }
        if (raiz != null) {
            throw new IllegalStateException("El arbol ya tiene raiz");
        }
        raiz = new Nodo<T>(dato, null);
        tamanio = 1;
        return raiz;
    }

    //AGREGA UN HIJO AL FINAL DE LA LISTA DE HIJOS DEL PADRE
    public Nodo<T> agregarHijo(Nodo<T> padre, T dato) {
        return agregarHijoEn(padre, dato, -1);
    }

    //posicion = -1 agrega al final; otra posicion se usa al deshacer para respetar el orden original
    public Nodo<T> agregarHijoEn(Nodo<T> padre, T dato, int posicion) {
        if (padre == null || dato == null) {
            throw new IllegalArgumentException("Padre y dato no pueden ser null");
        }
        if (raiz == null) {
            throw new EstructuraVaciaException("El arbol no tiene raiz");
        }
        if (!perteneceAlArbol(padre)) {
            throw new IllegalArgumentException("El nodo padre no pertenece a este arbol");
        }
        final T buscado = dato;
        if (buscar(new Condicion<T>() {
            public boolean cumple(T e) {
                return e.equals(buscado);
            }
        }) != null) {
            throw new ElementoDuplicadoException("Ya existe en el arbol: " + dato);
        }
        Nodo<T> hijo = new Nodo<T>(dato, padre);
        if (posicion < 0 || posicion > padre.hijos.tamanio()) {
            padre.hijos.agregarAlFinal(hijo);
        } else {
            padre.hijos.insertarEn(posicion, hijo);
        }
        tamanio++;
        return hijo;
    }

    //sube por los punteros padre: si llega a la raiz, el nodo sigue colgado del arbol
    private boolean perteneceAlArbol(Nodo<T> nodo) {
        Nodo<T> actual = nodo;
        while (actual.padre != null) {
            actual = actual.padre;
        }
        return actual == raiz;
    }

    //BUSQUEDA EN PREORDEN O(n)
    public Nodo<T> buscar(Condicion<T> condicion) {
        if (condicion == null) {
            throw new IllegalArgumentException("La condicion no puede ser null");
        }
        return buscarRec(raiz, condicion);
    }

    private Nodo<T> buscarRec(Nodo<T> nodo, Condicion<T> condicion) {
        if (nodo == null) {
            return null;
        }
        if (condicion.cumple(nodo.dato)) {
            return nodo;
        }
        for (Nodo<T> hijo : nodo.hijos) {
            Nodo<T> encontrado = buscarRec(hijo, condicion);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    //ELIMINA EL NODO Y TODO SU SUBARBOL
    public boolean eliminar(Condicion<T> condicion) {
        final Nodo<T> objetivo = buscar(condicion);
        if (objetivo == null) {
            return false;
        }
        int quitados = contar(objetivo);
        if (objetivo == raiz) {
            raiz = null;
        } else {
            objetivo.padre.hijos.eliminarPrimero(new Condicion<Nodo<T>>() {
                public boolean cumple(Nodo<T> n) {
                    return n == objetivo;
                }
            });
            objetivo.padre = null;
        }
        tamanio -= quitados;
        return true;
    }

    public ListaEnlazada<T> preorden() {
        ListaEnlazada<T> salida = new ListaEnlazada<T>();
        preordenRec(raiz, salida);
        return salida;
    }

    private void preordenRec(Nodo<T> nodo, ListaEnlazada<T> salida) {
        if (nodo == null) {
            return;
        }
        salida.agregarAlFinal(nodo.dato);
        for (Nodo<T> hijo : nodo.hijos) {
            preordenRec(hijo, salida);
        }
    }

    public ListaEnlazada<T> posorden() {
        ListaEnlazada<T> salida = new ListaEnlazada<T>();
        posordenRec(raiz, salida);
        return salida;
    }

    private void posordenRec(Nodo<T> nodo, ListaEnlazada<T> salida) {
        if (nodo == null) {
            return;
        }
        for (Nodo<T> hijo : nodo.hijos) {
            posordenRec(hijo, salida);
        }
        salida.agregarAlFinal(nodo.dato);
    }

    //ALTURA: arbol vacio = 0, solo raiz = 1
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int mayor = 0;
        for (Nodo<T> hijo : nodo.hijos) {
            int h = alturaRec(hijo);
            if (h > mayor) {
                mayor = h;
            }
        }
        return mayor + 1;
    }

    public int contar(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int total = 1;
        for (Nodo<T> hijo : nodo.hijos) {
            total += contar(hijo);
        }
        return total;
    }

    //DIBUJO CON SANGRIA, UN NIVEL POR CADA DOS ESPACIOS
    public String dibujar() {
        StringBuilder sb = new StringBuilder();
        dibujarRec(raiz, 0, sb);
        return sb.toString();
    }

    private void dibujarRec(Nodo<T> nodo, int nivel, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }
        sb.append(nivel == 0 ? "" : "|- ").append(nodo.dato).append("\n");
        for (Nodo<T> hijo : nodo.hijos) {
            dibujarRec(hijo, nivel + 1, sb);
        }
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}
