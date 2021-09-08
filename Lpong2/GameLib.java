import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.Graphics;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//
// Decompiled by Procyon v0.5.36
//

public class GameLib
{
    public static int WIDTH;
    public static int HEIGHT;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_CENTER = 2;
    public static final int KEY_UP = 0;
    public static final int KEY_DOWN = 1;
    public static final int KEY_LEFT = 2;
    public static final int KEY_RIGHT = 3;
    public static final int KEY_CONTROL = 4;
    public static final int KEY_ESCAPE = 5;
    public static final int KEY_A = 6;
    public static final int KEY_Z = 7;
    public static final int KEY_K = 8;
    public static final int KEY_M = 9;
    public static final int KEY_SPACE = 10;
    private static MyFrame frame;
    public static Graphics g;
    private static MyKeyAdapter keyboard;


    public static void initGraphics(final String s, final int width, final int height) {
        GameLib.WIDTH = width;
        GameLib.HEIGHT = height;
        (GameLib.frame = new MyFrame(s)).setDefaultCloseOperation(3);
        GameLib.frame.setSize(GameLib.WIDTH, GameLib.HEIGHT);
        GameLib.frame.setResizable(false);
        GameLib.frame.setVisible(true);
        GameLib.keyboard = new MyKeyAdapter();
        GameLib.frame.addKeyListener((KeyListener)GameLib.keyboard);
        GameLib.frame.requestFocus();
        GameLib.frame.createBufferStrategy(2);
        GameLib.g = GameLib.frame.getBufferStrategy().getDrawGraphics();
    }

    public static void initGraphics() {
        initGraphics("Projeto COO", GameLib.WIDTH, GameLib.HEIGHT);
    }

    public static void setColor(final Color color) {
        GameLib.g.setColor(color);
    }

    public static void drawLine(final double a, final double a2, final double a3, final double a4) {
        GameLib.g.drawLine((int)Math.round(a), (int)Math.round(a2), (int)Math.round(a3), (int)Math.round(a4));
    }

    public static void drawCircle(final double n, final double n2, final double n3) {
        GameLib.g.drawOval((int)Math.round(n - n3), (int)Math.round(n2 - n3), (int)Math.round(2.0 * n3), (int)Math.round(2.0 * n3));
    }

    public static void drawDiamond(final double n, final double n2, final double n3) {
        final int n4 = (int)Math.round(n);
        final int n5 = (int)Math.round(n2 - n3);
        final int n6 = (int)Math.round(n + n3);
        final int n7 = (int)Math.round(n2);
        final int n8 = (int)Math.round(n);
        final int n9 = (int)Math.round(n2 + n3);
        final int n10 = (int)Math.round(n - n3);
        final int n11 = (int)Math.round(n2);
        drawLine(n4, n5, n6, n7);
        drawLine(n6, n7, n8, n9);
        drawLine(n8, n9, n10, n11);
        drawLine(n10, n11, n4, n5);
    }

    public static void drawPlayer(final double n, final double n2, final double n3) {
      drawLine(n - n3, n2 + n3, n, n2 - n3);
      drawLine(n + n3, n2 + n3, n, n2 - n3);
      drawLine(n - n3, n2 + n3, n, n2 + n3 * 0.5);
      drawLine(n + n3, n2 + n3, n, n2 + n3 * 0.5);
    }

    public static void drawExplosion(final double n, final double n2, final double n3) {
        final int n4 = 5;
        setColor(new Color((int)(255.0 - Math.pow(n3, n4) * 255.0), (int)(128.0 - Math.pow(n3, n4) * 128.0), 0));
        drawCircle(n, n2, n3 * n3 * 40.0);
        drawCircle(n, n2, n3 * n3 * 40.0 + 1.0);
    }

    public static void fillRect(final double n, final double n2, final double a, final double a2) {
        GameLib.g.fillRect((int)Math.round(n - a / 2.0), (int)Math.round(n2 - a2 / 2.0), (int)Math.round(a), (int)Math.round(a2));
    }

    public static void drawText(final String str, final double a, final int n) {
        int n2 = 0;
        GameLib.g.setFont(new Font("Monospaced", 0, 28));
        final int stringWidth = GameLib.g.getFontMetrics().stringWidth(str);
        if (n == 0) {
            n2 = 40;
        }
        else if (n == 1) {
            n2 = GameLib.WIDTH - stringWidth - 40;
        }
        else if (n == 2) {
            n2 = GameLib.WIDTH / 2 - stringWidth / 2;
        }
        GameLib.g.drawString(str, n2, (int)Math.round(a));
    }

    public static void display() {
        GameLib.g.dispose();
        GameLib.frame.getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
        (GameLib.g = GameLib.frame.getBufferStrategy().getDrawGraphics()).setColor(Color.BLACK);
        GameLib.g.fillRect(0, 0, GameLib.frame.getWidth() - 1, GameLib.frame.getHeight() - 1);
        GameLib.g.setColor(Color.WHITE);
    }

    public static boolean isKeyPressed(final int n) {
        return GameLib.keyboard.isKeyPressed(n);
    }

    public static void debugKeys() {
        GameLib.keyboard.debug();
    }

    static {
        GameLib.WIDTH = 480;
        GameLib.HEIGHT = 700;
        GameLib.frame = null;
        GameLib.g = null;
        GameLib.keyboard = null;
    }
}
