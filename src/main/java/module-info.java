module org.example.graphingcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.junit.jupiter.api;


    opens org.example.graphingcalculator to javafx.fxml;
    exports org.example.graphingcalculator;
}