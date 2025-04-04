package Tercera.Ejercicio03;

import java.applet.Applet;
import java.awt.Graphics;
import java.util.ArrayList;

public class Mano {
    ArrayList<Carta> cartas; // Lista de cartas en la mano
    int posY; // Posición Y de la mano

    public Mano(int posY){
        cartas = new ArrayList<Carta>(); // Inicializa la lista de cartas
        this.posY = posY; // Establece la posición Y de la mano
    }

    public void paint(Graphics g, Applet app){
        for(int i = 0; i < cartas.size(); i++){ // Recorre cada carta en la mano
            cartas.get(i).setPosX(100 + (i * 30)); // Establece la posición X de la carta
            cartas.get(i).setPosY(posY); // Establece la posición Y de la carta
            cartas.get(i).paint(g, app); // Dibuja la carta en la pantalla
        }
    }
    
    public void anadirCarta(Carta carta){ // Método para agregar una carta a la mano
        cartas.add(carta); // Agrega la carta a la lista de cartas
    }
    public int getValor(){ // Método para obtener el valor total de la mano
        int valor = 0; // Inicializa el valor total de la mano en 0
        int ases = 0; // Inicializa el contador de ases en 0
        for(int i = 0; i < cartas.size(); i++){ // Recorre cada carta en la mano
            valor += cartas.get(i).valor; // Suma el valor de la carta al valor total de la mano
            if(cartas.get(i).valor == 1) ases++; // Si la carta es un As, incrementa el contador de ases
        }
        while(valor <= 11 && ases > 0){ // Mientras el valor total sea menor o igual a 11 y haya ases
            valor += 10; // Suma 10 al valor total (cambia el valor del As de 1 a 11)
            ases--; // Decrementa el contador de ases
        }
        return valor; // Devuelve el valor total de la mano
    }
    
    public int puntuacion(){
        int acumulador = 0;
        boolean as = false;
        for(Carta carta : cartas){
            acumulador += carta.valor;
            if(carta.valor == 1) as = true;
        }
        if((as) && (acumulador <= 11)) acumulador += 10;
        return acumulador;
    }
    public boolean seHaPasado(){
        return puntuacion() > 21;
    }
    public boolean menor17(){
        return puntuacion() < 17;
    }
}
