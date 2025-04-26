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

public class TableroPosicionamientoBShip {
    //Atributos
    private JButton[][] botones = new JButton[16][16];
    private JFrame frame = new JFrame("Version 1.0");
    private JPanel tableroPanel = new JPanel();
    private JPanel barcosPanel = new JPanel();
    private JLayeredPane layeredPane = new JLayeredPane();
    private JButton continuarButton = new JButton("ok");
    private ArrayList<BarcoBShip> barcos = new ArrayList<>();
    public final int MARGEN_FRAME_HEIGHT = 285;
    public final int MARGEN_FRAME_WIDTH = 420;

    


    //Ajustar el el tableroPanel para que se vea completo; ya que actualmente no se logra visualizar de dicha forma
    //ACTUALIZACION
    //SE AJUSTO DE FORMA MANUAL....
    public TableroPosicionamientoBShip() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920,1080);
        frame.setLayout(new BorderLayout()); // Cambiado a BorderLayout


        continuarButton.setBackground(Color.BLACK);
        continuarButton.setForeground(Color.WHITE);
        continuarButton.setLayout(new BorderLayout());
        continuarButton.setBounds(1495,0, 50,1030);

        continuarButton.addActionListener(e -> {
            if(estanBarcosPosicionados()){
                layeredPane.remove(barcosPanel); // elimina el panel
                layeredPane.revalidate();   // revalida el layout
                layeredPane.repaint();      // repinta la interfaz

                frame.setVisible(false);
            
                VentanaJuegoBShip ventanaJuego = new VentanaJuegoBShip(botones);
            }
                //Agregar condicion para que al ser presionado el boton continue el juego a
                //otra ventana en la que tendran que disparar a los barcos
        });

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
                botones[i][j].putClientProperty("ocupado", false); // Initialize as false
                tableroPanel.add(botones[i][j]);
            } 
        }
         // Configurar el panel para barcos (transparente)
         barcosPanel.setLayout(null); // Layout nulo para posicionamiento absoluto
         barcosPanel.setBounds(0, 0, frame.getWidth()-MARGEN_FRAME_WIDTH, frame.getHeight()-MARGEN_FRAME_HEIGHT); // Mismo tamaño y posición
         barcosPanel.setOpaque(false); // Transparente para ver botones

          // Añadir paneles al layeredPane
        layeredPane.add(tableroPanel, Integer.valueOf(0)); // Capa inferior
        layeredPane.add(barcosPanel, Integer.valueOf(1));
        layeredPane.add(continuarButton, Integer.valueOf(2));
        
        // Añadir el layeredPane al frame
        frame.add(layeredPane);

        ingresarBarcos();

        frame.add(layeredPane, BorderLayout.CENTER);
        frame.pack(); // Ajusta el tamaño al contenido preferido
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);    


    }



    public boolean estanBarcosPosicionados(){
        int contCuadros = 0;
        for(int i=0; i<16;i++){
            for(int j=0; j<16;j++){
                Object ocupado = botones[i][j].getClientProperty("ocupado");
                if((boolean)ocupado){
                    contCuadros++;
                }
            } 
        }
        return contCuadros == 26; //Cantidad de cuadros al colocar todos los barcos.
    }

    

    
    public void ingresarBarcos(){
        for(int i=1; i<=6; i++){
            barcos.add(new BarcoBShip(i));
        }
    
        for(BarcoBShip barco : barcos) {
            barco.hacerArrastrable(botones); // Pass the button grid
            barco.girarAlRecibirClick(botones);
            
            // Rest of the code...
        
            JLabel imagenBarco = barco.getLabelBarco();
            
            // Initial position 
            imagenBarco.setBounds(100, 100 + barcos.indexOf(barco) * 70, 
                                imagenBarco.getPreferredSize().width, 
                                imagenBarco.getPreferredSize().height);
        
            // Final reference for use in listener
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
        // Calculate button cell size
        int anchoCelda = botones[0][0].getWidth();
        int altoCelda = botones[0][0].getHeight();
        
        // Get current ship position
        int barcoX = imagenBarco.getX();
        int barcoY = imagenBarco.getY();
        
        // Calculate closest cell (indices i, j in button matrix)
        int i = Math.round((float)barcoY / altoCelda);
        int j = Math.round((float)barcoX / anchoCelda);
        
        // Limit indices to valid range
        i = Math.max(0, Math.min(15, i));
        j = Math.max(0, Math.min(15, j));
        
        // Get ship dimensions in buttons
        int xBarco = barco.getXBarco(); // Horizontal length in buttons
        int yBarco = barco.getYBarco(); // Vertical length in buttons
    
        if (!barco.seRedimensiono()) {
            barco.resizeBarcoABoton(anchoCelda, altoCelda);
        }
        
        // Ensure ship doesn't go off the board
        if (j + xBarco > 16) {
            j = 16 - xBarco;
        }
        if (i + yBarco > 16) {
            i = 16 - yBarco;
        }
        
        // Check if the position is valid (no overlap)
        if (posicionValida(i, j, xBarco, yBarco, barco.getTipo())) {
            // Set new ship position aligned with grid
            imagenBarco.setLocation(j * anchoCelda, i * altoCelda);
            
            // Register which buttons are occupied by this ship
            registrarBotones(barco, i, j);
        } else {
            // Return to original position or previous valid position
            // For now, just place in a safe starting position
            imagenBarco.setLocation(100, 100 + barcos.indexOf(barco) * 70);
        }
        
        // Update visual representation
        barcosPanel.repaint();
    }
    
    // Method to check if position is valid (no overlap)
    private boolean posicionValida(int filaInicio, int columnaInicio, int ancho, int alto, int barcoID) {
        for (int i = filaInicio; i < filaInicio + alto; i++) {
            for (int j = columnaInicio; j < columnaInicio + ancho; j++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    Object ocupado = botones[i][j].getClientProperty("ocupado");
                    Object idBarco = botones[i][j].getClientProperty("barcoID");
                    
                    // Check if cell is occupied by another ship
                    if (ocupado != null && (Boolean)ocupado && 
                        (idBarco == null || (Integer)idBarco != barcoID)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void registrarBotones(BarcoBShip barco, int filaInicio, int columnaInicio) {
        int xBarco = barco.getXBarco();
        int yBarco = barco.getYBarco();
        
        // Mark buttons as occupied
        for (int i = filaInicio; i < filaInicio + yBarco; i++) {
            for (int j = columnaInicio; j < columnaInicio + xBarco; j++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    // Set button properties
                    botones[i][j].setBackground(Color.DARK_GRAY);
                    botones[i][j].putClientProperty("ocupado", true);
                    botones[i][j].putClientProperty("barcoID", barco.getTipo());
                    // You can add more properties like ship orientation, ship size, etc.
                }
            }
        }
    }

    

    public static void main(String[] args) {
        new TableroPosicionamientoBShip();
    }
}
