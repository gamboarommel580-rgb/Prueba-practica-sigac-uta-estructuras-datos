package estructuras;

//GRAFO DINAMICO PONDERADO CON LISTAS DE ADYACENCIA PROPIAS.
//Reglas para que las pruebas sean reproducibles:
//  - los vertices se guardan en el orden en que se registran
//  - los vecinos de cada vertice se guardan en el orden en que se agregan las aristas
//  - BFS y DFS visitan los vecinos en ese mismo orden
//  - si el grafo es dirigido, la arista A->B solo aparece en la lista de A
//  - si no es dirigido, A-B se guarda en la lista de A y en la de B
public class Grafo<T> {

    private static class Vertice<T> {
        final String id;
        T dato;
        final ListaEnlazada<Arista<T>> adyacentes = new ListaEnlazada<Arista<T>>();
        int indice; //se recalcula antes de cada recorrido para marcar visitados en un arreglo

        Vertice(String id, T dato) {
            this.id = id;
            this.dato = dato;
        }
    }

    private static class Arista<T> {
        final Vertice<T> destino;
        final double peso;

        Arista(Vertice<T> destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    private final boolean dirigido;
    private final ListaEnlazada<Vertice<T>> vertices = new ListaEnlazada<Vertice<T>>();
    private int cantidadAristas;

    public Grafo(boolean dirigido) {
        this.dirigido = dirigido;
    }

    public boolean esDirigido() {
        return dirigido;
    }

    // ---------------- VERTICES ----------------

    public boolean agregarVertice(String id, T dato) {
        validarId(id);
        if (buscarVertice(id) != null) {
            return false;
        }
        vertices.agregarAlFinal(new Vertice<T>(id.trim(), dato));
        return true;
    }

    public boolean existeVertice(String id) {
        validarId(id);
        return buscarVertice(id) != null;
    }

    public T obtenerDato(String id) {
        return verticeObligatorio(id).dato;
    }

    //ELIMINA EL VERTICE, SUS ARISTAS SALIENTES Y TODAS LAS ARISTAS ENTRANTES DESDE OTROS VERTICES
    public boolean eliminarVertice(String id) {
        validarId(id);
        final Vertice<T> objetivo = buscarVertice(id);
        if (objetivo == null) {
            return false;
        }
        eliminarAristasEntrantes(id);
        //las salientes se descuentan aqui (en no dirigido ya se quitaron como entrantes)
        cantidadAristas -= objetivo.adyacentes.tamanio();
        objetivo.adyacentes.limpiar();
        vertices.eliminarPrimero(new Condicion<Vertice<T>>() {
            public boolean cumple(Vertice<T> v) {
                return v == objetivo;
            }
        });
        return true;
    }

    //RECORRE TODOS LOS VERTICES Y QUITA LAS ARISTAS QUE LLEGAN A id. Devuelve cuantas quito
    public int eliminarAristasEntrantes(String id) {
        final Vertice<T> objetivo = verticeObligatorio(id);
        int quitadas = 0;
        for (Vertice<T> v : vertices) {
            if (v == objetivo) {
                continue;
            }
            //basta con quitar la primera: no se aceptan aristas repetidas
            Arista<T> quitada = v.adyacentes.eliminarPrimero(new Condicion<Arista<T>>() {
                public boolean cumple(Arista<T> a) {
                    return a.destino == objetivo;
                }
            });
            if (quitada != null) {
                quitadas++;
                if (!dirigido) {
                    //en no dirigido tambien se borra la copia que esta en la lista del objetivo
                    final Vertice<T> otro = v;
                    objetivo.adyacentes.eliminarPrimero(new Condicion<Arista<T>>() {
                        public boolean cumple(Arista<T> a) {
                            return a.destino == otro;
                        }
                    });
                }
            }
        }
        cantidadAristas -= quitadas;
        return quitadas;
    }

    // ---------------- ARISTAS ----------------

    public boolean agregarArista(String origen, String destino, double peso) {
        return agregarAristaEn(origen, destino, peso, -1);
    }

    //posicion = -1 agrega al final; otra posicion se usa para deshacer y respetar el orden original
    public boolean agregarAristaEn(String origen, String destino, double peso, int posicion) {
        Vertice<T> o = verticeObligatorio(origen);
        Vertice<T> d = verticeObligatorio(destino);
        if (o == d) {
            throw new IllegalArgumentException("No se permiten lazos: " + origen + " -> " + destino);
        }
        if (Double.isNaN(peso) || Double.isInfinite(peso) || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser un numero finito mayor a 0");
        }
        if (buscarArista(o, d) != null) {
            return false;
        }
        Arista<T> nueva = new Arista<T>(d, peso);
        if (posicion < 0 || posicion > o.adyacentes.tamanio()) {
            o.adyacentes.agregarAlFinal(nueva);
        } else {
            o.adyacentes.insertarEn(posicion, nueva);
        }
        if (!dirigido) {
            d.adyacentes.agregarAlFinal(new Arista<T>(o, peso));
        }
        cantidadAristas++;
        return true;
    }

    public boolean existeArista(String origen, String destino) {
        return buscarArista(verticeObligatorio(origen), verticeObligatorio(destino)) != null;
    }

    //devuelve el peso o lanza excepcion si la arista no existe
    public double pesoArista(String origen, String destino) {
        Arista<T> a = buscarArista(verticeObligatorio(origen), verticeObligatorio(destino));
        if (a == null) {
            throw new ElementoNoEncontradoException("No existe la arista " + origen + " -> " + destino);
        }
        return a.peso;
    }

    public int posicionArista(String origen, String destino) {
        final Vertice<T> d = verticeObligatorio(destino);
        return verticeObligatorio(origen).adyacentes.indiceDe(new Condicion<Arista<T>>() {
            public boolean cumple(Arista<T> a) {
                return a.destino == d;
            }
        });
    }

    //ELIMINA LA ARISTA origen->destino (y destino->origen si no es dirigido). false si no existia
    public boolean eliminarArista(String origen, String destino) {
        final Vertice<T> o = verticeObligatorio(origen);
        final Vertice<T> d = verticeObligatorio(destino);
        Arista<T> quitada = o.adyacentes.eliminarPrimero(new Condicion<Arista<T>>() {
            public boolean cumple(Arista<T> a) {
                return a.destino == d;
            }
        });
        if (quitada == null) {
            return false;
        }
        if (!dirigido) {
            d.adyacentes.eliminarPrimero(new Condicion<Arista<T>>() {
                public boolean cumple(Arista<T> a) {
                    return a.destino == o;
                }
            });
        }
        cantidadAristas--;
        return true;
    }

    public ListaEnlazada<String> vecinos(String id) {
        ListaEnlazada<String> salida = new ListaEnlazada<String>();
        for (Arista<T> a : verticeObligatorio(id).adyacentes) {
            salida.agregarAlFinal(a.destino.id);
        }
        return salida;
    }

    public int gradoSalida(String id) {
        return verticeObligatorio(id).adyacentes.tamanio();
    }

    public int gradoEntrada(String id) {
        Vertice<T> objetivo = verticeObligatorio(id);
        int total = 0;
        for (Vertice<T> v : vertices) {
            if (buscarArista(v, objetivo) != null) {
                total++;
            }
        }
        return total;
    }

    // ---------------- RECORRIDOS ----------------

    //BFS O(V + E): la COLA es FIFO, asi que salen primero todos los vecinos directos del origen
    //y despues los vecinos de esos vecinos. Por eso el recorrido va por niveles
    public ListaEnlazada<String> bfs(String origen) {
        Vertice<T> inicio = verticeObligatorio(origen);
        boolean[] visitado = prepararVisitados();
        ListaEnlazada<String> orden = new ListaEnlazada<String>();
        Cola<Vertice<T>> cola = new Cola<Vertice<T>>();
        visitado[inicio.indice] = true;
        cola.encolar(inicio);
        while (!cola.estaVacia()) {
            Vertice<T> actual = cola.desencolar();
            orden.agregarAlFinal(actual.id);
            for (Arista<T> a : actual.adyacentes) {
                //se marca al encolar para no meter dos veces el mismo vertice
                if (!visitado[a.destino.indice]) {
                    visitado[a.destino.indice] = true;
                    cola.encolar(a.destino);
                }
            }
        }
        return orden;
    }

    //DFS RECURSIVO O(V + E): baja por el primer vecino antes de pasar al siguiente
    public ListaEnlazada<String> dfs(String origen) {
        Vertice<T> inicio = verticeObligatorio(origen);
        boolean[] visitado = prepararVisitados();
        ListaEnlazada<String> orden = new ListaEnlazada<String>();
        dfsRec(inicio, visitado, orden);
        return orden;
    }

    private void dfsRec(Vertice<T> actual, boolean[] visitado, ListaEnlazada<String> orden) {
        visitado[actual.indice] = true;
        orden.agregarAlFinal(actual.id);
        for (Arista<T> a : actual.adyacentes) {
            if (!visitado[a.destino.indice]) {
                dfsRec(a.destino, visitado, orden);
            }
        }
    }

    //VERTICES QUE NO APARECEN EN EL BFS DESDE origen (en orden de registro)
    public ListaEnlazada<String> noAlcanzables(String origen) {
        final ListaEnlazada<String> alcanzados = bfs(origen);
        ListaEnlazada<String> salida = new ListaEnlazada<String>();
        for (final Vertice<T> v : vertices) {
            boolean esta = alcanzados.contiene(new Condicion<String>() {
                public boolean cumple(String s) {
                    return s.equals(v.id);
                }
            });
            if (!esta) {
                salida.agregarAlFinal(v.id);
            }
        }
        return salida;
    }

    public boolean hayCamino(String origen, String destino) {
        final String idDestino = verticeObligatorio(destino).id;
        return bfs(origen).contiene(new Condicion<String>() {
            public boolean cumple(String s) {
                return s.equals(idDestino);
            }
        });
    }

    // ---------------- CONSULTAS ----------------

    public ListaEnlazada<String> idsVertices() {
        ListaEnlazada<String> salida = new ListaEnlazada<String>();
        for (Vertice<T> v : vertices) {
            salida.agregarAlFinal(v.id);
        }
        return salida;
    }

    //lista legible de aristas: "A->B(5.0)". En no dirigido cada arista sale una sola vez
    public ListaEnlazada<String> describirAristas() {
        ListaEnlazada<String> salida = new ListaEnlazada<String>();
        prepararVisitados();
        for (Vertice<T> v : vertices) {
            for (Arista<T> a : v.adyacentes) {
                if (dirigido) {
                    salida.agregarAlFinal(v.id + "->" + a.destino.id + "(" + a.peso + ")");
                } else if (v.indice < a.destino.indice) {
                    salida.agregarAlFinal(v.id + "-" + a.destino.id + "(" + a.peso + ")");
                }
            }
        }
        return salida;
    }

    public int cantidadVertices() {
        return vertices.tamanio();
    }

    public int cantidadAristas() {
        return cantidadAristas;
    }

    public boolean estaVacio() {
        return vertices.estaVacia();
    }

    // ---------------- AUXILIARES ----------------

    private boolean[] prepararVisitados() {
        int i = 0;
        for (Vertice<T> v : vertices) {
            v.indice = i++;
        }
        return new boolean[i];
    }

    //los ids se comparan sin espacios a los lados
    private Vertice<T> buscarVertice(String id) {
        final String limpio = id.trim();
        return vertices.buscar(new Condicion<Vertice<T>>() {
            public boolean cumple(Vertice<T> v) {
                return v.id.equals(limpio);
            }
        });
    }

    private Vertice<T> verticeObligatorio(String id) {
        validarId(id);
        Vertice<T> v = buscarVertice(id);
        if (v == null) {
            throw new ElementoNoEncontradoException("No existe el vertice: " + id);
        }
        return v;
    }

    private Arista<T> buscarArista(Vertice<T> o, final Vertice<T> d) {
        return o.adyacentes.buscar(new Condicion<Arista<T>>() {
            public boolean cumple(Arista<T> a) {
                return a.destino == d;
            }
        });
    }

    private void validarId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador del vertice no puede ser null ni vacio");
        }
    }
}
