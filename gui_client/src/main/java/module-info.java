module com.michal {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires com.google.gson;
    requires java.sql;

    requires java.logging;

    opens com.michal to javafx.fxml, com.google.gson;
    exports com.michal;
}