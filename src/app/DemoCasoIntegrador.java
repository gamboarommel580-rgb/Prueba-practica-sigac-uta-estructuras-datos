package app;

import java.io.PrintStream;

import modelo.Operacion;
import modelo.Solicitud;
import servicios.SistemaSIGAC;

//MUESTRA EN PANTALLA LOS 6 PUNTOS DEL CASO INTEGRADOR OBLIGATORIO, EN EL ORDEN DEL ENUNCIADO
public class DemoCasoIntegrador {

    public static SistemaSIGAC ejecutar(PrintStream out) {
        SistemaSIGAC s = SistemaSIGAC.conCasoIntegrador();
        out.println("\n===== CASO INTEGRADOR SIGAC-UTA =====");
        out.println("Matriculas insertadas en orden: 50, 30, 70, 20, 40, 60, 80");
        out.println("Red: PC1->SW1->SRV1->PC2, PC1->PC3, PC4 aislado (dirigido, ponderado)");
        out.println("Social: Ana-Beto, Ana-Carla, Beto-Diego, Carla-Diego, Carla-Elena (no dirigido)");

        out.println("\n[1] Inorden del ABB: " + s.getEstudiantes().inorden());
        out.println("    Preorden: " + s.getEstudiantes().preorden() + " | Posorden: " + s.getEstudiantes().posorden());

        s.eliminarEstudiante(30);
        out.println("\n[2] Eliminar 30 (dos hijos -> se reemplaza por el sucesor inorden 40)");
        out.println("    Inorden: " + s.getEstudiantes().inorden());
        out.println("    Preorden: " + s.getEstudiantes().preorden());
        out.println("    ABB valido: " + s.getEstudiantes().indiceValido());

        out.println("\n[3] Cola de solicitudes: " + s.getAtencion().getPendientes());
        for (int i = 1; i <= 3; i++) {
            Solicitud sol = s.atenderSolicitud();
            out.println("    Atendida " + i + ": " + sol);
        }
        for (int i = 1; i <= 2; i++) {
            Operacion op = s.deshacer();
            out.println("    Deshacer " + i + " (LIFO): " + op);
        }
        out.println("    Cola despues de deshacer: " + s.getAtencion().getPendientes());
        out.println("    Historial (cima primero): " + s.getAtencion().getHistorial());

        out.println("\n[4] " + s.getLaboratorio().resumenConectividad("PC1").replace("\n", "\n    "));

        out.println("\n[5] Recomendaciones para Ana: " + s.getSocial().recomendar("Ana"));

        s.desconectarEquipos("SW1", "SRV1");
        out.println("\n[6] Se elimina la arista SW1->SRV1");
        out.println("    " + s.getLaboratorio().resumenConectividad("PC1").replace("\n", "\n    "));
        out.println("\nEstado integrado consistente: " + s.estadoConsistente());
        return s;
    }
}
