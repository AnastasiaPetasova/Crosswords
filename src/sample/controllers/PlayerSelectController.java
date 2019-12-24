package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerSelectController {
    @FXML
    protected void changeAccount(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/start_screen.fxml")),415,400));
    }
    @FXML
    protected void selectCrossword(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(createCrossPlayScene(20,15));
    }
    public Scene createCrossPlayScene(int width, int height) {/*(Crossword crossword)*/
        // int width = crossword.getWidth();
        // int height = crossword.getHeight();
        if (width<15) width=15;
        if (width>30) width=30;
        if (height<15)height=15;
        if (height>30)height=30;
        double cellWidth = 29;
        double cellHeight = 26;
        BorderPane pane = new BorderPane();
        GridPane centerPane = new GridPane();
        centerPane.setPrefHeight(200.0/13*height);
        centerPane.setPrefWidth(450.0/13*width);
        centerPane.setStyle("-fx-background-color: #111; -fx-border-color: #111;");
        centerPane.setAlignment(Pos.CENTER);
        for (int i = 0; i<width; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.SOMETIMES);
            cc.setMinWidth(10.0);
            cc.setPrefWidth(cellWidth);
            centerPane.getColumnConstraints().add(cc);
        }
        for (int i = 0; i<height; i++) {
            RowConstraints cc = new RowConstraints();
            cc.setVgrow(Priority.SOMETIMES);
            cc.setMinHeight(10.0);
            cc.setPrefHeight(cellHeight);
            centerPane.getRowConstraints().add(cc);
        }
        pane.setCenter(centerPane);
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setPrefHeight(32.0);
        topBox.setPrefWidth(cellWidth*width);
        Button saveButton = new Button("Сохранить и выйти");
        saveButton.setMnemonicParsing(false);
        EventHandler<ActionEvent> actionEventEventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/start_screen.fxml")), 415, 400));
                }
                catch (IOException ex) {}
            }
        };
        saveButton.setOnAction(actionEventEventHandler);
        topBox.getChildren().add(saveButton);
        Button hintButton = new Button("?");
        hintButton.setFont(Font.font("System", FontWeight.BOLD, 14.0));
        hintButton.setMnemonicParsing(false);
        Insets hintIns = new Insets(0,0,0,10.0);
        topBox.getChildren().add(hintButton);
        HBox.setMargin(hintButton, hintIns);
        Label hintLabel = new Label();
        hintLabel.setText(""+"/"+""); // crossword.getRemainingHints()
        hintLabel.setFont(Font.font("System",  14.0));
        HBox.setMargin(hintLabel, new Insets(0,10,0,10));
        topBox.getChildren().add(hintLabel);
        Button iButton = new Button("i");
        iButton.setMnemonicParsing(false);
        iButton.setFont(new Font(14.0));
        topBox.getChildren().add(iButton);
        pane.setTop(topBox);
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPrefHeight(28.0);
        bottomBox.setPrefWidth(cellWidth*width);
        Button checkButton = new Button("Проверить");
        checkButton.setMnemonicParsing(false);
        bottomBox.getChildren().add(checkButton);
        pane.setBottom(bottomBox);
        pane.setPadding(new Insets(20,20,20,20));
        return new Scene(pane, 40+width*cellWidth, 100+height*cellHeight);
    }
}
