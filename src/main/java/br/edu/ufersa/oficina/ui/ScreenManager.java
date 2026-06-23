package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.model.Entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private Stage stage;
    private final ScreenLoader screenLoader;
    private User user;
    private BorderPane root;
    private static ScreenManager screenManager;

    private ScreenManager(){
        screenLoader = new ScreenLoader();
    }

    public static ScreenManager getInstance(){
        if (screenManager == null)
             screenManager = new  ScreenManager();
        return screenManager;
    }


    public void setScene(String fxml) throws IOException{
        Parent root = screenLoader.load(fxml);

        Scene scene = new Scene(root);

        stage.centerOnScreen();

        stage.setScene(scene);

    }

    public void show(){
        stage.show();
    }

    public FXMLLoader loader(String fxml){
        return screenLoader.loader(fxml);
    }

    public Parent load(String fxml) throws IOException {
        return screenLoader.load(fxml);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCenter(Parent view){
        root.setCenter(view);
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Stage getStage() {
        return stage;
    }

    public User getUser() {
        return user;
    }

    public ScreenLoader getScreenLoader() {
        return screenLoader;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
}
