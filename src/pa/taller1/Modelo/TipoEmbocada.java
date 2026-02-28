package pa.taller1.Modelo;

import java.io.Serializable;

/**
 * Enum que representa los tipos de embocada posibles en el juego del balero.
 * Cada tipo tiene un puntaje asociado.
 */
public enum TipoEmbocada implements Serializable {

    SIMPLE("Simple", 2),
    DOBLE("Doble", 10),
    VERTICAL("Vertical", 3),
    MARIQUITA("Mariquita", 4),
    PUNALADA("Puñalada", 5),
    PURTINA("Purtiña", 6),
    DOMINIO_REVES("Dominio de revés", 8),
    FALLA("Falla", 0);

    private final String nombre;
    private final int puntaje;

    TipoEmbocada(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
