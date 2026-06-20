package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PartForm extends Form<Part, PartsService> {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField manufacturerField;
    @FXML private TextField modelField;

    public PartForm(ScreenManager screenManager, Part entity, PartsService service) {
        super(screenManager, entity, service, "Part.fxml");
    }

    public PartForm(ScreenManager screenManager, PartsService service) {
        super(screenManager, service, "Part.fxml");
    }

    @Override
    public void fill() {
        nameField.setText(entity.getName());
        priceField.setText(String.valueOf(entity.getPrice()));
        manufacturerField.setText(entity.getManufacturer());
        modelField.setText(entity.getModel());
    }

    @Override
    public void setEntityValues() {
        if (entity == null)
            entity = new Part();

        entity.setName(nameField.getText());
        entity.setPrice(Double.parseDouble(priceField.getText()));
        entity.setManufacturer(manufacturerField.getText());
        entity.setModel(modelField.getText());
    }
}