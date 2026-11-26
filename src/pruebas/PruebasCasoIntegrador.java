package pruebas;

import servicios.SistemaSIGAC;

//CASO INTEGRADOR OBLIGATORIO (apartado 6 del examen). Se ejecuta sobre UNA sola instancia,
//en el orden del enunciado, para comprobar que los modulos trabajan juntos
public class PruebasCasoIntegrador {

    private static final String T = "Integrador";

    public static void ejecutar(Verificador v) {
        final SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();

        v.igual("CI-01", "M3", T, "insertar 50,30,70,20,40,60,80 y mostrar inorden",
                "[20, 30, 40, 50, 60, 70, 80]", () -> s.getEstudiantes().inorden());
        v.igual("CI-02", "M3", T, "eliminar matricula 30 (dos hijos)", "30 - Estudiante 30 (SW)",
                () -> s.eliminarEstudiante(30));
        v.igual("CI-03", "M3", T, "inorden despues de eliminar 30", "[20, 40, 50, 60, 70, 80]",
                () -> s.getEstudiantes().inorden());
        v.igual("CI-04", "M3", T, "preorden y validez del ABB despues de eliminar 30 (40 sube)",
                "[50, 40, 20, 70, 60, 80] valido=true", () -> s.getEstudiantes().preorden()
                        + " valido=" + s.getEstudiantes().indiceValido());
        v.igual("CI-05", "M1/M3", T, "30 ya no esta en vector, lista, ABB ni arbol general",
                "false -1 false [E50, E70, E20, E40, E60, E80]", () -> s.getEstudiantes().buscarSecuencial(30).encontrado()
                        + " " + s.getEstudiantes().posicionEnLista(30)
                        + " " + s.getEstudiantes().buscarEnABB(30).encontrado()
                        + " " + s.getAcademico().estudiantesDeCarrera("SW"));
        v.igual("CI-06", "M2", T, "procesar tres solicitudes FIFO", "S1 S2 S3",
                () -> "S" + s.atenderSolicitud().getId() + " S" + s.atenderSolicitud().getId()
                        + " S" + s.atenderSolicitud().getId());
        v.igual("CI-07", "M2", T, "recuperar dos operaciones LIFO (deshacer x2)", "Atender S3 | Atender S2",
                () -> s.deshacer() + " | " + s.deshacer());
        v.igual("CI-08", "M2", T, "cola y historial despues de deshacer",
                "frente=S2 pendientes=2 historial=[Atender S1, Eliminar 30]",
                () -> "frente=S" + s.getAtencion().getPendientes().frente().getId()
                        + " pendientes=" + s.getAtencion().getPendientes().tamanio()
                        + " historial=" + s.getAtencion().getHistorial());
        v.igual("CI-09", "M4", T, "BFS desde PC1", "[PC1, SW1, PC3, SRV1, PC2]",
                () -> s.getLaboratorio().bfs("PC1"));
        v.igual("CI-10", "M4", T, "equipos no alcanzables desde PC1", "[PC4]",
                () -> s.getLaboratorio().noAlcanzables("PC1"));
        v.igual("CI-11", "M4", T, "DFS desde PC1", "[PC1, SW1, SRV1, PC2, PC3]",
                () -> s.getLaboratorio().dfs("PC1"));
        v.igual("CI-12", "M5", T, "recomendaciones para Ana",
                "[Diego (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])]",
                () -> s.getSocial().recomendar("Ana"));
        v.igual("CI-13", "M5", T, "Diego va antes que Elena y no aparecen Ana, Beto ni Carla", "Diego,Elena",
                () -> s.getSocial().recomendar("Ana").obtener(0).getUsuario() + ","
                        + s.getSocial().recomendar("Ana").obtener(1).getUsuario()
                        + (s.getSocial().recomendar("Ana").tamanio() == 2 ? "" : " (sobran recomendaciones)"));
        v.igual("CI-14", "M4", T, "eliminar arista SW1->SRV1", "SW1->SRV1(1.0)",
                () -> s.desconectarEquipos("SW1", "SRV1"));
        v.igual("CI-15", "M4", T, "BFS desde PC1 despues de eliminar la arista", "[PC1, SW1, PC3]",
                () -> s.getLaboratorio().bfs("PC1"));
        v.igual("CI-16", "M4", T, "no alcanzables despues de eliminar la arista", "[SRV1, PC2, PC4]",
                () -> s.getLaboratorio().noAlcanzables("PC1"));
        v.igual("CI-17", "M6", T, "estado compartido consistente al final del caso", "true",
                () -> s.estadoConsistente());
    }
}
