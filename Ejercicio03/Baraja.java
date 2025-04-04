package Tercera.Ejercicio03;

import java.awt.Image;
import java.util.ArrayList; // Importa la clase ArrayList del paquete java.util para manejar listas dinámicas
import java.util.Collections;

public class Baraja {
    ArrayList<Carta> cartas; // Lista de cartas en la baraja

    public Baraja(Image[] imgs) { // Constructor de la clase Baraja que recibe un arreglo de imágenes
        cartas = new ArrayList<Carta>();
        for(int i = 0; i < BlackJack.NUMCARTAS; i++)
            // Crea una nueva carta con la imagen correspondiente y el valor adecuado
            cartas.add(new Carta(imgs[i], (i % BlackJack.CPP) + 1)); // Agrega la carta a la lista
        Collections.shuffle(cartas); // desordena los elementos de la lista    
        
    }
    public Carta sacarCarta(){ // Método para sacar una carta de la baraja
        Carta cartaTemp = cartas.get(0); // Obtiene la carta en la posición 0 de la lista (la primera carta)
        cartas.remove(0); // Elimina la carta en la posición 0 de la lista (la primera carta)

        return cartaTemp; //Devuelve la carta que se ha sacado
    }
}
