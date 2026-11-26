package modelo;

//EQUIPO DE LA RED DE LABORATORIO (vertice del grafo dirigido)
public class Equipo {

    private final String id;
    private final String tipo;

    public Equipo(String id, String tipo) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id del equipo no puede ser null ni vacio");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo del equipo no puede ser null ni vacio");
        }
        this.id = id.trim();
        this.tipo = tipo.trim();
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return id + " [" + tipo + "]";
    }
}
