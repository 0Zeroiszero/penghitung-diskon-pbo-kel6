package com.pbo.model;

public class DiscountCalculator {
    public double hitungHargaAkhir(double harga, int persenDiskon) {
        double potongan = harga * persenDiskon / 100.0;
        return harga - potongan;
    }
}
