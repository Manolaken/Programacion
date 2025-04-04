package Tercera.Ejercicio02; // Declaración del paquete

import java.applet.Applet; // Importación de la clase Applet
import java.awt.Color; // Importación de la clase Color
import java.awt.Graphics; // Importación de la clase Graphics
import java.awt.Image; // Importación de la clase Image
import java.awt.Event; // Importación de la clase Event

public class Puzzle extends Applet { // Declaración de la clase Puzzle que extiende Applet
    
    // Atributos de la clase Principal
    public static final int PIEZAS = 25; // Número total de piezas del puzzle
    public static final int FILAS = 5; // Número de filas en la cuadrícula del puzzle
    public static final int COLUMNAS = 5; // Número de columnas en la cuadrícula del puzzle
    public static final int TAMANO_CELDA = 60; // Tamaño de cada celda en píxeles
    Image imagenes[]; // Arreglo de imágenes para las piezas del rompecabezas

    Pieza piezas[]; // Arreglo de objetos Pieza para las piezas del rompecabezas
    Pieza actual; // Pieza que se está moviendo actualmente
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
 
    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(700, 500); // Crear una imagen de 700x500 píxeles
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen

        imagenes = new Image[PIEZAS]; // Crear el arreglo de imágenes para las piezas
        piezas = new Pieza[PIEZAS]; // Crear el arreglo de objetos Pieza para las piezas del rompecabezas

        for(int i = 0; i < PIEZAS; i++){
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio02/directorioImagenes/" + (i + 1) + ".png"); // Cargar las imágenes
            piezas[i] = new Pieza(imagenes[i], i); // Crear un objeto Pieza con la imagen correspondiente y la posición correcta     
            }

        this.setFocusable(true); // Permite que el applet reciba eventos del teclado
        this.requestFocus(); // Solicita el foco del teclado automáticamente

        this.setSize(700, 500); // Ajustar el tamaño de la ventana al del lienzo    
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    public void paint(Graphics g) {
        // Limpiar el área de dibujo llenándola con el color de fondo
        noseve.setColor(Color.BLACK); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 700, 500); // Llenar el área de dibujo con el color de fondo

        // Dibujar la cuadrícula
        noseve.setColor(Color.WHITE); // Establecer el color de la cuadrícula a blanco
        for (int i = 0; i <= FILAS; i++) {
            noseve.drawLine(0, i * TAMANO_CELDA, COLUMNAS * TAMANO_CELDA, i * TAMANO_CELDA); // Dibujar líneas horizontales
        }
        for (int i = 0; i <= COLUMNAS; i++) {
            noseve.drawLine(i * TAMANO_CELDA, 0, i * TAMANO_CELDA, FILAS * TAMANO_CELDA); // Dibujar líneas verticales
        }

        // Dibujar las piezas
        for(int i = 0; i < PIEZAS; i++){
            piezas[i].paint(noseve, this); // Dibujar cada pieza en su posición actual
        }

        // Dibujar la imagen completa en la pantalla para evitar parpadeo
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen en el applet
    }

    public boolean mouseDown(Event ev, int x, int y) { // Recoge el evento y la posición X e Y del lugar en el que se ha producido.
        for (int i = 0; i < PIEZAS; i++) {
            if (piezas[i].contiene(x, y)) { // Verifica si la pieza contiene el punto (x, y)
                actual = piezas[i]; // Asigna la pieza actual a la pieza que contiene el punto (x, y)
                break; // Sale del bucle una vez encontrada la pieza
            }
        }
        return true; // Indica que el evento ha sido manejado
    }

    // Método para cuando arrastras el ratón con el botón izq pulsado
    public boolean mouseDrag(Event ev, int x, int y) {
        if (actual != null && !actual.isColocado()) { // Verifica si hay una pieza seleccionada y no está colocada
            actual.mover(x - TAMANO_CELDA / 2, y - TAMANO_CELDA / 2); // Mueve la pieza a la nueva posición (x, y)
            repaint(); // Redibuja el applet
        }
        return true; // Indica que el evento ha sido manejado
    }

    public boolean mouseUp(Event ev, int x, int y) {
        if (actual != null && !actual.isColocado()) { // Verifica si hay una pieza seleccionada y no está colocada
            int fila = y / TAMANO_CELDA; // Calcula la fila en la que se soltó la pieza
            int columna = x / TAMANO_CELDA; // Calcula la columna en la que se soltó la pieza
            int posicionCorrecta = fila * COLUMNAS + columna; // Calcula la posición correcta en la cuadrícula

            if (actual.getPosicion() == posicionCorrecta) { // Verifica si la pieza está en la posición correcta
                actual.mover(columna * TAMANO_CELDA, fila * TAMANO_CELDA); // Mueve la pieza a la posición correcta
                actual.setColocado(true); // Marca la pieza como colocada
            }
        }
        actual = null; // Deselecciona la pieza actual
        repaint(); // Redibuja el applet
        return true; // Indica que el evento ha sido manejado
    }
}