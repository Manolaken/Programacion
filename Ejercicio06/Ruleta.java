package Tercera.Ejercicio06; // Define el paquete al que pertenece la clase Tablero

import java.applet.Applet; // Importa la clase Applet del paquete java.applet para crear un applet
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color; // Importa la clase Color para manejar colores
import java.awt.Event; // Importa la clase Event para manejar eventos
import java.awt.Graphics; // Importa la clase Graphics para dibujar en el applet
import java.awt.HeadlessException;
import java.awt.Image; // Importa la clase Image para manejar imágenes
import java.awt.Panel;
import java.util.ArrayList; // También se puede instanciar un objeto sin haber importado su clase.


public class Ruleta extends Applet { // Declara la clase pública Tablero que extiende Applet
    // Atributos de Clase
    public static final int FILAS = 12; // Cantidad de filas y columnas 10 x 10.
    public static final int COLUMNAS = 3; // Cantidad de minas
    public static final int NUMJUGADAS = 10; // Para guardar las 10 últimas jugadas.
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Casilla casillas[][]; // tablero de 10 x 10 para pintar las casillas
    public int rojos[] = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36}; // Números rojos
    ArrayList<Integer> numRojos;
    Ficha activa;
    ArrayList<Ficha> fichas[];
    Image imagenes[];
    Button boton;
    int numeroSuerte; // variable para guardar el número que salga en la ruleta.
    int jugadas[] = new int[NUMJUGADAS];
    int jugadaMax = 0;
    
    // Cada elemento del vector es una lista e inicalmente a cada lista le metemos una ficha
    // pero cuando seleccionamos una ficha, la metemos añadimos en la lista de esa ficha
    
    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(700, 800); // Crear una imagen de 400x500 píxeles para el doble búfer
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen
        setup(); // Llamada al metodo setup para crear el boton
        
        
        
        // Instancio numRojos y añadado datos de tipo int a la lista numRojos
        numRojos = new ArrayList<Integer>();
        for(int i = 0; i < rojos.length; i++)
            numRojos.add(rojos[i]);
        
        casillas = new Casilla[FILAS][COLUMNAS];
        // Inicializar el tablero de casillas añadiendo su color correspondiente
        for (int i = 0; i < FILAS * COLUMNAS; i++) {
            int x = i / COLUMNAS; // Fila
            int y = i % COLUMNAS; // Columna
            int valor = i + 1; // Valor de la casilla (1 a FILAS * COLUMNAS)
            Color color = numRojos.contains(valor) ? Color.RED : Color.BLACK; // Verificar si el valor está en la lista numRojos
            casillas[x][y] = new Casilla((y * Casilla.DIM) + 30, (x * Casilla.DIM) + 50, valor, color); // Crear la casilla con el color correspondiente
        }
        //También se puede Inicializar el tablero de casillas así
//        for(int i = 0; i < casillas.length; i++)
//            for(int j = 0; j < casillas[i].length; j++)
//                if(numRojos.contains(new Integer((i * COLUMNAS) + j + 1)))
//                    casillas[i][j] = new Casilla((j * Casilla.DIM) + 30, (i * Casilla.DIM) + 50, (i * COLUMNAS) + j + 1, Color.RED);
//                else
//                    casillas[i][j] = new Casilla((j * Casilla.DIM) + 30, (i * Casilla.DIM) + 50, (i * COLUMNAS) + j + 1, Color.BLACK);

//       // Para instanciar las imágenes en el vector imágenes. 
        imagenes = new Image[NUMJUGADAS];
        for(int i = 0; i < NUMJUGADAS; i++)
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio06/Fichas/ficha" + (i+1) + ".png");
        
        int valores[] = {1, 5, 10, 25, 50, 100, 500, 1000, 5000, 10000};
        // Inicializar el array de listas
        fichas = new ArrayList[NUMJUGADAS];
        for (int i = 0; i < NUMJUGADAS; i++) {
            fichas[i] = new ArrayList<>(); // Inicializar cada lista
            // Crear la ficha inicial y añadirla a la lista // Crear la ficha inicial
            fichas[i].add(new Ficha(300, 50 + (i * Ficha.TAM), valores[i], imagenes[i])); // Añadir la ficha inicial a la lista correspondiente
        }
                
        //this.setFocusable(true); // Permitir que el applet reciba eventos del teclado
        //this.requestFocus(); // Solicitar el foco del teclado automáticamente
        this.setSize(700, 800); // Ajustar el tamaño de la ventana al del lienzo
    }

    private void setup() throws HeadlessException {
        Panel panel = new Panel();
        boton = new Button("Jugar !");
        panel.add(boton);
        this.setLayout(new BorderLayout());
        this.add("North", panel);
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    public void paint(Graphics g) {
        noseve.setColor(Color.GREEN); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 700, 800); // Llenar el área de dibujo con el color de fondo
        for(int i = 0; i < FILAS; i++)
            for(int j = 0; j < COLUMNAS; j++)
                casillas[i][j].paint(noseve);
       
        for (int i = 0; i < NUMJUGADAS; i++) {
            for (Ficha ficha : fichas[i]) {
                ficha.paint(noseve, this); // Dibujar cada ficha en la lista
            }
        }
        // Para pintar las jugadas ganadoras
        noseve.setColor(Color.BLACK);
        noseve.drawString("Últimas jugadas: ", 400, 75);
        for(int i = 0; i < jugadas.length; i++)
            for(int j = 0; j < rojos.length; j++)
                if(jugadas[i] == rojos[j]){
                    noseve.setColor(Color.RED);
                    noseve.drawString("" + jugadas[i], 450, 125 + (i * 50));
                }
                else{
                    noseve.setColor(Color.BLACK);
                    noseve.drawString("" + jugadas[i], 450, 125 + (i * 50));
                }
        
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen completa en el applet para evitar parpadeo
    }
    
    // Método para manejar eventos de clic del ratón
    public boolean mouseDown(Event ev, int x, int y) {
        for(int i = 0; i < NUMJUGADAS; i++){
            for (Ficha ficha : fichas[i]) {
                if(ficha.contains(x, y)){ // Verificar si el clic está dentro de la ficha
                    activa = ficha; // Asignar la ficha activa al clic del ratón
                    return true; // Indicar que el evento ha sido manejado
                }
            }
        }
        return true; // Indicar que el evento ha sido manejado
    }

    public boolean mouseDrag(Event ev, int x, int y) {
        if(activa != null){
            activa.update(x, y);
            repaint(); // Llamar al método repaint() para actualizar la pantalla
        }
        return true; // Indicar que el evento ha sido manejado
    }

    public boolean mouseUp(Event ev, int x, int y) {
        if (activa != null) {
        // Crear una nueva ficha en la posición inicial de la ficha activa
        Ficha nuevaFicha = new Ficha(
            activa.getPosXInicial(), // Posición inicial en X
            activa.getPosYInicial(), // Posición inicial en Y
            activa.precio,
            activa.imagen
        );
        for(int i = 0; i < fichas.length; i++)
            for(Ficha ficha : fichas[i]){
                fichas[i].add(nuevaFicha);
                activa = null;
                repaint();
                activa.cargarApuesta(casillas);
            }
    }
    return true; // Indicar que el evento ha sido manejado
    }
    
    public boolean action(Event ev, Object obj){
        if(ev.target instanceof Button){
            this.numeroSuerte = (int)(Math.random() * 37); // para que nos devuelva un numero aleatorio entre 0 y 36
            //for(int i = 0; i < jugadas.length; i++)
            if(jugadaMax < NUMJUGADAS){
                jugadas[jugadaMax] = numeroSuerte;
                jugadaMax++;
            }
            else
                jugadaMax = 0;
            repaint();
        }
        
        return false;
    }
}