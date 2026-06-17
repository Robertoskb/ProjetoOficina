package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.Card;
import br.edu.ufersa.oficina.model.Services.GenericService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.Observer;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public abstract class PaginatorController<E, S extends GenericService<E>> extends BaseController implements Observer {
    @FXML protected VBox cardContainer;
    @FXML protected Pagination pagination;

    protected PaginationList<Card> paginationList;
    protected final S service;
    protected final ArrayList<Card> cards = new ArrayList<>();
    protected final int perPage = 4;

    public abstract void generateCards() throws IOException;

    public void initialize() throws IOException {
        pagination.setCurrentPageIndex(0);

        generateCards();

        paginationList = new PaginationList<>(cards, perPage);
        pagination.setPageCount((cards.size()/(perPage)) + (cards.size()%perPage != 0 ? 1 : 0));
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            updatePage(newIndex.intValue());
        });

        updatePage(0);
    }

    public PaginatorController(ScreenManager screenManager, S service){
        super(screenManager);
        this.service = service;
    }

    public GenericService<?> getService() {
        return service;
    }

    public void insertCards(ArrayList<Card> cards){
        for (Card card: cards)
            cardContainer.getChildren().add(card);
    }

    public void updatePage(int newIndex){
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
