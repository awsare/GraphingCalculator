module org.example.graphingcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.junit.platform.engine;


    opens org.example.graphingcalculator to javafx.fxml;
    exports org.example.graphingcalculator;
    exports org.example.graphingcalculator.expressions;
    opens org.example.graphingcalculator.expressions to javafx.fxml;
}