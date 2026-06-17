package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainController extends BaseController{
    @FXML private BorderPane root;

    @FXML private Button btnHome;
    @FXML private Button btnClient;
    @FXML private Button btnCar;
    @FXML private Button btnService;
    @FXML private Button btnPart;
    @FXML private Button btnBudget;
    @FXML private Button btnOrder;

    @FXML private Text txtCenter;

    private Button currentButton = null;

    public MainController(ScreenManager screenManager){
        super(screenManager);
    }

    private void setCenter(String fxml) throws IOException{
        Parent view = screenManager.getScreenLoader().load(fxml);

        root.setCenter(view);
    }

    private void loadShow(String fxml, Button btn) throws IOException{
        try {
            setCenter(fxml);
        }

        catch (IllegalStateException e){
            setCenter("empty.fxml");
        }
        setCurrentButton(btn);
        screenManager.show();
    }

    public void initialize() throws IOException {
        setCenter("menu.fxml");
        setCurrentButton(btnHome);
    }

    @FXML
    private void loadHome() throws IOException{
        loadShow("menu.fxml", btnHome);
    }

    @FXML
    private void loadClient() throws IOException{
        loadShow("Client.fxml", btnClient);
    }

    @FXML
    private void loadCar() throws IOException{
        loadShow("Car.fxml", btnCar);
    }

    @FXML
    private void loadService() throws IOException{
        loadShow("Service.fxml", btnService);
    }

    @FXML
    private void loadPart() throws IOException{
        loadShow("Part.fxml", btnPart);
    }

    @FXML
    private void loadBudget() throws IOException{
        loadShow("Budget.fxml", btnBudget);
    }

    @FXML
    private void loadOrder() throws IOException{
        loadShow("Order.fxml", btnOrder);
    }

    public void setCurrentButton(Button bnt){
        if (currentButton != null){
            currentButton.getStyleClass().remove("active");
        }
        currentButton = bnt;
        currentButton.getStyleClass().add("active");
    }
}
