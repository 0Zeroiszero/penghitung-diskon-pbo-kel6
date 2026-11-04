package com.pbo.view;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class CalculatorView {
    private static final PseudoClass ERROR = PseudoClass.getPseudoClass("error");

    private final BorderPane root = new BorderPane();
    private final TextField inputHarga = new TextField();
    private final ToggleGroup grupDiskon = new ToggleGroup();
    private final Button tombolHitung = new Button("Hitung Harga Akhir");
    private final Label labelError = new Label();
    private final Label labelHasil = new Label("Rp0");
    private final Label labelDetail = new Label("Masukkan harga untuk mulai menghitung.");
    private final Label labelHemat = new Label();
    private final Label labelRincianDiskonJudul = new Label("Diskon");
    private final Label labelRincianHarga = new Label("—");
    private final Label labelRincianDiskon = new Label("—");

    public CalculatorView() {
        root.getStyleClass().add("app-root");
        root.setTop(buatHeader());
        root.setCenter(buatKonten());
    }

    private VBox buatHeader() {
        Label logo = new Label("Rp");
        logo.getStyleClass().add("logo");

        Label judul = new Label("Kalkulator Diskon");
        judul.getStyleClass().add("title");

        Label subjudul = new Label("Hitung harga akhir setelah diskon 10%, 20%, 30%, atau 50%.");
        subjudul.getStyleClass().add("subtitle");

        VBox teks = new VBox(4, judul, subjudul);
        HBox baris = new HBox(14, logo, teks);
        baris.setAlignment(Pos.CENTER_LEFT);

        VBox header = new VBox(baris);
        header.getStyleClass().add("header");
        return header;
    }

    private HBox buatKonten() {
        VBox kartuForm = buatKartuForm();
        VBox kartuHasil = buatKartuHasil();

        HBox.setHgrow(kartuForm, Priority.ALWAYS);
        kartuForm.setMaxWidth(Double.MAX_VALUE);
        kartuForm.setMaxHeight(Double.MAX_VALUE);

        HBox konten = new HBox(24, kartuForm, kartuHasil);
        konten.getStyleClass().add("content");
        konten.setAlignment(Pos.TOP_LEFT);
        return konten;
    }

    private VBox buatKartuForm() {
        Label labelHarga = new Label("Harga Barang (Rp)");
        labelHarga.getStyleClass().add("section-label");
        labelHarga.setLabelFor(inputHarga);

        inputHarga.setPromptText("Contoh: 150000");
        inputHarga.getStyleClass().add("input-harga");
        UnaryOperator<TextFormatter.Change> filter = perubahan ->
                perubahan.getControlNewText().matches("\\d{0,12}") ? perubahan : null;
        inputHarga.setTextFormatter(new TextFormatter<>(filter));

        labelError.getStyleClass().add("error-label");
        labelError.setMinHeight(18);
        labelError.setWrapText(true);

        Label labelDiskon = new Label("Pilih Diskon");
        labelDiskon.getStyleClass().add("section-label");

        HBox pilihanDiskon = new HBox(8);
        pilihanDiskon.setAlignment(Pos.CENTER_LEFT);
        for (int persen : new int[]{10, 20, 30, 50}) {
            ToggleButton tombol = new ToggleButton(persen + "%");
            tombol.setUserData(persen);
            tombol.setToggleGroup(grupDiskon);
            tombol.getStyleClass().add("chip");
            tombol.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(tombol, Priority.ALWAYS);
            pilihanDiskon.getChildren().add(tombol);
        }
        grupDiskon.selectToggle((Toggle) pilihanDiskon.getChildren().get(0));
        grupDiskon.selectedToggleProperty().addListener((observable, lama, baru) -> {
            if (baru == null && lama != null) {
                grupDiskon.selectToggle(lama);
            }
        });

        tombolHitung.getStyleClass().add("button-primary");
        tombolHitung.setMaxWidth(Double.MAX_VALUE);
        tombolHitung.setDefaultButton(true);

        VBox grupHarga = new VBox(6, labelHarga, inputHarga, labelError);
        VBox grupDiskonBox = new VBox(8, labelDiskon, pilihanDiskon);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox kartu = new VBox(20, grupHarga, grupDiskonBox, spacer, tombolHitung);
        kartu.getStyleClass().add("card");
        kartu.setMinWidth(380);
        return kartu;
    }

    private VBox buatKartuHasil() {
        Label labelAtas = new Label("HASIL PERHITUNGAN");
        labelAtas.getStyleClass().add("result-label");

        Label labelHargaAkhir = new Label("Harga Akhir");
        labelHargaAkhir.getStyleClass().add("result-caption");

        labelHasil.getStyleClass().add("result-amount");
        labelHasil.setMaxWidth(Double.MAX_VALUE);

        labelDetail.getStyleClass().add("result-detail");
        labelDetail.setWrapText(true);

        labelHemat.getStyleClass().add("result-hemat");
        labelHemat.setWrapText(true);
        labelHemat.setVisible(false);
        labelHemat.setManaged(false);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label judulRincian = new Label("RINCIAN");
        judulRincian.getStyleClass().add("result-breakdown-title");

        Label judulHargaAwal = new Label("Harga awal");

        VBox rincian = new VBox(10, judulRincian,
                barisRincian(judulHargaAwal, labelRincianHarga),
                barisRincian(labelRincianDiskonJudul, labelRincianDiskon));
        rincian.getStyleClass().add("result-breakdown");

        Region garis = new Region();
        garis.getStyleClass().add("result-divider");
        garis.setMinHeight(1);
        garis.setMaxHeight(1);

        Label footer = new Label("Diskon tersedia: 10% · 20% · 30% · 50%");
        footer.getStyleClass().add("result-footer");
        footer.setWrapText(true);

        VBox kartu = new VBox(8, labelAtas, labelHargaAkhir, labelHasil, labelDetail, labelHemat,
                spacer, rincian, garis, footer);
        kartu.getStyleClass().add("result-card");
        kartu.setMinWidth(300);
        kartu.setPrefWidth(320);
        kartu.setMaxHeight(Double.MAX_VALUE);
        return kartu;
    }

    private HBox barisRincian(Label judul, Label nilai) {
        judul.getStyleClass().add("result-row-label");
        nilai.getStyleClass().add("result-row-value");

        Region pengisi = new Region();
        HBox.setHgrow(pengisi, Priority.ALWAYS);

        HBox baris = new HBox(8, judul, pengisi, nilai);
        baris.setAlignment(Pos.CENTER_LEFT);
        return baris;
    }

    public BorderPane getRoot() {
        return root;
    }

    public TextField getInputHarga() {
        return inputHarga;
    }

    public Button getTombolHitung() {
        return tombolHitung;
    }

    public int getDiskonTerpilih() {
        Toggle terpilih = grupDiskon.getSelectedToggle();
        return terpilih == null ? 10 : (int) terpilih.getUserData();
    }

    public void tampilkanHasil(double hargaAwal, int diskon, double hargaAkhir) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
        rupiah.setMaximumFractionDigits(0);

        labelHasil.setText(rupiah.format(hargaAkhir));
        labelHemat.setText("Anda hemat " + rupiah.format(hargaAwal - hargaAkhir));
        labelRincianHarga.setText(rupiah.format(hargaAwal));
        labelRincianDiskonJudul.setText("Diskon " + diskon + "%");
        labelRincianDiskon.setText("−" + rupiah.format(hargaAwal - hargaAkhir));

        labelDetail.setVisible(false);
        labelDetail.setManaged(false);
        labelHemat.setVisible(true);
        labelHemat.setManaged(true);
    }

    public void tampilkanError(String pesan) {
        labelError.setText(pesan);
        inputHarga.pseudoClassStateChanged(ERROR, true);
        inputHarga.requestFocus();
    }

    public void bersihkanError() {
        labelError.setText("");
        inputHarga.pseudoClassStateChanged(ERROR, false);
    }

    public void fokusInput() {
        Platform.runLater(inputHarga::requestFocus);
    }
}
