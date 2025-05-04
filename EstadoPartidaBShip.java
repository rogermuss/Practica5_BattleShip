import java.io.Serializable;

/**
 * Clase que almacena el estado de una partida de Batalla Naval
 * para guardar y cargar partidas.
 */
public class EstadoPartidaBShip implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Información del estado de los tableros
    public boolean[][] jugadorOcupado = new boolean[16][16];
    public boolean[][] jugadorDisparo = new boolean[16][16];
    public boolean[][] cpuOcupado = new boolean[16][16];
    public boolean[][] cpuDisparo = new boolean[16][16];
    
    // Información del turno actual
    public boolean turnoJugador; // true = CPU, false = JUGADOR
    
    // Información de barcos encontrados por la CPU
    public boolean barcoEncontrado;
    public int filaActual;
    public int columnaActual;
    public int[] direccionesDeBusqueda; // Almacena las direcciones pendientes para buscar
    
    public EstadoPartidaBShip() {
        // Inicialización de arreglos
        jugadorOcupado = new boolean[16][16];
        jugadorDisparo = new boolean[16][16];
        cpuOcupado = new boolean[16][16];
        cpuDisparo = new boolean[16][16];
        
        // Valores por defecto
        turnoJugador = false; // Comienza el jugador
        barcoEncontrado = false;
        direccionesDeBusqueda = new int[4]; // UP, LEFT, DOWN, RIGHT
    }
}