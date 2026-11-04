package com.pbo.view;

import com.pbo.view.component.DiscountCard;
import com.pbo.view.component.FadeInPanel;
import com.pbo.view.component.GradientPanel;
import com.pbo.view.component.Icons;
import com.pbo.view.component.RoundedButton;
import com.pbo.view.component.RoundedPanel;
import com.pbo.view.component.RoundedTextField;
import com.pbo.view.component.Toast;

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
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculatorFrame extends JFrame {
    private final RoundedTextField inputHarga = new RoundedTextField();
    private final JRadioButton diskon10 = new DiscountCard(10);
    private final JRadioButton diskon20 = new DiscountCard(20);
    private final JRadioButton diskon30 = new DiscountCard(30);
    private final JRadioButton diskon50 = new DiscountCard(50);
    private final RoundedButton tombolHitung = new RoundedButton(
            "Hitung Harga Akhir", Icons.panahKanan(18, Color.WHITE));
    private final JLabel labelHasil = new JLabel("Rp0");
    private final JLabel labelDiskonAktif = new JLabel("Diskon 10%");
    private final NumberFormat formatRupiah = buatFormatRupiah();

    private FadeInPanel akar;
    private JPanel sidebar;
    private Timer timerHasil;
    private double nilaiTampil;

    public CalculatorFrame() {
        setTitle("Kalkulator Diskon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Icons.appIcon(64));
        setBackground(Theme.BACKGROUND);
        setSize(1020, 660);
        setMinimumSize(new Dimension(760, 640));
        setLocationRelativeTo(null);

        akar = new FadeInPanel(new BorderLayout());
        akar.setBackground(Theme.BACKGROUND);
        sidebar = buatSidebar();
        akar.add(sidebar, BorderLayout.WEST);
        akar.add(buatKonten(), BorderLayout.CENTER);
        setContentPane(akar);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                aturResponsif();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                akar.fadeIn(360);
                inputHarga.requestFocusInWindow();
            }
        });

        aturResponsif();
    }

    private JPanel buatSidebar() {
        GradientPanel panel = new GradientPanel(new BorderLayout(), Theme.SIDEBAR_START, Theme.SIDEBAR_END);
        panel.setPreferredSize(new Dimension(340, 0));
        panel.setBorder(new EmptyBorder(40, 34, 34, 34));

        JPanel atas = new JPanel();
        atas.setOpaque(false);
        atas.setLayout(new BoxLayout(atas, BoxLayout.Y_AXIS));

        JPanel merek = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        merek.setOpaque(false);
        merek.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel lencana = new RoundedPanel(new BorderLayout(), 14, new Color(255, 255, 255, 42));
        lencana.setBorderColor(new Color(255, 255, 255, 70));
        lencana.setPreferredSize(new Dimension(48, 48));
        lencana.setMinimumSize(new Dimension(48, 48));
        lencana.setMaximumSize(new Dimension(48, 48));
        JLabel ikon = new JLabel(Icons.persen(24, Color.WHITE));
        ikon.setHorizontalAlignment(SwingConstants.CENTER);
        lencana.add(ikon, BorderLayout.CENTER);
        merek.add(lencana);

        JPanel teksMerek = new JPanel();
        teksMerek.setOpaque(false);
        teksMerek.setLayout(new BoxLayout(teksMerek, BoxLayout.Y_AXIS));
        JLabel nama = new JLabel("Diskon.in");
        nama.setFont(Theme.font(Font.BOLD, 20));
        nama.setForeground(Color.WHITE);
        JLabel sub = new JLabel("KALKULATOR HARGA");
        sub.setFont(Theme.font(Font.BOLD, 9));
        sub.setForeground(Theme.TEXT_ON_DARK_SOFT);
        teksMerek.add(nama);
        teksMerek.add(Box.createVerticalStrut(2));
        teksMerek.add(sub);
        merek.add(teksMerek);

        JLabel headline = new JLabel("<html><div style='width:238px'>Hitung harga akhir,<br>tanpa ribet.</div></html>");
        headline.setFont(Theme.font(Font.BOLD, 29));
        headline.setForeground(Color.WHITE);
        headline.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel(
                "<html><div style='width:236px'>Masukkan harga, pilih diskon, dan lihat hasilnya seketika.</div></html>");
        tagline.setFont(Theme.font(Font.PLAIN, 13));
        tagline.setForeground(Theme.TEXT_ON_DARK_SOFT);
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        atas.add(merek);
        atas.add(Box.createVerticalStrut(34));
        atas.add(headline);
        atas.add(Box.createVerticalStrut(12));
        atas.add(tagline);
        panel.add(atas, BorderLayout.NORTH);

        JPanel tengah = new JPanel();
        tengah.setOpaque(false);
        tengah.setLayout(new BoxLayout(tengah, BoxLayout.Y_AXIS));
        tengah.setBorder(new EmptyBorder(38, 0, 0, 0));

        JLabel judulDiskon = new JLabel("PILIHAN DISKON");
        judulDiskon.setFont(Theme.font(Font.BOLD, 10));
        judulDiskon.setForeground(Theme.TEXT_ON_DARK_SOFT);
        judulDiskon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel kisi = new JPanel(new GridLayout(2, 2, 10, 10));
        kisi.setOpaque(false);
        kisi.setAlignmentX(Component.LEFT_ALIGNMENT);
        kisi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 156));
        for (int persen : new int[]{10, 20, 30, 50}) {
            kisi.add(buatChip(persen));
        }

        tengah.add(judulDiskon);
        tengah.add(Box.createVerticalStrut(12));
        tengah.add(kisi);
        panel.add(tengah, BorderLayout.CENTER);

        RoundedPanel catatan = new RoundedPanel(new BorderLayout(10, 0), 16, new Color(255, 255, 255, 34));
        catatan.setBorderColor(new Color(255, 255, 255, 58));
        catatan.setPadding(14, 14);
        JLabel ikonCatatan = new JLabel(Icons.centang(18, Color.WHITE));
        JLabel teksCatatan = new JLabel("<html>Hasil dihitung otomatis<br>dan langsung ditampilkan.</html>");
        teksCatatan.setFont(Theme.font(Font.PLAIN, 12));
        teksCatatan.setForeground(Color.WHITE);
        catatan.add(ikonCatatan, BorderLayout.WEST);
        catatan.add(teksCatatan, BorderLayout.CENTER);
        panel.add(catatan, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buatChip(int persen) {
        RoundedPanel chip = new RoundedPanel(Theme.RADIUS_MD, new Color(255, 255, 255, 30));
        chip.setLayout(new BoxLayout(chip, BoxLayout.Y_AXIS));
        chip.setBorderColor(new Color(255, 255, 255, 55));
        chip.setPadding(10, 12);

        JLabel nilai = new JLabel(persen + "%");
        nilai.setFont(Theme.font(Font.BOLD, 20));
        nilai.setForeground(Color.WHITE);
        nilai.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ket = new JLabel("diskon");
        ket.setFont(Theme.font(Font.PLAIN, 10));
        ket.setForeground(Theme.TEXT_ON_DARK_SOFT);
        ket.setAlignmentX(Component.LEFT_ALIGNMENT);

        chip.add(nilai);
        chip.add(Box.createVerticalStrut(1));
        chip.add(ket);
        return chip;
    }

    private JPanel buatKonten() {
        JPanel konten = new JPanel(new BorderLayout());
        konten.setOpaque(false);
        konten.setBorder(new EmptyBorder(32, 32, 32, 32));
        konten.add(buatHeader(), BorderLayout.NORTH);
        konten.add(buatKartu(), BorderLayout.CENTER);
        return konten;
    }

    private JPanel buatHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(0, 0, 18, 0));

        JLabel eyebrow = new JLabel("KALKULATOR DISKON", Icons.persen(14, Theme.PRIMARY), SwingConstants.LEFT);
        eyebrow.setFont(Theme.font(Font.BOLD, 10));
        eyebrow.setForeground(Theme.PRIMARY_DARK);
        eyebrow.setIconTextGap(7);
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel judul = new JLabel("Berapa harga akhirnya?");
        judul.setFont(Theme.font(Font.BOLD, 28));
        judul.setForeground(Theme.TEXT);
        judul.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subjudul = new JLabel("Masukkan harga barang, pilih diskon, lalu lihat hasilnya.");
        subjudul.setFont(Theme.font(Font.PLAIN, 13));
        subjudul.setForeground(Theme.TEXT_MUTED);
        subjudul.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(eyebrow);
        header.add(Box.createVerticalStrut(8));
        header.add(judul);
        header.add(Box.createVerticalStrut(2));
        header.add(subjudul);
        return header;
    }

    private JPanel buatKartu() {
        RoundedPanel kartu = new RoundedPanel(Theme.RADIUS_XL, Color.WHITE, new Color(255, 253, 251), 10);
        kartu.setLayout(new BoxLayout(kartu, BoxLayout.Y_AXIS));
        kartu.setPadding(22, 28);

        JPanel barisHarga = buatLabelBagian("Harga Barang", "Angka lebih dari 0");
        JLabel labelHarga = (JLabel) barisHarga.getClientProperty("label");
        labelHarga.setLabelFor(inputHarga);

        inputHarga.setPlaceholder("Contoh: 100000");
        inputHarga.setPrefix("Rp");
        inputHarga.setToolTipText("Masukkan harga barang dalam Rupiah");
        inputHarga.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputHarga.setPreferredSize(new Dimension(200, 50));
        inputHarga.setMinimumSize(new Dimension(120, 46));
        inputHarga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JPanel barisDiskon = buatLabelBagian("Pilih Diskon", "Satu pilihan");

        JPanel pilihan = new JPanel(new GridLayout(1, 4, 10, 0));
        pilihan.setOpaque(false);
        pilihan.setAlignmentX(Component.LEFT_ALIGNMENT);
        pilihan.setPreferredSize(new Dimension(200, 84));
        pilihan.setMinimumSize(new Dimension(200, 76));
        pilihan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));
        ButtonGroup grup = new ButtonGroup();
        for (JRadioButton tombol : new JRadioButton[]{diskon10, diskon20, diskon30, diskon50}) {
            grup.add(tombol);
            pilihan.add(tombol);
        }
        diskon10.setSelected(true);

        tombolHitung.setAlignmentX(Component.LEFT_ALIGNMENT);
        tombolHitung.setPreferredSize(new Dimension(200, 50));
        tombolHitung.setMinimumSize(new Dimension(120, 46));
        tombolHitung.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        tombolHitung.setToolTipText("Hitung harga akhir setelah diskon");

        kartu.add(barisHarga);
        kartu.add(Box.createVerticalStrut(8));
        kartu.add(inputHarga);
        kartu.add(Box.createVerticalStrut(20));
        kartu.add(barisDiskon);
        kartu.add(Box.createVerticalStrut(8));
        kartu.add(pilihan);
        kartu.add(Box.createVerticalStrut(22));
        kartu.add(tombolHitung);
        kartu.add(Box.createVerticalGlue());
        kartu.add(Box.createVerticalStrut(14));
        kartu.add(buatPanelHasil());
        return kartu;
    }

    private JPanel buatLabelBagian(String teks, String petunjuk) {
        JPanel baris = new JPanel(new BorderLayout());
        baris.setOpaque(false);
        baris.setAlignmentX(Component.LEFT_ALIGNMENT);
        baris.setPreferredSize(new Dimension(200, 20));
        baris.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        JLabel kiri = new JLabel(teks);
        kiri.setFont(Theme.font(Font.BOLD, 12));
        kiri.setForeground(Theme.TEXT);

        JLabel kanan = new JLabel(petunjuk);
        kanan.setFont(Theme.font(Font.PLAIN, 11));
        kanan.setForeground(Theme.TEXT_MUTED);

        baris.add(kiri, BorderLayout.WEST);
        baris.add(kanan, BorderLayout.EAST);
        baris.putClientProperty("label", kiri);
        return baris;
    }

    private JPanel buatPanelHasil() {
        RoundedPanel panel = new RoundedPanel(
                new BorderLayout(16, 0), 18, Theme.SUCCESS_BG, new Color(240, 253, 250), 0);
        panel.setBorderColor(Theme.SUCCESS_BORDER);
        panel.setPadding(14, 18);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setPreferredSize(new Dimension(200, 108));
        panel.setMinimumSize(new Dimension(200, 108));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 108));

        JPanel kiri = new JPanel();
        kiri.setOpaque(false);
        kiri.setLayout(new BoxLayout(kiri, BoxLayout.Y_AXIS));

        JLabel judul = new JLabel("HARGA AKHIR");
        judul.setFont(Theme.font(Font.BOLD, 10));
        judul.setForeground(Theme.TEXT_MUTED);
        judul.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelHasil.setFont(Theme.font(Font.BOLD, 30));
        labelHasil.setForeground(Theme.SUCCESS_DARK);
        labelHasil.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hint = new JLabel("Sudah termasuk potongan diskon");
        hint.setFont(Theme.font(Font.PLAIN, 11));
        hint.setForeground(Theme.TEXT_MUTED);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        kiri.add(judul);
        kiri.add(Box.createVerticalStrut(1));
        kiri.add(labelHasil);
        kiri.add(hint);

        labelDiskonAktif.setIcon(Icons.persen(12, Theme.SUCCESS_DARK));
        labelDiskonAktif.setIconTextGap(6);
        labelDiskonAktif.setFont(Theme.font(Font.BOLD, 11));
        labelDiskonAktif.setForeground(Theme.SUCCESS_DARK);

        RoundedPanel lencana = new RoundedPanel(new BorderLayout(), Theme.RADIUS_PILL, new Color(255, 255, 255, 205));
        lencana.setBorderColor(Theme.SUCCESS_BORDER);
        lencana.setPadding(7, 12);
        lencana.add(labelDiskonAktif, BorderLayout.CENTER);

        JPanel kanan = new JPanel(new GridBagLayout());
        kanan.setOpaque(false);
        kanan.add(lencana);

        panel.add(kiri, BorderLayout.CENTER);
        panel.add(kanan, BorderLayout.EAST);
        return panel;
    }

    private void aturResponsif() {
        if (sidebar == null || akar == null) {
            return;
        }
        boolean tampil = getWidth() >= 900;
        if (sidebar.isVisible() != tampil) {
            sidebar.setVisible(tampil);
            akar.revalidate();
            akar.repaint();
        }
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
        labelDiskonAktif.setText("Diskon " + getDiskonTerpilih() + "%");
        animasiHasil(hargaAkhir);
        Toast.show(getRootPane(), "Harga akhir berhasil dihitung", true);
    }

    private void animasiHasil(double target) {
        if (timerHasil != null) {
            timerHasil.stop();
        }
        double awal = nilaiTampil;
        long mulai = System.currentTimeMillis();
        int durasi = 420;
        timerHasil = new Timer(16, event -> {
            double progres = Math.min(1.0, (System.currentTimeMillis() - mulai) / (double) durasi);
            double easing = 1.0 - Math.pow(1.0 - progres, 3);
            nilaiTampil = awal + (target - awal) * easing;
            labelHasil.setText(formatRupiah.format(nilaiTampil));
            if (progres >= 1.0) {
                nilaiTampil = target;
                labelHasil.setText(formatRupiah.format(target));
                ((Timer) event.getSource()).stop();
            }
        });
        timerHasil.start();
    }

    public void tampilkanError(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
        inputHarga.sorotError();
    }

    private static NumberFormat buatFormatRupiah() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format;
    }
}
