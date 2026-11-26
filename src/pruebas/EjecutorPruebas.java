package pruebas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

//EJECUTOR CENTRAL DE PRUEBAS. Uso:
//  java -cp bin pruebas.EjecutorPruebas                 -> corre todo y muestra resumen
//  java -cp bin pruebas.EjecutorPruebas --detalle       -> muestra cada caso
//  java -cp bin pruebas.EjecutorPruebas --matriz RUTA   -> ademas escribe la matriz en Markdown
public class EjecutorPruebas {

    public static void main(String[] args) throws IOException {
        boolean detalle = false;
        String rutaMatriz = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--detalle")) {
                detalle = true;
            } else if (args[i].equals("--matriz") && i + 1 < args.length) {
                rutaMatriz = args[++i];
            }
        }
        Verificador v = ejecutarTodo(System.out, detalle);
        if (rutaMatriz != null) {
            escribirMatriz(v, rutaMatriz);
            System.out.println("Matriz escrita en " + rutaMatriz);
        }
        if (v.aprobados() != v.total()) {
            System.exit(1);
        }
    }

    public static Verificador ejecutarTodo(PrintStream out, boolean detalle) {
        Verificador v = new Verificador();
        PruebasUnitarias.ejecutar(v);
        PruebasCasoIntegrador.ejecutar(v);
        PruebasNormales.ejecutar(v);
        PruebasExtremos.ejecutar(v);
        PruebasErroneos.ejecutar(v);
        PruebasRegresion.ejecutar(v);
        out.println("\n===== PRUEBAS SIGAC-UTA =====");
        v.imprimir(out, !detalle);
        out.println("Resultado: " + v.aprobados() + "/" + v.total() + " pruebas aprobadas"
                + (v.aprobados() == v.total() ? " (100%)" : " - HAY FALLAS"));
        return v;
    }

    private static void escribirMatriz(Verificador v, String ruta) throws IOException {
        Writer w = new OutputStreamWriter(new FileOutputStream(ruta), "UTF-8");
        try {
            w.write("# Matriz de pruebas - SIGAC-UTA\n\n");
            w.write("Este archivo lo genera el ejecutor de pruebas (`java -cp bin pruebas.EjecutorPruebas --matriz docs/matriz-pruebas.md`), "
                    + "as\u00ed el resultado obtenido es el real de la \u00faltima ejecuci\u00f3n y no algo escrito a mano.\n\n");
            w.write("Total: " + v.aprobados() + "/" + v.total() + " aprobadas.\n\n");
            w.write("| ID | M\u00f3dulo | Tipo | Entrada | Resultado esperado | Resultado obtenido | Estado |\n");
            w.write("|---|---|---|---|---|---|---|\n");
            for (Verificador.Caso c : v.getCasos()) {
                w.write("| " + c.id + " | " + c.modulo + " | " + tipoConTilde(c.tipo) + " | " + celda(c.entrada) + " | "
                        + celda(c.esperado) + " | " + celda(c.obtenido) + " | " + (c.paso ? "Pas\u00f3" : "Fall\u00f3") + " |\n");
            }
        } finally {
            w.close();
        }
    }

    //en consola se usa texto sin tildes (la consola de Windows las muestra mal); en la matriz si van
    private static String tipoConTilde(String tipo) {
        if (tipo.equals("Erroneo")) {
            return "Err\u00f3neo";
        }
        if (tipo.equals("Regresion")) {
            return "Regresi\u00f3n";
        }
        return tipo;
    }

    private static String celda(String s) {
        return s.replace("|", "\\|").replace("\n", " ");
    }
}
