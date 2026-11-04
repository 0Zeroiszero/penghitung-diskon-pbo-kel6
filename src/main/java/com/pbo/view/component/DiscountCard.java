package com.pbo.view.component;

import com.pbo.view.Theme;

import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiscountCard extends JRadioButton {
    private final int persen;
    private final int radius = Theme.RADIUS_MD;
    private float hover;
    private float targetHover;
    private Timer timerHover;

    public DiscountCard(int persen) {
        super(persen + "%");
        this.persen = persen;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Theme.font(Font.BOLD, 20));
        setBorder(new EmptyBorder(10, 8, 10, 8));
        setToolTipText("Pilih diskon " + persen + "%");
        getAccessibleContext().setAccessibleName("Diskon " + persen + " persen");

        addItemListener(event -> repaint());
        getModel().addChangeListener(event -> repaint());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animasiHover(1f);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                animasiHover(0f);
            }
        });
    }

    private void animasiHover(float target) {
        if (Math.abs(targetHover - target) < 0.01f) {
            return;
        }
        targetHover = target;
        if (timerHover != null) {
            timerHover.stop();
        }
        timerHover = new Timer(16, event -> {
            float selisih = targetHover - hover;
            if (Math.abs(selisih) < 0.03f) {
                hover = targetHover;
                repaint();
                ((Timer) event.getSource()).stop();
                return;
            }
            hover += selisih * 0.35f;
            repaint();
        });
        timerHover.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int lebar = getWidth();
        int tinggi = getHeight();
        int sudut = Math.min(radius, Math.min(lebar, tinggi) / 2);
        boolean terpilih = isSelected();
        boolean tekan = getModel().isPressed();

        Color isiAwal;
        Color isiAkhir;
        if (terpilih) {
            isiAwal = Theme.mix(Theme.PRIMARY, Color.WHITE, hover * 0.08f);
            isiAkhir = Theme.mix(Theme.PRIMARY_DARK, Color.WHITE, hover * 0.06f);
        } else {
            isiAwal = Theme.mix(Theme.SURFACE, Theme.PRIMARY_TINT, hover * 0.55f);
            isiAkhir = Theme.mix(Theme.INPUT_BG, Theme.PRIMARY_TINT, hover * 0.35f);
        }
        if (tekan) {
            isiAwal = Theme.mix(isiAwal, Color.BLACK, 0.05f);
            isiAkhir = Theme.mix(isiAkhir, Color.BLACK, 0.05f);
        }

        g2.setPaint(new GradientPaint(0, 0, isiAwal, 0, tinggi, isiAkhir));
        g2.fillRoundRect(1, 1, lebar - 2, tinggi - 2, sudut, sudut);

        g2.setColor(terpilih
                ? Theme.alpha(Theme.PRIMARY_DARK, 70)
                : Theme.mix(Theme.BORDER, Theme.PRIMARY_LIGHT, hover));
        g2.setStroke(new BasicStroke(1.4f));
        g2.drawRoundRect(1, 1, lebar - 3, tinggi - 3, sudut, sudut);

        if (isFocusOwner()) {
            g2.setColor(terpilih ? Theme.alpha(Color.WHITE, 160) : Theme.alpha(Theme.PRIMARY_DARK, 140));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(4, 4, lebar - 9, tinggi - 9, sudut - 2, sudut - 2);
        }

        Color warnaUtama = terpilih ? Color.WHITE : Theme.TEXT;
        Color warnaKecil = terpilih ? Theme.alpha(Color.WHITE, 205) : Theme.TEXT_MUTED;

        g2.setFont(Theme.font(Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        String angka = persen + "%";
        int xAngka = (lebar - fm.stringWidth(angka)) / 2;
        int yAngka = (int) (tinggi * 0.5);
        g2.setColor(warnaUtama);
        g2.drawString(angka, xAngka, yAngka);

        g2.setFont(Theme.font(Font.BOLD, 9));
        FontMetrics fk = g2.getFontMetrics();
        String label = "DISKON";
        int xLabel = (lebar - fk.stringWidth(label)) / 2;
        int yLabel = yAngka + fk.getHeight() + 3;
        g2.setColor(warnaKecil);
        g2.drawString(label, xLabel, yLabel);

        if (terpilih) {
            int diameter = 18;
            int x = lebar - diameter - 9;
            int y = 9;
            g2.setColor(Theme.alpha(Color.WHITE, 55));
            g2.fillOval(x, y, diameter, diameter);
            Icons.centang(10, Color.WHITE).paintIcon(this, g2, x + 4, y + 4);
        }

        g2.dispose();
    }
}
