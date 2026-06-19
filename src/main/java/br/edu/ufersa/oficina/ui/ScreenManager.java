package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.model.Entity.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private final Stage stage;
    private final ScreenLoader screenLoader;
    private User user;
    private BorderPane root;
    
    public ScreenManager(Stage primaryStage){
        stage = primaryStage;
        screenLoader = new ScreenLoader(this);
    }

    public void setScene(String fxml) throws IOException{
        Parent root = screenLoader.load(fxml);

        Scene scene = new Scene(root);

        stage.setScene(scene);
    }

    public void setCenter(Parent view){
        root.setCenter(view);
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

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
}
