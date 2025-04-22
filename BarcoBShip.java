
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BarcoBShip{
    //Atributos
    public static final int UP = 1;
    public static final int LEFT = 0;
    public static final int DOWN = 3;
    public static final int RIGHT = 2;
    private int orientacion;
    private int XBarco;
    private int yBarco;
    private int tipo;
    private JLabel imagenBarco;
    private Point posicionInicial;

    public BarcoBShip(int tipo){
        this.orientacion = LEFT;
        this.tipo = tipo;
        inicializarBarcos();
        this.imagenBarco = obtenerImagenBarco();
    }

    public void girar90Grados() {
        // Cambiar orientacion circularmente
        orientacion = (orientacion + 1) % 4;
    
        // Intercambiar largo y ancho si el barco no es cuadrado
        int temp = XBarco;
        XBarco = yBarco;
        yBarco = temp;
    
        // Obtener el icono actual del JLabel
        ImageIcon icono = (ImageIcon) imagenBarco.getIcon();
        if (icono == null) return;
    
        Image imagen = icono.getImage();
    
        // Convertir a BufferedImage
        BufferedImage bufferedImage = new BufferedImage(
                imagen.getWidth(null),
                imagen.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(imagen, 0, 0, null);
        g2d.dispose();
    
        // Rotar 90 grados
        AffineTransform tx = new AffineTransform();
        tx.translate(bufferedImage.getHeight(), 0); // Mover origen
        tx.rotate(Math.toRadians(90)); // Rotar
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    
        BufferedImage imagenRotada = new BufferedImage(
                bufferedImage.getHeight(),
                bufferedImage.getWidth(),
                BufferedImage.TYPE_INT_ARGB);
        op.filter(bufferedImage, imagenRotada);
    
        // Aplicar nueva imagen al JLabel
        imagenBarco.setIcon(new ImageIcon(imagenRotada));
        imagenBarco.setSize(imagenBarco.getPreferredSize()); 

        //Ajustar valores
        ajustarMedidas();
    }

    public void ajustarMedidas(){
        int longTemp = XBarco;
        XBarco = yBarco;
        yBarco = longTemp;
    }

    public void inicializarBarcos(){
        switch(tipo){
            case 1 -> {
                XBarco = 2;
                yBarco = 1;
            }
            case 2 -> {
                XBarco = 3;
                yBarco = 1;
            }
            case 3 -> {
                XBarco = 4;
                yBarco = 1;
            }
            case 4 -> {
                XBarco = 3;
                yBarco = 2;
            }
            case 5 -> {
                XBarco = 5;
                yBarco = 1;
            }
            case 6 -> {
                XBarco = 6;
                yBarco = 1;
            }
        }
    }

    public JLabel obtenerImagenBarco(){
        String rutaImagen = "pngBattleship/Barco"+tipo+".png"; 
        java.net.URL imgURL = getClass().getResource(rutaImagen);
        if (imgURL != null) {
            ImageIcon imagen = new ImageIcon(imgURL);
            return new JLabel(imagen);
        } else {
            System.err.println("No se encontró la imagen: " + rutaImagen);
            return new JLabel(); // Retorna un icono vacío en caso de error
        }
    }

    @Override
    public String toString() {
        return "Orientacion: "+orientacion+" Size Barco: "+XBarco+" Tipo: "+tipo+
        " Imagen Barco: "+imagenBarco; 
    }

    public void resizeShipToButton(){

    }

    public void girarAlRecibirClick() {
        imagenBarco.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) { // clic izquierdo
                    girar90Grados();
                }
            }
        });
    }

    //Se crea una funcion que arrastra el png del barco para colocarse en la posicion deseada para iniciar el juego
    public void hacerArrastrable() {
        final Point offset = new Point();
    
        imagenBarco.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                offset.setLocation(e.getPoint());
            }
        });
    
        imagenBarco.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int newX = imagenBarco.getX() + e.getX() - offset.x;
                int newY = imagenBarco.getY() + e.getY() - offset.y;
    
                // Limites del JFrame (padre del JLabel)
                Container parent = imagenBarco.getParent();
                if (parent != null) {
                    int maxX = parent.getWidth() - imagenBarco.getWidth();
                    int maxY = parent.getHeight() - imagenBarco.getHeight();
    
                    newX = Math.max(0, Math.min(newX, maxX));
                    newY = Math.max(0, Math.min(newY, maxY));
    
                    imagenBarco.setLocation(newX, newY);
                }
            }
        });
    }
    
    
    //Ejemplo de implementacion
    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1920, 1080);
        ventana.setLayout(null); // Layout absoluto
    
        BarcoBShip barquillo = new BarcoBShip(3);
        barquillo.hacerArrastrable();
        barquillo.girarAlRecibirClick();

        JLabel imagenBarco = barquillo.getImagenBarco();
        imagenBarco.setBounds(100, 100, imagenBarco.getPreferredSize().width, imagenBarco.getPreferredSize().height);
        ventana.add(imagenBarco);
    
        ventana.setVisible(true);
    }

    public void moverBarco(Point p) {
        // Calcular el cambio en las coordenadas y mover el barco
        int x = (int) (p.getX() - posicionInicial.getX());
        int y = (int) (p.getY() - posicionInicial.getY());

        // Actualizar la posición del barco (utiliza el método setBounds para moverlo)
        imagenBarco.setLocation(imagenBarco.getX() + x, imagenBarco.getY() + y);
        posicionInicial = p;
    }
    
    public void setPosicionInicial(Point p) {
        this.posicionInicial = p;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public void setXBarco(int longitud) {
        this.XBarco = longitud;
    }
    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }
    public void setImagenBarco(JLabel imagenBarco) {
        this.imagenBarco = imagenBarco;
    }
    public void setyBarco(int ancho) {
        this.yBarco = ancho;
    }

    public int getYBarco() {
        return yBarco;
    }
    public JLabel getImagenBarco() {
        return imagenBarco;
    }
    public int getXBarco() {
        return XBarco;
    }
    public int getOrientacion() {
        return orientacion;
    }
    public int getTipo() {
        return tipo;
    }
}