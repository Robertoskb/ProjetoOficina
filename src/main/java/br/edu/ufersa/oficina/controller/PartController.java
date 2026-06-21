package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardSubject;
import br.edu.ufersa.oficina.controller.form.PartForm;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;

public class PartController extends PaginatorController<PartsService> {

    @FXML private ComboBox<String> filterName;
    @FXML private ComboBox<String> filterManufacturer;
    @FXML private ComboBox<String> filterModel;

    public PartController(ScreenManager screenManager) {
        super(screenManager, new PartsService());
    }

    @Override
    public void initialize() throws IOException {

        filterName.getItems().addAll(
                "Filtro de Óleo", "Pastilha de Freio", "Amortecedor Dianteiro",
                "Bateria 60Ah", "Correia Dentada", "Vela de Ignição",
                "Filtro de Ar", "Disco de Freio", "Radiador",
                "Bomba de Combustível", "Alternador", "Motor de Partida",
                "Rolamento de Roda", "Sensor de Oxigênio", "Filtro de Combustível",
                "Kit Embreagem", "Coxim do Motor", "Mangueira do Radiador",
                "Junta do Cabeçote", "Pneu 185/65 R15"
        );

        filterManufacturer.getItems().addAll(
                "Bosch", "Cobreq", "Monroe", "Moura", "Gates", "Dayco",
                "NGK", "Mann", "Tecfil", "Fremax", "Valeo", "Delphi",
                "SKF", "Michelin", "Luk", "Axios", "Goodyear", "Sabó"
        );

        filterModel.getItems().addAll(
                "Cobreq Street N-242", "Cobreq Cerâmica N-1437C",
                "Bosch Ceramic", "Bosch EuroLine", "Bosch Super 4", "Bosch Double Platinum",
                "Bosch BD0241", "Bosch Elétrica Universal 3 Bar", "Bosch Flex F000TE159X",
                "Bosch Linha Leve 90A", "Bosch Linha Pesada 120A", "Bosch HEF95-L", "Bosch TSC (Start-Stop)",
                "Monroe OESpectrum", "Monroe Monro-Matic Plus",
                "Moura M60GD", "Moura Inteligente M60GE",
                "Delphi Freedom DF60D", "Delphi FE10113",
                "Gates PowerGrip", "Gates Horizon",
                "Dayco Teflon HT",
                "NGK Iridium IX", "NGK G-Power Platina",
                "Mann-Filter C30130", "Mann-Filter C22014",
                "Tecfil ARL5132", "Tecfil ARL8831",
                "Fremax Carbon+ BD5600", "Fremax Max-Rotors BD3451",
                "Valeo Compact", "Valeo Termo-Sistemas", "Valeo SG10B022", "Valeo FS10E1",
                "Bosch Premium OB012", "Mann-Filter W712", "Tecfil PSL145"
        );

        filterName.valueProperty().addListener((obs, oldVal, newVal) -> {
            filterManufacturer.getSelectionModel().clearSelection();
            filterManufacturer.setPromptText("Selecione o fabricante");
            filterModel.getSelectionModel().clearSelection();
            filterModel.setPromptText("Selecione o modelo");
            filterManufacturer.getItems().clear();
            filterModel.getItems().clear();

            if (newVal == null) {
                filterManufacturer.getItems().addAll("Bosch", "Cobreq", "Monroe", "Moura", "Gates", "Dayco",
                        "NGK", "Mann", "Tecfil", "Fremax", "Valeo", "Delphi",
                        "SKF", "Michelin", "Luk", "Axios", "Goodyear", "Sabó");
                filterModel.getItems().addAll(
                        "Cobreq Street N-242", "Cobreq Cerâmica N-1437C",
                        "Bosch Ceramic", "Bosch EuroLine", "Bosch Super 4", "Bosch Double Platinum",
                        "Monroe OESpectrum", "Monroe Monro-Matic Plus",
                        "Moura M60GD", "Moura Inteligente M60GE",
                        "Gates PowerGrip", "Gates Horizon",
                        "NGK Iridium IX", "NGK G-Power Platina",
                        "Mann-Filter C30130", "Mann-Filter C22014",
                        "Tecfil ARL5132", "Tecfil ARL8831",
                        "Fremax Carbon+ BD5600", "Fremax Max-Rotors BD3451",
                        "Valeo Compact", "Valeo Termo-Sistemas"
                );
                return;
            }

            if (newVal.equals("Pastilha de Freio")) {
                filterManufacturer.getItems().addAll("Cobreq", "Bosch");
                filterModel.getItems().addAll("Cobreq Street N-242", "Cobreq Cerâmica N-1437C", "Bosch Ceramic", "Bosch EuroLine");
            } else if (newVal.equals("Amortecedor Dianteiro")) {
                filterManufacturer.getItems().addAll("Monroe");
                filterModel.getItems().addAll("Monroe OESpectrum", "Monroe Monro-Matic Plus");
            } else if (newVal.equals("Bateria 60Ah")) {
                filterManufacturer.getItems().addAll("Moura", "Delphi");
                filterModel.getItems().addAll("Moura M60GD", "Moura Inteligente M60GE", "Delphi Freedom DF60D");
            } else if (newVal.equals("Correia Dentada")) {
                filterManufacturer.getItems().addAll("Gates", "Dayco");
                filterModel.getItems().addAll("Gates PowerGrip", "Gates Horizon", "Dayco Teflon HT");
            } else if (newVal.equals("Vela de Ignição")) {
                filterManufacturer.getItems().addAll("NGK", "Bosch");
                filterModel.getItems().addAll("NGK Iridium IX", "NGK G-Power Platina", "Bosch Super 4", "Bosch Double Platinum");
            } else if (newVal.equals("Filtro de Ar")) {
                filterManufacturer.getItems().addAll("Mann", "Tecfil");
                filterModel.getItems().addAll("Mann-Filter C30130", "Mann-Filter C22014", "Tecfil ARL5132", "Tecfil ARL8831");
            } else if (newVal.equals("Disco de Freio")) {
                filterManufacturer.getItems().addAll("Fremax", "Bosch");
                filterModel.getItems().addAll("Fremax Carbon+ BD5600", "Fremax Max-Rotors BD3451", "Bosch BD0241");
            } else if (newVal.equals("Radiador")) {
                filterManufacturer.getItems().addAll("Valeo");
                filterModel.getItems().addAll("Valeo Compact", "Valeo Termo-Sistemas");
            } else if (newVal.equals("Bomba de Combustível")) {
                filterManufacturer.getItems().addAll("Bosch", "Delphi");
                filterModel.getItems().addAll("Bosch Elétrica Universal 3 Bar", "Bosch Flex F000TE159X", "Delphi FE10113");
            } else if (newVal.equals("Alternador")) {
                filterManufacturer.getItems().addAll("Bosch", "Valeo");
                filterModel.getItems().addAll("Bosch Linha Leve 90A", "Bosch Linha Pesada 120A", "Valeo SG10B022");
            } else if (newVal.equals("Motor de Partida")) {
                filterManufacturer.getItems().addAll("Bosch", "Valeo");
                filterModel.getItems().addAll("Bosch HEF95-L", "Bosch TSC (Start-Stop)", "Valeo FS10E1");
            } else if (newVal.equals("Filtro de Óleo")) {
                filterManufacturer.getItems().addAll("Bosch", "Mann", "Tecfil");
                filterModel.getItems().addAll("Bosch Premium OB012", "Mann-Filter W712", "Tecfil PSL145");
            }
        });

        super.initialize();
    }

    @Override
    public void generateCards() throws IOException {
        for (Part part : service.getAllParts()) {
            CardSubject card = new CardSubject();
            card.setCardId(part.getId());
            card.setTitle(part.getName());
            card.setDescription(part.getManufacturer() + " - " + part.getModel());
            card.registerObserver(this);
            cards.add(card);
        }
    }

    @FXML
    public void filterByName() {
        String name = filterName.getValue();
        if (name == null) { clearFilter(); return; }
        try {
            ArrayList<Part> parts = service.getPartsByName(name);
            cards.clear();
            cardContainer.getChildren().clear();
            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            cards.add(cardAdd);
            for (Part part : parts) {
                CardSubject card = new CardSubject();
                card.setCardId(part.getId());
                card.setTitle(part.getName());
                card.setDescription(part.getManufacturer() + " - " + part.getModel());
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
    public void filterByManufacturer() {
        String manufacturer = filterManufacturer.getValue();
        if (manufacturer == null) { clearFilter(); return; }
        try {
            ArrayList<Part> parts = service.getPartsByManufacturer(manufacturer);
            cards.clear();
            cardContainer.getChildren().clear();
            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            cards.add(cardAdd);
            for (Part part : parts) {
                CardSubject card = new CardSubject();
                card.setCardId(part.getId());
                card.setTitle(part.getName());
                card.setDescription(part.getManufacturer() + " - " + part.getModel());
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
    public void filterByModel() {
        String model = filterModel.getValue();
        if (model == null) { clearFilter(); return; }
        try {
            ArrayList<Part> parts = service.getPartsByModel(model);
            cards.clear();
            cardContainer.getChildren().clear();
            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            cards.add(cardAdd);
            for (Part part : parts) {
                CardSubject card = new CardSubject();
                card.setCardId(part.getId());
                card.setTitle(part.getName());
                card.setDescription(part.getManufacturer() + " - " + part.getModel());
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
        filterName.getSelectionModel().clearSelection();
        filterName.setPromptText("Selecione o nome");
        filterManufacturer.getSelectionModel().clearSelection();
        filterManufacturer.setPromptText("Selecione o fabricante");
        filterModel.getSelectionModel().clearSelection();
        filterModel.setPromptText("Selecione o modelo");
        cards.clear();
        cardContainer.getChildren().clear();
        try {
            generateCards();
            CardAdd cardAdd = new CardAdd();
            cardAdd.registerObserver(this);
            cards.add(0, cardAdd);
            paginationList = new PaginationList<>(cards, perPage);
            updatePage(0);
        } catch (IOException e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(screenManager, service));
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
            Part part = service.getPartById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/partForm.fxml");
            loader.setController(new PartForm(screenManager, part, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}