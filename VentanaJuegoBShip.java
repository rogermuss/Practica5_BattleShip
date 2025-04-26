import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaJuegoBShip {
    private JFrame frame = new JFrame("Version 1.0");
    private final int FILAS = 16;
    private final int COLUMNAS = 16;
    private JButton[][] puntosDeTiroJugador = new JButton[FILAS][COLUMNAS]; 
    private JButton[][] puntosDeTiroCPU = new JButton[FILAS][COLUMNAS];
    private JPanel panelCPU = new JPanel();
    private JPanel panelJugador = new JPanel();
    private final int VERTICAL = 1;
    private final int HORIZONTAL = 0;
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


        panelCPU.setLayout(new GridLayout(16,16));
        panelCPU.setBackground(Color.CYAN);
        panelCPU.setBounds(0, 0, frame.getWidth()-MARGEN_FRAME_WIDTH, frame.getHeight()-MARGEN_FRAME_HEIGHT);

        llenarTableroCPU();

        for(int i=0; i<16;i++){
            for(int j=0; j<16;j++){
                panelJugador.add(this.puntosDeTiroJugador[i][j]);
                panelCPU.add(puntosDeTiroCPU[i][j]);
            } 
        }

        frame.add(panelCPU);
        frame.setVisible(true);
        



    }

    public void llenarTableroCPU(){
        for(int i=0; i<16;i++){
            for(int j=0; j<16;j++){
                puntosDeTiroCPU[i][j] = new JButton();
                puntosDeTiroCPU[i][j].setBackground(Color.CYAN);
                puntosDeTiroCPU[i][j].putClientProperty("ocupado", false); // Initialize as false
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

    public static void main(String[] args) {
    }

    



}
