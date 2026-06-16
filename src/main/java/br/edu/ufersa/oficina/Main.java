package br.edu.ufersa.oficina;

import br.edu.ufersa.oficina.ui.ControllerFactory;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ScreenManager.initialize(stage);

        ScreenManager.show("/br/edu/ufersa/oficina/view/login.fxml");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}