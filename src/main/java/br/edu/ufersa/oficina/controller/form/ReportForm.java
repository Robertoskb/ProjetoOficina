package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.controller.BaseController;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ReportForm extends BaseController {
    @FXML private ComboBox<String> reportTypeComboBox;

    @FXML private TextField titleField;

    @FXML private DatePicker startDatePicker;

    @FXML private DatePicker endDatePicker;

    @FXML private TextField pathField;

    @FXML private Button browseButton;

    @FXML private Button generateButton;

    public ReportForm(ScreenManager screenManager) {
        super(screenManager);
    }

    public void initialize(){
        reportTypeComboBox.getItems().addAll("Orçamento", "Ordem de Serviço");

    }

    @FXML public void browse(ActionEvent event){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "PDF",
                        "*.pdf"
                )
        );

        Window stage = ((Node) event.getSource()).getScene().getWindow();

        File arquivoSelecionado = fileChooser.showSaveDialog(stage);

        if (arquivoSelecionado != null) {
            pathField.setText(arquivoSelecionado.getAbsolutePath());
        }
    }
}
