package modelo;

//REGISTRO DE UNA OPERACION REVERSIBLE. Guarda lo justo para aplicar la operacion inversa
public class Operacion {

    private final TipoOperacion tipo;
    private final Estudiante estudiante;      //estado anterior del estudiante (registrar/actualizar/eliminar)
    private final int posicionVector;         //para reinsertar en el mismo lugar
    private final int posicionLista;
    private final int posicionArbol;          //lugar del estudiante entre los hijos de su carrera
    private final Conexion conexion;          //para conexiones de la red
    private final Solicitud solicitud;        //para atenciones

    private Operacion(TipoOperacion tipo, Estudiante estudiante, int posicionVector, int posicionLista,
                      int posicionArbol, Conexion conexion, Solicitud solicitud) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de operacion no puede ser null");
        }
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.posicionVector = posicionVector;
        this.posicionLista = posicionLista;
        this.posicionArbol = posicionArbol;
        this.conexion = conexion;
        this.solicitud = solicitud;
    }

    public static Operacion registro(Estudiante e) {
        return new Operacion(TipoOperacion.REGISTRAR_ESTUDIANTE, e.copia(), -1, -1, -1, null, null);
    }

    public static Operacion actualizacion(Estudiante anterior, int posArbol) {
        return new Operacion(TipoOperacion.ACTUALIZAR_ESTUDIANTE, anterior.copia(), -1, -1, posArbol, null, null);
    }

    public static Operacion eliminacion(Estudiante e, int posVector, int posLista, int posArbol) {
        return new Operacion(TipoOperacion.ELIMINAR_ESTUDIANTE, e.copia(), posVector, posLista, posArbol, null, null);
    }

    public static Operacion conexionAgregada(Conexion c) {
        return new Operacion(TipoOperacion.AGREGAR_CONEXION, null, -1, -1, -1, c, null);
    }

    public static Operacion conexionEliminada(Conexion c) {
        return new Operacion(TipoOperacion.ELIMINAR_CONEXION, null, -1, -1, -1, c, null);
    }

    public static Operacion solicitudRegistrada(Solicitud s) {
        return new Operacion(TipoOperacion.REGISTRAR_SOLICITUD, null, -1, -1, -1, null, s);
    }

    public static Operacion atencion(Solicitud s) {
        return new Operacion(TipoOperacion.ATENDER_SOLICITUD, null, -1, -1, -1, null, s);
    }

    //true si la operacion depende del equipo (conexion que lo toca o solicitud sobre ese equipo)
    public boolean involucraEquipo(String idEquipo) {
        if (conexion != null) {
            return conexion.getOrigen().equals(idEquipo) || conexion.getDestino().equals(idEquipo);
        }
        return solicitud != null && solicitud.getIdEquipo().equals(idEquipo);
    }

    public TipoOperacion getTipo() {
        return tipo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public int getPosicionVector() {
        return posicionVector;
    }

    public int getPosicionLista() {
        return posicionLista;
    }

    public int getPosicionArbol() {
        return posicionArbol;
    }

    public Conexion getConexion() {
        return conexion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    @Override
    public String toString() {
        switch (tipo) {
            case REGISTRAR_ESTUDIANTE:
                return "Registrar " + estudiante.getMatricula();
            case ACTUALIZAR_ESTUDIANTE:
                return "Actualizar " + estudiante.getMatricula();
            case ELIMINAR_ESTUDIANTE:
                return "Eliminar " + estudiante.getMatricula();
            case AGREGAR_CONEXION:
                return "Conectar " + conexion;
            case ELIMINAR_CONEXION:
                return "Desconectar " + conexion;
            case REGISTRAR_SOLICITUD:
                return "Registrar solicitud S" + solicitud.getId();
            default:
                return "Atender S" + solicitud.getId();
        }
    }
}
