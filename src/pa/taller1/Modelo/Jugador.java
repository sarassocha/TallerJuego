package pa.taller1.Modelo;

import java.io.Serializable;

/**
 * Representa un jugador dentro de un equipo.
 */
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private int puntaje;
    private int intentos;
    private int intentosExitosos;

    public Jugador(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puntaje = 0;
        this.intentos = 0;
        this.intentosExitosos = 0;
    }

    public void registrarIntento(TipoEmbocada tipo) {
        intentos++;
        if (tipo != TipoEmbocada.FALLA) {
            intentosExitosos++;
            puntaje += tipo.getPuntaje();
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getIntentos() {
        return intentos;
    }

    public int getIntentosExitosos() {
        return intentosExitosos;
    }
}