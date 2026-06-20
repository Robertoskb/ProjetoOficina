package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientForm extends Form<Client, ClientService> {

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField cpfField;

    public ClientForm(ScreenManager screenManager, Client entity, ClientService service) {
        super(screenManager, entity, service, "Client.fxml");
    }

    public ClientForm(ScreenManager screenManager, ClientService service) {
        super(screenManager, service, "Client.fxml");
    }

    @FXML
    public void initialize() {

        cpfField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*"))
                cpfField.setText(newVal.replaceAll("[^\\d]", ""));
            if (cpfField.getText().length() > 11)
                cpfField.setText(cpfField.getText().substring(0, 11));
        });

        if (entity != null && entity.isValid())
            fill();

    }

    @Override
    public void fill() {
        nameField.setText(entity.getName());
        addressField.setText(entity.getAddress());
        cpfField.setText(String.valueOf(entity.getCPF()));
    }

    @Override
    public void setEntityValues() {
        if (entity == null)
            entity = new Client();

        String cpfText = cpfField.getText();
        if (cpfText.length() != 11)
            throw new MecException("CPF deve ter 11 dígitos");

        entity.setName(nameField.getText());
        entity.setAddress(addressField.getText());
        entity.setCPF(Long.parseLong(cpfText));
    }
}