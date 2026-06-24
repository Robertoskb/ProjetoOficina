package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PartForm extends Form<Part, PartService> {

    @FXML private ComboBox<String> nameField;
    @FXML private ComboBox<String> manufacturerField;
    @FXML private ComboBox<String> modelField;
    @FXML private TextField priceField;

    public PartForm(Part entity, PartService service) {
        super(entity, service, "Part.fxml");
    }

    @FXML
    public void initialize() {
        nameField.getItems().addAll(
                "Filtro de Óleo", "Pastilha de Freio", "Amortecedor Dianteiro",
                "Bateria 60Ah", "Correia Dentada", "Vela de Ignição",
                "Filtro de Ar", "Disco de Freio", "Radiador",
                "Bomba de Combustível", "Alternador", "Motor de Partida",
                "Rolamento de Roda", "Sensor de Oxigênio", "Filtro de Combustível",
                "Kit Embreagem", "Coxim do Motor", "Mangueira do Radiador",
                "Junta do Cabeçote", "Pneu 185/65 R15"
        );

        nameField.valueProperty().addListener((obs, oldVal, newVal) -> {
            manufacturerField.getItems().clear();
            modelField.getItems().clear();
            if (newVal == null) return;
            if (newVal.equals("Pastilha de Freio"))
                manufacturerField.getItems().addAll("Cobreq", "Bosch");
            else if (newVal.equals("Amortecedor Dianteiro"))
                manufacturerField.getItems().addAll("Monroe");
            else if (newVal.equals("Bateria 60Ah"))
                manufacturerField.getItems().addAll("Moura", "Delphi");
            else if (newVal.equals("Correia Dentada"))
                manufacturerField.getItems().addAll("Gates", "Dayco");
            else if (newVal.equals("Vela de Ignição"))
                manufacturerField.getItems().addAll("NGK", "Bosch");
            else if (newVal.equals("Filtro de Ar"))
                manufacturerField.getItems().addAll("Mann", "Tecfil");
            else if (newVal.equals("Disco de Freio"))
                manufacturerField.getItems().addAll("Fremax", "Bosch");
            else if (newVal.equals("Radiador"))
                manufacturerField.getItems().addAll("Valeo");
            else if (newVal.equals("Bomba de Combustível"))
                manufacturerField.getItems().addAll("Bosch", "Delphi");
            else if (newVal.equals("Alternador"))
                manufacturerField.getItems().addAll("Bosch", "Valeo");
            else if (newVal.equals("Motor de Partida"))
                manufacturerField.getItems().addAll("Bosch", "Valeo");
            else if (newVal.equals("Filtro de Óleo"))
                manufacturerField.getItems().addAll("Bosch", "Mann", "Tecfil");
            else
                manufacturerField.getItems().addAll("Bosch", "Valeo", "SKF", "Axios", "Goodyear", "Sabó", "Michelin", "Luk");
        });

        manufacturerField.valueProperty().addListener((obs, oldVal, newVal) -> {
            modelField.getItems().clear();
            if (newVal == null) return;
            String name = nameField.getValue();
            if (name == null) return;

            if (name.equals("Pastilha de Freio")) {
                if (newVal.equals("Cobreq"))
                    modelField.getItems().addAll("Cobreq Street N-242", "Cobreq Cerâmica N-1437C");
                else if (newVal.equals("Bosch"))
                    modelField.getItems().addAll("Bosch Ceramic", "Bosch EuroLine");
            } else if (name.equals("Amortecedor Dianteiro")) {
                if (newVal.equals("Monroe"))
                    modelField.getItems().addAll("Monroe OESpectrum", "Monroe Monro-Matic Plus");
            } else if (name.equals("Bateria 60Ah")) {
                if (newVal.equals("Moura"))
                    modelField.getItems().addAll("Moura M60GD", "Moura Inteligente M60GE");
                else if (newVal.equals("Delphi"))
                    modelField.getItems().addAll("Delphi Freedom DF60D");
            } else if (name.equals("Correia Dentada")) {
                if (newVal.equals("Gates"))
                    modelField.getItems().addAll("Gates PowerGrip", "Gates Horizon");
                else if (newVal.equals("Dayco"))
                    modelField.getItems().addAll("Dayco Teflon HT");
            } else if (name.equals("Vela de Ignição")) {
                if (newVal.equals("NGK"))
                    modelField.getItems().addAll("NGK Iridium IX", "NGK G-Power Platina");
                else if (newVal.equals("Bosch"))
                    modelField.getItems().addAll("Bosch Super 4", "Bosch Double Platinum");
            } else if (name.equals("Filtro de Ar")) {
                if (newVal.equals("Mann"))
                    modelField.getItems().addAll("Mann-Filter C30130", "Mann-Filter C22014");
                else if (newVal.equals("Tecfil"))
                    modelField.getItems().addAll("Tecfil ARL5132", "Tecfil ARL8831");
            } else if (name.equals("Disco de Freio")) {
                if (newVal.equals("Fremax"))
                    modelField.getItems().addAll("Fremax Carbon+ BD5600", "Fremax Max-Rotors BD3451");
                else if (newVal.equals("Bosch"))
                    modelField.getItems().addAll("Bosch BD0241");
            } else if (name.equals("Radiador")) {
                if (newVal.equals("Valeo"))
                    modelField.getItems().addAll("Valeo Compact", "Valeo Termo-Sistemas");
            } else if (name.equals("Bomba de Combustível")) {
                if (newVal.equals("Bosch"))
                    modelField.getItems().addAll("Bosch Elétrica Universal 3 Bar", "Bosch Flex F000TE159X");
                else if (newVal.equals("Delphi"))
                    modelField.getItems().addAll("Delphi FE10113");
            } else if (name.equals("Alternador")) {
                if (newVal.equals("Bosch"))
                    modelField.getItems().addAll("Bosch Linha Leve 90A", "Bosch Linha Pesada 120A");
                else if (newVal.equals("Valeo"))
                    modelField.getItems().addAll("Valeo SG10B022");
            } else if (name.equals("Motor de Partida")) {
                if (newVal.equals("Bosch"))
                    modelField.getItems().addAll("Bosch HEF95-L", "Bosch TSC (Start-Stop)");
                else if (newVal.equals("Valeo"))
                    modelField.getItems().addAll("Valeo FS10E1");
            } else if (name.equals("Filtro de Óleo")) {
                if (newVal.equals("Bosch")) modelField.getItems().addAll("Bosch Premium OB012");
                else if (newVal.equals("Mann")) modelField.getItems().addAll("Mann-Filter W712");
                else if (newVal.equals("Tecfil")) modelField.getItems().addAll("Tecfil PSL145");
            } else {
                modelField.getItems().addAll(newVal + " Linha Premium", newVal + " Universal Standard");
            }
        });

        priceField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*\\.?\\d*"))
                priceField.setText(oldVal);
        });

        if (entity != null && entity.isValid())
            fill();
    }

    @Override
    public void fill() {
        nameField.setValue(entity.getName());
        manufacturerField.setValue(entity.getManufacturer());
        modelField.setValue(entity.getModel());
        priceField.setText(String.valueOf(entity.getPrice()));
    }

    @Override
    public void setEntityValues() {
        if (entity == null)
            entity = new Part();

        entity.setName(nameField.getValue());
        entity.setManufacturer(manufacturerField.getValue());
        entity.setModel(modelField.getValue());
        entity.setPrice(Double.parseDouble(priceField.getText()));
    }
}