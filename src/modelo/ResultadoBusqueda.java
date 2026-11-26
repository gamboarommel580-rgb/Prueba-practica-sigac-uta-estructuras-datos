package modelo;

//RESULTADO DE UNA BUSQUEDA CON EL NUMERO DE COMPARACIONES REALIZADAS
public class ResultadoBusqueda {

    private final String metodo;
    private final Estudiante estudiante;
    private final int comparaciones;

    public ResultadoBusqueda(String metodo, Estudiante estudiante, int comparaciones) {
        this.metodo = metodo;
        this.estudiante = estudiante;
        this.comparaciones = comparaciones;
    }

    public String getMetodo() {
        return metodo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public boolean encontrado() {
        return estudiante != null;
    }

    public int getComparaciones() {
        return comparaciones;
    }

    @Override
    public String toString() {
        return metodo + ": " + (encontrado() ? estudiante.toString() : "no encontrado")
                + " | comparaciones = " + comparaciones;
    }
}
