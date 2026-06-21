package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.ServiceForm;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;

public class ServiceController extends PaginatorController<ServiceService> {

    @FXML private ComboBox<String> filterName;

    protected ArrayList<Service> currentServices;

    public ServiceController(ScreenManager screenManager) {
        super(screenManager, new ServiceService());
        currentServices = service.getAllServices();
    }

    public ArrayList<Service> getServicesByName(String name) throws Exception {
        ArrayList<Service> filtered = new ArrayList<>();
        for (Service s : service.getAllServices()) {
            if (s.getName().equalsIgnoreCase(name)) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    @Override
    public void initialize() throws IOException {
        filterName.getItems().addAll(
                "Troca de Óleo", "Alinhamento", "Balanceamento",
                "Troca de Pastilhas de Freio", "Revisão Completa",
                "Troca de Correia Dentada", "Limpeza de Bicos Injetores",
                "Troca de Bateria", "Diagnóstico Eletrônico",
                "Troca de Amortecedores", "Troca de Embreagem",
                "Troca de Radiador", "Troca de Velas",
                "Higienização do Ar Condicionado", "Recarga de Ar Condicionado",
                "Troca de Filtro de Ar", "Troca de Filtro de Combustível",
                "Troca de Rolamento", "Funilaria Simples",
                "Polimento e Cristalização"
        );

        super.initialize();
    }

    @Override
    public void generateCards() throws IOException {
        for (Service entityService : currentServices) {
            CardSubject card = new CardSubject();
            card.setCardId(entityService.getId());
            card.setTitle(entityService.getName());
            card.setDescription(String.format("R$ %.2f", entityService.getPrice()));
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @FXML
    public void filterByName() {
        String name = filterName.getValue();
        if (name != null && !name.isEmpty()) {
            try {
                currentServices = getServicesByName(name);
                loadPagination();
            } catch (Exception e) {
                alert(e.getMessage());
            }
        }
    }

    @FXML
    public void clearFilter() {
        filterName.setValue(null);

        try {
            currentServices = service.getAllServices();
            loadPagination();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/serviceForm.fxml");
            loader.setController(new ServiceForm(screenManager, new Service("", 0), service));
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
            Service s = service.getServiceById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/serviceForm.fxml");
            loader.setController(new ServiceForm(screenManager, s, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}