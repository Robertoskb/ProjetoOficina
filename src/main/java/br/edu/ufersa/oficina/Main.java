package br.edu.ufersa.oficina;

import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/br/edu/ufersa/oficina/icon.jpg")));

        ScreenManager manager = new ScreenManager(stage);

        manager.setScene("login.fxml");

        manager.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}