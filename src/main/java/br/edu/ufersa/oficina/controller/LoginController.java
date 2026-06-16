package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Text txtError;

    private final UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    @FXML
    private void login() {

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        User user;

        try {
            if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                user = userService.login(email, password);

                txtError.setText("Bem vindo, " + user.getName());
            }
            else{
                txtError.setText("Campos vazios");
            }

        } catch (Exception e) {
            txtError.setText(e.getMessage());
        }

    }

    public UserService getUserService() {
        return userService;
    }
}