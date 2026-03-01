package pa.taller1.Controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pa.taller1.Vista.VistaPrincipal;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Intermediario entre Vista y ControlPrincipal.
 * La Vista SOLO conoce esta clase.
 * Implementa ActionListener para manejar eventos de botones.
 */
public class ControlVista implements ActionListener {

    private VistaPrincipal vista;
    private ControlPrincipal controlPrincipal;

    public ControlVista(ControlPrincipal controlPrincipal) {

        this.controlPrincipal = controlPrincipal;
        this.vista = new VistaPrincipal();

        vista.setControlVista(this);
        
        // Configurar listeners de botones
        vista.getBtnCargar().addActionListener(this);
        vista.getBtnEmbocar().addActionListener(this);
    }

    /**
     * Maneja los eventos de acción de los botones
     *
     * @param e Evento de acción
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("CARGAR")) {
            cargarArchivo();
        } else if (comando.equals("EMBOCAR")) {
            intentarEmbocar();
        }
    }

    public void iniciarVista() {
        vista.launchApp();
    }

    /**
     * Carga archivo properties usando JFileChooser.
     * Primero valida las propiedades, luego solicita tiempo.
     */
    public void cargarArchivo() {

        JFileChooser fileChooser = new JFileChooser();
        int opcion = fileChooser.showOpenDialog(null);

        if (opcion == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            
            try {
                //  Cargar configuración visual 
                java.util.Properties props = new java.util.Properties();

                try (java.io.FileInputStream fis = new java.io.FileInputStream(file)) {
                    props.load(fis);
                }

                String rutaIcono = props.getProperty("jugador.icono");
                java.net.URL url = getClass().getResource(rutaIcono);
                int tamanoIcono = Integer.parseInt(props.getProperty("jugador.icono.tamano"));

                java.awt.image.BufferedImage buffered =
                        javax.imageio.ImageIO.read(
                                getClass().getResource(rutaIcono)
                        );

                Image imgEscalada = buffered.getScaledInstance(
                        tamanoIcono,
                        tamanoIcono,
                        Image.SCALE_SMOOTH
                );

                ImageIcon iconoFinal = new ImageIcon(imgEscalada);

                // Inyectar en la vista
                vista.setIconoJugador(iconoFinal);
                // Validar y cargar equipos a través de ControlPrincipal
                java.util.List<pa.taller1.Modelo.Equipo> equipos =
                        controlPrincipal.validarYCargarEquipos(file.getAbsolutePath());

                if (equipos == null || equipos.isEmpty()) {
                    notificarError("No se encontraron equipos en el archivo.");
                    return;
                }

                // Si es válido, pedir tiempo
                int tiempoEquipo = solicitarTiempoPorEquipo();

                // Inicializar juego
                controlPrincipal.inicializarJuegoConEquipos(equipos, tiempoEquipo);
                notificarCargaExitosa(equipos);

                Map<String, List<String>> datos =
                        controlPrincipal.obtenerDatosEquipos();

                vista.mostrarEquipos(datos);

                actualizarVista();

            } catch (java.io.IOException e) {
                notificarError("Error al cargar archivo");
            }
        }
    }

    /**
     * Solicita y valida el tiempo total por equipo.
     * - Debe ser número entero
     * - Mayor que 0
     * - Mínimo 9 segundos (3 jugadores × 3 segundos)
     */
    private int solicitarTiempoPorEquipo() {

        while (true) {

            String input = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el tiempo total por equipo (mínimo 9 segundos).\n" +
                    "Cada lanzamiento tarda aproximadamente 3 segundos.",
                    "Tiempo por equipo",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Debe ingresar un tiempo válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }

            try {
                int tiempo = Integer.parseInt(input);

                if (tiempo <= 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "El tiempo debe ser mayor que 0.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    continue;
                }

                if (tiempo < 9) {
                    JOptionPane.showMessageDialog(
                            null,
                            "El tiempo mínimo permitido es 9 segundos.\n" +
                            "3 jugadores × 3 segundos por intento.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    continue;
                }

                return tiempo;

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Debe ingresar un número entero válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Ejecuta intento desde botón Embocar.
     */
    public void intentarEmbocar() {

        if (controlPrincipal.isJuegoTerminado()) {
            return;
        }

        String resultado = controlPrincipal.ejecutarIntento();
        vista.mostrarResultado(resultado);

        if (controlPrincipal.isJuegoTerminado()) {
            vista.finalizarJuego();
            controlPrincipal.finalizarJuego();
            return; 
        }

        actualizarVista();
    }

    /**
     * Notifica carga exitosa de equipos a la vista.
     */
    public void notificarCargaExitosa(List<?> equipos) {
        JOptionPane.showMessageDialog(null,
                "¡Carga exitosa!\n" + equipos.size() + " equipos cargados.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Notifica error durante la carga de equipos.
     */
    public void notificarError(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra mensaje final con ganador y número de victorias históricas.
     */
    public void mostrarResultadoFinal(pa.taller1.Modelo.Equipo ganador,
                                      int victorias) {

        JOptionPane.showMessageDialog(null,
                "Equipo ganador: " + ganador.getNombreEquipo()
                + "\nPuntaje: " + ganador.getPuntajeTotal()
                + "\nHa ganado " + victorias + " veces en total.");

        System.exit(0);
    }

    /**
     * Actualiza información visible en pantalla.
     */
    private void actualizarVista() {

        vista.actualizarDatos(
                controlPrincipal.getNombreEquipoActual(),
                controlPrincipal.getNombreJugadorActual(),
                controlPrincipal.getTiempoRestanteJugador(),
                controlPrincipal.getIntentosActuales()
        );
    }
}