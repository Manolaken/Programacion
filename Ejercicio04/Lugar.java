package Tercera.Ejercicio04;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Lugar {
    public static final int DIM = 48; // Tamaño de las imágenes
    private Image imagen; // Encapsulamos la Imagen del lugar
    int valor; // Valor del lugar
    
    public Lugar(Image imagen, int valor) {
        this.imagen = imagen; // Inicializa la imagen del lugar
        this.valor = valor; // Inicializa el valor del lugar
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    public void paint(Graphics g, Applet app, int x, int y) {
        if(imagen != null); // Verifica si la imagen no es nula
            g.drawImage(imagen, x*DIM, y*DIM, app); // Dibuja la imagen en la posición (x, y) del applet
    }
}
