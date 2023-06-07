package view.pregame;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import utils.Graphics;

import java.util.Objects;

public class InitGameMenu {
    private final Text numberOfTurnsText = new Text("Number of turns: ");
    private final TextField numberField = new TextField();
    private final Text mapWidthText = new Text("Map width: ");
    private final TextField mapWidthField = new TextField();
    private final Text mapHeightText = new Text("Map height: ");
    private final TextField mapHeightField = new TextField();

    public Pane getPane() {
        Pane initGamePane = new Pane();
        initPane(initGamePane);
        return initGamePane;
    }

    private void initPane(Pane pane) {
        pane.setBackground(Graphics.getBackground(Objects.requireNonNull(getClass().getResource("/images/backgrounds/main-menu.png"))));
        pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/Menus.css")).toExternalForm());
        pane.setPrefSize(960, 540);
        initNumberOfTurnsFields(pane);
        initMapSizeFields(pane);
    }

    private void initNumberOfTurnsFields(Pane pane) {
        numberOfTurnsText.setLayoutX(100);
        numberOfTurnsText.setLayoutY(120);
        numberOfTurnsText.getStyleClass().add("title1");
        numberField.setLayoutX(300);
        numberField.setLayoutY(100);
        numberField.setMaxWidth(45);

        pane.getChildren().addAll(numberOfTurnsText, numberField);
    }

    private void initMapSizeFields(Pane pane) {
        mapWidthText.setLayoutX(100);
        mapWidthText.setLayoutY(160);
        mapWidthText.getStyleClass().add("title1");
        mapHeightText.setLayoutX(100);
        mapHeightText.setLayoutY(200);
        mapHeightText.getStyleClass().add("title1");

        mapWidthField.setLayoutX(300);
        mapWidthField.setLayoutY(140);
        mapWidthField.setMaxWidth(45);
        mapHeightField.setLayoutX(300);
        mapHeightField.setLayoutY(180);
        mapHeightField.setMaxWidth(45);

        pane.getChildren().addAll(mapWidthText, mapWidthField, mapHeightText, mapHeightField);
    }
}
