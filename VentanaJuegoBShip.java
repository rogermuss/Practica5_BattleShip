import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaJuegoBShip {
    private JFrame frame = new JFrame("Version 1.0");
    private final int FILAS = 16;
    private final int COLUMNAS = 16;
    private JButton[][] puntosDeTiroJugador = new JButton[FILAS][COLUMNAS]; 
    private JButton[][] puntosDeTiroCPU = new JButton[FILAS][COLUMNAS];
    private final int XSIZE = 1920;
    private final int YSIZE = 1080;

    public VentanaJuegoBShip(JButton[][] puntosDeTiroJugador) {
        this.puntosDeTiroJugador = puntosDeTiroJugador;


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(XSIZE, YSIZE);
        frame.setLayout(new GridLayout(FILAS, COLUMNAS)); // Distribución en forma de matriz
        
    }
    
}
