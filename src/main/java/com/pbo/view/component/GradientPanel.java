package com.pbo.view.component;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

public class GradientPanel extends JPanel {
    private final Color start;
    private final Color end;

    public GradientPanel(LayoutManager layout, Color start, Color end) {
        super(layout);
        this.start = start;
        this.end = end;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int lebar = getWidth();
        int tinggi = getHeight();
        g2.setPaint(new GradientPaint(0, 0, start, lebar, tinggi, end));
        g2.fillRect(0, 0, lebar, tinggi);

        g2.setColor(new Color(255, 255, 255, 18));
        int diameter = (int) (lebar * 0.95);
        g2.fillOval((int) (lebar * 0.45), (int) (-tinggi * 0.15), diameter, diameter);

        g2.setColor(new Color(255, 255, 255, 12));
        diameter = (int) (lebar * 0.7);
        g2.fillOval((int) (-lebar * 0.22), (int) (tinggi * 0.62), diameter, diameter);

        g2.setColor(new Color(255, 255, 255, 26));
        g2.setStroke(new BasicStroke(2f));
        diameter = (int) (lebar * 0.26);
        g2.drawOval((int) (lebar * 0.58), (int) (tinggi * 0.1), diameter, diameter);
        g2.drawOval((int) (lebar * 0.66), (int) (tinggi * 0.16), diameter, diameter);

        g2.dispose();
    }
}
