package pruebas;

import servicios.SistemaSIGAC;

//CASOS NORMALES: flujos esperados con datos validos y paso de datos entre modulos
public class PruebasNormales {

    private static final String T = "Normal";

    public static void ejecutar(Verificador v) {
        v.igual("N-01", "M1/M3", T, "registrar 90 en TI: entra al ABB y al arbol bajo TI", "true [E90] [20, 30, 40, 50, 60, 70, 80, 90]", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.registrarEstudiante(90, "Maria Perez", "TI");
            return s.getEstudiantes().existe(90) + " " + s.getAcademico().estudiantesDeCarrera("TI")
                    + " " + s.getEstudiantes().inorden();
        });
        v.igual("N-02", "M1/M2/M4", T, "solicitud de un estudiante nuevo para PC4 y atencion en su turno", "S4 S1", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.registrarEstudiante(90, "Maria Perez", "SW");
            int id = s.registrarSolicitud(90, "PC4", "Mantenimiento").getId();
            return "S" + id + " S" + s.atenderSolicitud().getId();
        });
        v.igual("N-03", "M1/M3", T, "actualizar carrera de 70 a TI lo mueve en el arbol general", "[E70] true", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.actualizarEstudiante(70, "Estudiante 70", "TI");
            return s.getAcademico().estudiantesDeCarrera("TI") + " " + s.estadoConsistente();
        });
        v.igual("N-04", "M4", T, "conectar PC3->PC4 vuelve alcanzable a PC4", "[PC1, SW1, PC3, SRV1, PC4, PC2] []", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.conectarEquipos("PC3", "PC4", 5);
            return s.getLaboratorio().bfs("PC1") + " " + s.getLaboratorio().noAlcanzables("PC1");
        });
        v.igual("N-05", "M5", T, "Ana se hace amiga de Diego y Diego de Elena (Fabian sin amigos): Diego sale y queda solo Elena", "[Elena (2 en comun: [Carla, Diego])]", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.getSocial().registrarUsuario("Fabian");
            s.getSocial().registrarAmistad("Ana", "Diego");
            s.getSocial().registrarAmistad("Diego", "Elena");
            return s.getSocial().recomendar("Ana");
        });
        v.igual("N-06", "M1", T, "comparar busqueda de 80: ABB hace menos comparaciones que la secuencial", "7 > 3", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.getEstudiantes().buscarSecuencial(80).getComparaciones() + " > "
                    + s.getEstudiantes().buscarEnABB(80).getComparaciones();
        });
        v.igual("N-07", "M4", T, "hay camino PC1->PC2 y no hay PC2->PC1 (aristas dirigidas)", "true false", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            return s.getLaboratorio().hayCamino("PC1", "PC2") + " " + s.getLaboratorio().hayCamino("PC2", "PC1");
        });
        v.igual("N-08", "M4", T, "eliminar equipo SW1: se quitan PC1->SW1 y SW1->SRV1", "[PC3] 2 [PC1, PC3]", () -> {
            SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
            s.eliminarEquipo("SW1");
            return s.getLaboratorio().getRed().vecinos("PC1") + " " + s.getLaboratorio().getRed().cantidadAristas()
                    + " " + s.getLaboratorio().bfs("PC1");
        });
    }
}
