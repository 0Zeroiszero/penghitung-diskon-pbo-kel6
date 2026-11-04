package com.pbo.controller;

import com.pbo.model.DiscountCalculator;
import com.pbo.view.CalculatorFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController implements ActionListener {
    private final CalculatorFrame view;
    private final DiscountCalculator calculator;

    public CalculatorController(CalculatorFrame view, DiscountCalculator calculator) {
        this.view = view;
        this.calculator = calculator;
        view.getTombolHitung().addActionListener(this);
        view.getInputHarga().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
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

        if (!(harga > 0)) {
            view.tampilkanError("Harga barang harus lebih dari 0.");
            return;
        }

        double hargaAkhir = calculator.hitungHargaAkhir(harga, view.getDiskonTerpilih());
        view.tampilkanHasil(hargaAkhir);
    }
}
