package Tercera.Ejercicio04; // Define el paquete al que pertenece la clase Tablero

import java.applet.Applet; // Importa la clase Applet del paquete java.applet para crear un applet
import java.applet.AudioClip; // Importa la clase AudioClip para reproducir sonidos
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color; // Importa la clase Color para manejar colores
import java.awt.Event; // Importa la clase Event para manejar eventos
import java.awt.Graphics; // Importa la clase Graphics para dibujar en el applet
import java.awt.Image; // Importa la clase Image para manejar imágenes
import java.awt.Panel;
import java.awt.Point; // Importa la clase Point para representar coordenadas (x, y)
import java.net.MalformedURLException;
import java.net.URL;

public class Tablero extends Applet { // Declara la clase pública Tablero que extiende Applet
    public static final int TAM = 5; // Define el tamaño del tablero (5x5)
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Image imagenes[][]; // Arreglo bidimensional para almacenar las imágenes de las fichas
    Lugar lugares[][]; // Arreglo bidimensional para almacenar los lugares del tablero
    Point blanco; // Indica la posición de la ficha en blanco (espacio vacío)
    AudioClip error, acierto, exito; // Clips de audio para diferentes eventos (error, acierto, éxito)
    Button mezclar; // Botón para mezclar las fichas
    Panel panel1; // Panel para contener los componentes del applet

    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(400, 500); // Crear una imagen de 400x500 píxeles para el doble búfer
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen
        try{
        error = getAudioClip(new URL(getCodeBase(), "Tercera/Ejercicio04/sonidos/error.wav"));
        acierto = getAudioClip(new URL(getCodeBase(), "Tercera/Ejercicio04/sonidos/correct.wav"));
        exito = getAudioClip(new URL(getCodeBase(), "Tercera/Ejercicio04/sonidos/exito.wav"));
        }catch(MalformedURLException e){};
        imagenes = new Image[TAM][TAM]; // Crear un arreglo bidimensional de imágenes para las fichas
        // Cargar las imágenes de las fichas en el arreglo
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                imagenes[i][j] = getImage(getCodeBase(), "Tercera/Ejercicio04/botones/" + (((i * TAM) + j) + 1) + ".gif"); 
                // Cargar las imágenes desde la ruta especificada
            }
        }

        lugares = new Lugar[TAM][TAM]; // Crear un arreglo bidimensional de lugares para las fichas
        // Inicializar los lugares con las imágenes y valores correspondientes
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                lugares[i][j] = new Lugar(imagenes[i][j], (i * TAM) + j + 1); // Asigno a lugares, valores del 1 al 25.
                // Asignar cada imagen y un valor único a cada lugar
            }
        }

        blanco = new Point(TAM - 1, TAM - 1); // Inicializar la posición del espacio en blanco (última posición del tablero)

        this.setLayout(new BorderLayout()); // Establecer el diseño del applet como BorderLayout
        panel1 = new Panel(); // Crear un panel para contener las etiquetas
        panel1.setBackground(Color.BLACK); // Establecer el color de fondo del panel a verde
        //panel1.setForeground(Color.red); // Establecer el color de primer plano del panel a rojo
        mezclar = new Button("Mezclar"); // Crear un botón para mezclar las fichas
        panel1.add(mezclar); // Agregar el botón al panel
        this.add(panel1, BorderLayout.SOUTH); // Agregar el panel al applet en la parte inferior
        this.setFocusable(true); // Permitir que el applet reciba eventos del teclado
        this.requestFocus(); // Solicitar el foco del teclado automáticamente
        this.setSize(400, 500); // Ajustar el tamaño de la ventana al del lienzo
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    public void paint(Graphics g) {
        noseve.setColor(Color.BLACK); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 400, 500); // Llenar el área de dibujo con el color de fondo

        // Dibujar cada lugar del tablero
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                lugares[i][j].paint(noseve, this, j, i); 
                // Pintar cada lugar en su posición correspondiente
            }
        }
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen completa en el applet para evitar parpadeo
    }

    // Método para mover una ficha en el tablero
    public boolean mover(Point click) {
        Point desplazamiento, hasta; // Variables para calcular el desplazamiento y la posición final
        desplazamiento = new Point(delta(click.x, blanco.x), delta(click.y, blanco.y)); 
        // Calcular el desplazamiento entre el clic y la posición en blanco

        // Verificar si el movimiento es válido
        if ((desplazamiento.x == 0 && desplazamiento.y == 0)) return false; // Si no hay desplazamiento, no se puede mover
        if ((desplazamiento.x != 0) && (desplazamiento.y != 0)) return false; // Si hay desplazamiento en ambas direcciones, no se puede mover

        hasta = new Point(click.x + desplazamiento.x, click.y + desplazamiento.y); // Calcular la posición final
        if(!((blanco.x == hasta.x) && (blanco.y == hasta.y))) // Si la posición de blanco no coincide con la posición final, no se puede mover.
            mover(hasta); // Llamar recursivamente al método mover para mover la ficha a la posición final

        lugares[blanco.x][blanco.y].setImagen(lugares[click.x][click.y].getImagen()); // Mover la imagen de la ficha a la posición en blanco
        lugares[blanco.x][blanco.y].valor = lugares[click.x][click.y].valor; // Actualizar el valor de la posición en blanco
        lugares[click.x][click.y].setImagen(null); // Establecer la imagen de la posición de clic a null (vacío)
        lugares[click.x][click.y].valor = 24; // Actualizar el valor de la posición de clic a 24 (vacío)
        blanco = click; // Actualizar la posición en blanco al nuevo clic

        return true; // Si el movimiento es válido, devolver true
    }

    // Método para calcular la diferencia entre dos valores
    public int delta(int a, int b) {
        if (a == b) return 0; // Si los valores son iguales, no hay diferencia
        else return ((b - a) / Math.abs(b - a)); // Calcular la dirección del desplazamiento (1 o -1)
    }

    // Método para manejar eventos de clic del ratón
    public boolean mouseDown(Event ev, int x, int y) {
        Point click; // Variable para almacenar la posición del clic
        click = new Point(y / Lugar.DIM, x / Lugar.DIM);// Convertir las coordenadas del clic a la posición en el tablero
        if(!mover(click)) error.play();
        else acierto.play();
        repaint(); // Redibujar el applet después de mover la ficha
        
        return true; // Indicar que el evento ha sido manejado
    }

    // Método para manejar eventos de acción
    public boolean action(Event ev, Object obj) {
        if (ev.target == mezclar) { // Verificar si el botón presionado es el botón "mezclar"
            mezclar(); // Llamar al método mezclar
        }
        repaint(); // Redibujar el applet después de la acción

        return true; // Indicar que el evento ha sido manejado
    }
    
    public void mezclar() {
        // Mezclar las fichas en el tablero
        for (int i = 0; i < (TAM * TAM); i++) {
            int x1 = (int) (Math.random() * TAM);
            int y1 = (int) (Math.random() * TAM);
            int x2 = (int) (Math.random() * TAM);
            int y2 = (int) (Math.random() * TAM);

        // Intercambiar los valores y las imágenes entre dos posiciones aleatorias
            if(lugares[x1][y1].valor == 25 || lugares[x2][y2].valor == 25) continue; // Evitar mezclar con la posición en blanco
            int tempValor = lugares[x1][y1].valor;
            Image tempImagen = lugares[x1][y1].getImagen();

            lugares[x1][y1].valor = lugares[x2][y2].valor;
            lugares[x1][y1].setImagen(lugares[x2][y2].getImagen());

            lugares[x2][y2].valor = tempValor;
            lugares[x2][y2].setImagen(tempImagen);
        }

    // Restablecer la posición en blanco al final del tablero
    blanco = new Point(TAM - 1, TAM - 1);
    lugares[blanco.x][blanco.y].setImagen(null); // La posición en blanco no tiene imagen
    lugares[blanco.x][blanco.y].valor = 25; // Valor especial para la posición en blanco

    repaint(); // Redibujar el tablero
}
}