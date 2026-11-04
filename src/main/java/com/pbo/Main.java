package com.pbo;

import com.pbo.controller.CalculatorController;
import com.pbo.model.DiscountCalculator;
import com.pbo.view.CalculatorFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            new CalculatorController(frame, new DiscountCalculator());
            frame.setVisible(true);
        });
    }
}
