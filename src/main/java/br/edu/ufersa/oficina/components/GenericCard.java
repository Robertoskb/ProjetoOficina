package br.edu.ufersa.oficina.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class GenericCard extends AnchorPane {
    protected int cardId;

    public GenericCard(String fxml) throws IOException {
        String basePath = "/br/edu/ufersa/oficina/view/components/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(basePath + fxml));

        loader.setRoot(this);
        loader.setController(this);

        loader.load();
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
