package com.pbo.view.component;

import com.pbo.view.Theme;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class Icons {
    private Icons() {
    }

    public static Icon persen(int ukuran, Color warna) {
        return new BaseIcon(ukuran, warna) {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = siap(g, x, y);
                int r = Math.max(2, Math.round(ukuran * 0.17f));
                g2.drawOval(Math.round(ukuran * 0.08f), Math.round(ukuran * 0.08f), r * 2, r * 2);
                g2.drawOval(Math.round(ukuran * 0.58f), Math.round(ukuran * 0.58f), r * 2, r * 2);
                g2.drawLine(
                        Math.round(ukuran * 0.24f), Math.round(ukuran * 0.78f),
                        Math.round(ukuran * 0.76f), Math.round(ukuran * 0.22f));
                g2.dispose();
            }
        };
    }

    public static Icon centang(int ukuran, Color warna) {
        return new BaseIcon(ukuran, warna) {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = siap(g, x, y);
                g2.drawLine(
                        Math.round(ukuran * 0.18f), Math.round(ukuran * 0.55f),
                        Math.round(ukuran * 0.41f), Math.round(ukuran * 0.76f));
                g2.drawLine(
                        Math.round(ukuran * 0.41f), Math.round(ukuran * 0.76f),
                        Math.round(ukuran * 0.83f), Math.round(ukuran * 0.25f));
                g2.dispose();
            }
        };
    }

    public static Icon panahKanan(int ukuran, Color warna) {
        return new BaseIcon(ukuran, warna) {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = siap(g, x, y);
                g2.drawLine(
                        Math.round(ukuran * 0.16f), Math.round(ukuran * 0.5f),
                        Math.round(ukuran * 0.82f), Math.round(ukuran * 0.5f));
                g2.drawLine(
                        Math.round(ukuran * 0.56f), Math.round(ukuran * 0.24f),
                        Math.round(ukuran * 0.84f), Math.round(ukuran * 0.5f));
                g2.drawLine(
                        Math.round(ukuran * 0.84f), Math.round(ukuran * 0.5f),
                        Math.round(ukuran * 0.56f), Math.round(ukuran * 0.76f));
                g2.dispose();
            }
        };
    }

    public static Icon percikan(int ukuran, Color warna) {
        return new BaseIcon(ukuran, warna) {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = siap(g, x, y);
                int tengah = ukuran / 2;
                g2.drawLine(tengah, Math.round(ukuran * 0.08f), tengah, Math.round(ukuran * 0.34f));
                g2.drawLine(tengah, Math.round(ukuran * 0.66f), tengah, Math.round(ukuran * 0.92f));
                g2.drawLine(Math.round(ukuran * 0.08f), tengah, Math.round(ukuran * 0.34f), tengah);
                g2.drawLine(Math.round(ukuran * 0.66f), tengah, Math.round(ukuran * 0.92f), tengah);
                g2.dispose();
            }
        };
    }

    public static Image appIcon(int ukuran) {
        BufferedImage gambar = new BufferedImage(ukuran, ukuran, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = gambar.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, Theme.PRIMARY, ukuran, ukuran, Theme.PRIMARY_DARK));
        int sudut = Math.round(ukuran * 0.3f);
        g2.fillRoundRect(0, 0, ukuran, ukuran, sudut, sudut);
        Icon ikon = persen(Math.round(ukuran * 0.58f), Color.WHITE);
        ikon.paintIcon(null, g2, (ukuran - ikon.getIconWidth()) / 2, (ukuran - ikon.getIconHeight()) / 2);
        g2.dispose();
        return gambar;
    }

    private abstract static class BaseIcon implements Icon {
        protected final int ukuran;
        protected final Color warna;

        BaseIcon(int ukuran, Color warna) {
            this.ukuran = ukuran;
            this.warna = warna;
        }

        protected Graphics2D siap(Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(warna);
            g2.setStroke(new BasicStroke(
                    Math.max(1.4f, ukuran * 0.11f),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));
            return g2;
        }

        @Override
        public int getIconWidth() {
            return ukuran;
        }

        @Override
        public int getIconHeight() {
            return ukuran;
        }
    }
}
