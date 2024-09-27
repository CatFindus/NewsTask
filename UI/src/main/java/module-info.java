module ru.puchinets.ui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires static lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens ru.puchinets.ui to javafx.fxml;
    exports ru.puchinets.ui;
    exports ru.puchinets.ui.controller;
    opens ru.puchinets.ui.controller to javafx.fxml;
}