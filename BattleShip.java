import java.util.concurrent.Semaphore;

public class BattleShip {

    //El curso de juego estara dado por si el jugador acerto el disparo o fallo el mismo

    //La cpu estara creada de forma inicial para hacer aleatorios los tiros, a su vez al acertar aplicara la busqueda de 
    //un kernel exterior para seguir su patron de busqueda a partir de ese punto para encontrar dicho barco, sera aleatorio buscar arriba
    //abajo, hacia la izquierda o hacia la derecha.
    //Todo dependiendo de los tiros programados.
    //Cuando se revele el barco se iniciara de nuevo el proceso aleatorio de busqueda.
    
    //Se jugara hasta encontrar todos los barcos

    //Los barcos se revelaran al dar todos los disparos y a su vez mostraran la zona a sus alrededores imposibiltando los tiros
    //en las zonas, pero ayudando a mejorar la busqueda de los barcos

    //Se jugara de forma grafica en su totalidad y habra una pausa entre movimientos para hacer el juego realista.

    //Atributos
    public static final boolean MODO_CPU = true;
    public static final boolean MODO_VS = false;
    private TableroPosicionamientoBShip tableroJ1;
    private TableroPosicionamientoBShip tableroCPU;
    private TableroPosicionamientoBShip tableroJ2;
    private static Semaphore semaforo = new Semaphore(0); // Controla la pausa

    


    public BattleShip(boolean modoDeJuego){
        //Modo contra CPU
        if(modoDeJuego){
            new TableroPosicionamientoBShip();
        }
        //Modo contra otro jugador
        else{
            //No lo hice jiji
        }
    }
    
    public static void esperarClick() {
        try {
            semaforo.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //METODOS PARA JUEGO CONTRA CPU.


    //METODOS PARA JUEGO CONTRA JUGADOR.

}
