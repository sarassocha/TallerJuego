package pa.taller1.Modelo;

import java.io.*;
import java.util.List;

/**
 * Clase encargada de la serialización y deserialización de equipos.
 */
public class PersistenciaSerializacion {

    private final String ruta = "data/equipos.ser";

    public void guardarEquipos(List<Equipo> equipos) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(equipos);
        oos.close();
    }

    public List<Equipo> cargarEquipos() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        List<Equipo> equipos = (List<Equipo>) ois.readObject();
        ois.close();
        return equipos;
    }
}