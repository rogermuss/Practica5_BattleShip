import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase para manejar el guardado y carga de partidas de Batalla Naval
 */
public class ArchivoJuegoBShip {
    private String ruta;

    /**
     * Constructor que establece la ruta del archivo
     * @param ruta Ruta del archivo para guardar/cargar la partida
     */
    public ArchivoJuegoBShip(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Guarda el estado de la partida en un archivo
     * @param estado Estado de la partida a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean guardar(EstadoPartidaBShip estado) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(estado);
            System.out.println("Partida guardada en: " + ruta);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar partida: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carga el estado de una partida desde un archivo
     * @return Estado de la partida cargada o null si hay error
     */
    public EstadoPartidaBShip cargar() {
        File f = new File(ruta);
        if (!f.exists()) {
            System.out.println("Archivo no encontrado: " + ruta);
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            EstadoPartidaBShip estado = (EstadoPartidaBShip) ois.readObject();
            System.out.println("Partida cargada desde: " + ruta);
            return estado;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar partida: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Comprueba si existe un archivo de guardado
     * @return true si existe el archivo, false en caso contrario
     */
    public boolean existePartidaGuardada() {
        File f = new File(ruta);
        return f.exists() && f.isFile() && f.length() > 0;
    }
    
    /**
     * Elimina el archivo de guardado si existe
     * @return true si se eliminó correctamente o no existía, false en caso contrario
     */
    public boolean eliminarPartidaGuardada() {
        File f = new File(ruta);
        if (f.exists()) {
            return f.delete();
        }
        return true;
    }
}