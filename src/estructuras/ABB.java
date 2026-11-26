package estructuras;

//ARBOL BINARIO DE BUSQUEDA (clave -> valor). No admite claves repetidas.
//Insertar, buscar y eliminar: O(h). h cerca de log2(n) si esta equilibrado, n en el peor caso (datos ordenados).
//POLITICA DE ELIMINACION:
//  - hoja: se quita el nodo
//  - un hijo: el hijo ocupa el lugar del nodo
//  - dos hijos: se copia el SUCESOR INORDEN (minimo del subarbol derecho) y luego se elimina ese sucesor
public class ABB<K extends Comparable<K>, V> {

    private static class Nodo<K, V> {
        K clave;
        V valor;
        Nodo<K, V> izq;
        Nodo<K, V> der;

        Nodo(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private Nodo<K, V> raiz;
    private int tamanio;
    private int ultimasComparaciones;
    private boolean eliminadoEnUltimaLlamada;

    //INSERCION ITERATIVA. Devuelve false si la clave ya existe (duplicado)
    public boolean insertar(K clave, V valor) {
        validarClave(clave);
        if (valor == null) {
            throw new IllegalArgumentException("El valor no puede ser null");
        }
        if (raiz == null) {
            raiz = new Nodo<K, V>(clave, valor);
            tamanio++;
            return true;
        }
        Nodo<K, V> actual = raiz;
        while (true) {
            int cmp = clave.compareTo(actual.clave);
            if (cmp == 0) {
                return false;
            }
            if (cmp < 0) {
                if (actual.izq == null) {
                    actual.izq = new Nodo<K, V>(clave, valor);
                    tamanio++;
                    return true;
                }
                actual = actual.izq;
            } else {
                if (actual.der == null) {
                    actual.der = new Nodo<K, V>(clave, valor);
                    tamanio++;
                    return true;
                }
                actual = actual.der;
            }
        }
    }

    //BUSQUEDA: cuenta cuantos nodos se comparan para poder compararla con la secuencial
    public V buscar(K clave) {
        validarClave(clave);
        ultimasComparaciones = 0;
        Nodo<K, V> actual = raiz;
        while (actual != null) {
            ultimasComparaciones++;
            int cmp = clave.compareTo(actual.clave);
            if (cmp == 0) {
                return actual.valor;
            }
            actual = (cmp < 0) ? actual.izq : actual.der;
        }
        return null;
    }

    public boolean contiene(K clave) {
        return buscar(clave) != null;
    }

    public boolean eliminar(K clave) {
        validarClave(clave);
        eliminadoEnUltimaLlamada = false;
        raiz = eliminarRec(raiz, clave);
        if (eliminadoEnUltimaLlamada) {
            tamanio--;
        }
        return eliminadoEnUltimaLlamada;
    }

    private Nodo<K, V> eliminarRec(Nodo<K, V> nodo, K clave) {
        if (nodo == null) {
            return null;
        }
        int cmp = clave.compareTo(nodo.clave);
        if (cmp < 0) {
            nodo.izq = eliminarRec(nodo.izq, clave);
            return nodo;
        }
        if (cmp > 0) {
            nodo.der = eliminarRec(nodo.der, clave);
            return nodo;
        }
        eliminadoEnUltimaLlamada = true;
        //CASO 1 Y 2: hoja o un solo hijo
        if (nodo.izq == null) {
            return nodo.der;
        }
        if (nodo.der == null) {
            return nodo.izq;
        }
        //CASO 3: dos hijos -> sucesor inorden
        //PASO 1: buscar el menor del subarbol derecho
        //PASO 2: copiar su clave y valor en este nodo
        //PASO 3: borrar el sucesor del subarbol derecho (no tiene hijo izq, cae en caso 1 o 2)
        Nodo<K, V> sucesor = nodo.der;
        while (sucesor.izq != null) {
            sucesor = sucesor.izq;
        }
        nodo.clave = sucesor.clave;
        nodo.valor = sucesor.valor;
        nodo.der = eliminarRec(nodo.der, sucesor.clave);
        return nodo;
    }

    public ListaEnlazada<K> preorden() {
        ListaEnlazada<K> salida = new ListaEnlazada<K>();
        preordenRec(raiz, salida);
        return salida;
    }

    private void preordenRec(Nodo<K, V> n, ListaEnlazada<K> salida) {
        if (n == null) {
            return;
        }
        salida.agregarAlFinal(n.clave);
        preordenRec(n.izq, salida);
        preordenRec(n.der, salida);
    }

    public ListaEnlazada<K> inorden() {
        ListaEnlazada<K> salida = new ListaEnlazada<K>();
        inordenRec(raiz, salida);
        return salida;
    }

    private void inordenRec(Nodo<K, V> n, ListaEnlazada<K> salida) {
        if (n == null) {
            return;
        }
        inordenRec(n.izq, salida);
        salida.agregarAlFinal(n.clave);
        inordenRec(n.der, salida);
    }

    public ListaEnlazada<K> posorden() {
        ListaEnlazada<K> salida = new ListaEnlazada<K>();
        posordenRec(raiz, salida);
        return salida;
    }

    private void posordenRec(Nodo<K, V> n, ListaEnlazada<K> salida) {
        if (n == null) {
            return;
        }
        posordenRec(n.izq, salida);
        posordenRec(n.der, salida);
        salida.agregarAlFinal(n.clave);
    }

    public ListaEnlazada<V> valoresInorden() {
        ListaEnlazada<V> salida = new ListaEnlazada<V>();
        valoresRec(raiz, salida);
        return salida;
    }

    private void valoresRec(Nodo<K, V> n, ListaEnlazada<V> salida) {
        if (n == null) {
            return;
        }
        valoresRec(n.izq, salida);
        salida.agregarAlFinal(n.valor);
        valoresRec(n.der, salida);
    }

    //VERIFICA LA PROPIEDAD DEL ABB: todo a la izquierda es menor y todo a la derecha es mayor
    public boolean esValido() {
        return validoRec(raiz, null, null);
    }

    private boolean validoRec(Nodo<K, V> n, K minimo, K maximo) {
        if (n == null) {
            return true;
        }
        if (minimo != null && n.clave.compareTo(minimo) <= 0) {
            return false;
        }
        if (maximo != null && n.clave.compareTo(maximo) >= 0) {
            return false;
        }
        return validoRec(n.izq, minimo, n.clave) && validoRec(n.der, n.clave, maximo);
    }

    //ALTURA: vacio = 0, solo raiz = 1
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(Nodo<K, V> n) {
        if (n == null) {
            return 0;
        }
        return 1 + Math.max(alturaRec(n.izq), alturaRec(n.der));
    }

    public K minimo() {
        if (raiz == null) {
            throw new EstructuraVaciaException("El ABB esta vacio");
        }
        Nodo<K, V> n = raiz;
        while (n.izq != null) {
            n = n.izq;
        }
        return n.clave;
    }

    public K maximo() {
        if (raiz == null) {
            throw new EstructuraVaciaException("El ABB esta vacio");
        }
        Nodo<K, V> n = raiz;
        while (n.der != null) {
            n = n.der;
        }
        return n.clave;
    }

    public K claveRaiz() {
        if (raiz == null) {
            throw new EstructuraVaciaException("El ABB esta vacio");
        }
        return raiz.clave;
    }

    public int getUltimasComparaciones() {
        return ultimasComparaciones;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    private void validarClave(K clave) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
    }
}
