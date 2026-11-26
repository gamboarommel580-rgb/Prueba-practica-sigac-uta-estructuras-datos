package modelo;

//ARISTA DE LA RED DE LABORATORIO VISTA DESDE FUERA DEL GRAFO.
//posicion = lugar que ocupaba en la lista de vecinos del origen (sirve para deshacer)
public class Conexion {

    private final String origen;
    private final String destino;
    private final double peso;
    private final int posicion;

    public Conexion(String origen, String destino, double peso, int posicion) {
        if (origen == null || origen.trim().isEmpty() || destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("Origen y destino de la conexion no pueden ser null ni vacios");
        }
        //se guardan recortados para que coincidan con los ids del grafo
        this.origen = origen.trim();
        this.destino = destino.trim();
        this.peso = peso;
        this.posicion = posicion;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public int getPosicion() {
        return posicion;
    }

    @Override
    public String toString() {
        return origen + "->" + destino + "(" + peso + ")";
    }
}
