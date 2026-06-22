package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.UserService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UserForm extends Form<User, UserService> {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private Button saveButton;

    public UserForm(User user, UserService service) {
        super(user, service, "User.fxml");
    }

    @Override
    public void fill() {
        nameField.setText(entity.getName());
        emailField.setText(entity.getEmail());
        passwordField.setText(entity.getPassword());
        adminCheckBox.setSelected(entity.isAdmin());
    }

    @Override
    public void setEntityValues() {
        if (entity == null)
            entity = new User();

        entity.setName(nameField.getText());
        entity.setEmail(emailField.getText());
        entity.setPassword(passwordField.getText());
        entity.setAdmin(adminCheckBox.isSelected());
    }
}

