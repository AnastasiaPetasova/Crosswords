package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;

public class AdminCreateController {
    @FXML private TabPane tabPane;
    @FXML
    protected void createClick(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Кроссворды"))
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/cross_create.fxml")), 400, 300));
            else stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/dict_edit.fxml")), 450, 400));
    }
    @FXML
    protected void changeAccount(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/scenes/start_screen.fxml")),415,400));
    }
}
