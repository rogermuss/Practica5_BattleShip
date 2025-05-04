import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuBShip {
    //Crear ventana de juego, asi como una ventana de menu inicial, necesario para seleccionar el modo de juego,
    //puede ser para dos jugadores en una misma red o jugando contra la cpu.

    //Se aplicara una seccion de botones en toda la ventana, asi como un diseño de fondo haciendo referencia al mar

    //Se ingresaran una seleccion de barcos para el juego para colocar los barcos seleccionandolos con el mouse y colocarlos
    //en la posicion deseada


    //Atributos
    public final int XSIZE = 1920;
    public final int YSIZE = 1080;
    private JButton opcionVsCPU = new JButton("Jugador vs CPU");
    private JButton opcionVsJugador = new JButton("Jugador vs Jugador");
    private JButton opcionSalir = new JButton("Salir");
    private JFrame ventanaMenu = new JFrame("Version 1.0");
    private JPanel panelInferior = new JPanel();
    private BattleShip juegoBattleShip;


    // Constructor principal que configura y muestra la ventana del menú inicial.
    // Establece el tamaño, fondo, logo, botones de selección de modo de juego y acciones correspondientes.
    public MenuBShip() {
         // Agregar las opciones al menú
         ventanaMenu.setSize(XSIZE, YSIZE);
         ventanaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         ventanaMenu.setLayout(new BorderLayout()); 
         ventanaMenu.getContentPane().setBackground(new Color(0x000000)); 

        //Se busca y coloca la imagen como logo del menu
        ImageIcon imagenCentral = buscarImagenMenu();
        Image img = imagenCentral.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH); 
        ImageIcon imagenRedimensionada = new ImageIcon(img);
        JLabel etiquetaImagen = new JLabel(imagenRedimensionada); 

        //Se cambia el color de los botones
         // Configuración de colores para los botones
    // Botón Jugador vs CPU - Azul
    opcionVsCPU.setBackground(new Color(30, 144, 255)); // Azul dodger
    opcionVsCPU.setForeground(Color.WHITE); // Texto blanco
    opcionVsCPU.setFocusPainted(false); // Quita el borde de enfoque
    
    // Botón Jugador vs Jugador - Verde
    opcionVsJugador.setBackground(new Color(46, 139, 87)); // Verde mar medio
    opcionVsJugador.setForeground(Color.WHITE);
    opcionVsJugador.setFocusPainted(false);
    
    // Botón Salir - Rojo
    opcionSalir.setBackground(new Color(178, 34, 34)); // Rojo ladrillo
    opcionSalir.setForeground(Color.WHITE);
    opcionSalir.setFocusPainted(false);
    
    opcionVsCPU.setOpaque(true);
    opcionVsJugador.setOpaque(true);
    opcionSalir.setOpaque(true);
        
        //Se crea un panel para colocar las opciones del menu para comenzar el juego
        panelInferior.setBackground(Color.WHITE); // test visual
        panelInferior.add(opcionVsCPU);
        panelInferior.add(opcionVsJugador);
        panelInferior.add(opcionSalir);

        opcionSalir();
        opcionVsCPU();

        ventanaMenu.add(panelInferior, BorderLayout.SOUTH);
        ventanaMenu.add(etiquetaImagen, BorderLayout.NORTH);

        ventanaMenu.setVisible(true);
    }

    //Se genera el juego y al terminar regresa al menu del juego.
    public void opcionVsCPU(){
         opcionVsCPU.addActionListener(e -> {
            File f = new File("partida.dat");
            if(f.exists()){
                JOptionPane.showMessageDialog(ventanaMenu, "Iniciar Jugador vs CPU (Cargando partida guardada.)");
                ventanaMenu.setVisible(false);
                JButton[][] botonesJugador = new JButton[16][16];
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        botonesJugador[i][j] = new JButton();
                        botonesJugador[i][j].setBackground(Color.CYAN);
                        botonesJugador[i][j].putClientProperty("ocupado", false);
                        botonesJugador[i][j].putClientProperty("SeDisparo", false);
                    }
                }

                VentanaJuegoBShip ventana = new VentanaJuegoBShip(botonesJugador);
                
            }
            else{
                JOptionPane.showMessageDialog(ventanaMenu, "Iniciar Jugador vs CPU");
                ventanaMenu.setVisible(false);
                juegoBattleShip = new BattleShip(BattleShip.MODO_CPU);
            }
        });
    }

    //Busca la imagen de el menu en la carpeta
    public ImageIcon buscarImagenMenu(){
        String rutaImagen = "pngBattleship/MenuBattleship.jpg"; 
        java.net.URL imgURL = getClass().getResource(rutaImagen);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se encontró la imagen: " + rutaImagen);
            return new ImageIcon(); // Retorna un icono vacío en caso de error
        }
    }

    //Sin uso actual
    public void opcionVsJugador(){
        opcionVsJugador.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventanaMenu, "Iniciar Jugador vs Jugador");
            ventanaMenu.setVisible(false);
            juegoBattleShip = new BattleShip(BattleShip.MODO_VS);
        });
    }

    //Sale del menu y termina el programa, dando la opcion de aceptar o cancelar
    public void opcionSalir(){
        opcionSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(ventanaMenu, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    //Genera el menu de juego
    public static void main(String[] args) {
        new MenuBShip();
    }

}
