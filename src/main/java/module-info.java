module projetooficina{
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;

    opens br.edu.ufersa.oficina.controller to javafx.fxml, javafx.graphics;
    opens br.edu.ufersa.oficina.components to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.oficina.controller;
    exports br.edu.ufersa.oficina.components;
    exports br.edu.ufersa.oficina.model.Entity;
    exports br.edu.ufersa.oficina.model.DAO;
    exports br.edu.ufersa.oficina.model.Services;
    exports br.edu.ufersa.oficina.model.Mappers;
    exports br.edu.ufersa.oficina.ui;
    exports br.edu.ufersa.oficina.utils;
    exports br.edu.ufersa.oficina;
    exports br.edu.ufersa.oficina.controller.form;
    opens br.edu.ufersa.oficina.controller.form to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.oficina.controller.Paginator;
    opens br.edu.ufersa.oficina.controller.Paginator to javafx.fxml, javafx.graphics;
}