package modelo;

//ENTIDAD ESTUDIANTE. La matricula es el identificador unico
public final class Estudiante {

    private final int matricula;
    private String nombres;
    private String codigoCarrera;

    public Estudiante(int matricula, String nombres, String codigoCarrera) {
        if (matricula <= 0) {
            throw new IllegalArgumentException("La matricula debe ser un entero positivo");
        }
        this.matricula = matricula;
        setNombres(nombres);
        setCodigoCarrera(codigoCarrera);
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (nombres == null || nombres.trim().isEmpty()) {
            throw new IllegalArgumentException("Los nombres no pueden ser null ni vacios");
        }
        this.nombres = nombres.trim();
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        if (codigoCarrera == null || codigoCarrera.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo de carrera no puede ser null ni vacio");
        }
        this.codigoCarrera = codigoCarrera.trim();
    }

    public Estudiante copia() {
        return new Estudiante(matricula, nombres, codigoCarrera);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Estudiante && ((Estudiante) o).matricula == matricula;
    }

    @Override
    public int hashCode() {
        return matricula;
    }

    @Override
    public String toString() {
        return matricula + " - " + nombres + " (" + codigoCarrera + ")";
    }
}
