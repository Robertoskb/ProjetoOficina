package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.ClientForm;
import br.edu.ufersa.oficina.utils.PaginationList;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.ArrayList;

public class ClientController extends PaginatorController<ClientService> {

    @FXML private TextField filterName;
    @FXML private TextField filterCpf;

    public ClientController(ScreenManager screenManager) {
        super(screenManager, new ClientService());
    }

    @Override
    public void generateCards() throws IOException {
        for (Client client : service.getAllClients()) {
            CardSubject card = new CardSubject();
            card.setCardId(client.getId());
            card.setTitle(client.getName());
            card.setDescription(client.getAddress());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @FXML
    public void filter() {
        try {
            String name = filterName.getText().toLowerCase().trim();
            String cpf  = filterCpf.getText().trim();

            ArrayList<Client> filtered = new ArrayList<>();
            for (Client client : service.getAllClients()) {
                boolean matchName = name.isEmpty() || client.getName().toLowerCase().contains(name);
                boolean matchCpf  = cpf.isEmpty()  || String.valueOf(client.getCPF()).contains(cpf);
                if (matchName && matchCpf)
                    filtered.add(client);
            }

            cards.clear();
            for (Client client : filtered) {
                CardSubject card = new CardSubject();
                card.setCardId(client.getId());
                card.setTitle(client.getName());
                card.setDescription(client.getAddress());
                card.registerObserver(this);
                cards.add(card);
            }

            paginationList = new PaginationList<>(cards, perPage);
            updatePage(0);

        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @FXML
    public void clearFilter() {
        filterName.clear();
        filterCpf.clear();
        cards.clear();
        try {
            initialize();
        } catch (IOException e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/clientForm.fxml");
            loader.setController(new ClientForm(screenManager, new Client(), service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Client client = service.getClientById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/clientForm.fxml");
            loader.setController(new ClientForm(screenManager, client, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}