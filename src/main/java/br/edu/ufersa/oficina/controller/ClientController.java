package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.Card;
import br.edu.ufersa.oficina.components.EntityCard;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.Observer;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController extends BaseController implements Observer {

    @FXML private VBox cardContainer;
    @FXML private Pagination pagination;

    private PaginationList<Card> paginationList;
    private final ClientService clientService = new ClientService();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final int perPage = 4;

    public ClientController(ScreenManager screenManager){
        super(screenManager);
    }


    public void initialize() throws IOException {
        pagination.setCurrentPageIndex(0);
        for (Client client: clientService.getAllClients()){
            Card card = new EntityCard();
            card.setService(clientService);
            card.setEntityId(client.getId());
            card.setTitle(client.getName());
            card.setDescription(client.getAddress());
            card.registerObserver(this);

            cards.add(card);
        }

        paginationList = new PaginationList<>(cards, perPage);
        pagination.setPageCount((cards.size()/(perPage)) + (cards.size()%perPage != 0 ? 1 : 0));
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            updatePage(newIndex.intValue());
        });

        updatePage(0);
    }

    public void insertCards(ArrayList<Card> cards){
        for (Card card: cards)
            cardContainer.getChildren().add(card);
    }

    private void updatePage(int newIndex){
        cardContainer.getChildren().clear();
        insertCards(paginationList.getPage(newIndex+1));
        pagination.setPageCount((cards.size()/(perPage)) + (cards.size()%perPage != 0 ? 1 : 0));

    }

    @Override
    public void update(int id){
        cards.removeIf(card -> card.getEntityId() == id);
        updatePage(pagination.getCurrentPageIndex());
    }
}
