module projetooficina{
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens br.edu.ufersa.oficina.controller to javafx.fxml, javafx.graphics;
    opens br.edu.ufersa.oficina.components to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.oficina.controller;
    exports br.edu.ufersa.oficina.components;
    exports br.edu.ufersa.oficina.model.Entity;
    exports br.edu.ufersa.oficina.model.DAO;
    exports br.edu.ufersa.oficina.model.Services;
    exports br.edu.ufersa.oficina.ui;
    exports br.edu.ufersa.oficina.utils;
    exports br.edu.ufersa.oficina;
}