package Tercera.Ejercicio06;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ficha extends Rectangle{
    public static final int TAM = 50;
    public Image imagen;
    public int precio;
    private int posXInicial; // Posición inicial en X
    private int posYInicial; // Posición inicial en Y
    ArrayList<Integer> numerosApostados;

    public Ficha(int posX, int posY, int valor, Image img) {
        super(posX, posY, TAM, TAM);
        imagen = img;
        precio = valor;
        this.posXInicial = posX; // Guardar la posición inicial
        this.posYInicial = posY; // Guardar la posición inicial
    }
    
    public int getPosXInicial() {
        return posXInicial;
    }

    public int getPosYInicial() {
        return posYInicial;
    }
    
    public void paint(Graphics g, Applet app){
        
        g.drawImage(imagen, x, y, this.width, this.height, app);
    }
    
    public void update(int posX, int posY){
        x = posX - (TAM/2);
        y = posY - (TAM/2);
    }   
    
    public void cargarApuesta(Casilla casillas[][]){
        numerosApostados.clear(); // Necesario vaciar la lista por si cambia la apuesta
        for(int i = 0; i < casillas.length; i++)
            for(int j = 0; j < casillas[i].length; j++)
                if(casillas[i][j].intersects(this))
                    numerosApostados.add(new Integer(casillas[i][j].getValor()));
        
    }
}
