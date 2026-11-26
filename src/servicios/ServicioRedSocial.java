package servicios;

import estructuras.Condicion;
import estructuras.ElementoDuplicadoException;
import estructuras.ElementoNoEncontradoException;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import estructuras.Vector;
import modelo.Recomendacion;
import modelo.Usuario;

//M5: GRAFO SOCIAL NO DIRIGIDO (la amistad es mutua). Peso fijo 1
public class ServicioRedSocial {

    private final Grafo<Usuario> red = new Grafo<Usuario>(false);

    public void registrarUsuario(String nombre) {
        Usuario u = new Usuario(nombre);
        if (!red.agregarVertice(u.getNombre(), u)) {
            throw new ElementoDuplicadoException("Ya existe el usuario " + nombre);
        }
    }

    public void registrarAmistad(String a, String b) {
        if (!red.agregarArista(a, b, 1)) {
            throw new ElementoDuplicadoException(a + " y " + b + " ya son amigos");
        }
    }

    public void eliminarAmistad(String a, String b) {
        if (!red.eliminarArista(a, b)) {
            throw new ElementoNoEncontradoException(a + " y " + b + " no son amigos");
        }
    }

    public ListaEnlazada<String> amigos(String usuario) {
        return red.vecinos(usuario);
    }

    public boolean sonAmigos(String a, String b) {
        return red.existeArista(a, b);
    }

    //RECOMENDACION DE SEGUNDO GRADO
    //PASO 1: por cada amigo directo f del usuario, revisar los amigos g de f
    //PASO 2: descartar g si es el propio usuario o si ya es amigo directo
    //PASO 3: acumular f como amigo en comun de g
    //PASO 4: ordenar por cantidad de amigos en comun (mayor primero); empate -> nombre alfabetico sin distinguir mayusculas
    public Vector<Recomendacion> recomendar(String usuario) {
        if (!red.existeVertice(usuario)) {
            throw new ElementoNoEncontradoException("No existe el usuario " + usuario);
        }
        Vector<Recomendacion> candidatos = new Vector<Recomendacion>();
        for (String amigo : red.vecinos(usuario)) {
            for (final String candidato : red.vecinos(amigo)) {
                if (candidato.equals(usuario) || red.existeArista(usuario, candidato)) {
                    continue;
                }
                int pos = candidatos.indiceDe(new Condicion<Recomendacion>() {
                    public boolean cumple(Recomendacion r) {
                        return r.getUsuario().equals(candidato);
                    }
                });
                Recomendacion r;
                if (pos == -1) {
                    r = new Recomendacion(candidato);
                    candidatos.agregar(r);
                } else {
                    r = candidatos.obtener(pos);
                }
                r.agregarAmigoEnComun(amigo);
            }
        }
        ordenar(candidatos);
        return candidatos;
    }

    //ORDENAMIENTO POR INSERCION, ESTABLE, HECHO A MANO
    private void ordenar(Vector<Recomendacion> v) {
        for (int i = 1; i < v.tamanio(); i++) {
            Recomendacion actual = v.obtener(i);
            int j = i - 1;
            while (j >= 0 && vaAntes(actual, v.obtener(j))) {
                v.reemplazar(j + 1, v.obtener(j));
                j--;
            }
            v.reemplazar(j + 1, actual);
        }
    }

    private boolean vaAntes(Recomendacion a, Recomendacion b) {
        if (a.cantidadEnComun() != b.cantidadEnComun()) {
            return a.cantidadEnComun() > b.cantidadEnComun();
        }
        return a.getUsuario().compareToIgnoreCase(b.getUsuario()) < 0;
    }

    public Grafo<Usuario> getRed() {
        return red;
    }
}
