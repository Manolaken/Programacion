package Tercera.Ejercicio01; // Declaración del paquete

import java.applet.Applet; // Importación de la clase Applet
import java.awt.Graphics; // Importación de la clase Graphics
import java.awt.Image; // Importación de la clase Image

public class DibujoAnimado {
    // Atributos de clase
    private Image imagenes[]; // Para guardar el conjunto de imágenes
    public int indice = 0; // Para saber qué imagen se está mostrando

    // Constructor
    public DibujoAnimado(Image[] imgs) {
        // Inicializamos el atributo imagenes con el parámetro imgs
        imagenes = imgs; // Guardamos el conjunto de imágenes en el atributo imagenes
    }

    // Método para obtener las imágenes
    public Image[] getImagenes() {
        return imagenes;
    }

    // Método para establecer las imágenes
    public void setImagenes(Image[] imagenes) {
        this.imagenes = imagenes;
    }

    // Método para pintar la imagen actual en el applet
    public void paint(Graphics g, Applet ap) {
        g.drawImage(imagenes[indice], 0, 0, 200, 250, ap); // Dibuja la imagen actual en el applet
    }

    // Método para actualizar el índice de la imagen
    public void update() {
        indice = (indice + 1) % imagenes.length; // Asigna a índice el resto de la división de indice + 1 entre el tamaño del array de imágenes
    }
}