package pa.taller1.Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utilidad para cargar un archivo de propiedades.
 * Sólo retorna el objeto Properties, sin construir objetos de dominio.
 */
public class CargadorEquiposProperties {

    public Properties leerPropiedades(String ruta) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(ruta));
        return props;
    }
}