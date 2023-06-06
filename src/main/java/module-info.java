module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jdk.jshell;
    requires java.desktop;

    exports view;
    exports view.enums;
    opens view to javafx.fxml;
    exports models;
    opens models to com.google.gson;
    exports models.units;
    exports models.buildings;
}
