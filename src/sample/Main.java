package sample;

import javax.swing.*;

public class Main{

    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setTitle("Sistema multiagentes - Peces");
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);

        OceanoJPanel panel = new OceanoJPanel();
        ventana.setContentPane(panel);

        ventana.setVisible(true);
        panel.Ejecutar();
    }
}
