package Tercera.Ejercicio06;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Font;

public class Casilla extends Rectangle{
    public static final int DIM = 60;
    int valor;
    Color color;

    public Casilla(int posX, int posY, int val, Color color) {
        super(posX, posY, DIM, DIM);
        valor = val;
        this.color = color;
    }

    
    public void paint(Graphics g){
        g.setColor(Color.BLACK); // Color del borde
        g.drawRect(x, y, DIM, DIM); // Dibuja el borde de la casilla
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24)); // Establece la fuente y el tamaño
        g.setColor(color); // Color de la casilla
        g.fillOval(x, y, DIM, DIM);
        g.setColor(Color.WHITE); // Color de la casilla que se aplicará al texto
        g.drawString("" + valor, x + 20, y + 35); // Dibuja el valor en la casilla
        
    }
    
}
