package com.pbo.view.component;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

public class FadeInPanel extends JPanel {
    private float alpha;
    private float offset = 12f;
    private Timer timer;

    public FadeInPanel(LayoutManager layout) {
        super(layout);
        setOpaque(true);
    }

    public void fadeIn(int durasiMs) {
        if (timer != null) {
            timer.stop();
        }
        alpha = 0f;
        offset = 12f;
        long mulai = System.currentTimeMillis();
        timer = new Timer(16, event -> {
            float progres = Math.min(1f, (System.currentTimeMillis() - mulai) / (float) durasiMs);
            float easing = 1f - (float) Math.pow(1 - progres, 3);
            alpha = easing;
            offset = 12f * (1 - easing);
            repaint();
            if (progres >= 1f) {
                ((Timer) event.getSource()).stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.SrcOver.derive(Math.max(0f, Math.min(1f, alpha))));
        g2.translate(0, Math.round(offset));
        super.paintChildren(g2);
        g2.dispose();
    }
}
