package Tercera.Ejercicio01; // Declaración del paquete

import java.applet.Applet; // Importación de la clase Applet
import java.awt.Color; // Importación de la clase Color
import java.awt.Graphics; // Importación de la clase Graphics
import java.awt.Image; // Importación de la clase Image
import java.awt.Event; // Importación de la clase Event

public class Caminar extends Applet implements Runnable { // Declaración de la clase Caminar que extiende Applet e implementa Runnable
    // Atributos de la clase Principal
    public static final int GURRERO = 0; // Constante para identificar al guerrero
    public static final int VAQUERO = 1; // Constante para identificar al vaquero
    public static final int HAMPON = 2; // Constante para identificar al hampón

    public static final int TIEMPO = 50; // Tiempo de espera entre fotogramas (en ms)
    Thread animacion; // Hilo para la animación
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Image fotogramas[][]; // Matriz de imágenes para los fotogramas de la animación
    String lugares[] = {"Guerrillero/g", "Hampon/h", "Vaquero/v"}; // Para guardar los nombres o rutas de las imágenes
    DibujoAnimado da; // Objeto de la clase DibujoAnimado para la animación del personaje seleccionado (guerrero por defecto).

    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(300, 300); // Crear una imagen de 300x300 píxeles
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen

        fotogramas = new Image[3][4]; // Inicializar la matriz de fotogramas con 3 personajes y 4 imágenes cada uno
        for (int i = 0; i < fotogramas.length; i++) // Bucle para cargar las imágenes de los fotogramas
            for (int j = 0; j < fotogramas[i].length; j++)
                // Cargar cada imagen en la matriz de fotogramas a partir de un archivo GIF obtenido mediante getCodeBase() y la ruta de la imagen en el paquete Sprites de la carpeta Tercera/Ejercicio01
                fotogramas[i][j] = getImage(getCodeBase(), "Tercera/Ejercicio01/Sprites/" + lugares[i] + (j + 1) + ".gif"); 

        da = new DibujoAnimado(fotogramas[0]); // Crear un objeto de la clase DibujoAnimado y pasarle el array de imágenes del primer personaje

        this.setFocusable(true); // Permite que el applet reciba eventos del teclado
        this.requestFocus(); // Solicita el foco del teclado automáticamente

        this.setSize(300, 300); // Ajustar el tamaño de la ventana al del lienzo    
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método que se ejecuta al iniciar el applet (inicia el hilo)
    public void start() {
        animacion = new Thread(this); // Crear un nuevo hilo asociado a esta clase
        animacion.start(); // Iniciar el hilo (ejecuta el método run())
    }

    // Método para pintar en la pantalla
    public void paint(Graphics g) {
        // Limpiar el área de dibujo llenándola con el color de fondo
        noseve.setColor(Color.BLACK); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 300, 300); // Llenar el área de dibujo con el color de fondo
        da.paint(noseve, this); // Llamar al método paint() del objeto DibujoAnimado para dibujar la imagen actual
        da.update(); // Llamar al método update() del objeto DibujoAnimado para actualizar el índice de la imagen
        // Dibujar la imagen completa en la pantalla para evitar parpadeo
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen en el applet
    }

    // Método que ejecuta la animación en un bucle infinito
    public void run() {
        do {
            repaint(); // Redibujar el applet
            // Pausar el hilo por 30 ms para controlar la velocidad de la animación
            try {
                Thread.sleep(TIEMPO); // Pausar el hilo
            } catch (InterruptedException e) {
                // Si ocurre una interrupción, simplemente continuar
            }
        } while (true); // Continuar el bucle indefinidamente
    }

    @Override
    public boolean keyDown(Event e, int tecla) {
        switch (tecla) {
            case 103: // Tecla 'g'
            case 71:  // Tecla 'G'
                da.setImagenes(fotogramas[GURRERO]); // Cambiar las imágenes del objeto DibujoAnimado al guerrero
                break;
            case 104: // Tecla 'h'
            case 72:  // Tecla 'H'
                da.setImagenes(fotogramas[VAQUERO]); // Cambiar las imágenes del objeto DibujoAnimado al vaquero
                break;
            case 118: // Tecla 'v'
            case 86:  // Tecla 'V'
                da.setImagenes(fotogramas[HAMPON]); // Cambiar las imágenes del objeto DibujoAnimado al hampón
                break;
        }
        return true; // Indicar que el evento ha sido procesado
    }
}