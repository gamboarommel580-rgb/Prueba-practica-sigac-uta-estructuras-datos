package servicios;

import estructuras.ElementoNoEncontradoException;
import modelo.Conexion;
import modelo.Estudiante;
import modelo.Operacion;
import modelo.Solicitud;

//PATRON FACHADA: integra los modulos. Todos comparten el mismo estado:
//  - un estudiante registrado entra al vector, a la lista, al ABB y al arbol general a la vez
//  - una solicitud solo se acepta si la matricula existe (M1) y el equipo existe en la red (M4)
//  - cada operacion reversible se apila en el historial (M2) y deshacer aplica la inversa
public class SistemaSIGAC {

    private final ServicioEstudiantes estudiantes = new ServicioEstudiantes();
    private final ServicioAcademico academico;
    private final ServicioAtencion atencion = new ServicioAtencion();
    private final ServicioRedLaboratorio laboratorio = new ServicioRedLaboratorio();
    private final ServicioRedSocial social = new ServicioRedSocial();

    //JERARQUIA BASE: UTA -> FISEI -> Software / Tecnologias de la Informacion
    public SistemaSIGAC() {
        academico = new ServicioAcademico("UTA", "Universidad Tecnica de Ambato");
        academico.agregarFacultad("FISEI", "Facultad de Ingenieria en Sistemas, Electronica e Industrial");
        academico.agregarCarrera("FISEI", "SW", "Ingenieria en Software");
        academico.agregarCarrera("FISEI", "TI", "Tecnologias de la Informacion");
    }

    // ---------------- M1 + M3 ----------------

    public Estudiante registrarEstudiante(int matricula, String nombres, String carrera) {
        return registrarEstudiante(matricula, nombres, carrera, true);
    }

    private Estudiante registrarEstudiante(int matricula, String nombres, String carrera, boolean conHistorial) {
        Estudiante e = new Estudiante(matricula, nombres, carrera);
        //se valida la carrera antes de insertar para no dejar el sistema a medias
        if (!academico.existeCarrera(e.getCodigoCarrera())) {
            throw new ElementoNoEncontradoException("No existe la carrera " + carrera);
        }
        estudiantes.registrar(e);
        try {
            academico.vincularEstudiante(e);
        } catch (RuntimeException ex) {
            //si el arbol general rechaza el nodo, se revierte M1 para no dejar el sistema a medias
            estudiantes.eliminar(e.getMatricula());
            throw ex;
        }
        if (conHistorial) {
            atencion.registrarOperacion(Operacion.registro(e));
        }
        return e;
    }

    public void actualizarEstudiante(int matricula, String nombres, String carrera) {
        Estudiante actual = estudiantes.obtener(matricula);
        if (carrera == null || !academico.existeCarrera(carrera.trim())) {
            throw new ElementoNoEncontradoException("No existe la carrera " + carrera);
        }
        //se valida el nombre antes de tocar nada
        Estudiante nuevo = new Estudiante(matricula, nombres, carrera);
        if (nuevo.getNombres().equals(actual.getNombres())
                && nuevo.getCodigoCarrera().equals(actual.getCodigoCarrera())) {
            return; //sin cambios: no se apila nada
        }
        int posArbol = academico.posicionEstudiante(matricula);
        Estudiante anterior = estudiantes.actualizar(matricula, nombres, carrera);
        //si sigue en la misma carrera conserva su lugar; si cambia, entra al final de la nueva
        boolean mismaCarrera = anterior.getCodigoCarrera().equals(nuevo.getCodigoCarrera());
        reubicarEnArbol(matricula, mismaCarrera ? posArbol : -1);
        atencion.registrarOperacion(Operacion.actualizacion(anterior, posArbol));
    }

    //ELIMINACION SEGURA: no se borra a un estudiante con solicitudes en espera
    public Estudiante eliminarEstudiante(int matricula) {
        if (estudiantes.estaVacio()) {
            throw new estructuras.EstructuraVaciaException("No hay estudiantes registrados");
        }
        estudiantes.obtener(matricula);
        if (atencion.tienePendientes(matricula)) {
            throw new IllegalStateException("El estudiante " + matricula + " tiene solicitudes pendientes");
        }
        int posV = estudiantes.posicionEnVector(matricula);
        int posL = estudiantes.posicionEnLista(matricula);
        int posA = academico.posicionEstudiante(matricula);
        Estudiante e = estudiantes.eliminar(matricula);
        academico.desvincularEstudiante(matricula);
        atencion.registrarOperacion(Operacion.eliminacion(e, posV, posL, posA));
        return e;
    }

    // ---------------- M2 ----------------

    public Solicitud registrarSolicitud(int matricula, String idEquipo, String motivo) {
        return registrarSolicitud(matricula, idEquipo, motivo, true);
    }

    private Solicitud registrarSolicitud(int matricula, String idEquipo, String motivo, boolean conHistorial) {
        estudiantes.obtener(matricula);
        if (idEquipo == null || idEquipo.trim().isEmpty() || !laboratorio.existeEquipo(idEquipo.trim())) {
            throw new ElementoNoEncontradoException("No existe el equipo " + idEquipo);
        }
        Solicitud s = atencion.registrarSolicitud(matricula, idEquipo, motivo);
        if (conHistorial) {
            atencion.registrarOperacion(Operacion.solicitudRegistrada(s));
        }
        return s;
    }

    public Solicitud atenderSolicitud() {
        Solicitud s = atencion.atenderSiguiente();
        atencion.registrarOperacion(Operacion.atencion(s));
        return s;
    }

    //DESHACER: saca la ultima operacion (LIFO) y aplica la inversa
    public Operacion deshacer() {
        Operacion op = atencion.sacarUltimaOperacion();
        try {
            aplicarInversa(op);
        } catch (RuntimeException ex) {
            //si no se pudo deshacer, la operacion vuelve a la pila para no perderla
            atencion.registrarOperacion(op);
            throw ex;
        }
        return op;
    }

    private void aplicarInversa(Operacion op) {
        Estudiante e = op.getEstudiante();
        switch (op.getTipo()) {
            case REGISTRAR_ESTUDIANTE:
                if (atencion.tienePendientes(e.getMatricula())) {
                    throw new IllegalStateException("No se puede deshacer: " + e.getMatricula()
                            + " tiene solicitudes pendientes");
                }
                estudiantes.eliminar(e.getMatricula());
                academico.desvincularEstudiante(e.getMatricula());
                break;
            case ACTUALIZAR_ESTUDIANTE:
                estudiantes.actualizar(e.getMatricula(), e.getNombres(), e.getCodigoCarrera());
                reubicarEnArbol(e.getMatricula(), op.getPosicionArbol());
                break;
            case ELIMINAR_ESTUDIANTE: {
                if (!academico.existeCarrera(e.getCodigoCarrera())) {
                    throw new ElementoNoEncontradoException("No existe la carrera " + e.getCodigoCarrera());
                }
                Estudiante restaurado = e.copia();
                estudiantes.registrarEn(restaurado, op.getPosicionVector(), op.getPosicionLista());
                try {
                    academico.vincularEstudianteEn(restaurado, op.getPosicionArbol());
                } catch (RuntimeException ex) {
                    //se revierte la restauracion parcial antes de devolver la operacion a la pila
                    estudiantes.eliminar(restaurado.getMatricula());
                    throw ex;
                }
                break;
            }
            case AGREGAR_CONEXION:
                laboratorio.desconectar(op.getConexion().getOrigen(), op.getConexion().getDestino());
                break;
            case ELIMINAR_CONEXION:
                laboratorio.reconectar(op.getConexion());
                break;
            case REGISTRAR_SOLICITUD:
                if (!atencion.retirarSolicitud(op.getSolicitud().getId())) {
                    throw new IllegalStateException("La solicitud S" + op.getSolicitud().getId() + " ya no esta en la cola");
                }
                break;
            case ATENDER_SOLICITUD:
                atencion.devolverAlFrente(op.getSolicitud());
                break;
            default:
                throw new IllegalStateException("Operacion desconocida");
        }
    }

    // ---------------- M4 ----------------

    public void agregarEquipo(String id, String tipo) {
        laboratorio.agregarEquipo(id, tipo);
    }

    public Conexion conectarEquipos(String origen, String destino, double peso) {
        return conectarEquipos(origen, destino, peso, true);
    }

    private Conexion conectarEquipos(String origen, String destino, double peso, boolean conHistorial) {
        Conexion c = laboratorio.conectar(origen, destino, peso);
        if (conHistorial) {
            atencion.registrarOperacion(Operacion.conexionAgregada(c));
        }
        return c;
    }

    //ELIMINA EL EQUIPO CON SUS ARISTAS ENTRANTES Y SALIENTES.
    //No se permite si hay solicitudes en espera para ese equipo. Esta operacion no se deshace y,
    //como el vertice deja de existir, se quitan del historial las operaciones que dependian de el.
    //Devuelve cuantas operaciones del historial se descartaron
    public int eliminarEquipo(String id) {
        if (id == null || id.trim().isEmpty() || !laboratorio.existeEquipo(id.trim())) {
            throw new ElementoNoEncontradoException("No existe el equipo " + id);
        }
        if (atencion.tienePendientesEnEquipo(id.trim())) {
            throw new IllegalStateException("El equipo " + id + " tiene solicitudes pendientes");
        }
        laboratorio.eliminarEquipo(id.trim());
        return atencion.descartarOperacionesDeEquipo(id.trim());
    }

    public Conexion desconectarEquipos(String origen, String destino) {
        Conexion c = laboratorio.desconectar(origen, destino);
        atencion.registrarOperacion(Operacion.conexionEliminada(c));
        return c;
    }

    // ---------------- CASO INTEGRADOR ----------------

    //CARGA LOS DATOS DEL CASO OBLIGATORIO. La carga inicial no entra al historial
    public static SistemaSIGAC conCasoIntegrador() {
        SistemaSIGAC s = new SistemaSIGAC();
        int[] matriculas = {50, 30, 70, 20, 40, 60, 80};
        for (int m : matriculas) {
            s.registrarEstudiante(m, "Estudiante " + m, "SW", false);
        }

        s.agregarEquipo("PC1", "PC");
        s.agregarEquipo("SW1", "SWITCH");
        s.agregarEquipo("SRV1", "SERVIDOR");
        s.agregarEquipo("PC2", "PC");
        s.agregarEquipo("PC3", "PC");
        s.agregarEquipo("PC4", "PC");
        //orden de insercion de aristas: define el orden de vecinos de PC1 (SW1 primero, PC3 despues)
        s.conectarEquipos("PC1", "SW1", 2, false);
        s.conectarEquipos("SW1", "SRV1", 1, false);
        s.conectarEquipos("SRV1", "PC2", 3, false);
        s.conectarEquipos("PC1", "PC3", 4, false);
        //PC4 queda aislado

        String[] usuarios = {"Ana", "Beto", "Carla", "Diego", "Elena"};
        for (String u : usuarios) {
            s.social.registrarUsuario(u);
        }
        s.social.registrarAmistad("Ana", "Beto");
        s.social.registrarAmistad("Ana", "Carla");
        s.social.registrarAmistad("Beto", "Diego");
        s.social.registrarAmistad("Carla", "Diego");
        s.social.registrarAmistad("Carla", "Elena");

        s.registrarSolicitud(50, "PC1", "Instalar JDK", false);
        s.registrarSolicitud(70, "PC2", "Revisar conexion de red", false);
        s.registrarSolicitud(20, "PC3", "Acceso al servidor", false);
        return s;
    }

    // ---------------- ACCESO A MODULOS ----------------

    private void reubicarEnArbol(int matricula, int posicion) {
        academico.desvincularEstudiante(matricula);
        academico.vincularEstudianteEn(estudiantes.obtener(matricula), posicion);
    }

    public ServicioEstudiantes getEstudiantes() {
        return estudiantes;
    }

    public ServicioAcademico getAcademico() {
        return academico;
    }

    public ServicioAtencion getAtencion() {
        return atencion;
    }

    public ServicioRedLaboratorio getLaboratorio() {
        return laboratorio;
    }

    public ServicioRedSocial getSocial() {
        return social;
    }

    //VERIFICA QUE LOS MODULOS NO SE HAYAN DESINCRONIZADO
    public boolean estadoConsistente() {
        if (!estudiantes.estructurasSincronizadas()) {
            return false;
        }
        int enArbol = academico.contar(modelo.TipoUnidad.ESTUDIANTE);
        if (enArbol != estudiantes.cantidad()) {
            return false;
        }
        for (Estudiante e : estudiantes.getLista()) {
            final String codigo = ServicioAcademico.codigoEstudiante(e.getMatricula());
            boolean esta = academico.estudiantesDeCarrera(e.getCodigoCarrera())
                    .contiene(new estructuras.Condicion<String>() {
                        public boolean cumple(String c) {
                            return c.equals(codigo);
                        }
                    });
            if (!esta) {
                return false;
            }
        }
        //toda solicitud en espera apunta a un estudiante y a un equipo que existen
        estructuras.Cola<Solicitud> cola = atencion.getPendientes();
        int n = cola.tamanio();
        boolean solicitudesValidas = true;
        for (int i = 0; i < n; i++) {
            Solicitud s = cola.desencolar();
            if (!estudiantes.existe(s.getMatricula()) || !laboratorio.existeEquipo(s.getIdEquipo())) {
                solicitudesValidas = false;
            }
            cola.encolar(s);
        }
        return solicitudesValidas;
    }
}
