import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaJuegoBShip {
    private JFrame miVentana = new JFrame("Version 1.0");
    private final int FILAS = 16;
    private final int COLUMNAS = 16;
    private JButton[][] puntosDeTiro = new JButton[FILAS][COLUMNAS]; 
    private final int XSIZE = 1920;
    private final int YSIZE = 1080;

    public VentanaJuegoBShip() {
        miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miVentana.setSize(XSIZE, YSIZE);
        miVentana.setLayout(new GridLayout(FILAS, COLUMNAS)); // Distribución en forma de matriz
        
    }
    
}
