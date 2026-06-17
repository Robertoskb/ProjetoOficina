package br.edu.ufersa.oficina.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AddCard extends Card{
    @FXML private Button btnAdd;

    public AddCard() throws IOException{
        super("addCard.fxml");
    }
}
