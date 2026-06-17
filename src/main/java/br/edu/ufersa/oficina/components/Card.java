package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.model.Services.GenericService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Optional;

public class Card extends AnchorPane {
    protected GenericService<?> service;
    protected int entityId;

    @FXML private Text lblTitle;
    @FXML private Text lblDescription;

    public void setService(GenericService<?> service) {
        this.service = service;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setTitle(String title){
        lblTitle.setText(title);
    }

    public void setDescription(String description){
        lblDescription.setText(description);
    }

    public Card(String fxml) throws IOException {
        String basePath = "/br/edu/ufersa/oficina/view/components/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(basePath + fxml));

        loader.setRoot(this);
        loader.setController(this);

        loader.load();
    }

    private boolean confirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Remoção");
        alert.setHeaderText("Deseja realmente excluir este item?");

        Optional<ButtonType> dialog = alert.showAndWait();

        return dialog.isPresent() && dialog.get() == ButtonType.OK;

    }
    public void delete(){
        Pane parent = (Pane) this.getParent();

        if (parent != null && confirm()){
            parent.getChildren().remove(this);

            if (service != null)
                service.delete(entityId);
        }
    }
}
