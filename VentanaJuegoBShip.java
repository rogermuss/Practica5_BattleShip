import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaJuegoBShip {
    private JFrame frame = new JFrame("Version 1.0");
    private final int FILAS = 16;
    private final int COLUMNAS = 16;
    private JButton[][] puntosDeTiroJugador = new JButton[FILAS][COLUMNAS]; 
    private JButton[][] puntosDeTiroCPU = new JButton[FILAS][COLUMNAS];
    private JPanel panelCPU = new JPanel();
    private JPanel panelJugador = new JPanel();
    private JPanel panelTablero = new JPanel();
    private JPanel panelCentral = new JPanel();
    private JLabel labelJugador = new JLabel();
    private JLabel labelCPU = new JLabel();
    private JLabel labelTurno = new JLabel();
    private final int VERTICAL = 1;
    private final int HORIZONTAL = 0;
    private ArrayList<Integer> direccionesDeBusqueda = new ArrayList<>();
    private boolean barcoEncontrado = false;
    private int filaActual;
    private int columnaActual;
    public static final int UP = 1;
    public static final int LEFT = 0;
    public static final int DOWN = 3;
    public static final int RIGHT = 2;
    public final int MARGEN_FRAME_HEIGHT = 285;
    public final int MARGEN_FRAME_WIDTH = 420;

    public VentanaJuegoBShip(JButton[][] puntosDeTiroJugador) {
        this.puntosDeTiroJugador = puntosDeTiroJugador;


        frame.setSize(1920,1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Distribución en forma de matriz

        panelJugador.setLayout(new GridLayout(16,16));
        panelJugador.setBackground(Color.CYAN);
        panelJugador.setBounds(0, 0, frame.getWidth()/2-MARGEN_FRAME_WIDTH, frame.getHeight()/2-MARGEN_FRAME_HEIGHT-100);

        panelCPU.setLayout(new GridLayout(16,16));
        panelCPU.setBackground(Color.CYAN);
        panelCPU.setBounds(0, 0, frame.getWidth()/2-MARGEN_FRAME_WIDTH, frame.getHeight()/2-MARGEN_FRAME_HEIGHT-100);


        //Aplicar diseño del panel central, junto con mensajes de actualizacion de ronda.

        panelCentral.setLayout(new GridLayout(3, 1)); // Un label arriba y otro abajo
        panelCentral.setBackground(Color.BLACK);

        // Cargar la imagen original
        ImageIcon iconoFlechaDerecha = new ImageIcon("FlechaDerecha.png");

        // Redimensionar la imagen
        Image imagenFlechaDerecha = iconoFlechaDerecha.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        // Crear un nuevo icono a partir de la imagen redimensionada
        iconoFlechaDerecha = new ImageIcon(imagenFlechaDerecha);

        // Cargar la imagen original
        ImageIcon iconoFlechaIzquierda = new ImageIcon("FlechaIzquierda.png");

        // Redimensionar la imagen
        Image imagenFlechaIzquierda = iconoFlechaIzquierda.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        // Crear un nuevo icono a partir de la imagen redimensionada
        iconoFlechaIzquierda = new ImageIcon(imagenFlechaIzquierda);


        labelJugador.setText("JUGADOR");
        labelJugador.setForeground(Color.WHITE);
        labelJugador.setIcon(iconoFlechaDerecha);
        labelJugador.setHorizontalTextPosition(SwingConstants.LEFT);
        labelJugador.setHorizontalAlignment(SwingConstants.CENTER); // Centramos
        labelJugador.setVerticalAlignment(SwingConstants.CENTER);
        labelJugador.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

        labelCPU.setText("CPU");
        labelCPU.setForeground(Color.WHITE);
        labelCPU.setIcon(iconoFlechaIzquierda);
        labelCPU.setHorizontalTextPosition(SwingConstants.RIGHT);
        labelCPU.setHorizontalAlignment(SwingConstants.CENTER);
        labelCPU.setVerticalAlignment(SwingConstants.CENTER);
        labelCPU.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));


        labelTurno.setText("Turno: JUGADOR");
        labelTurno.setForeground(Color.MAGENTA);
        labelTurno.setHorizontalAlignment(SwingConstants.CENTER);
        labelTurno.setHorizontalAlignment(SwingConstants.CENTER);
        labelTurno.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));


        panelCentral.add(labelJugador);
        panelCentral.add(labelCPU);
        panelCentral.add(labelTurno);

        // No pongas setBounds en BorderLayout, se ajusta solo


        panelTablero.setLayout(new BorderLayout());
        panelTablero.setBounds(0,0, frame.getWidth()-MARGEN_FRAME_WIDTH, frame.getHeight()-MARGEN_FRAME_HEIGHT); // o el tamaño que desees
        panelTablero.setLayout(new BorderLayout());
        panelTablero.setBackground(Color.DARK_GRAY);


        
        llenarTableroCPU();

        for(int i=0; i<16;i++){
            for(int j=0; j<16;j++){
                this.puntosDeTiroJugador[i][j].setEnabled(false);
                this.puntosDeTiroJugador[i][j].setFocusable(false);
                this.puntosDeTiroJugador[i][j].putClientProperty("SeDisparo", false);
                panelJugador.add(this.puntosDeTiroJugador[i][j]);
                panelCPU.add(puntosDeTiroCPU[i][j]);
            } 
        }

        panelTablero.add(panelJugador, BorderLayout.EAST);
        panelTablero.add(panelCPU, BorderLayout.WEST );
        panelTablero.add(panelCentral, BorderLayout.CENTER);

        frame.add(panelTablero);
        frame.setVisible(true);
        



    }

    

    public void llenarTableroCPU(){
        for(int i=0; i<16;i++){
            for(int j=0; j<16;j++){
                puntosDeTiroCPU[i][j] = new JButton();
                puntosDeTiroCPU[i][j].setBackground(Color.CYAN);
                puntosDeTiroCPU[i][j].putClientProperty("ocupado", false); // Initialize as false
                puntosDeTiroCPU[i][j].putClientProperty("SeDisparo", false);
                panelCPU.add(puntosDeTiroCPU[i][j]);
            } 
        }
        asignarBarcosAleatoriamenteCPU();
    }

    public void asignarBarcosAleatoriamenteCPU(){
        Random rdm = new Random();

        int posicionBarco = rdm.nextInt(2); //Indicara si se llenara de forma horizontal o vertical (0 o 1)
        int fila = rdm.nextInt(16);
        int columna = rdm.nextInt(16);
        

        //ARREGLAR DESBORDAMIENTO EN CASO DE PASARSE DEL TABLERO --- ocurren errores de indexacion, lo odio.....
        //llenar 2x3 o 3x2
        if (posicionBarco == HORIZONTAL) {
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 2; j++) {
                    if(columna+j > 15 && fila+i > 15){
                        puntosDeTiroCPU[fila-i][columna-j].putClientProperty("ocupado", true);  
                        puntosDeTiroCPU[fila-i][columna-j].setBackground(Color.GRAY);                  
                    }
                    else if(fila+i > 15){
                        puntosDeTiroCPU[fila-i][columna+j].putClientProperty("ocupado", true); 
                        puntosDeTiroCPU[fila-i][columna+j].setBackground(Color.GRAY);                   
                    }
                    else if(columna+j > 15){
                        puntosDeTiroCPU[fila+i][columna-j].putClientProperty("ocupado", true);  
                        puntosDeTiroCPU[fila+i][columna-j].setBackground(Color.GRAY);                  
                    }
                    else{
                        puntosDeTiroCPU[fila+i][columna+j].putClientProperty("ocupado", true);
                        puntosDeTiroCPU[fila+i][columna+j].setBackground(Color.GRAY);
                    }
                }
            }
        }
        else{
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 1; j++) {
                    if(columna+j > 15 && fila+i > 15){
                        puntosDeTiroCPU[fila-i][columna-j].putClientProperty("ocupado", true);      
                        puntosDeTiroCPU[fila-i][columna-j].setBackground(Color.GRAY);                  
              
                    }
                    else if(fila+i > 15){
                        puntosDeTiroCPU[fila-i][columna+j].putClientProperty("ocupado", true); 
                        puntosDeTiroCPU[fila-i][columna+j].setBackground(Color.GRAY);                  
                   
                    }
                    else if(columna+j > 15){
                        puntosDeTiroCPU[fila+i][columna-j].putClientProperty("ocupado", true); 
                        puntosDeTiroCPU[fila+i][columna-j].setBackground(Color.GRAY);                  
                   
                    }
                    else{
                        puntosDeTiroCPU[fila+i][columna+j].putClientProperty("ocupado", true);
                        puntosDeTiroCPU[fila+i][columna+j].setBackground(Color.GRAY);                  

                    }
                }
            }
        }

        //llenar 1x2 o 2x1
        colocarBarco(2);
        //llenar 1x3 o 3x1
        colocarBarco(3);
        //llenar 1x4 o 4x1
        colocarBarco(4);
        //llenar 1x5 o 5x1
        colocarBarco(5);
        //llenar 1x6 o 6x1
        colocarBarco(6);

    }

    public void colocarBarco(int indiceFor){
        Random rdm = new Random();

        int fila = rdm.nextInt(16);
        int columna = rdm.nextInt(16);

        //llenar 1x2 o 2x1

        Object ocupado; //Toma el valor booleano del boton.
        boolean puedeColocar = true; //Indica si se pudo colocar el barco.
        do{
            ArrayList<Integer> colocacionesDisponibles = new ArrayList<>(); //Guarda si el barco puede colocarse en algun punto cardinal

            //Randomiza de nuevo el punto de inicio para colocar el barco
            fila = rdm.nextInt(16); 
            columna = rdm.nextInt(16);
            ocupado = puntosDeTiroCPU[fila][columna].getClientProperty("ocupado"); 
            //Indica si el boton inicial esta ocupado o no.
            if(ocupado != null && !((boolean)ocupado)){
                boolean arribaDisponible = true;
                boolean derechaDisponible = true;
                boolean abajoDisponible = true;
                boolean izquierdaDisponible = true;

                int contadorArribaDisponible = 0;
                int contadorAbajoDisponible = 0;
                int contadorDerechaDisponible = 0;
                int contadorIzquierdaDisponible = 0;

                for (int i = 0; i < indiceFor; i++) {
                    if (fila - i >= 0) {
                        if (Boolean.FALSE.equals(puntosDeTiroCPU[fila - i][columna].getClientProperty("ocupado"))) {
                            contadorArribaDisponible++;
                        } else {
                            arribaDisponible = false;
                        }
                    } else {
                        arribaDisponible = false;
                    }
                
                    if (fila + i <= 15) {
                        if (Boolean.FALSE.equals(puntosDeTiroCPU[fila + i][columna].getClientProperty("ocupado"))) {
                            contadorAbajoDisponible++;
                        } else {
                            abajoDisponible = false;
                        }
                    } else {
                        abajoDisponible = false;
                    }
                
                    if (columna + i <= 15) {
                        if (Boolean.FALSE.equals(puntosDeTiroCPU[fila][columna + i].getClientProperty("ocupado"))) {
                            contadorDerechaDisponible++;
                        } else {
                            derechaDisponible = false;
                        }
                    } else {
                        derechaDisponible = false;
                    }
                
                    if (columna - i >= 0) {
                        if (Boolean.FALSE.equals(puntosDeTiroCPU[fila][columna - i].getClientProperty("ocupado"))) {
                            contadorIzquierdaDisponible++;
                        } else {
                            izquierdaDisponible = false;
                        }
                    } else {
                        izquierdaDisponible = false;
                    }
                }
                
                // Verificar que haya espacio suficiente completo en esa dirección
                if (contadorArribaDisponible < indiceFor) arribaDisponible = false;
                if (contadorAbajoDisponible < indiceFor) abajoDisponible = false;
                if (contadorDerechaDisponible < indiceFor) derechaDisponible = false;
                if (contadorIzquierdaDisponible < indiceFor) izquierdaDisponible = false;
                
                // Evaluar si puede colocar o no
                if (!arribaDisponible && !abajoDisponible && !derechaDisponible && !izquierdaDisponible) {
                    puedeColocar = false;
                } else {
                    if (arribaDisponible) colocacionesDisponibles.add(UP);
                    if (abajoDisponible) colocacionesDisponibles.add(DOWN);
                    if (derechaDisponible) colocacionesDisponibles.add(RIGHT);
                    if (izquierdaDisponible) colocacionesDisponibles.add(LEFT);
                }
                int index = rdm.nextInt(colocacionesDisponibles.size());
                int colocacionSeleccionada = colocacionesDisponibles.get(index);
                for(int i = 0; i < indiceFor; i++){
                    switch (colocacionSeleccionada) {
                        case UP -> {
                            puntosDeTiroCPU[fila-i][columna].putClientProperty("ocupado", true);
                            puntosDeTiroCPU[fila-i][columna].setBackground(Color.GRAY);

                        }
                        case DOWN -> {
                            puntosDeTiroCPU[fila+i][columna].putClientProperty("ocupado", true);
                            puntosDeTiroCPU[fila+i][columna].setBackground(Color.GRAY);


                        }
                        case RIGHT -> {
                            puntosDeTiroCPU[fila][columna+i].putClientProperty("ocupado", true);
                            puntosDeTiroCPU[fila][columna+i].setBackground(Color.GRAY);


                        }
                        case LEFT -> {
                            puntosDeTiroCPU[fila][columna-i].putClientProperty("ocupado", true);
                            puntosDeTiroCPU[fila][columna-i].setBackground(Color.GRAY);

                        }
                    }
                }
            }
            //Repetir si esta ocupado el boton o si no se pudo colocar el barco.
        }while((boolean)ocupado || !puedeColocar);
    }

    public void tiroProgramadoCPU(){
        Random rdm = new Random();
        if(!barcoEncontrado){
            filaActual = rdm.nextInt(16);
            columnaActual = rdm.nextInt(16);
            if(Boolean.TRUE.equals(puntosDeTiroCPU[filaActual][columnaActual].getClientProperty("Ocupado"))){
                //Agregar funcion para oscurecer el boton y/o cambiarlo de color, asi como la de dormir la CPU 2 segundos.
                puntosDeTiroCPU[filaActual][columnaActual].putClientProperty("SeDisparo", true);
                barcoEncontrado = true;
                //Son UP, DOWN, LEFT y RIGHT.
                for (int i = 0; i <= 3; i++) {
                    direccionesDeBusqueda.add(i);
                }
                tiroProgramadoCPU();
            }
            //Agregar funcion para oscurecer el boton y/o cambiarlo de color, asi como la de dormir la CPU 2 segundos.
            puntosDeTiroCPU[filaActual][columnaActual].putClientProperty("SeDisparo", true);
        }
        else{

            do { 
                int acumDireccion = 1;
                int index = rdm.nextInt(direccionesDeBusqueda.size());
                int direccionSeleccionada = direccionesDeBusqueda.get(index);
                switch (direccionSeleccionada) {
                    case UP -> {
                        if(filaActual+acumDireccion >= 0){
                            while(Boolean.TRUE.equals(puntosDeTiroCPU[filaActual-acumDireccion][columnaActual].getClientProperty("Ocupado"))){
                                puntosDeTiroCPU[filaActual-acumDireccion][columnaActual].putClientProperty("SeDisparo", true);
                                acumDireccion++;
                            }
                        }
                    }
                    case DOWN -> {
                        if(filaActual+acumDireccion <= 15){
                            while(Boolean.TRUE.equals(puntosDeTiroCPU[filaActual+acumDireccion][columnaActual].getClientProperty("Ocupado"))){
                                puntosDeTiroCPU[filaActual+acumDireccion][columnaActual].putClientProperty("SeDisparo", true);
                                acumDireccion++;
                            }
                        }
                    }
                    case LEFT -> {
                        if(filaActual+acumDireccion >= 0){
                            while(Boolean.TRUE.equals(puntosDeTiroCPU[filaActual][columnaActual-acumDireccion].getClientProperty("Ocupado"))){
                                puntosDeTiroCPU[filaActual][columnaActual-acumDireccion].putClientProperty("SeDisparo", true);
                                acumDireccion++;
                            }
                        }
                    }
                    case RIGHT -> {
                        if(filaActual+acumDireccion <= 15){
                            while(Boolean.TRUE.equals(puntosDeTiroCPU[filaActual+acumDireccion][columnaActual].getClientProperty("Ocupado"))){
                                puntosDeTiroCPU[filaActual+acumDireccion][columnaActual].putClientProperty("SeDisparo", true);
                                acumDireccion++;
                            }
                        }
                    }
                }
            } while (true);
        }

    }

    

    public void cambiarTurno(){

    }

}
