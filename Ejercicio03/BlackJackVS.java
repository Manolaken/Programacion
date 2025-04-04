package Tercera.Ejercicio03;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Label;

public class BlackJackVS extends Applet implements ActionListener {
    // Atributos de la clase Principal
    public static final int NUMCARTAS = 52; // Número total de piezas del puzzle
    public static final int CPP = 13; // Número de filas en la cuadrícula del puzzle
    Image imagenes[]; // Arreglo de imágenes para las piezas del rompecabezas
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Baraja baraja; // Objeto Baraja que representa la baraja de cartas
    Mano jugador; // Objeto Mano que representa la mano del jugador
    Mano croupier; // Objeto Mano que representa la mano de la banca
    Panel panel; // Panel para contener el applet
    Button pedirCarta; // Botón para iniciar el juego
    Button plantarse; // Botón para plantarse
    TextField apuesta;


    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(700, 500); // Crear una imagen de 700x500 píxeles
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen
        String palos[] = {"_of_clubs.png", "_of_diamonds.png", "_of_hearts.png", "_of_spades.png"}; // Nombres de los palos
        imagenes = new Image[NUMCARTAS]; // Crear el arreglo de imágenes para las cartas
        for (int i = 0; i < NUMCARTAS; i++)
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio03/Cartas/" + ((i % CPP) + 1) + palos[i / CPP]); // Cargar las imágenes de las cartas    

        baraja = new Baraja(imagenes); // Crear la baraja de cartas
        jugador = new Mano(275); // Crear la mano del jugador
        croupier = new Mano(50); // Crear la mano de la banca

        for (int i = 0; i < 2; i++) { // Repartir dos cartas al jugador y a la banca
            jugador.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano del jugador
            croupier.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano de la banca
        }
        Label etiqueta = new Label("Introduce tu apuesta: ", Label.RIGHT);

        pedirCarta = new Button("Pedir Carta"); // Crear el botón "Pedir Carta"
        plantarse = new Button("Plantarse"); // Crear el botón "Me Planto"
        pedirCarta.addActionListener(this); // Añadir ActionListener al botón "Pedir Carta"
        plantarse.addActionListener(this); // Añadir ActionListener al botón "Plantarse"
        Panel panel1 = new Panel(); // Crear un panel para contener los botones
        panel1.add(pedirCarta); // Añadir el botón "Pedir Carta" al panel
        panel1.add(plantarse); // Añadir el botón "Plantarse" al panel
        this.add(panel1); // Añadir el panel al applet

        this.setFocusable(true); // Permite que el applet reciba eventos del teclado
        this.requestFocus(); // Solicita el foco del teclado automáticamente

        this.setSize(700, 500); // Ajustar el tamaño de la ventana al del lienzo    
    }

    @Override
    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    @Override
    public void paint(Graphics g) {
        // Limpiar el área de dibujo llenándola con el color de fondo
        noseve.setColor(Color.BLACK); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 700, 500); // Llenar el área de dibujo con el color de fondo
        jugador.paint(noseve, this); // Dibujar la mano del jugador
        croupier.paint(noseve, this); // Dibujar la mano de la banca

        if(jugador.getValor() > 21){
            g.drawString("Te has pasado de 21", 500, 25);
        }
        // Dibujar la imagen completa en la pantalla para evitar parpadeo
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen en el applet
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pedirCarta) { // Si el evento es del botón "Pedir Carta"
            jugador.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano del jugador
            repaint();
            if(jugador.getValor() > 21)
                repaint();
        } else if (e.getSource() == plantarse) { // Si el evento es del botón "Plantarse"
            
        }
    }
}