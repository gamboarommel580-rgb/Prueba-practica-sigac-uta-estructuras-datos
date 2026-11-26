package pruebas;

import java.io.PrintStream;

import estructuras.ListaEnlazada;

//VERIFICADOR DE PRUEBAS PROPIO (sin JUnit para que compile solo con javac)
//Cada caso guarda: id, modulo, tipo, entrada, esperado, obtenido y estado
public class Verificador {

    public interface Valor {
        Object obtener() throws Exception;
    }

    public interface Accion {
        void ejecutar() throws Exception;
    }

    public static class Caso {
        public final String id, modulo, tipo, entrada, esperado, obtenido;
        public final boolean paso;

        Caso(String id, String modulo, String tipo, String entrada, String esperado, String obtenido, boolean paso) {
            this.id = id;
            this.modulo = modulo;
            this.tipo = tipo;
            this.entrada = entrada;
            this.esperado = esperado;
            this.obtenido = obtenido;
            this.paso = paso;
        }
    }

    private final ListaEnlazada<Caso> casos = new ListaEnlazada<Caso>();

    //compara el texto esperado con String.valueOf(lo obtenido)
    public void igual(String id, String modulo, String tipo, String entrada, Object esperado, Valor valor) {
        String esp = String.valueOf(esperado);
        String obt;
        boolean paso;
        try {
            obt = String.valueOf(valor.obtener());
            paso = esp.equals(obt);
        } catch (Throwable ex) {
            obt = "Excepcion inesperada " + ex.getClass().getSimpleName() + ": " + ex.getMessage();
            paso = false;
        }
        casos.agregarAlFinal(new Caso(id, modulo, tipo, entrada, esp, obt, paso));
    }

    //pasa solo si la accion lanza la excepcion indicada (o una subclase)
    public void lanza(String id, String modulo, String tipo, String entrada,
                      Class<? extends Throwable> esperada, Accion accion) {
        String esp = esperada.getSimpleName();
        String obt;
        boolean paso;
        try {
            accion.ejecutar();
            obt = "No lanzo excepcion";
            paso = false;
        } catch (Throwable ex) {
            obt = ex.getClass().getSimpleName();
            paso = esperada.isInstance(ex);
        }
        casos.agregarAlFinal(new Caso(id, modulo, tipo, entrada, esp, obt, paso));
    }

    public ListaEnlazada<Caso> getCasos() {
        return casos;
    }

    public int aprobados() {
        int n = 0;
        for (Caso c : casos) {
            if (c.paso) {
                n++;
            }
        }
        return n;
    }

    public int total() {
        return casos.tamanio();
    }

    public void imprimir(PrintStream out, boolean soloFallos) {
        for (Caso c : casos) {
            if (soloFallos && c.paso) {
                continue;
            }
            out.println((c.paso ? "[OK]    " : "[FALLA] ") + c.id + " | " + c.modulo + " | " + c.tipo + " | " + c.entrada);
            if (!c.paso) {
                out.println("          esperado: " + c.esperado);
                out.println("          obtenido: " + c.obtenido);
            }
        }
    }
}
