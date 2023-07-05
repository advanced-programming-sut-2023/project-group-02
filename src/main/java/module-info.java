module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jdk.jshell;
    requires java.desktop;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    exports client.view;
    exports client.view.enums;

    opens client.view to javafx.fxml;
    exports models;
    opens models to com.google.gson;
    exports models.units;
    exports models.buildings;
    exports server;
    exports server.chat;
    opens server.logic to com.google.gson;
}
