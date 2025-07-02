package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class MiJdesktopPane extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // === FONDO NEGRO ===
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // === TÍTULO CENTRAL ===
        String titulo = "KENTUCKY FRIED CHICKEN";
        Font fuenteTitulo = new Font("Serif", Font.BOLD, 28);
        g.setFont(fuenteTitulo);
        FontMetrics fm = g.getFontMetrics();
        int textoAncho = fm.stringWidth(titulo);
        int textoX = (getWidth() - textoAncho) / 2;
        int textoY = 50;

        Color rojoKFC = new Color(216, 0, 0);
        g.setColor(rojoKFC);
        g.drawString(titulo, textoX, textoY);

        // Línea subrayada blanca
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.WHITE);
        g2.drawLine(20, textoY + 10, getWidth() - 20, textoY + 10);

        // === LOGO ESTILO KFC CON PAPAS ===
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int logoWidth = 180;
        int logoHeight = 200;

        // Fondo rojo redondeado
        g.setColor(rojoKFC);
        g.fillRoundRect(centerX - logoWidth / 2, centerY - logoHeight / 2, logoWidth, logoHeight, 60, 60);

        // === PAPAS FRITAS CENTRADAS ===
        Color[] amarillos = {
                new Color(255, 230, 0),
                new Color(255, 215, 0),
                new Color(255, 200, 0)
        };

        int numPapas = 7;
        int anchoPapa = 6;
        int espacio = 10;

        int totalAncho = (numPapas - 1) * espacio + anchoPapa; // espacio entre papas + última
        int inicioX = centerX - totalAncho / 2;
        int cajaY = centerY - 60;

        for (int i = 0; i < numPapas; i++) {
            g.setColor(amarillos[i % amarillos.length]);
            int altura = 40 + (i % 3) * 5;
            g.fillRect(inicioX + i * espacio, cajaY - altura, anchoPapa, altura);
        }

        // === CAJA DE PAPAS CENTRADA ===
        int cajaAncho = totalAncho + 20;
        int cajaX = centerX - cajaAncho / 2;

        g.setColor(new Color(180, 0, 0)); // rojo oscuro para contraste
        g.fillRoundRect(cajaX, cajaY, cajaAncho, 60, 20, 20);
        g.setColor(Color.WHITE);
        g.drawRoundRect(cajaX, cajaY, cajaAncho, 60, 20, 20);

        // === TEXTO "KFC" CENTRADO CON FONDO SUAVE ===
        String textoKfc = "KFC";
        Font fuenteKfc = new Font("SansSerif", Font.BOLD, 22);
        g.setFont(fuenteKfc);
        FontMetrics fmKfc = g.getFontMetrics();
        int kfcAncho = fmKfc.stringWidth(textoKfc);
        int kfcX = centerX - (kfcAncho / 2);
        int kfcY = centerY + 90;

        // Fondo redondeado gris claro
        int fondoAncho = kfcAncho + 20;
        int fondoAlto = 30;
        int fondoX = centerX - fondoAncho / 2;
        int fondoY = kfcY - 22;

        g.setColor(new Color(240, 240, 240));
        g.fillRoundRect(fondoX, fondoY, fondoAncho, fondoAlto, 20, 20);

        g.setColor(rojoKFC);
        g.drawString(textoKfc, kfcX, kfcY);
    }
}
