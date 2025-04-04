package Tercera.Ejercicio03; // Define el paquete al que pertenece la clase Carta

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image; // Importa la clase Image del paquete java.awt para manejar imágenes

public class Carta { // Declara la clase pública Carta
    public static final int ANCHO = 100; // Define una constante pública para el ancho de la carta
    public static final int ALTO = 200; // Define una constante pública para el alto de la carta
    Image imagen; // Declara una variable de instancia para almacenar la imagen de la carta
    int valor; // Declara una variable de instancia para almacenar el valor de la carta
    private int posX, posY; // Declara dos variables de instancia para almacenar la posición X e Y de la carta
    
    public Carta(Image imagen, int valor){ // Declara el constructor de la clase Carta que recibe una imagen y un valor
        this.imagen = imagen; // Inicializa la variable de instancia imagen con el parámetro imagen
        /*this.valor = valor; // Inicializa la variable de instancia valor con el parámetro valor
        if(valor > 10) this.valor = 10;  */ // Si el valor es mayor que 10, lo establece en 10 (asumiendo que es un As o una figura)
        this.valor = (valor > 10) ? 10 : valor; // Asigna el valor a la variable de instancia valor, limitándolo a un máximo de 10
    }
    
    public void paint(Graphics g, Applet app){ // Declara un método público que dibuja la carta en la pantalla
        g.drawImage(imagen, posX, posY, ANCHO, ALTO, app); // Dibuja la imagen de la carta en la posición X e Y especificada
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
}