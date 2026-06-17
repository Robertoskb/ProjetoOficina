package br.edu.ufersa.oficina.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Card extends AnchorPane {
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;

    public Card(String fxml) throws IOException {
        String basePath = "/br/edu/ufersa/oficina/view/components/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(basePath + fxml));

        loader.setRoot(this);
        loader.setController(this);

        loader.load();
    }

    public void setTitle(String title){
        lblTitle.setText(title);
    }

    public void setDescription(String description){
        lblDescription.setText(description);
    }
}
