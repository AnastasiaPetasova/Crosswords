package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private final String ADMIN_LOGIN = "admin";
    private final String ADMIN_PASSWORD = "admin";
    @FXML private TextField loginField;
    @FXML private TextField passwordField;
    @FXML
    protected void authorize(ActionEvent event)throws Exception {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        if (loginField.getText().equals(ADMIN_LOGIN) && passwordField.getText().equals(ADMIN_PASSWORD))
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/admin_create.fxml")),642,500));
        //else stage.setScene(createCrossPlayScene(20,30));
        else stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/player_select.fxml")),642,500));
    }


}
