package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.controller.form.Form;
import br.edu.ufersa.oficina.model.Entity.Entity;
import br.edu.ufersa.oficina.model.Services.GenericService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CardAdd extends CardGeneric{
    @FXML private Button btnAdd;

    public CardAdd() throws IOException{
        super("addCard.fxml");
    }
}
