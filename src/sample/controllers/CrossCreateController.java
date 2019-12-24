package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class CrossCreateController {
    @FXML private Spinner<Integer> widthSpinner;
    @FXML private Spinner<Integer> heightSpinner;
    @FXML private ComboBox<String> dictionaryChoice;
    @FXML private CheckBox autogenCheckbox;
    @FXML protected void createClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(createCrossEditScene(widthSpinner.getValue(), heightSpinner.getValue()));
    }
    public Scene createCrossEditScene(int width, int height) {
        if (width<15) width=15;
        if (width>30) width=30;
        if (height<15)height=15;
        if (height>30)height=30;
        double cellWidth = 29.15;
        double cellHeight = 26.15;
        BorderPane pane = new BorderPane();
        GridPane centerPane = new GridPane();
        centerPane.setGridLinesVisible(true);
        centerPane.setPrefHeight(200.0/13*height);
        centerPane.setPrefWidth(450.0/13*width);
        centerPane.setStyle("-fx-background-color: #ddd; -fx-border-color: #123;");
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
        // setCrossEditTextFields(centerPane);
        pane.setCenter(centerPane);
        VBox rightBox = new VBox();
        rightBox.setPrefHeight(cellHeight*height);
        rightBox.setPrefWidth(172.0);
        rightBox.setAlignment(Pos.CENTER);
        CheckBox selectSort = new CheckBox();
        selectSort.setMaxWidth(1.7976931348623157E308);
        selectSort.setPrefHeight(25.0);
        TextField enterWord = new TextField();
        ListView selectWord = new ListView();
        rightBox.getChildren().add(selectSort);
        rightBox.getChildren().add(enterWord);
        rightBox.getChildren().add(selectWord);
        pane.setRight(rightBox);
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setPrefHeight(32.0);
        topBox.setPrefWidth(240.0+cellWidth*width);
        HBox innerBox = new HBox();
        innerBox.setAlignment(Pos.CENTER_LEFT);
        innerBox.setMaxWidth(1.7976931348623157E308);
        innerBox.setMaxHeight(1.7976931348623157E308);
        innerBox.setPrefHeight(100.0);
        innerBox.setPrefWidth(cellWidth*width);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(32.0);
        imageView.setFitWidth(52.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("@../../res/img/button_erase.png"));
        innerBox.getChildren().add(imageView);
        topBox.getChildren().add(innerBox);
        Label numHints = new Label("Число подсказок");
        Insets numIns = new Insets(0,10.0,0,0);
        topBox.getChildren().add(numHints);
        HBox.setMargin(numHints, numIns);
        TextField tf = new TextField();
        tf.setPrefHeight(25.0);
        tf.setPrefWidth(36.0);
        topBox.getChildren().add(tf);
        HBox.setMargin(tf, new Insets(0,25,0,0));
        Button iButton = new Button("i");
        iButton.setMnemonicParsing(false);
        iButton.setFont(new Font(14.0));
        topBox.getChildren().add(iButton);

        pane.setTop(topBox);
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPrefHeight(28.0);
        bottomBox.setPrefWidth(561.0);
        Button saveButton = new Button("Сохранить");
        EventHandler<ActionEvent> actionEventEventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/admin_create.fxml")), 642, 500));
                }
                catch (IOException ex) {}
            }
        };
        saveButton.setMnemonicParsing(false);
        saveButton.setOnAction(actionEventEventHandler);
        bottomBox.getChildren().add(saveButton);
        pane.setBottom(bottomBox);
        pane.setPadding(new Insets(20,20,20,20));
        BorderPane.setMargin(rightBox, new Insets(0,0,0,10));

        return new Scene(pane, 222+width*cellWidth, 100+height*cellHeight);
    }
}
