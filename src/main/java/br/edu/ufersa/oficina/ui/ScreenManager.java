package br.edu.ufersa.oficina.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private final Stage stage;
    private final ScreenLoader screenLoader;

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
        screenLoader = new ScreenLoader(this);
    }

    public void setScene(String fxml) throws IOException{
        Parent root = screenLoader.load(fxml);

        Scene scene = new Scene(root);

        stage.setScene(scene);
    }


    public void show(){
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public ScreenLoader getScreenLoader() {
        return screenLoader;
    }
}
