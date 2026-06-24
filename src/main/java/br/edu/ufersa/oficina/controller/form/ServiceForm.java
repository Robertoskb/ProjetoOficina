package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServiceForm extends Form<Service, ServiceService> {

    @FXML private TextField nameField;
    @FXML private TextField priceField;

    public ServiceForm(Service entity, ServiceService service) {
        super(entity, service, "Service.fxml");
    }


    @FXML
    public void initialize() {
        priceField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*\\.?\\d*"))
                priceField.setText(oldVal);
        });

        if (entity != null && entity.isValid())
            fill();
    }

    @Override
    public void fill() {
        nameField.setText(entity.getName());
        priceField.setText(String.valueOf(entity.getPrice()));
    }

    @Override
    public void setEntityValues() {
        if (entity == null)
            entity = new Service("", 0);

        entity.setName(nameField.getText());
        entity.setPrice(Double.parseDouble(priceField.getText()));
    }
}