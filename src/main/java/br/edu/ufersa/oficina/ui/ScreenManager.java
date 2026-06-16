package br.edu.ufersa.oficina.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private static Stage stage;
    private static ScreenLoader screenLoader;

    public static void initialize(Stage primaryStage){
        stage = primaryStage;
        screenLoader = new ScreenLoader();
    }

    public static void show(String fxml) throws IOException{
        Parent root = screenLoader.load(fxml);

        Scene scene = new Scene(root);

        stage.setScene(scene);
    }
}
