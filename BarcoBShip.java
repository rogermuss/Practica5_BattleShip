
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    private JLabel labelBarco;
    private Point posicionInicial;
    private Boolean seRedimensiono = false;

    public BarcoBShip(int tipo){
        this.orientacion = LEFT;
        this.tipo = tipo;
        inicializarBarcos();
        this.labelBarco = obtenerImagenBarco();
    }



    public void girar90Grados(JButton[][] botones, int tamBoton) {
        // Clear currently occupied cells
        if (botones != null) {
            limpiarCeldasOcupadas(botones);
        }
        
        // Perform rotation logic (existing code)
        orientacion = (orientacion + 1) % 4;
        
        int temp = XBarco;
        XBarco = yBarco;
        yBarco = temp;
        
        // Rotate image logic (existing code)
        ImageIcon icono = (ImageIcon) labelBarco.getIcon();
        if (icono == null) return;
        
        Image imagen = icono.getImage();
        
        BufferedImage bufferedImage = new BufferedImage(
                imagen.getWidth(null),
                imagen.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(imagen, 0, 0, null);
        g2d.dispose();
        
        AffineTransform tx = new AffineTransform();
        tx.translate(bufferedImage.getHeight(), 0);
        tx.rotate(Math.toRadians(90));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        
        BufferedImage imagenRotada = new BufferedImage(
                bufferedImage.getHeight(),
                bufferedImage.getWidth(),
                BufferedImage.TYPE_INT_ARGB);
        op.filter(bufferedImage, imagenRotada);
        
        labelBarco.setIcon(new ImageIcon(imagenRotada));
        labelBarco.setSize(labelBarco.getPreferredSize());
        
        // Re-register buttons if we have button info
        if (botones != null) {
            alinearConCuadricula(botones, tamBoton);
        }
    }
    
    // Simplified version of limpiarCeldasOcupadas
    private void limpiarCeldasOcupadas(JButton[][] botones) {
        // Get current position
        int filaInicio = labelBarco.getY() / botones[0][0].getHeight();
        int colInicio = labelBarco.getX() / botones[0][0].getWidth();
        
        // Clear previous cells
        for (int i = filaInicio; i < filaInicio + yBarco && i < botones.length; i++) {
            for (int j = colInicio; j < colInicio + XBarco && j < botones[0].length; j++) {
                if (i >= 0 && j >= 0 && i < botones.length && j < botones[0].length) {
                    botones[i][j].setBackground(Color.CYAN);
                    botones[i][j].putClientProperty("barcoID", null);
                    botones[i][j].putClientProperty("ocupado", false);
                }
            }
        }
    }
    
    // Method to align with grid after rotation
    public void alinearConCuadricula(JButton[][] botones, int tamBoton) {
        int filaInicio = labelBarco.getY() / tamBoton;
        int colInicio = labelBarco.getX() / tamBoton;
        
        // Adjust to make sure ship is inside boundaries
        if (colInicio + XBarco > botones[0].length) {
            colInicio = botones[0].length - XBarco;
            labelBarco.setLocation(colInicio * tamBoton, labelBarco.getY());
        }
        if (filaInicio + yBarco > botones.length) {
            filaInicio = botones.length - yBarco;
            labelBarco.setLocation(labelBarco.getX(), filaInicio * tamBoton);
        }
        
        // Realign position to grid
        labelBarco.setLocation(colInicio * tamBoton, filaInicio * tamBoton);
        
        // Register buttons
        for (int i = filaInicio; i < filaInicio + yBarco; i++) {
            for (int j = colInicio; j < colInicio + XBarco; j++) {
                if (i >= 0 && j >= 0 && i < botones.length && j < botones[0].length) {
                    botones[i][j].setBackground(Color.DARK_GRAY);
                    botones[i][j].putClientProperty("barcoID", tipo);
                    botones[i][j].putClientProperty("ocupado", true);
                }
            }
        }
    }

    private void limpiarCeldasOcupadas(JButton[][] botones, int tamBoton) {
        for (int fila = 0; fila < botones.length; fila++) {
            for (int col = 0; col < botones[0].length; col++) {
                Object ocupado = botones[fila][col].getClientProperty("ocupado");
                if (ocupado != null && ocupado.equals(true)) {
                    botones[fila][col].setBackground(null);
                    botones[fila][col].putClientProperty("ocupado", false);
                }
            }
        }
    }

    
    
    private boolean puedeColocar(List<Point> celdas, JButton[][] botones) {
    for (Point p : celdas) {
        if (p.x < 0 || p.y < 0 || p.x >= botones.length || p.y >= botones[0].length)
            return false;

        Object ocupado = botones[p.x][p.y].getClientProperty("ocupado");
        if (ocupado != null && ocupado.equals(true)) {
            return false; // ya está ocupado
        }
    }
    return true;
}

    


    public java.util.List<Point> obtenerCeldasOcupadas(int tamBoton, JButton[][] botones) {
    java.util.List<Point> celdas = new java.util.ArrayList<>();

    int x = labelBarco.getX();
    int y = labelBarco.getY();

    int filaInicio = y / tamBoton;
    int colInicio = x / tamBoton;

    for (int i = 0; i < yBarco; i++) {
        for (int j = 0; j < XBarco; j++) {
            int fila = filaInicio + i;
            int col = colInicio + j;

            if (fila >= 0 && fila < botones.length && col >= 0 && col < botones[0].length) {
                celdas.add(new Point(fila, col));
            }
        }
    }

    return celdas;
}


    public void resizeBarcoABoton(int widthCelda, int heightCelda) {
        int anchoDeseado = widthCelda * XBarco;
        int altoDeseado = heightCelda * yBarco;
    
        ImageIcon icono = (ImageIcon) labelBarco.getIcon();
        if (icono == null) return;
    
        Image imagenOriginal = icono.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
    
        labelBarco.setIcon(new ImageIcon(imagenRedimensionada));
        labelBarco.setSize(anchoDeseado, altoDeseado);
        seRedimensiono = true;
    }

    public boolean seRedimensiono(){
        return  seRedimensiono;
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
            ImageIcon iconoBarco = new ImageIcon(rutaImagen);
            return new JLabel(iconoBarco);
        } else {
            System.err.println("No se encontró la imagen: " + rutaImagen);
            return new JLabel(); // Retorna un icono vacío en caso de error
        }
    }

    @Override
    public String toString() {
        return "Orientacion: "+orientacion+" Size Barco: "+XBarco+" Tipo: "+tipo+
        " Imagen Barco: "+labelBarco; 
    }

    public void resizeShipToButton(){

    }

    public void girarAlRecibirClick(JButton[][] botones) {
        labelBarco.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) { // left click
                    int tamBoton = botones[0][0].getWidth(); // Get button size
                    girar90Grados(botones, tamBoton);
                }
            }
        });
    }

        // In BarcoBShip.java
    public void hacerArrastrable(JButton[][] botones) {
        final Point offset = new Point();

        labelBarco.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                offset.setLocation(e.getPoint());
            
                // Clear current occupied cells when starting to drag
                if (botones != null) {
                    limpiarCeldasOcupadas(botones);
                }
            }
        });

        labelBarco.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int newX = labelBarco.getX() + e.getX() - offset.x;
                int newY = labelBarco.getY() + e.getY() - offset.y;

                // JFrame limits (parent of JLabel)
                Container parent = labelBarco.getParent();
                if (parent != null) {
                    int maxX = parent.getWidth() - labelBarco.getWidth();
                    int maxY = parent.getHeight() - labelBarco.getHeight();

                    newX = Math.max(0, Math.min(newX, maxX));
                    newY = Math.max(0, Math.min(newY, maxY));

                    labelBarco.setLocation(newX, newY);
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

        JLabel imagenBarco = barquillo.getLabelBarco();
        imagenBarco.setBounds(100, 100, imagenBarco.getPreferredSize().width, imagenBarco.getPreferredSize().height);
        ventana.add(imagenBarco);
    
        ventana.setVisible(true);
    }

    public void moverBarco(Point p) {
        // Calcular el cambio en las coordenadas y mover el barco
        int x = (int) (p.getX() - posicionInicial.getX());
        int y = (int) (p.getY() - posicionInicial.getY());

        // Actualizar la posición del barco (utiliza el método setBounds para moverlo)
        labelBarco.setLocation(labelBarco.getX() + x, labelBarco.getY() + y);
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
    public void setLabelBarco(JLabel imagenBarco) {
        this.labelBarco = imagenBarco;
    }
    public void setyBarco(int ancho) {
        this.yBarco = ancho;
    }

    public int getYBarco() {
        return yBarco;
    }
    public JLabel getLabelBarco() {
        return labelBarco;
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