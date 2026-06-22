package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.UserService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController extends BaseController{

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

                screenManager.setUser(user);
                screenManager.setScene("Main.fxml");
                screenManager.getStage().setMaximized(true);
                screenManager.show();
            }
            else{
                screenManager.setUser(new User("Ghost", "", "", true)); // debug
                screenManager.setScene("Main.fxml");
                screenManager.getStage().setMaximized(true);
                screenManager.show();//
            }

        } catch (Exception e) {
            txtError.setText(e.getMessage());
        }

    }

    public UserService getUserService() {
        return userService;
    }
}