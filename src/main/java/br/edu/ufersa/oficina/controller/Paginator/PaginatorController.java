package br.edu.ufersa.oficina.controller.Paginator;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.GenericCard;
import br.edu.ufersa.oficina.controller.BaseController;
import br.edu.ufersa.oficina.model.Services.GenericService;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public abstract class PaginatorController<S extends GenericService<?>> extends BaseController implements Observer {
    @FXML protected VBox cardContainer;
    @FXML protected Pagination pagination;

    protected PaginationList<GenericCard> paginationList;
    protected S service;
    protected final ArrayList<GenericCard> cards = new ArrayList<>();
    protected final int perPage = 4;

    public PaginatorController(S service){
        setService(service);
    }

    public abstract void generateCards() throws IOException;

    public void loadPagination() throws IOException{
        if (!cards.isEmpty())
            cards.clear();

        try {
            generateCards();
            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);

            cards.add(0, cardAdd);
        }

        catch (MecException e){
            alert(e.getMessage());
        }

        paginationList = new PaginationList<>(cards, perPage);
        pagination.setPageCount((cards.size()/(perPage)) + (cards.size()%perPage != 0 ? 1 : 0));
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            updatePage(newIndex.intValue());
        });

        updatePage(0);
    }

    public void initialize() throws IOException {
        pagination.setCurrentPageIndex(0);

        loadPagination();
    }

    public GenericService<?> getService() {
        return service;
    }

    public void insertCards(ArrayList<GenericCard> cards){
        for (GenericCard card: cards)
            cardContainer.getChildren().add(card);
    }

    public void updatePage(int newIndex) {
        cardContainer.getChildren().clear();

        insertCards(paginationList.getPage(newIndex+1));

        pagination.setPageCount((cards.size()/(perPage)) + (cards.size()%perPage != 0 ? 1 : 0));
    }

    @Override
    public void delete(int id){
        try {
            service.delete(id);
            cards.removeIf(card -> card.getCardId() == id);
            updatePage(pagination.getCurrentPageIndex());
        }

        catch (Exception e){
            alert(e.getMessage());
        }
    }

    public void setService(S service) {
        this.service = service;
    }
}
