package Tercera.Ejercicio05; // Define el paquete al que pertenece la clase Tablero

import java.applet.Applet; // Importa la clase Applet del paquete java.applet para crear un applet
import java.awt.Color; // Importa la clase Color para manejar colores
import java.awt.Event; // Importa la clase Event para manejar eventos
import java.awt.Graphics; // Importa la clase Graphics para dibujar en el applet
import java.awt.Image; // Importa la clase Image para manejar imágenes


public class BuscaMinas extends Applet { // Declara la clase pública Tablero que extiende Applet
    public static final int DIM = 10; // Cantidad de filas y columnas 10 x 10.
    public static final int N_MINA = 10; // Cantidad de minas
    Image imagen; // Imagen que actuará como un "lienzo" para evitar parpadeo
    Graphics noseve; // Objeto Graphics que dibujará sobre la imagen (doble búfer)
    Image mina;
    Image reverso;
    Casilla casillas[][]; // tablero de 10 x 10 para pintar las casillas
    int contador = 0;
    
    
    // Método de inicialización (se ejecuta al iniciar el applet)
    public void init() {
        imagen = this.createImage(400, 500); // Crear una imagen de 400x500 píxeles para el doble búfer
        noseve = imagen.getGraphics(); // Obtener el objeto Graphics asociado a la imagen
       
        mina = getImage(getCodeBase(), "Tercera/Ejercicio05/Imagenes/mina.png");
        reverso = getImage(getCodeBase(), "Tercera/Ejercicio05/Imagenes/casilla.png");
        
        casillas = new Casilla [DIM][DIM]; //
        
        for(int i = 0; i < DIM; i++)
            for(int j = 0; j < DIM; j++)
                casillas[i][j] = new Casilla(100 + (j * Casilla.TAM), 100 + (i * Casilla.TAM), reverso);
        
        // Crear las minas y asignarlas a las casillas
        while(contador != N_MINA){
            int x = (int)(Math.random() * DIM); // Generar un número aleatorio entre 0 y 9 para la fila
            int y = (int)(Math.random() * DIM); // Generar un número aleatorio entre 0 y 9 para la columna
            if(casillas[x][y].getMina() == null){ // Verificar si la casilla no tiene una mina ya
                casillas[x][y].setMina(mina); // Establecer la imagen de la mina en la casilla
                contador++; // Incrementar el contador de minas colocadas
            }
        }
        // Colocar el número de minas alrededor de cada casilla
        for(int i = 0; i < DIM; i++) // Recorrer las filas
            for(int j = 0; j < DIM; j++){ // Recorrer las columnas
                int alrededor = 0; // Inicializar el contador de minas alrededor de la casilla
                for(int k = -1; k <= 1; k++) // Recorrer las filas adyacentes
                    for(int l = -1; l <= 1; l++) // Recorrer las columnas adyacentes
                        if(i + k >= 0 && i + k < DIM && j + l >= 0 && j + l < DIM && casillas[i + k][j + l].getMina() != null)// Verificar si la casilla adyacente está dentro de los límites y tiene una mina
                            alrededor++;// Incrementar el contador de minas alrededor
                casillas[i][j].setAlrededor(alrededor); // Establecer el número de minas alrededor de la casilla
            }
        
        
        //this.setFocusable(true); // Permitir que el applet reciba eventos del teclado
        //this.requestFocus(); // Solicitar el foco del teclado automáticamente
        this.setSize(400, 500); // Ajustar el tamaño de la ventana al del lienzo
    }

    public void update(Graphics g) {
        paint(g); // Redirige la actualización al método paint()
    }

    // Método para pintar en la pantalla
    public void paint(Graphics g) {
        noseve.setColor(Color.GREEN); // Establecer el color de fondo a negro
        noseve.fillRect(0, 0, 400, 500); // Llenar el área de dibujo con el color de fondo
        
        for(int i = 0; i < DIM; i++)
            for(int j = 0; j < DIM; j++)
                casillas[i][j].paint(noseve, this);
        g.drawImage(imagen, 0, 0, this); // Dibujar la imagen completa en el applet para evitar parpadeo
    }
    
    // Método para manejar eventos de clic del ratón
    public boolean mouseDown(Event ev, int x, int y) {
        // Bucle para recorrer todas las casillas para poder destaparlas.
       for(int i = 0; i < DIM; i++)
            for(int j = 0; j < DIM; j++)
                if(casillas[i][j].contains(x, y)) { // Verificar si el clic está dentro de la casilla
                    casillas[i][j].setTapada(false); // Desmarcar la casilla como tapada
                    //casillas[i][j].setMina(mina); // Establecer la imagen de la mina en la casilla
                    repaint(); // Volver a dibujar el applet para reflejar los cambios
                }
        return true; // Indicar que el evento ha sido manejado
    }

    // Método para manejar eventos de acción
    public boolean action(Event ev, Object obj) {
     
        return true; // Indicar que el evento ha sido manejado
    }
    
}