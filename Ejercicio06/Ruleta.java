package Tercera.Ejercicio06; // Define el paquete al que pertenece la clase Tablero

import java.applet.Applet; // Importa la clase Applet del paquete java.applet para crear un applet
import java.awt.Color; // Importa la clase Color para manejar colores
import java.awt.Event; // Importa la clase Event para manejar eventos
import java.awt.Graphics; // Importa la clase Graphics para dibujar en el applet
import java.awt.Image; // Importa la clase Image para manejar imágenes
import java.util.ArrayList; // También se puede instanciar un objeto sin haber importado su clase.


public class Ruleta extends Applet { // Declara la clase pública Tablero que extiende Applet
    // Atributos de Clase
    public static final int FILAS = 12; // Cantidad de filas y columnas 10 x 10.
    public static final int COLUMNAS = 3; // Cantidad de minas
    public static final int NUMJUGADAS = 10; // Para guardar las 10 últimas jugadas.
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Casilla casillas[][]; // tablero de 10 x 10 para pintar las casillas
    public int rojos[] = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36}; // Números rojos
    ArrayList<Integer> numRojos;
    Ficha ficha;
    Ficha fichas[];
    Image imagenes[];
    
    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(400, 800); // Crear una imagen de 400x500 píxeles para el doble búfer
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen
        // Instancio numRojos y añadado datos de tipo int a la lista numRojos
        numRojos = new ArrayList<Integer>();
        for(int i = 0; i < rojos.length; i++)
            numRojos.add(rojos[i]);
        
        casillas = new Casilla[FILAS][COLUMNAS];
        // Inicializar el tablero de casillas añadiendo su color correspondiente
        for (int i = 0; i < FILAS * COLUMNAS; i++) {
            int x = i / COLUMNAS; // Fila
            int y = i % COLUMNAS; // Columna
            int valor = i + 1; // Valor de la casilla (1 a FILAS * COLUMNAS)
            Color color = numRojos.contains(valor) ? Color.RED : Color.BLACK; // Verificar si el valor está en la lista numRojos
            casillas[x][y] = new Casilla((y * Casilla.DIM) + 30, (x * Casilla.DIM) + 50, valor, color); // Crear la casilla con el color correspondiente
        }
        //También se puede Inicializar el tablero de casillas así
//        for(int i = 0; i < casillas.length; i++)
//            for(int j = 0; j < casillas[i].length; j++)
//                if(numRojos.contains(new Integer((i * COLUMNAS) + j + 1)))
//                    casillas[i][j] = new Casilla((j * Casilla.DIM) + 30, (i * Casilla.DIM) + 50, (i * COLUMNAS) + j + 1, Color.RED);
//                else
//                    casillas[i][j] = new Casilla((j * Casilla.DIM) + 30, (i * Casilla.DIM) + 50, (i * COLUMNAS) + j + 1, Color.BLACK);

//       // Para instanciar las imágenes en el vector imágenes. 
        imagenes = new Image[NUMJUGADAS];
        for(int i = 0; i < NUMJUGADAS; i++)
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio06/Fichas/ficha" + (i+1) + ".png");
        
        //Para instanciar una ficha
        ficha = new Ficha(400, 400, 10, imagenes[2]);
                
                
        //this.setFocusable(true); // Permitir que el applet reciba eventos del teclado
        //this.requestFocus(); // Solicitar el foco del teclado automáticamente
        this.setSize(400, 800); // Ajustar el tamaño de la ventana al del lienzo
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    public void paint(Graphics g) {
        noseve.setColor(Color.GREEN); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 400, 800); // Llenar el área de dibujo con el color de fondo
        for(int i = 0; i < FILAS; i++)
            for(int j = 0; j < COLUMNAS; j++)
                casillas[i][j].paint(noseve);
       
        ficha.paint(noseve, this);
        
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen completa en el applet para evitar parpadeo
    }
    
    // Método para manejar eventos de clic del ratón
    public boolean mouseDown(Event ev, int x, int y) {
      
        return true; // Indicar que el evento ha sido manejado
    }

    // Método para manejar eventos de acción
    public boolean action(Event ev, Object obj) {
     
        return true; // Indicar que el evento ha sido manejado
    }
    
}