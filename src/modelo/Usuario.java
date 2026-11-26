package modelo;

//USUARIO DE LA RED SOCIAL ACADEMICA (vertice del grafo no dirigido)
public class Usuario {

    private final String nombre;

    public Usuario(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario no puede ser null ni vacio");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
