package com.pbo.view.component;

import com.pbo.view.Theme;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

public class RoundedPanel extends JPanel {
    private final int radius;
    private final Color fillStart;
    private final Color fillEnd;
    private final int shadow;
    private Color borderColor;
    private int paddingTop;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingRight;

    public RoundedPanel(int radius, Color fill) {
        this(null, radius, fill, null, 0);
    }

    public RoundedPanel(int radius, Color fillStart, Color fillEnd, int shadow) {
        this(null, radius, fillStart, fillEnd, shadow);
    }

    public RoundedPanel(LayoutManager layout, int radius, Color fill) {
        this(layout, radius, fill, null, 0);
    }

    public RoundedPanel(LayoutManager layout, int radius, Color fillStart, Color fillEnd, int shadow) {
        super(layout);
        this.radius = radius;
        this.fillStart = fillStart;
        this.fillEnd = fillEnd;
        this.shadow = shadow;
        setOpaque(false);
        perbaruiBorder();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setPadding(int vertikal, int horizontal) {
        setPadding(vertikal, horizontal, vertikal, horizontal);
    }

    public void setPadding(int atas, int kiri, int bawah, int kanan) {
        paddingTop = atas;
        paddingLeft = kiri;
        paddingBottom = bawah;
        paddingRight = kanan;
        perbaruiBorder();
    }

    private void perbaruiBorder() {
        setBorder(new EmptyBorder(
                shadow + paddingTop,
                shadow + paddingLeft,
                shadow + paddingBottom,
                shadow + paddingRight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int lebar = getWidth();
        int tinggi = getHeight();
        int sudut = Math.min(radius, Math.min(lebar, tinggi) / 2);

        if (shadow > 0) {
            for (int i = shadow; i >= 1; i--) {
                int alpha = Math.max(2, 16 / i);
                g2.setColor(new Color(31, 41, 55, alpha));
                g2.fillRoundRect(
                        shadow - i,
                        shadow - i + 2,
                        lebar - 2 * (shadow - i),
                        tinggi - 2 * (shadow - i),
                        sudut + i,
                        sudut + i);
            }
        }

        int x = shadow;
        int y = shadow;
        int w = lebar - 2 * shadow;
        int h = tinggi - 2 * shadow;

        if (fillEnd == null) {
            g2.setColor(fillStart);
        } else {
            g2.setPaint(new GradientPaint(x, y, fillStart, x, y + h, fillEnd));
        }
        g2.fillRoundRect(x, y, w, h, sudut, sudut);

        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(x, y, w - 1, h - 1, sudut, sudut);
        }

        g2.dispose();
    }

    protected int getRadiusValue() {
        return radius;
    }

    protected Color getFillStart() {
        return fillStart;
    }

    protected Color getFillEnd() {
        return fillEnd;
    }

    protected Color getBorderColor() {
        return borderColor;
    }

    protected static Color softShadow(int alpha) {
        return Theme.alpha(Theme.TEXT, alpha);
    }
}
