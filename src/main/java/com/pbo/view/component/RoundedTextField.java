package com.pbo.view.component;

import com.pbo.view.Theme;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RoundedTextField extends JTextField {
    private final int radius = Theme.RADIUS_MD;
    private String placeholder = "";
    private String prefix;
    private float fokus;
    private float fokusTarget;
    private Timer timerFokus;
    private boolean error;
    private Timer timerError;
    private Timer timerGetar;
    private int guncangan;

    public RoundedTextField() {
        setOpaque(false);
        setFont(Theme.font(Font.PLAIN, 16));
        setForeground(Theme.TEXT);
        setCaretColor(Theme.PRIMARY_DARK);
        setSelectionColor(Theme.PRIMARY_TINT);
        setSelectedTextColor(Theme.TEXT);
        setBorder(new EmptyBorder(13, 16, 13, 16));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                animasiFokus(1f);
            }

            @Override
            public void focusLost(FocusEvent e) {
                animasiFokus(0f);
            }
        });
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? "" : placeholder;
        repaint();
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        perbaruiBorder();
        repaint();
    }

    public void sorotError() {
        error = true;
        repaint();
        if (timerError != null) {
            timerError.stop();
        }
        timerError = new Timer(1300, event -> {
            error = false;
            repaint();
            ((Timer) event.getSource()).stop();
        });
        timerError.setRepeats(false);
        timerError.start();

        int[] pola = {-5, 5, -4, 4, -3, 3, -2, 2, 0};
        if (timerGetar != null) {
            timerGetar.stop();
        }
        int[] indeks = {0};
        timerGetar = new Timer(28, event -> {
            guncangan = pola[indeks[0]++];
            repaint();
            if (indeks[0] >= pola.length) {
                guncangan = 0;
                ((Timer) event.getSource()).stop();
            }
        });
        timerGetar.start();

        requestFocusInWindow();
        selectAll();
    }

    private void perbaruiBorder() {
        int kiri = prefix == null || prefix.isEmpty() ? 16 : 48;
        setBorder(new EmptyBorder(13, kiri, 13, 16));
    }

    private void animasiFokus(float target) {
        fokusTarget = target;
        if (timerFokus != null) {
            timerFokus.stop();
        }
        timerFokus = new Timer(16, event -> {
            float selisih = fokusTarget - fokus;
            if (Math.abs(selisih) < 0.03f) {
                fokus = fokusTarget;
                repaint();
                ((Timer) event.getSource()).stop();
                return;
            }
            fokus += selisih * 0.35f;
            repaint();
        });
        timerFokus.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int lebar = getWidth();
        int tinggi = getHeight();

        Graphics2D latar = (Graphics2D) g.create();
        latar.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        latar.translate(guncangan, 0);

        latar.setColor(error ? Theme.mix(Color.WHITE, Theme.ERROR, 0.04f) : Theme.INPUT_BG);
        latar.fillRoundRect(1, 1, lebar - 2, tinggi - 2, radius, radius);

        Color garis = error
                ? Theme.ERROR
                : Theme.mix(Theme.BORDER, Theme.PRIMARY_DARK, fokus);
        latar.setColor(garis);
        float ketebalan = error || fokus > 0.05f ? 2f : 1.4f;
        latar.setStroke(new BasicStroke(ketebalan, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        latar.drawRoundRect(1, 1, lebar - 3, tinggi - 3, radius, radius);
        latar.dispose();

        if (prefix != null && !prefix.isEmpty()) {
            Graphics2D gp = (Graphics2D) g.create();
            gp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gp.translate(guncangan, 0);
            gp.setFont(Theme.font(Font.BOLD, 13));
            gp.setColor(error ? Theme.ERROR : Theme.TEXT_MUTED);
            FontMetrics fm = gp.getFontMetrics();
            int y = (tinggi - fm.getHeight()) / 2 + fm.getAscent();
            gp.drawString(prefix, 17, y);
            gp.dispose();
        }

        Graphics2D teks = (Graphics2D) g.create();
        teks.translate(guncangan, 0);
        super.paintComponent(teks);
        teks.dispose();

        if (getText().isEmpty() && !placeholder.isEmpty()) {
            Graphics2D gp = (Graphics2D) g.create();
            gp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gp.translate(guncangan, 0);
            gp.setFont(Theme.font(Font.PLAIN, 15));
            gp.setColor(Theme.mix(Theme.TEXT_MUTED, Color.WHITE, 0.25f));
            Insets insets = getInsets();
            FontMetrics fm = gp.getFontMetrics();
            int y = (tinggi - fm.getHeight()) / 2 + fm.getAscent();
            gp.drawString(placeholder, insets.left, y);
            gp.dispose();
        }
    }
}
