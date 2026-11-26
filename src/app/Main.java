package app;

import java.io.PrintStream;
import java.util.Scanner;

import estructuras.Vector;
import modelo.Operacion;
import modelo.Recomendacion;
import modelo.Solicitud;
import pruebas.EjecutorPruebas;
import servicios.SistemaSIGAC;

//PUNTO DE ENTRADA. Menu por consola que opera sobre UNA sola instancia de SistemaSIGAC
public class Main {

    //se lanza cuando ya no hay mas entrada (por ejemplo al probar el menu con un guion)
    private static class FinDeEntrada extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    private final Scanner in;
    private final PrintStream out;
    private SistemaSIGAC sistema;

    public Main(Scanner in, PrintStream out, SistemaSIGAC sistema) {
        this.in = in;
        this.out = out;
        this.sistema = sistema;
    }

    public static void main(String[] args) {
        new Main(new Scanner(System.in), System.out, SistemaSIGAC.conCasoIntegrador()).ejecutar();
    }

    public void ejecutar() {
        out.println("SIGAC-UTA - Sistema Inteligente de Gestion Academica y Conectividad");
        out.println("(se cargaron los datos del caso integrador para empezar)");
        try {
            int opcion;
            do {
                out.println("\n========== MENU PRINCIPAL ==========");
                out.println("1. M1 Gestion de estudiantes (vector y lista enlazada)");
                out.println("2. M2 Atencion de solicitudes e historial (cola y pila)");
                out.println("3. M3 Organizacion academica (arbol general y ABB)");
                out.println("4. M4 Red de laboratorios (grafo dirigido)");
                out.println("5. M5 Red social y recomendaciones (grafo no dirigido)");
                out.println("6. Ejecutar caso integrador paso a paso");
                out.println("7. Reiniciar con los datos del caso integrador");
                out.println("8. Reiniciar con el sistema vacio (solo la jerarquia base)");
                out.println("9. Ejecutar todas las pruebas automatizadas");
                out.println("0. Salir");
                opcion = leerEntero("Opcion: ");
                switch (opcion) {
                    case 1: menuEstudiantes(); break;
                    case 2: menuAtencion(); break;
                    case 3: menuAcademico(); break;
                    case 4: menuLaboratorio(); break;
                    case 5: menuSocial(); break;
                    case 6:
                        out.println("(el caso se ejecuta sobre datos nuevos y reemplaza el estado actual del menu)");
                        sistema = DemoCasoIntegrador.ejecutar(out);
                        break;
                    case 7: sistema = SistemaSIGAC.conCasoIntegrador(); out.println("Datos del caso cargados."); break;
                    case 8: sistema = new SistemaSIGAC(); out.println("Sistema reiniciado."); break;
                    case 9: EjecutorPruebas.ejecutarTodo(out, false); break;
                    case 0: out.println("Hasta luego."); break;
                    default: out.println("Opcion no valida.");
                }
            } while (opcion != 0);
        } catch (FinDeEntrada fin) {
            out.println("\nFin de la entrada.");
        }
    }

    // ---------------- M1 ----------------

    private void menuEstudiantes() {
        int op;
        do {
            out.println("\n--- M1 ESTUDIANTES ---");
            out.println("1. Registrar  2. Buscar (secuencial vs ABB)  3. Actualizar  4. Eliminar");
            out.println("5. Listar vector  6. Listar lista enlazada  0. Volver");
            op = leerEntero("Opcion: ");
            try {
                switch (op) {
                    case 1: {
                        int m = leerEntero("Matricula: ");
                        String n = leerTexto("Nombres: ");
                        String c = leerTexto("Codigo de carrera (SW, TI...): ");
                        out.println("Registrado: " + sistema.registrarEstudiante(m, n, c));
                        break;
                    }
                    case 2: {
                        int m = leerEntero("Matricula a buscar: ");
                        out.println(sistema.getEstudiantes().buscarSecuencial(m));
                        out.println(sistema.getEstudiantes().buscarEnABB(m));
                        break;
                    }
                    case 3: {
                        int m = leerEntero("Matricula: ");
                        String n = leerTexto("Nuevos nombres: ");
                        String c = leerTexto("Nueva carrera: ");
                        sistema.actualizarEstudiante(m, n, c);
                        out.println("Actualizado: " + sistema.getEstudiantes().obtener(m));
                        break;
                    }
                    case 4: {
                        int m = leerEntero("Matricula a eliminar: ");
                        out.println("Eliminado: " + sistema.eliminarEstudiante(m));
                        break;
                    }
                    case 5: out.println("Vector: " + sistema.getEstudiantes().getVector()); break;
                    case 6: out.println("Lista: " + sistema.getEstudiantes().getLista()); break;
                    case 0: break;
                    default: out.println("Opcion no valida.");
                }
            } catch (FinDeEntrada fin) {
                throw fin;
            } catch (RuntimeException ex) {
                out.println("ERROR: " + ex.getMessage());
            }
        } while (op != 0);
    }

    // ---------------- M2 ----------------

    private void menuAtencion() {
        int op;
        do {
            out.println("\n--- M2 ATENCION E HISTORIAL ---");
            out.println("1. Registrar solicitud  2. Atender siguiente (FIFO)  3. Ver pendientes");
            out.println("4. Ver atendidas  5. Deshacer ultima operacion (LIFO)  6. Ver historial  0. Volver");
            op = leerEntero("Opcion: ");
            try {
                switch (op) {
                    case 1: {
                        int m = leerEntero("Matricula: ");
                        String e = leerTexto("Equipo: ");
                        String mo = leerTexto("Motivo: ");
                        out.println("En cola: " + sistema.registrarSolicitud(m, e, mo));
                        break;
                    }
                    case 2: {
                        Solicitud s = sistema.atenderSolicitud();
                        out.println("Atendida: " + s);
                        break;
                    }
                    case 3: out.println("Pendientes: " + sistema.getAtencion().getPendientes()); break;
                    case 4: out.println("Atendidas: " + sistema.getAtencion().getAtendidas()); break;
                    case 5: {
                        Operacion o = sistema.deshacer();
                        out.println("Se deshizo: " + o);
                        break;
                    }
                    case 6: out.println("Historial (cima primero): " + sistema.getAtencion().getHistorial()); break;
                    case 0: break;
                    default: out.println("Opcion no valida.");
                }
            } catch (FinDeEntrada fin) {
                throw fin;
            } catch (RuntimeException ex) {
                out.println("ERROR: " + ex.getMessage());
            }
        } while (op != 0);
    }

    // ---------------- M3 ----------------

    private void menuAcademico() {
        int op;
        do {
            out.println("\n--- M3 ORGANIZACION ACADEMICA ---");
            out.println("1. Ver jerarquia  2. Agregar facultad  3. Agregar carrera  4. Estudiantes de una carrera");
            out.println("5. Recorridos del ABB  6. Buscar en ABB  7. Validar ABB  0. Volver");
            out.println("(la insercion y eliminacion en el ABB se hace desde M1, porque es el indice de estudiantes)");
            op = leerEntero("Opcion: ");
            try {
                switch (op) {
                    case 1: out.print(sistema.getAcademico().dibujar()); break;
                    case 2: {
                        String c = leerTexto("Codigo: ");
                        String n = leerTexto("Nombre: ");
                        sistema.getAcademico().agregarFacultad(c, n);
                        out.println("Facultad agregada.");
                        break;
                    }
                    case 3: {
                        String f = leerTexto("Codigo de la facultad: ");
                        String c = leerTexto("Codigo de la carrera: ");
                        String n = leerTexto("Nombre: ");
                        sistema.getAcademico().agregarCarrera(f, c, n);
                        out.println("Carrera agregada.");
                        break;
                    }
                    case 4: {
                        String c = leerTexto("Codigo de carrera: ");
                        out.println(sistema.getAcademico().estudiantesDeCarrera(c));
                        break;
                    }
                    case 5:
                        out.println("Preorden:  " + sistema.getEstudiantes().preorden());
                        out.println("Inorden:   " + sistema.getEstudiantes().inorden());
                        out.println("Posorden:  " + sistema.getEstudiantes().posorden());
                        break;
                    case 6: {
                        int m = leerEntero("Matricula: ");
                        out.println(sistema.getEstudiantes().buscarEnABB(m));
                        break;
                    }
                    case 7:
                        out.println("ABB valido: " + sistema.getEstudiantes().indiceValido()
                                + " | altura: " + sistema.getEstudiantes().alturaIndice()
                                + " | nodos: " + sistema.getEstudiantes().cantidad());
                        break;
                    case 0: break;
                    default: out.println("Opcion no valida.");
                }
            } catch (FinDeEntrada fin) {
                throw fin;
            } catch (RuntimeException ex) {
                out.println("ERROR: " + ex.getMessage());
            }
        } while (op != 0);
    }

    // ---------------- M4 ----------------

    private void menuLaboratorio() {
        int op;
        do {
            out.println("\n--- M4 RED DE LABORATORIOS ---");
            out.println("1. Agregar equipo  2. Conectar  3. Desconectar  4. Eliminar equipo");
            out.println("5. BFS / DFS / no alcanzables  6. Ver conexiones  7. Hay camino  0. Volver");
            op = leerEntero("Opcion: ");
            try {
                switch (op) {
                    case 1: {
                        String id = leerTexto("Id: ");
                        String t = leerTexto("Tipo (PC, SWITCH, SERVIDOR...): ");
                        sistema.agregarEquipo(id, t);
                        out.println("Equipo agregado.");
                        break;
                    }
                    case 2: {
                        String o = leerTexto("Origen: ");
                        String d = leerTexto("Destino: ");
                        double p = leerDecimal("Peso: ");
                        out.println("Conectado: " + sistema.conectarEquipos(o, d, p));
                        break;
                    }
                    case 3: {
                        String o = leerTexto("Origen: ");
                        String d = leerTexto("Destino: ");
                        out.println("Desconectado: " + sistema.desconectarEquipos(o, d));
                        break;
                    }
                    case 4: {
                        String id = leerTexto("Id: ");
                        int descartadas = sistema.eliminarEquipo(id);
                        out.println("Equipo y conexiones entrantes/salientes eliminados.");
                        if (descartadas > 0) {
                            out.println("Se quitaron del historial " + descartadas + " operaciones que dependian de " + id + ".");
                        }
                        break;
                    }
                    case 5: {
                        String o = leerTexto("Origen: ");
                        out.println(sistema.getLaboratorio().resumenConectividad(o));
                        break;
                    }
                    case 6: out.println(sistema.getLaboratorio().getRed().describirAristas()); break;
                    case 7: {
                        String o = leerTexto("Origen: ");
                        String d = leerTexto("Destino: ");
                        out.println("Hay camino: " + sistema.getLaboratorio().hayCamino(o, d));
                        break;
                    }
                    case 0: break;
                    default: out.println("Opcion no valida.");
                }
            } catch (FinDeEntrada fin) {
                throw fin;
            } catch (RuntimeException ex) {
                out.println("ERROR: " + ex.getMessage());
            }
        } while (op != 0);
    }

    // ---------------- M5 ----------------

    private void menuSocial() {
        int op;
        do {
            out.println("\n--- M5 RED SOCIAL ---");
            out.println("1. Registrar usuario  2. Registrar amistad  3. Eliminar amistad");
            out.println("4. Ver amigos  5. Recomendar contactos  0. Volver");
            op = leerEntero("Opcion: ");
            try {
                switch (op) {
                    case 1: sistema.getSocial().registrarUsuario(leerTexto("Nombre: ")); out.println("Registrado."); break;
                    case 2: {
                        String a = leerTexto("Usuario A: ");
                        String b = leerTexto("Usuario B: ");
                        sistema.getSocial().registrarAmistad(a, b);
                        out.println("Amistad registrada.");
                        break;
                    }
                    case 3: {
                        String a = leerTexto("Usuario A: ");
                        String b = leerTexto("Usuario B: ");
                        sistema.getSocial().eliminarAmistad(a, b);
                        out.println("Amistad eliminada.");
                        break;
                    }
                    case 4: out.println(sistema.getSocial().amigos(leerTexto("Usuario: "))); break;
                    case 5: {
                        Vector<Recomendacion> r = sistema.getSocial().recomendar(leerTexto("Usuario: "));
                        out.println(r.estaVacio() ? "Sin contactos de segundo grado." : r.toString());
                        break;
                    }
                    case 0: break;
                    default: out.println("Opcion no valida.");
                }
            } catch (FinDeEntrada fin) {
                throw fin;
            } catch (RuntimeException ex) {
                out.println("ERROR: " + ex.getMessage());
            }
        } while (op != 0);
    }

    // ---------------- LECTURA VALIDADA ----------------

    private String leerLinea(String mensaje) {
        out.print(mensaje);
        if (!in.hasNextLine()) {
            throw new FinDeEntrada();
        }
        return in.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            String linea = leerLinea(mensaje);
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException ex) {
                out.println("Entrada invalida, escribe un numero entero.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            String linea = leerLinea(mensaje);
            try {
                return Double.parseDouble(linea.replace(',', '.'));
            } catch (NumberFormatException ex) {
                out.println("Entrada invalida, escribe un numero.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            String linea = leerLinea(mensaje);
            if (!linea.isEmpty()) {
                return linea;
            }
            out.println("El valor no puede estar vacio.");
        }
    }
}
