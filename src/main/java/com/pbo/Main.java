package com.pbo;

import com.pbo.controller.CalculatorController;
import com.pbo.model.DiscountCalculator;
import com.pbo.view.CalculatorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        CalculatorView view = new CalculatorView();
        new CalculatorController(view, new DiscountCalculator());

        Scene scene = new Scene(view.getRoot(), 880, 610);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Kalkulator Diskon");
        stage.setScene(scene);
        stage.setMinWidth(780);
        stage.setMinHeight(560);
        stage.show();
        view.fokusInput();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
