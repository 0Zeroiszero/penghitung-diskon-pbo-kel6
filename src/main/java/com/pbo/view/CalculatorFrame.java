package com.pbo.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculatorFrame extends JFrame {
    private static final Color WARNA_LATAR = new Color(241, 245, 249);
    private static final Color WARNA_TEKS = new Color(15, 23, 42);
    private static final Color WARNA_TEKS_ABU = new Color(100, 116, 139);
    private static final Color WARNA_UTAMA = new Color(37, 99, 235);
    private static final Color WARNA_HASIL = new Color(4, 120, 87);

    private final JTextField inputHarga = new JTextField();
    private final JRadioButton diskon10 = new JRadioButton("10%");
    private final JRadioButton diskon20 = new JRadioButton("20%");
    private final JRadioButton diskon30 = new JRadioButton("30%");
    private final JRadioButton diskon50 = new JRadioButton("50%");
    private final JButton tombolHitung = new JButton("Hitung Harga Akhir");
    private final JLabel labelHasil = new JLabel("Rp0");

    public CalculatorFrame() {
        setTitle("Kalkulator Diskon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 560);
        setMinimumSize(new Dimension(420, 520));
        setLocationRelativeTo(null);
        setContentPane(buatTampilan());
    }

    private JPanel buatTampilan() {
        JPanel akar = new JPanel(new BorderLayout());
        akar.setBackground(WARNA_LATAR);
        akar.setBorder(new EmptyBorder(28, 28, 28, 28));

        akar.add(buatJudul(), BorderLayout.NORTH);
        akar.add(buatKartu(), BorderLayout.CENTER);
        return akar;
    }

    private JPanel buatJudul() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel judul = new JLabel("Kalkulator Diskon");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 26));
        judul.setForeground(WARNA_TEKS);

        JLabel subjudul = new JLabel("Hitung harga akhir setelah diskon");
        subjudul.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subjudul.setForeground(WARNA_TEKS_ABU);

        panel.add(judul);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subjudul);
        return panel;
    }

    private JPanel buatKartu() {
        JPanel kartu = new JPanel();
        kartu.setLayout(new BoxLayout(kartu, BoxLayout.Y_AXIS));
        kartu.setBackground(Color.WHITE);
        kartu.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(24, 24, 24, 24)));

        JLabel labelHarga = new JLabel("Harga Barang (Rp)");
        labelHarga.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelHarga.setForeground(WARNA_TEKS);
        labelHarga.setAlignmentX(LEFT_ALIGNMENT);

        inputHarga.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputHarga.setAlignmentX(LEFT_ALIGNMENT);
        inputHarga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        inputHarga.setPreferredSize(new Dimension(200, 40));
        inputHarga.setBorder(new CompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(6, 10, 6, 10)));

        JLabel labelDiskon = new JLabel("Pilih Diskon");
        labelDiskon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelDiskon.setForeground(WARNA_TEKS);
        labelDiskon.setAlignmentX(LEFT_ALIGNMENT);

        tombolHitung.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tombolHitung.setAlignmentX(LEFT_ALIGNMENT);
        tombolHitung.setForeground(Color.WHITE);
        tombolHitung.setBackground(WARNA_UTAMA);
        tombolHitung.setFocusPainted(false);
        tombolHitung.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tombolHitung.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tombolHitung.setPreferredSize(new Dimension(200, 44));

        kartu.add(labelHarga);
        kartu.add(Box.createVerticalStrut(6));
        kartu.add(inputHarga);
        kartu.add(Box.createVerticalStrut(18));
        kartu.add(labelDiskon);
        kartu.add(Box.createVerticalStrut(6));
        kartu.add(buatPilihanDiskon());
        kartu.add(Box.createVerticalStrut(24));
        kartu.add(tombolHitung);
        kartu.add(Box.createVerticalStrut(24));
        kartu.add(buatPanelHasil());
        return kartu;
    }

    private JPanel buatPilihanDiskon() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(LEFT_ALIGNMENT);

        ButtonGroup grup = new ButtonGroup();
        for (JRadioButton tombol : new JRadioButton[]{diskon10, diskon20, diskon30, diskon50}) {
            tombol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tombol.setBackground(Color.WHITE);
            tombol.setFocusPainted(false);
            tombol.setCursor(new Cursor(Cursor.HAND_CURSOR));
            grup.add(tombol);
            panel.add(tombol);
        }
        diskon10.setSelected(true);
        return panel;
    }

    private JPanel buatPanelHasil() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(236, 253, 245));
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(167, 243, 208), 1, true),
                new EmptyBorder(14, 16, 14, 16)));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 92));

        JLabel judul = new JLabel("Harga Akhir");
        judul.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        judul.setForeground(WARNA_TEKS_ABU);

        labelHasil.setFont(new Font("Segoe UI", Font.BOLD, 30));
        labelHasil.setForeground(WARNA_HASIL);

        panel.add(judul);
        panel.add(Box.createVerticalStrut(2));
        panel.add(labelHasil);
        return panel;
    }

    public JTextField getInputHarga() {
        return inputHarga;
    }

    public JButton getTombolHitung() {
        return tombolHitung;
    }

    public int getDiskonTerpilih() {
        if (diskon20.isSelected()) {
            return 20;
        }
        if (diskon30.isSelected()) {
            return 30;
        }
        if (diskon50.isSelected()) {
            return 50;
        }
        return 10;
    }

    public void tampilkanHasil(double hargaAkhir) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
        rupiah.setMaximumFractionDigits(0);
        labelHasil.setText(rupiah.format(hargaAkhir));
    }

    public void tampilkanError(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
    }
}
