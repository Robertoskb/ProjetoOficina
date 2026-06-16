module projetooficina{
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens br.edu.ufersa.oficina.controller to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.oficina.controller;
    exports br.edu.ufersa.oficina.model.Services;
    exports br.edu.ufersa.oficina;
}