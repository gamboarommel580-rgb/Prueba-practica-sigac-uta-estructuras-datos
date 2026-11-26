package modelo;

//NODO DEL ARBOL GENERAL. Dos unidades son iguales si tienen el mismo codigo
public class UnidadAcademica {

    private final String codigo;
    private final String nombre;
    private final TipoUnidad tipo;

    public UnidadAcademica(String codigo, String nombre, TipoUnidad tipo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo de la unidad no puede ser null ni vacio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la unidad no puede ser null ni vacio");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de unidad no puede ser null");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoUnidad getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UnidadAcademica && ((UnidadAcademica) o).codigo.equals(codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + codigo + " - " + nombre;
    }
}
