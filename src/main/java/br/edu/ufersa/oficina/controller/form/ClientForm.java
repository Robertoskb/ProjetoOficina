package br.edu.ufersa.oficina.controller.form;

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

        entity.setName(nameField.getText());
        entity.setAddress(addressField.getText());
        entity.setCPF(Long.parseLong(cpfField.getText()));
    }
}