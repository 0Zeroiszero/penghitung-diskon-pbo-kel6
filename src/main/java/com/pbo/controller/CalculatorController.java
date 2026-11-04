package com.pbo.controller;

import com.pbo.model.DiscountCalculator;
import com.pbo.view.CalculatorView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CalculatorController {
    private final CalculatorView view;
    private final DiscountCalculator calculator;

    public CalculatorController(CalculatorView view, DiscountCalculator calculator) {
        this.view = view;
        this.calculator = calculator;

        EventHandler<ActionEvent> aksiHitung = event -> hitung();
        view.getTombolHitung().setOnAction(aksiHitung);
        view.getInputHarga().setOnAction(aksiHitung);
        view.getInputHarga().textProperty().addListener((observable, lama, baru) -> view.bersihkanError());
    }

    private void hitung() {
        String teks = view.getInputHarga().getText().trim();

        if (teks.isEmpty()) {
            view.tampilkanError("Harga barang belum diisi.");
            return;
        }

        double harga;
        try {
            harga = Double.parseDouble(teks);
        } catch (NumberFormatException ex) {
            view.tampilkanError("Harga barang harus berupa angka.");
            return;
        }

        if (!Double.isFinite(harga) || harga <= 0) {
            view.tampilkanError("Harga barang harus lebih dari 0.");
            return;
        }

        int diskon = view.getDiskonTerpilih();
        double hargaAkhir = calculator.hitungHargaAkhir(harga, diskon);
        view.bersihkanError();
        view.tampilkanHasil(harga, diskon, hargaAkhir);
    }
}
