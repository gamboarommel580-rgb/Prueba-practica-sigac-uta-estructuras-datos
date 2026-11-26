package modelo;

//SOLICITUD DE ATENCION EN LABORATORIO: un estudiante pide usar o revisar un equipo de la red
public class Solicitud {

    private final int id;
    private final int matricula;
    private final String idEquipo;
    private final String motivo;

    public Solicitud(int id, int matricula, String idEquipo, String motivo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id de la solicitud debe ser positivo");
        }
        if (matricula <= 0) {
            throw new IllegalArgumentException("La matricula de la solicitud debe ser positiva");
        }
        if (idEquipo == null || idEquipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El equipo de la solicitud no puede ser null ni vacio");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo no puede ser null ni vacio");
        }
        this.id = id;
        this.matricula = matricula;
        this.idEquipo = idEquipo.trim();
        this.motivo = motivo.trim();
    }

    public int getId() {
        return id;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public String toString() {
        return "S" + id + "(mat " + matricula + ", " + idEquipo + ": " + motivo + ")";
    }
}
