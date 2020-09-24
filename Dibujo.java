
package Dibujo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class Dibujo extends JPanel{
    private JFrame ventana;
    private Container contenedor;
    
    //figura, mascara y ancho en bits
    private final long [] FIGURA = {
        0x00000000,
        0x00000000,
        0x00000000,
        0x00000000,
        0x00000000,
        0x00008000,
        0x00008000,
        0x0001C000,
        0x0001C000,
        0x0007F000,
        0x0007F000,
        0x003FFC00,
        0x07FFFFE0,
        0x07FFFFE0,
        0x03FDBFC0,
        0x03FDBFC0,
        0x00FDBF80,
        0x007FFE00,
        0x007FFE00,
        0x007FFE00,
        0x00FFFF00,
        0x00FFFF00,
        0x00FFFF00,
        0x01FC3F80,
        0x01FC3F80,
        0x03C003C0,
        0x030000A0,
        0x00000000,
        0x00000000,
        0x00000000,
        0x00000000,
        0x00000000
    };
    
    private final int MASCARA = 0x8000000;
    
    private final int ANCHO_BITS = 32;
    
    private int coordenadasx, coordenadasy;
    
    //Definiendo hilo de ejecucion
    private Thread hilo;

    public Dibujo() {
        ventana = new JFrame ("Dibujando icono");
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        
        contenedor = ventana.getContentPane();
        contenedor.setSize(800,600);
        contenedor.add(this, BorderLayout.CENTER);
        
        this.hilo = new Thread();
    }

    @Override
    protected void paintComponent(Graphics graficos) {
        super.paintComponent(graficos);
        
        //Iterador de la figura
        for (int i = 0; i < this.FIGURA.length; i++) {
          //Iterador para el ancho en bits
            for (int j = 0; j < this.ANCHO_BITS; j++) {
                long temp = this.FIGURA [i] & (this.MASCARA >> j);
                if (temp != 0){
                    graficos.drawLine(j + coordenadasx,
                                      i + coordenadasy,
                                      j + coordenadasx,
                                      i + coordenadasy);
                }
            }
        }
    }
    
    public void dibujar(){
        double iteraciones;
        Random r = new Random();//inicializamos el packete random para facilitar la obtencion de aleatoriedad
        
        for (int i = 0; i < 10; i++) {
            int desicion = r.nextInt(4); //Para determinar si se mueve a la izquierda o derecha
            this.coordenadasx = (int) (Math.random()*800);
            this.coordenadasy = (int) (Math.random()*500);
            switch(desicion){ //Swich de descicon para la direccion del movimiento.
            case 0:
                iteraciones = Math.ceil((this.coordenadasx)/10);//esta variable depende de la direccion
                for (int j = 0; j < iteraciones; j++) {
                    try{
                        this.coordenadasx = this.coordenadasx -10;
                        this.hilo.sleep(300);
                        paint(getGraphics());
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
                paint(getGraphics());
            break;
            case 1:
                iteraciones = Math.ceil((770-this.coordenadasx)/10);//esta variable depende de la direccion
                for (int j = 0; j < iteraciones; j++) {
                    try{
                        this.coordenadasx = this.coordenadasx +10;
                        this.hilo.sleep(300);
                        paint(getGraphics());
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
                paint(getGraphics());
            break;
            case 2:
                iteraciones = Math.ceil((this.coordenadasy)/10);//esta variable depende de la direccion
                for (int j = 0; j < iteraciones; j++) {
                    try{
                        this.coordenadasy = this.coordenadasy -10;
                        this.hilo.sleep(300);
                        paint(getGraphics());
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
                paint(getGraphics());
            break;
            case 3:
                iteraciones = Math.ceil((570-this.coordenadasy)/10);//esta variable depende de la direccion
                for (int j = 0; j < iteraciones; j++) {
                    try{
                        this.coordenadasy = this.coordenadasy+10;
                        this.hilo.sleep(300);
                        paint(getGraphics());
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
                paint(getGraphics());
            break;
            }   
            JOptionPane.showMessageDialog(null, "Coliciones registradas: " +(i+1));
        }
    }
}
