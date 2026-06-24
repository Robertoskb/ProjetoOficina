package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuController extends BaseController{
    @FXML private Text txtOrderInProgress;
    @FXML private Text txtOrderFinished;
    @FXML private Text txtOrderPaidPending;

    private OrderService service;

    public MenuController(OrderService orderService){
        setService(orderService);
    }

    public void initialize(){
        txtOrderInProgress.setText("" + service.getTransactionInProgress().size());
        txtOrderFinished.setText("" + service.getTransactionCompleteThisMonth().size());
        txtOrderPaidPending.setText("" + service.getTransactionPaidPending().size());
    }

    public void setService(OrderService service) {
        this.service = service;
    }
}
