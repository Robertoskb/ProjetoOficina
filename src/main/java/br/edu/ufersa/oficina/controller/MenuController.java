package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuController extends BaseController{
    @FXML private Text txtOrderInProgress;
    @FXML private Text txtOrderFinished;
    @FXML private Text txtOrderPaidPending;

    private final OrderService service = new OrderService();

    public MenuController(ScreenManager screenManager){
        super(screenManager);
    }

    public void initialize(){
        txtOrderInProgress.setText("" + service.getTransactionInProgress().size());
        txtOrderFinished.setText("" + service.getTransactionCompleteThisMonth().size());
        txtOrderPaidPending.setText("" + service.getTransactionPaidPending().size());
    }
}
