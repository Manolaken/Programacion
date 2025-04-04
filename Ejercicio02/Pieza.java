package Tercera.Ejercicio02; // Declaración del paquete

import java.applet.Applet; // Importación de la clase Applet
import java.awt.Graphics; // Importación de la clase Graphics
import java.awt.Image; // Importación de la clase Image
import java.awt.Rectangle; // Importación de la clase Rectangle

public class Pieza extends Rectangle { // Declaración de la clase Pieza que extiende Rectangle
    public static final int DIMENSION = 60; // Tamaño de la pieza

    Image imagen; // Imagen de la pieza
    int posicion; // Posición correcta de la pieza en la cuadrícula
    private boolean colocado = false; // Indica si la pieza está bien colocada

    public Pieza(Image img, int pos) { // Constructor de la clase Pieza
        super((int)(Math.random() * 300 + 350), (int)(Math.random() * 450), DIMENSION, DIMENSION); // Posición inicial aleatoria y tamaño de la pieza
        this.imagen = img; // Asigna la imagen a la pieza
        this.posicion = pos; // Asigna la posición correcta a la pieza
        this.colocado = false; // Inicializa la pieza como no colocada
    }

    public void paint(Graphics g, Applet app) { // Método para pintar la pieza
        g.drawImage(imagen, x, y, app); // Dibuja la imagen de la pieza en su posición actual
    }

    public boolean isColocado() { // Método para verificar si la pieza está colocada
        return colocado; // Devuelve el estado de la pieza (colocada o no)
    }

    public void setColocado(boolean colocado) { // Método para establecer el estado de la pieza
        this.colocado = colocado; // Asigna el estado de la pieza (colocada o no)
    }

    public int getPosicion() { // Método para obtener la posición correcta de la pieza
        return posicion; // Devuelve la posición correcta de la pieza
    }

    public void mover(int posx, int posy) { // Método para mover la pieza
        x = posx; // Asigna la nueva posición x de la pieza
        y = posy; // Asigna la nueva posición y de la pieza
    }

    public boolean contiene(int x, int y) { // Método para verificar si un punto (x, y) está dentro de la pieza
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height; // Devuelve true si el punto está dentro de la pieza
    }
}