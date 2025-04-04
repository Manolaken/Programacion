package Tercera.Ejercicio03;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class BlackJack extends Applet {
    // Atributos de la clase Principal
    public static final int NUMCARTAS = 52; // Número total de piezas del puzzle
    public static final int CPP = 13; // Número de filas en la cuadrícula del puzzle
    Image imagenes[]; // Arreglo de imágenes para las piezas del rompecabezas
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Baraja baraja; // Objeto Baraja que representa la baraja de cartas
    Mano jugador; // Objeto Mano que representa la mano del jugador
    Mano croupier; // Objeto Mano que representa la mano de la banca
    TextField apuesta;
    Panel panel; // Panel para contener el applet
    Button pedirCarta; // Botón para iniciar el juego
    Button plantarse; // Botón para plantarse
    Boolean mePlanto = false;

    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(700, 500); // Crear una imagen de 700x500 píxeles
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen
        String palos[] = {"_of_clubs.png", "_of_diamonds.png", "_of_hearts.png", "_of_spades.png"}; // Nombres de los palos
        imagenes = new Image[NUMCARTAS]; // Crear el arreglo de imágenes para las cartas
        for (int i = 0; i < NUMCARTAS; i++)
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio03/Cartas/" + ((i % CPP) + 1) + palos[i / CPP]); // Cargar las imágenes de las cartas    

        baraja = new Baraja(imagenes); // Crear la baraja de cartas
        jugador = new Mano(265); // Crear la mano del jugador
        croupier = new Mano(50); // Crear la mano de la banca
        confPaneles();

        this.setFocusable(true); // Permite que el applet reciba eventos del teclado
        this.requestFocus(); // Solicita el foco del teclado automáticamente

        this.setSize(700, 500); // Ajustar el tamaño de la ventana al del lienzo    
    }

    public void confPaneles() throws HeadlessException {
        this.setLayout(new BorderLayout()); // Para poder distribuir luego los paneles.
        Panel panel1 = new Panel();// Crear un panel para contener las etiquetas
        panel1.setBackground(Color.green);
        panel1.setForeground(Color.red);
        Label etiqueta = new Label("Introduce tu apuesta: ", Label.RIGHT);
        apuesta = new TextField("", 10);
        panel1.add(etiqueta);
        panel1.add(apuesta);
        this.add("North", panel1);//añadimos el panel1 al applet y los botones se visualizarán en la ventana.

        Panel panel2 = new Panel(); // Crear un panel para los botones
        panel2.setBackground(Color.green);
        //modificadores necesarios para que se pueda visualizar la ventana y los botones.
        pedirCarta = new Button("Pedir Carta");// Crear el botón "Pedir Carta"
        plantarse = new Button("Plantarse");// Crear el botón "Me Planto"
        panel2.add(pedirCarta);//añadimos el boton1 al panel1.
        panel2.add(plantarse);//añadimos el boton2 al panel1.
        this.add("South", panel2);
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    @Override
    public void paint(Graphics g) {
    // Limpiar el área de dibujo llenándola con el color de fondo
    noseve.setColor(Color.green); // Establecer el color de fondo a verde
    noseve.fillRect(0, 0, 700, 500); // Llenar el área de dibujo con el color de fondo

    jugador.paint(noseve, this); // Dibujar la mano del jugador
    croupier.paint(noseve, this); // Dibujar la mano del croupier

    if (jugador.seHaPasado()) {
        noseve.setColor(Color.RED);
        noseve.drawString("Te has pasado de 21", 500, 350);
        if (croupier.puntuacion() <= 21) {
            noseve.drawString("Croupier gana con " + croupier.puntuacion(), 500, 300);
        }
    } else if (mePlanto) {
        if (jugador.puntuacion() > croupier.puntuacion() && jugador.puntuacion() <= 21) {
            noseve.setColor(Color.RED);
            noseve.drawString("Has GANADO", 500, 350);
        } else if (jugador.puntuacion() == croupier.puntuacion() && jugador.puntuacion() <= 21) {
            noseve.setColor(Color.RED);
            noseve.drawString("Has EMPATADO", 500, 350);
        } else {
            noseve.setColor(Color.RED);
            noseve.drawString("Croupier gana con : " + croupier.puntuacion(), 500, 300);
        }
    }

    // Dibujar la imagen completa en la pantalla para evitar parpadeo
    g.drawImage(imagen, 0, 0, this); // Dibujar la imagen en el applet
}

    public boolean action(Event ev, Object obj) {
        if (ev.target instanceof TextField) {
            apuesta.setEnabled(false); // Para impedir que se modifique la apuesta después de hacerla
            // Repartir dos cartas al jugador y una a la banca
            croupier.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano de la banca
            jugador.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano del jugador
            jugador.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano del jugador
            repaint();
            return true;
        } else if (ev.target instanceof Button) {
            if (ev.arg.equals("Pedir Carta")) { // Si el evento es del botón "Pedir Carta"
                jugador.anadirCarta(baraja.sacarCarta()); // Añadir una carta a la mano del jugador
                repaint();
                if (jugador.seHaPasado()) { // Si el valor de las cartas del jugador es mayor a 21
                    pedirCarta.setEnabled(false); // Desactivar el botón "Pedir Carta"
                    plantarse.setEnabled(false); // Desactivar el botón "Plantarse"
                    juegaElCroupier();
                }
                return true;
            } else if (ev.arg.equals("Plantarse")) { // Si el evento es del botón "Plantarse"
                mePlanto = true;
                pedirCarta.setEnabled(false);
                plantarse.setEnabled(false);
                juegaElCroupier();
                repaint();
                return true;
            }
        }
        return true;
    }

    private void juegaElCroupier() {
        while (croupier.puntuacion() < 17) {
            croupier.anadirCarta(baraja.sacarCarta());
        }
        repaint();
    }
}