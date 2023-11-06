package sample;

import java.io.Serializable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import data.Restaurant;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchResController implements Serializable, Initializable {

    private List<Restaurant> resList = new ArrayList<>();

    private String resSearchList[] = { "Search By Name", "Search by Score", "Search by Price($ or $$ or $$$)",
            "Search by Zip Code", "Search by Category" };

    @FXML
    private ComboBox<String> myComboBox;

    @FXML
    private TextField resSearchBox;
    @FXML
    private Button confirmButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
        myComboBox.getItems().addAll(resSearchList);
        myComboBox.getSelectionModel().selectFirst();
        }
        catch(Exception e)
        {
            //System.out.println("error in initialize of SearchResController");
            //System.out.println(e);
        }

    }

    @FXML
    void confirmButton(ActionEvent event) throws IOException {
        String selectedOption = myComboBox.getValue();
        String searchBoxText = resSearchBox.getText();
        resSearchBox.setText(null);
        if (selectedOption.equals("Search By Name")) {
            main.getSocketWrapper().write("restaurantlist,name," + searchBoxText);
        } else if (selectedOption.equals("Search by Score")) {

            main.getSocketWrapper().write("restaurantlist,score," + searchBoxText);
        } else if (selectedOption.equals("Search by Price($ or $$ or $$$)")) {

            main.getSocketWrapper().write("restaurantlist,price," + searchBoxText);
        } else if (selectedOption.equals("Search by Category")) {

            main.getSocketWrapper().write("restaurantlist,category," + searchBoxText);
        } else if (selectedOption.equals("Search by Zip Code")) {

            main.getSocketWrapper().write("restaurantlist,zipcode," + searchBoxText);
        }
        // showResDetailsinTable(resList);
    }

    private Main main;

    void setsearchResMain(Main main) {
        this.main = main;
    }

    @FXML
    private TableColumn<Restaurant, String> restaurantNameColumn;
    @FXML
    private TableColumn<Restaurant, String> restaurantPriceColumn;
    @FXML
    private TableColumn<Restaurant, Double> restaurantScoreColumn;
    @FXML
    private TableColumn<Restaurant, String> restaurantZipCodeColumn;
    @FXML
    private TableColumn<Restaurant, String> restaurantCategoryColumn;
    @FXML
    private TableView<Restaurant> restaurantTable;

    void showResDetailsinTable() {
        try {
            if (resList == null) {
                System.out.println("resList is null");
                return;

            }

            ObservableList<Restaurant> resData = FXCollections.observableArrayList(resList);

            restaurantNameColumn
                    .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRestaurantName()));
            restaurantPriceColumn.setCellValueFactory(
                    cellData -> new SimpleStringProperty(cellData.getValue().getRestaurantPrice()));
            restaurantScoreColumn.setCellValueFactory(
                    cellData -> new SimpleDoubleProperty(cellData.getValue().getRestaurantScore()).asObject());
            restaurantZipCodeColumn
                    .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZipCode()));
            restaurantCategoryColumn
                    .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getnewResCategory()));

            restaurantTable.setItems(resData);
        } catch (Exception e) {
            System.out.println("error in showResDetailsinTable");
            System.out.println(e);

        }

    }

    void setRestaurantList(List<Restaurant> tempResList) {
        this.resList = tempResList;

        // System.out.println("setting tempResList of SearchRescontroller");

    }

    Stage stage;

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public String SelectedRestaurantName;

    @FXML
    void restaurantConfirmButton(ActionEvent event) throws IOException {
        // System.out.println("Res confirmed");
        Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
        if (selectedRestaurant == null) {
            System.out.println("No Restaurant Selected");
            return;
        }
        // System.out.println("Selected Restaurant: " +
        // selectedRestaurant.getRestaurantName());
        SelectedRestaurantName = selectedRestaurant.getRestaurantName();
        main.getSocketWrapper().write("foodlist," + SelectedRestaurantName);
        stage.close();

    }

}
