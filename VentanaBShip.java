import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class VentanaBShip {
    //Crear ventana de juego, asi como una ventana de menu inicial, necesario para seleccionar el modo de juego,
    //puede ser para dos jugadores en una misma red o jugando contra la cpu.

    //Se aplicara una seccion de botones en toda la ventana, asi como un diseño de fondo haciendo referencia al mar

    //Se ingresaran una seleccion de barcos para el juego para colocar los barcos seleccionandolos con el mouse y colocarlos
    //en la posicion deseada


    //Atributos
    public final int XSIZE = 1920;
    public final int YSIZE = 1080;
    private JMenuItem opcionVsCPU = new JMenuItem("Jugador vs CPU");
    private JMenuItem opcionVsJugador = new JMenuItem("Jugador vs Jugador");
    private JMenuItem opcionSalir = new JMenuItem("Salir");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuJuego = new JMenu("BattleShip");
    private JFrame ventanaMenu = new JFrame("Version 1.0");
    private JFrame tableroJ1 = new JFrame();
    private JFrame tableroJ2 = new JFrame();
    private JFrame tableroCPU = new JFrame();

    public VentanaBShip() {
         // Agregar las opciones al menú
         menuJuego.add(opcionVsCPU);
         menuJuego.add(opcionVsJugador);
         menuJuego.addSeparator();
         menuJuego.add(opcionSalir);
 
         // Agregar el menú a la barra de menú
         menuBar.add(ventanaMenu);
 
         // Asignar la barra de menú al frame
         ventanaMenu.setJMenuBar(menuBar);
 
         // Mostrar la ventana
         ventanaMenu.setVisible(true);
    }

    public void opcionVsCPU(){
         opcionVsCPU.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventanaMenu, "Iniciar Jugador vs CPU");
            // aqui puedes llamar al metodo para iniciar ese modo
        });
    }

    public void opcionVsJugador(){
        opcionVsJugador.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventanaMenu, "Iniciar Jugador vs Jugador");
            // aqui puedes llamar al metodo para iniciar ese modo
        });
    }
    public void opcionSalir(){
        opcionSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(ventanaMenu, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        VentanaBShip miVentana = new VentanaBShip();
    }

}
