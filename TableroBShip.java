import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class TableroBShip {
    //Atributos
    private JButton[][] botones = new JButton[16][16];
    private JFrame frame = new JFrame("Version 1.0");
    private JPanel tableroPanel = new JPanel();
    private JPanel barcosPanel = new JPanel();
    private JPanel panelDePaneles = new JPanel();
    private JLayeredPane layeredPane = new JLayeredPane();
    public final int MARGEN_FRAME_HEIGHT = 300;
    public final int MARGEN_FRAME_WIDTH = 420;

    


    //IMPORTANTEEEEEEE
    //Ajustar el el tableroPanel para que se vea completo; ya que actualmente no se logra visualizar de dicha forma
    public TableroBShip() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920,1080);
        frame.setLayout(new BorderLayout()); // Cambiado a BorderLayout


        tableroPanel.setLayout(new GridLayout(16,16));
        tableroPanel.setBounds(0, 0, frame.getWidth()-MARGEN_FRAME_WIDTH, frame.getHeight()-MARGEN_FRAME_HEIGHT); // Mismo tamaño que layeredPane
        tableroPanel.setBackground(Color.CYAN);
        tableroPanel.setOpaque(true);

        // Antes de agregar nada al frame:
        layeredPane.setPreferredSize(new Dimension(frame.getWidth()-MARGEN_FRAME_WIDTH, frame.getHeight()-MARGEN_FRAME_HEIGHT)); // o el tamaño que desees
        layeredPane.setLayout(null);


        for(int i=0; i<16;i++){
            for(int j=0; j<16;j++){
                botones[i][j] = new JButton();
                botones[i][j].setBackground(Color.CYAN);
                //botones[i][j].addActionListener(e -> );
                
                tableroPanel.add(botones[i][j]);
            } 
        }
         // Configurar el panel para barcos (transparente)
         barcosPanel.setLayout(null); // Layout nulo para posicionamiento absoluto
         barcosPanel.setBounds(0, 0, frame.getWidth()-MARGEN_FRAME_WIDTH, frame.getHeight()-MARGEN_FRAME_HEIGHT); // Mismo tamaño y posición
         barcosPanel.setOpaque(false); // Transparente para ver botones

          // Añadir paneles al layeredPane
        layeredPane.add(tableroPanel, JLayeredPane.DEFAULT_LAYER); // Capa inferior
        layeredPane.add(barcosPanel, JLayeredPane.PALETTE_LAYER); // Capa superior
        
        // Añadir el layeredPane al frame
        frame.add(layeredPane);

        ingresarBarcos();

        frame.add(layeredPane, BorderLayout.CENTER);
        frame.pack(); // Ajusta el tamaño al contenido preferido
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);    
    }

    

    
    public void ingresarBarcos(){
        ArrayList<BarcoBShip> barcos = new ArrayList<>();
        for(int i=1; i<=6; i++){
            barcos.add(new BarcoBShip(i));
        }

        for(BarcoBShip barco : barcos) {
            barco.hacerArrastrable();
            barco.girarAlRecibirClick();
        
            JLabel imagenBarco = barco.getImagenBarco();
            
            // Posición inicial 
            imagenBarco.setBounds(100, 100 + barcos.indexOf(barco) * 70, 
                                imagenBarco.getPreferredSize().width, 
                                imagenBarco.getPreferredSize().height);
        
            // Referencia final para usar en el listener
            final BarcoBShip barcoFinal = barco;
            
            imagenBarco.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    alinearBarcoConCuadricula(imagenBarco, barcoFinal);
                }
            });
            
            barcosPanel.add(imagenBarco);
        }


    }

    //IMPORTANTEEEEEEEEEEEEEEEEEEE
    //Alinear tambien cuando se desea girar el barco y ajustar el tamaño del barco segun se necesite para formar parte del boton

    private void alinearBarcoConCuadricula(JLabel imagenBarco, BarcoBShip barco) {
        // Calcular el tamaño de cada celda de botón
        int anchoCelda = tableroPanel.getWidth() / 16;
        int altoCelda = tableroPanel.getHeight() / 16;
        
        // Obtener la posición actual del barco
        int barcoX = imagenBarco.getX();
        int barcoY = imagenBarco.getY();
        
        // Calcular la celda más cercana (índices i, j en la matriz de botones)
        int i = Math.round((float)barcoY / altoCelda);
        int j = Math.round((float)barcoX / anchoCelda);
        
        // Limitar los índices dentro del rango válido
        i = Math.max(0, Math.min(15, i));
        j = Math.max(0, Math.min(15, j));
        
        // Obtener las dimensiones en botones del barco
        int xBarco = barco.getXBarco(); // Longitud horizontal en botones
        int yBarco = barco.getYBarco(); // Longitud vertical en botones
        
        // Asegurar que el barco no se salga del tablero
        if (j + xBarco > 16) {
            j = 16 - xBarco;
        }
        if (i + yBarco > 16) {
            i = 16 - yBarco;
        }
        
        // Establecer la nueva posición del barco alineada con la cuadrícula
        imagenBarco.setLocation(j * anchoCelda, i * altoCelda);
        
        // También puedes registrar qué botones está ocupando este barco
        registrarBotones(barco, i, j);
        
        // Actualizar la representación visual
        barcosPanel.repaint();
    }
    
    // Método para registrar qué botones ocupa un barco
    private void registrarBotones(BarcoBShip barco, int filaInicio, int columnaInicio) {
        int xBarco = barco.getXBarco();
        int yBarco = barco.getYBarco();
        
        // Marcar los botones como ocupados
        for (int i = filaInicio; i < filaInicio + yBarco; i++) {
            for (int j = columnaInicio; j < columnaInicio + xBarco; j++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    // Aquí puedes registrar que este botón está ocupado
                    // Por ejemplo:
                    botones[i][j].setBackground(Color.DARK_GRAY);
                    // O guardar una referencia al barco en alguna estructura de datos
                }
            }
        }
    }

    public static void main(String[] args) {
        new TableroBShip();
    }
}
