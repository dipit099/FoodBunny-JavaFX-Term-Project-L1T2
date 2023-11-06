package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AdminController {

    private Main main;

    @FXML
    void showResLoginPage(ActionEvent event) throws Exception {
        main.showResLoginPage();
    }
    @FXML
    void showUserLoginPage(ActionEvent event) throws Exception {
        main.showUserLoginPage();
    }
    @FXML
    void addRestaurant(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adding Restaurant");
        alert.setHeaderText("Add Restaurant");
        alert.setContentText("Please go to Server to add restaurant");
        alert.showAndWait();
        main.getSocketWrapper().write("addrestaurant");
    }

    void setMain(Main main) {
        this.main = main;
    }

}
