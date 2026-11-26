package modelo;

import estructuras.ListaEnlazada;

//CONTACTO DE SEGUNDO GRADO SUGERIDO Y LOS AMIGOS QUE TIENE EN COMUN CON EL USUARIO
public class Recomendacion {

    private final String usuario;
    private final ListaEnlazada<String> amigosEnComun = new ListaEnlazada<String>();

    public Recomendacion(String usuario) {
        this.usuario = usuario;
    }

    public void agregarAmigoEnComun(String amigo) {
        amigosEnComun.agregarAlFinal(amigo);
    }

    public String getUsuario() {
        return usuario;
    }

    public ListaEnlazada<String> getAmigosEnComun() {
        return amigosEnComun;
    }

    public int cantidadEnComun() {
        return amigosEnComun.tamanio();
    }

    @Override
    public String toString() {
        return usuario + " (" + cantidadEnComun() + " en comun: " + amigosEnComun + ")";
    }
}
