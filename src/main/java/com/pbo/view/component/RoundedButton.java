package com.pbo.view.component;

import com.pbo.view.Theme;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private final Color warnaAwal;
    private final Color warnaAkhir;
    private final int radius;
    private float hover;
    private float targetHover;
    private Timer timerHover;

    public RoundedButton(String teks, Icon ikon) {
        super(teks);
        this.warnaAwal = Theme.PRIMARY;
        this.warnaAkhir = Theme.PRIMARY_DARK;
        this.radius = Theme.RADIUS_MD;

        setIcon(ikon);
        setIconTextGap(10);
        setFont(Theme.font(Font.BOLD, 15));
        setForeground(Color.WHITE);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(13, 22, 13, 22));

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
        getModel().addChangeListener(e -> repaint());
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
        int inset = 2;
        boolean ditekan = getModel().isPressed();

        for (int i = 4; i >= 1; i--) {
            g2.setColor(new Color(234, 88, 12, 9));
            g2.fillRoundRect(inset, inset + i, lebar - 2 * inset, tinggi - 2 * inset, radius, radius);
        }

        Color awal = Theme.mix(warnaAwal, Color.WHITE, hover * 0.16f);
        Color akhir = Theme.mix(warnaAkhir, Color.WHITE, hover * 0.12f);
        if (ditekan) {
            awal = Theme.mix(awal, Color.BLACK, 0.08f);
            akhir = Theme.mix(akhir, Color.BLACK, 0.08f);
        }

        g2.setPaint(new GradientPaint(0, 0, awal, 0, tinggi, akhir));
        g2.fillRoundRect(inset, inset, lebar - 2 * inset, tinggi - 2 * inset, radius, radius);

        if (isFocusOwner()) {
            g2.setColor(Theme.alpha(Theme.PRIMARY_DARK, 120));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(inset + 2, inset + 2, lebar - 2 * inset - 5, tinggi - 2 * inset - 5, radius - 2, radius - 2);
        }
        g2.dispose();

        Graphics2D gt = (Graphics2D) g.create();
        if (ditekan) {
            gt.translate(0, 1);
        }
        super.paintComponent(gt);
        gt.dispose();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setCursor(enabled
                ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                : Cursor.getDefaultCursor());
    }
}
