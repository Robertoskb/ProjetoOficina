package br.edu.ufersa.oficina.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class TreatmentCard extends Card {
    @FXML private Button btnCheck;
    
    public TreatmentCard() throws IOException {
        super("treatmentCard");
    }
}
