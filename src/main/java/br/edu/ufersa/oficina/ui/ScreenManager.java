package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.model.Entity.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private final Stage stage;
    private final ScreenLoader screenLoader;
    private User user;

    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
        screenLoader = new ScreenLoader(this);
    }

    public void setScene(String fxml) throws IOException{
        Parent root = screenLoader.load(fxml);

        Scene scene = new Scene(root);

        stage.setScene(scene);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void show(){
        stage.show();
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
}
