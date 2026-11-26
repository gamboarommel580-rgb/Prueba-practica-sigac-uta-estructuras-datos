package pruebas;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import app.Main;
import servicios.SistemaSIGAC;

//REGRESION E INTEGRACION DE FLUJO COMPLETO: secuencias largas encadenadas y el menu real con un guion de entrada
public class PruebasRegresion {

    private static final String T = "Regresion";

    public static void ejecutar(Verificador v) {
        final SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();

        v.igual("RG-01", "M6", T, "secuencia: +90, +10(TI), atender S1, -50 (raiz), 70->TI, solicitud 90, -PC1->PC3, +PC3->PC4",
                "[10, 20, 30, 40, 60, 70, 80, 90] valido=true consistente=true TI=[E10, E70] hist=8", () -> {
                    s.registrarEstudiante(90, "Maria", "SW");
                    s.registrarEstudiante(10, "Luis", "TI");
                    s.atenderSolicitud();
                    s.eliminarEstudiante(50);
                    s.actualizarEstudiante(70, "Estudiante 70", "TI");
                    s.registrarSolicitud(90, "PC4", "Revision");
                    s.desconectarEquipos("PC1", "PC3");
                    s.conectarEquipos("PC3", "PC4", 5);
                    return s.getEstudiantes().inorden() + " valido=" + s.getEstudiantes().indiceValido()
                            + " consistente=" + s.estadoConsistente() + " TI=" + s.getAcademico().estudiantesDeCarrera("TI")
                            + " hist=" + s.getAtencion().getHistorial().tamanio();
                });
        v.igual("RG-02", "M6", T, "deshacer las 8 operaciones en orden inverso",
                "Conectar PC3->PC4(5.0), Desconectar PC1->PC3(4.0), Registrar solicitud S4, Actualizar 70, "
                        + "Eliminar 50, Atender S1, Registrar 10, Registrar 90", () -> {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 8; i++) {
                        sb.append(i == 0 ? "" : ", ").append(s.deshacer());
                    }
                    return sb.toString();
                });
        v.igual("RG-03", "M6", T, "estado restaurado: inorden, BFS, cola, vecinos de PC1 e historial como al inicio",
                "[20, 30, 40, 50, 60, 70, 80] [PC1, SW1, PC3, SRV1, PC2] [1, 2, 3] [SW1, PC3] hist=0 true", () ->
                        s.getEstudiantes().inorden() + " " + s.getLaboratorio().bfs("PC1")
                                + " " + PruebasUnitarias.idsPendientes(s.getAtencion())
                                + " " + s.getLaboratorio().getRed().vecinos("PC1")
                                + " hist=" + s.getAtencion().getHistorial().tamanio() + " " + s.estadoConsistente());
        v.igual("RG-04", "M1", T, "el 50 restaurado vuelve a su posicion original en el vector", "50",
                () -> s.getEstudiantes().getVector().obtener(0).getMatricula());
        v.igual("RG-05", "M2/M4", T, "conectar PC4->PC2, +90, eliminar PC4: se descarta la conexion y deshacer sigue funcionando",
                "descartadas=1 Registrar 90 hist=0 PC4=false", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.conectarEquipos("PC4", "PC2", 2);
                    x.registrarEstudiante(90, "Maria", "SW");
                    int d = x.eliminarEquipo("PC4");
                    return "descartadas=" + d + " " + x.deshacer() + " hist=" + x.getAtencion().getHistorial().tamanio()
                            + " PC4=" + x.getLaboratorio().existeEquipo("PC4");
                });
        v.igual("RG-06", "M2/M4", T, "atender S1..S3, registrar y atender S4 en PC4, eliminar PC4 y deshacer 3 veces: S4 no revive",
                "descartadas=2 [1, 2, 3]", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.atenderSolicitud();
                    x.atenderSolicitud();
                    x.atenderSolicitud();
                    x.registrarSolicitud(40, "PC4", "Revision");
                    x.atenderSolicitud();
                    //el registro y la atencion de S4 dependen de PC4: se descartan
                    int d = x.eliminarEquipo("PC4");
                    x.deshacer();
                    x.deshacer();
                    x.deshacer();
                    return "descartadas=" + d + " " + PruebasUnitarias.idsPendientes(x.getAtencion());
                });

        v.igual("RG-07", "M6", T, "100 registros (100..199) y eliminar los pares", "57 true true", () -> {
            SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
            for (int m = 100; m < 200; m++) {
                x.registrarEstudiante(m, "Est " + m, (m % 2 == 0) ? "SW" : "TI");
            }
            for (int m = 100; m < 200; m += 2) {
                x.eliminarEstudiante(m);
            }
            return x.getEstudiantes().cantidad() + " " + x.getEstudiantes().indiceValido() + " " + x.estadoConsistente();
        });
        v.igual("RG-08", "M6", T, "repetir el flujo de RG-07 en una instancia nueva y deshacer las 150 operaciones: vuelve al estado inicial",
                "7 0 true [20, 30, 40, 50, 60, 70, 80] [50, 30, 70, 20, 40, 60, 80] [E50, E30, E70, E20, E40, E60, E80]", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    for (int m = 100; m < 200; m++) {
                        x.registrarEstudiante(m, "Est " + m, "SW");
                    }
                    for (int m = 100; m < 200; m += 2) {
                        x.eliminarEstudiante(m);
                    }
                    for (int i = 0; i < 150; i++) {
                        x.deshacer();
                    }
                    return x.getEstudiantes().cantidad() + " " + x.getAtencion().getHistorial().tamanio() + " "
                            + x.estadoConsistente() + " " + x.getEstudiantes().inorden() + " "
                            + matriculasVector(x) + " " + x.getAcademico().estudiantesDeCarrera("SW");
                });
        v.igual("RG-09", "M6", T, "menu: M3 recorridos + M1 eliminar 30 + M3 inorden (guion de entrada)",
                "true true true", () -> {
                    String guion = "3\n5\n0\n1\n4\n30\n0\n3\n5\n0\n0\n";
                    String salida = correrMenu(guion);
                    return salida.contains("Inorden:   [20, 30, 40, 50, 60, 70, 80]") + " "
                            + salida.contains("Eliminado: 30 - Estudiante 30 (SW)") + " "
                            + salida.contains("Inorden:   [20, 40, 50, 60, 70, 80]");
                });
        v.igual("RG-10", "M6", T, "menu: entrada no numerica y error controlado sin cerrar el programa",
                "true true true", () -> {
                    String guion = "abc\n1\n1\n50\nRepetido\nSW\n0\n0\n";
                    String salida = correrMenu(guion);
                    return salida.contains("Entrada invalida") + " " + salida.contains("ERROR: Ya existe un estudiante con matricula 50")
                            + " " + salida.contains("Hasta luego.");
                });
        v.igual("RG-11", "M6", T, "menu: opcion 6 ejecuta el caso integrador completo", "true true true", () -> {
            String salida = correrMenu("6\n0\n");
            return salida.contains("No alcanzables: [PC4]") + " "
                    + salida.contains("Recomendaciones para Ana: [Diego (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])]")
                    + " " + salida.contains("No alcanzables: [SRV1, PC2, PC4]");
        });
        v.igual("RG-12", "M6", T, "menu: fin de entrada inesperado no lanza excepcion", "true",
                () -> correrMenu("1\n").contains("Fin de la entrada."));
        v.igual("RG-13", "M6", T, "si el arbol general rechaza el nodo, el registro se revierte en vector, lista y ABB",
                "false 7 0", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.getAcademico().vincularEstudiante(new modelo.Estudiante(95, "Fantasma", "SW"));
                    try {
                        x.registrarEstudiante(95, "Real", "SW");
                    } catch (estructuras.ElementoDuplicadoException ok) {
                        //se espera
                    }
                    return x.getEstudiantes().existe(95) + " " + x.getEstudiantes().cantidad() + " "
                            + x.getAtencion().getHistorial().tamanio();
                });
        v.igual("RG-14", "M6", T, "si deshacer una eliminacion falla en el arbol, M1 no queda restaurado a medias",
                "error false hist=1", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.eliminarEstudiante(40);
                    x.getAcademico().vincularEstudiante(new modelo.Estudiante(40, "Fantasma", "SW"));
                    String r;
                    try {
                        x.deshacer();
                        r = "sin error";
                    } catch (RuntimeException ok) {
                        r = "error";
                    }
                    return r + " " + x.getEstudiantes().existe(40) + " hist=" + x.getAtencion().getHistorial().tamanio();
                });
        v.igual("RG-15", "M3", T, "eliminar 6 estudiantes y deshacer: el orden de hijos de SW vuelve a ser el original",
                "[E50, E30, E70, E20, E40, E60, E80]", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.eliminarEstudiante(30);
                    x.eliminarEstudiante(40);
                    x.eliminarEstudiante(60);
                    x.eliminarEstudiante(80);
                    x.actualizarEstudiante(70, "Otro nombre", "SW");
                    x.actualizarEstudiante(20, "Estudiante 20", "TI");
                    for (int i = 0; i < 6; i++) {
                        x.deshacer();
                    }
                    return x.getAcademico().estudiantesDeCarrera("SW");
                });
        v.igual("RG-16", "M2", T, "actualizar sin cambios no apila una operacion", "0",
                () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.actualizarEstudiante(50, "Estudiante 50", "SW");
                    return x.getAtencion().getHistorial().tamanio();
                });
        v.igual("RG-17", "M4/M2", T, "conectar ' PC4 '->PC1 con espacios, eliminar PC4 y deshacer: la operacion se descarta",
                "1 [] [SW1, PC3]", () -> {
                    SistemaSIGAC x = SistemaSIGAC.conCasoIntegrador();
                    x.conectarEquipos(" PC4 ", "PC1", 1);
                    int descartadas = x.eliminarEquipo("PC4");
                    return descartadas + " " + x.getAtencion().getHistorial() + " "
                            + x.getLaboratorio().getRed().vecinos("PC1");
                });
    }

    static String matriculasVector(SistemaSIGAC x) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < x.getEstudiantes().getVector().tamanio(); i++) {
            sb.append(i == 0 ? "" : ", ").append(x.getEstudiantes().getVector().obtener(i).getMatricula());
        }
        return sb.append("]").toString();
    }

    static String correrMenu(String guion) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer);
        new Main(new Scanner(guion), out, SistemaSIGAC.conCasoIntegrador()).ejecutar();
        out.flush();
        return buffer.toString();
    }
}
