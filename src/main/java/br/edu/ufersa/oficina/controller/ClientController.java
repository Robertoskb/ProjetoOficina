package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.Card;
import br.edu.ufersa.oficina.components.GenericCard;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController extends BaseController{

    @FXML private VBox cardContainer;
    @FXML private Pagination pagination;

    private PaginationList<Card> paginationList;
    private final ClientService clientService = new ClientService();
    private final ArrayList<Card> cards = new ArrayList<>();

    public ClientController(ScreenManager screenManager){
        super(screenManager);
    }


    public void initialize() throws IOException {
        for (Client client: clientService.getAllClients()){
            Card card = new GenericCard();
            card.setService(clientService);
            card.setTitle(client.getName());
            card.setDescription(client.getAddress());

            cards.add(card);
        }
        int pageSize = 6;
        paginationList = new PaginationList<>(cards, pageSize);
        pagination.setPageCount(cards.size()/(pageSize));
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            updatePage(newIndex.intValue());
        });
    }

    public void insertCards(ArrayList<Card> cards){
        for (Card card: cards)
            cardContainer.getChildren().add(card);
    }

    private void updatePage(int newIndex){
        cardContainer.getChildren().clear();
        insertCards(paginationList.getPage(newIndex));
    }
}
