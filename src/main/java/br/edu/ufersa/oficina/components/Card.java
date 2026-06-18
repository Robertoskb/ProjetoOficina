package br.edu.ufersa.oficina.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Card extends AnchorPane {
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;

    @FXML private Button btnDelete;
    @FXML private Button btnEdit;

    @FXML private VBox vbContainer;
    @FXML private HBox hbContainer;

    public Card(String fxml) throws IOException {
        String basePath = "/br/edu/ufersa/oficina/view/components/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(basePath + fxml));

        loader.setRoot(this);
        loader.setController(this);

        loader.load();
    }

    public void removeButton(Button btn){
        hbContainer.getChildren().remove(btn);
    }

    public void setTitle(String title){
        lblTitle.setText(title);
    }

    public void setDescription(String description){
        lblDescription.setText(description);
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }
}
