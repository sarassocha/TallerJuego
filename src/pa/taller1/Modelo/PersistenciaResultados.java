package pa.taller1.Modelo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Persistencia usando RandomAccessFile.
 * Mantiene histórico completo de ejecuciones.
 */
public class PersistenciaResultados {

    private final String ruta = "src/Specs/data/resultados.dat";

    /**
     * Guarda un nuevo resultado al final del archivo.
     */
    public void guardarResultado(int clave, Equipo equipo) throws IOException {

        File archivo = new File(ruta);

        // Crear carpeta data si no existe
        if (!archivo.getParentFile().exists()) {
            archivo.getParentFile().mkdirs();
        }

        RandomAccessFile raf = new RandomAccessFile(archivo, "rw");

        raf.seek(raf.length()); // Ir al final

        raf.writeInt(clave);
        raf.writeUTF(equipo.getNombreEquipo());
        raf.writeUTF(equipo.getJugadores().get(0).getNombre());
        raf.writeUTF(equipo.getJugadores().get(1).getNombre());
        raf.writeUTF(equipo.getJugadores().get(2).getNombre());
        raf.writeInt(equipo.getPuntajeTotal());

        raf.close();
    }

    /**
     * Cuenta cuántas veces ha ganado un equipo.
     */
    public int contarVictorias(String nombreEquipo) throws IOException {

        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return 0;
        }

        RandomAccessFile raf = new RandomAccessFile(archivo, "r");

        int contador = 0;

        while (raf.getFilePointer() < raf.length()) {

            raf.readInt(); // clave
            String nombre = raf.readUTF();
            raf.readUTF();
            raf.readUTF();
            raf.readUTF();
            raf.readInt();

            if (nombre.equals(nombreEquipo)) {
                contador++;
            }
        }

        raf.close();
        return contador;
    }

    /**
     * Obtiene la última clave registrada.
     */
    public int obtenerUltimaClave() throws IOException {

        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return 0;
        }

        RandomAccessFile raf = new RandomAccessFile(archivo, "r");

        int ultimaClave = 0;

        while (raf.getFilePointer() < raf.length()) {

            ultimaClave = raf.readInt();
            raf.readUTF();
            raf.readUTF();
            raf.readUTF();
            raf.readUTF();
            raf.readInt();
        }

        raf.close();
        return ultimaClave;
    }
}