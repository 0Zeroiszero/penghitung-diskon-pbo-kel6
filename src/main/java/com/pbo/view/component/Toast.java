package com.pbo.view.component;

import com.pbo.view.Theme;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public final class Toast {
    private static final String KUNCI = "toast.aktif";
    private static final int DURASI_MASUK = 220;
    private static final int DURASI_TAHAN = 1500;
    private static final int DURASI_KELUAR = 260;

    private Toast() {
    }

    public static void show(JRootPane root, String pesan, boolean sukses) {
        JLayeredPane lapisan = root.getLayeredPane();
        Object lama = lapisan.getClientProperty(KUNCI);
        if (lama instanceof ToastPanel) {
            ((ToastPanel) lama).hentikan();
        }
        if (lama != null) {
            lapisan.remove((java.awt.Component) lama);
        }

        ToastPanel toast = new ToastPanel(pesan, sukses);
        lapisan.putClientProperty(KUNCI, toast);

        Dimension ukuran = toast.getPreferredSize();
        int x = Math.max(16, (lapisan.getWidth() - ukuran.width) / 2);
        int yAkhir = Math.max(16, lapisan.getHeight() - ukuran.height - 26);
        int yAwal = yAkhir + 14;
        toast.setBounds(x, yAwal, ukuran.width, ukuran.height);
        lapisan.add(toast, JLayeredPane.POPUP_LAYER);
        toast.mulai(yAwal, yAkhir);
        lapisan.repaint();
    }

    private static final class ToastPanel extends JComponent {
        private final String pesan;
        private final boolean sukses;
        private Timer timer;
        private float alpha;
        private int yAwal;
        private int yAkhir;

        ToastPanel(String pesan, boolean sukses) {
            this.pesan = pesan;
            this.sukses = sukses;
            setFont(Theme.font(Font.BOLD, 13));
            FontMetrics fm = getFontMetrics(getFont());
            int lebar = fm.stringWidth(pesan) + 14 + 22 + 10 + 16 + 16;
            setPreferredSize(new Dimension(Math.max(250, lebar), 46));
        }

        void mulai(int yAwal, int yAkhir) {
            this.yAwal = yAwal;
            this.yAkhir = yAkhir;
            long mulai = System.currentTimeMillis();
            alpha = 0f;
            timer = new Timer(16, event -> {
                long t = System.currentTimeMillis() - mulai;
                float a;
                int y;
                if (t < DURASI_MASUK) {
                    float progres = t / (float) DURASI_MASUK;
                    float easing = 1f - (float) Math.pow(1 - progres, 3);
                    a = easing;
                    y = Math.round(yAwal - (yAwal - yAkhir) * easing);
                } else if (t < DURASI_MASUK + DURASI_TAHAN) {
                    a = 1f;
                    y = yAkhir;
                } else if (t < DURASI_MASUK + DURASI_TAHAN + DURASI_KELUAR) {
                    float progres = (t - DURASI_MASUK - DURASI_TAHAN) / (float) DURASI_KELUAR;
                    a = 1f - progres;
                    y = yAkhir;
                } else {
                    hentikan();
                    Container induk = getParent();
                    if (induk != null) {
                        induk.remove(this);
                        induk.repaint();
                    }
                    return;
                }
                alpha = Math.max(0f, Math.min(1f, a));
                setLocation(getX(), y);
                repaint();
            });
            timer.start();
        }

        void hentikan() {
            if (timer != null) {
                timer.stop();
                timer = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setComposite(AlphaComposite.SrcOver.derive(alpha));

            int lebar = getWidth();
            int tinggi = getHeight();
            int sudut = tinggi;

            g2.setColor(new Color(15, 23, 42, 45));
            g2.fillRoundRect(1, 4, lebar - 2, tinggi - 2, sudut, sudut);

            g2.setColor(Theme.TOAST_BG);
            g2.fillRoundRect(1, 1, lebar - 2, tinggi - 3, sudut, sudut);

            Color warnaIkon = sukses ? Theme.SUCCESS : Theme.ERROR;
            int diameter = 22;
            int xIkon = 14;
            int yIkon = (tinggi - diameter) / 2;
            g2.setColor(warnaIkon);
            g2.fillOval(xIkon, yIkon, diameter, diameter);
            Icons.centang(12, Color.WHITE).paintIcon(this, g2, xIkon + 5, yIkon + 5);

            g2.setFont(Theme.font(Font.BOLD, 13));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int xTeks = xIkon + diameter + 11;
            int yTeks = (tinggi + fm.getAscent() - fm.getDescent()) / 2 - 1;
            g2.drawString(pesan, xTeks, yTeks);

            g2.dispose();
        }
    }
}
