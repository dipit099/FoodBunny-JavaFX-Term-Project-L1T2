package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import server.Objpacked;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import data.Food;
import data.Restaurant;

public class ResController {

    private Main main;
    @FXML
    private AnchorPane resHome;
    @FXML
    private AnchorPane ResMenu;
    @FXML
    private AnchorPane AddFoodList;
    @FXML
    private AnchorPane PendingReqList;

    List<Food> tempFood;
    List<Food> mainFoodList;
    List<Food> confirmedFoods; // list for 2nd table
    Double totalEarned;

    public ResController() {
        tempFood = new ArrayList<>(); // tempfood for 1st table
        tempconfirmedFoods = new ArrayList<>(); // for 2nd table
        intorderNumber = 12934;
        totalEarned = 0.0;

    }

    public Restaurant myRestaurant;

    void setRestaurantReference(Restaurant r) {
        this.myRestaurant = r;
    }

    public void setMainFoodList(List<Food> foodList) throws IOException {
        // this.mainFoodList = foodList;
        mainFoodList = new ArrayList<>();
        confirmedFoods = new ArrayList<>();
        for (Food originalFood : foodList) {
            // if (originalFood.getOrderedCount() > 0)
            //     System.out.println("gotcha " + originalFood.getFoodName() + " " + originalFood.getOrderedCount());

            Food copiedFood = new Food(originalFood.getFoodCategory(), originalFood.getFoodName(),
                    originalFood.getFoodPrice());
            confirmedFoods.add(copiedFood);
            Food copiedFood2 = new Food(originalFood.getFoodCategory(), originalFood.getFoodName(),
                    originalFood.getFoodPrice());

            mainFoodList.add(copiedFood2);
        }
        showleftRequest(foodList);
    }

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    void loginAction(ActionEvent event) {
        String userName = userText.getText();
        String password = passwordText.getText();
        String message = "password" + "," + userName + "," + password;
        //System.out.println(message);

        try {
            main.getSocketWrapper().write(message);
        } catch (IOException e) {
            System.out.println("Cant send the password to server");
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    @FXML
    void backtoAdmin(ActionEvent event) throws Exception {
        main.showAdminPage();
    }

    @FXML
    private Button HomeButton;

    @FXML
    private Button MenuButton;

    @FXML
    private Button PendingButton;
    @FXML
    private Button AddFoodButton;
    @FXML
    private Button ConfirmedButton;

    @FXML
    private Label ResCategory;

    @FXML
    private Label ResName;
    String resName;

    @FXML
    private Label ResPrice;

    @FXML
    private Label ResScore;

    @FXML
    private Label ResZipCode;

    void setAllNames(Restaurant r) // home page
    {
        resName = r.getRestaurantName();
        ResName.setText(r.getRestaurantName());
        ResScore.setText(Double.toString(r.getRestaurantScore()));
        ResPrice.setText(r.getRestaurantPrice());
        ResZipCode.setText(r.getZipCode());

        String[] cat = r.getRestaurantCategory();
        int n = cat.length;
        String wholeCat = "";
        for (int i = 0; i < n; i++) {
            wholeCat = wholeCat + cat[i] + ",";
        }
        ResCategory.setText(wholeCat);

    }

    // called from Main.java initially
    void setResHome() throws Exception {
        resHome.setVisible(true);
        ResMenu.setVisible(false);
        AddFoodList.setVisible(false);
        PendingReqList.setVisible(false);
        confirmed.setVisible(false);
        HomeButton.setStyle("-fx-background-color:#a5bfe8 ");
        // c0c0c0
        MenuButton.setStyle("-fx-background-color:#c0c0c0 ");
        PendingButton.setStyle("-fx-background-color:#c0c0c0 ");
        AddFoodButton.setStyle("-fx-background-color:#c0c0c0 ");
        ConfirmedButton.setStyle("-fx-background-color:#c0c0c0 ");

    }

    @FXML
    void resHome(ActionEvent event) {
        try {
            setResHome(); // TODO change
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void menuList(ActionEvent event) throws Exception {
        resHome.setVisible(false);
        ResMenu.setVisible(true);
        AddFoodList.setVisible(false);
        PendingReqList.setVisible(false);
        confirmed.setVisible(false);
        HomeButton.setStyle("-fx-background-color:#c0c0c0 ");
        MenuButton.setStyle("-fx-background-color:#a5bfe8 ");
        PendingButton.setStyle("-fx-background-color:#c0c0c0 ");
        AddFoodButton.setStyle("-fx-background-color:#c0c0c0 ");
        ConfirmedButton.setStyle("-fx-background-color:#c0c0c0 ");
        setAllFoodNames();
    }

    @FXML
    private ListView<String> menuList;

    public void setAllFoodNames() {
        List<String> foodtable = new ArrayList<>();
        for (int i = 0; i < mainFoodList.size(); i++) {
            String foodName = mainFoodList.get(i).getFoodDetails();
            foodtable.add(foodName);
        }
         
        ObservableList<String> observableList = FXCollections.observableArrayList(foodtable);
        menuList.setItems(observableList);
        menuList.refresh();
       
    }

    @FXML
    void addFoodRes(ActionEvent event) {
        resHome.setVisible(false);
        ResMenu.setVisible(false);
        AddFoodList.setVisible(true);
        PendingReqList.setVisible(false);
        confirmed.setVisible(false);
        HomeButton.setStyle("-fx-background-color:#c0c0c0 ");
        MenuButton.setStyle("-fx-background-color:#c0c0c0 ");
        PendingButton.setStyle("-fx-background-color:#c0c0c0 ");
        AddFoodButton.setStyle("-fx-background-color:#a5bfe8 ");
        ConfirmedButton.setStyle("-fx-background-color:#c0c0c0 ");

    }

    // after Adding food , Submit Button
    @FXML
    void submitFood(ActionEvent event) {
        String foodCategoryInput = this.foodCategory.getText();
        String foodNameInput = this.foodName.getText();
        String foodPriceInput = this.foodPrice.getText();
        try {
            Food tempFood = new Food(foodCategoryInput, foodNameInput, Double.parseDouble(foodPriceInput));
            Objpacked objPacked = new Objpacked();
            objPacked.setName1("addfood");
            objPacked.setName2(myRestaurant.getRestaurantName());
            List<Food> selectedFoodList = new ArrayList<>();
            selectedFoodList.add(tempFood);
            objPacked.setFoodList(selectedFoodList);
            main.getSocketWrapper().write(objPacked);
            foodCategory.setText(null);
            foodName.setText(null);
            foodPrice.setText(null);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Food Not Added");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        }

    }

    @FXML
    private TextField foodCategory;

    @FXML
    private TextField foodName;

    @FXML
    private TextField foodPrice;

    @FXML
    void pendingReq(ActionEvent event) throws IOException {
        resHome.setVisible(false);
        ResMenu.setVisible(false);
        AddFoodList.setVisible(false);
        PendingReqList.setVisible(true);
        confirmed.setVisible(false);
        HomeButton.setStyle("-fx-background-color:#c0c0c0 ");
        MenuButton.setStyle("-fx-background-color:#c0c0c0 ");
        PendingButton.setStyle("-fx-background-color:#a5bfe8 ");
        AddFoodButton.setStyle("-fx-background-color:#c0c0c0 ");
        ConfirmedButton.setStyle("-fx-background-color:#c0c0c0 ");

    }

    @FXML
    private TableColumn<Food, String> foodCategoryColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;
    // @FXML
    // private TableColumn<Food, Double> foodPriceColumn;
    @FXML
    private TableColumn<Food, Integer> foodAmount;

    @FXML
    private TableColumn<ResController, String> orderNumber;

    @FXML
    private TableView<Food> table;

    ObservableList<Food> products;
    Integer intorderNumber;
    private String orderNumberString;

    public void showleftRequest(List<Food> pendingFoods) throws IOException {
        if (pendingFoods.size() == 0) {
           // System.out.println("No pending foods");
            return;
        }

        // tempFood.clear();

        for (Food f : pendingFoods) {
            if (f.getOrderedCount() > 0)
                tempFood.add(f);
            for (Food food : mainFoodList) {
                if (f.getFoodName().equals(food.getFoodName())) {
                    food.setOrderedCount(food.getOrderedCount() + f.getOrderedCount());
                    break;

                }
            }
        }

        //System.out.println("Print in table");
        // for (Food f : tempFood) {
        //     System.out.println(f.getFoodName() + " " + f.getOrderedCount());
        // }

        orderNumberString = "#";
        orderNumberString += intorderNumber.toString();
        intorderNumber++;

        orderNumber.setCellValueFactory(cellData -> new SimpleStringProperty(orderNumberString));
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));
        foodCategoryColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodCategory()));

        foodAmount.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderedCount()).asObject());
        products = FXCollections.observableArrayList(tempFood);
        table.setItems(products);
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Get the selected row data
                Food selectedFood = table.getSelectionModel().getSelectedItem();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Order Accepted?");
                a.setContentText("FoodName: " + selectedFood.getFoodName());
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");

                a.getButtonTypes().setAll(yesButton, noButton);

                a.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == yesButton) {
                        Objpacked objPacked = new Objpacked();
                        objPacked.setName1("lowerfoodcount");
                        objPacked.setName2(resName);
                        List<Food> selectedFoodList = new ArrayList<>();
                        selectedFoodList.add(selectedFood);
                        objPacked.setFoodList(selectedFoodList);

                        tempFood.remove(selectedFood);
                        for (Food f : confirmedFoods) {
                            if (f.getFoodName().equals(selectedFood.getFoodName())) {
                                f.setOrderedCount(f.getOrderedCount() + selectedFood.getOrderedCount());
                            }
                        }
                        // System.out.println("bring it here");
                        for (Food f : mainFoodList) {
                            if (f.getFoodName().equals(selectedFood.getFoodName())) {
                                f.setOrderedCount(f.getOrderedCount() - selectedFood.getOrderedCount());
                            }
                        }

                        try {
                            main.getSocketWrapper().write(objPacked);
                        } catch (IOException e) {
                           // System.out.println("prob");
                            e.printStackTrace();
                        }

                        products = FXCollections.observableArrayList(tempFood);
                        table.setItems(products);
                    } else if (buttonType == noButton) {
                        tempFood.remove(selectedFood);
                        System.out.println("User clicked No.");
                        products = FXCollections.observableArrayList(tempFood);
                        table.setItems(products);

                    }

                });

            }
        });

    }

    @FXML
    private Label Cost;
    @FXML
    private TableView<Food> confirmedTable;
    @FXML
    private TableColumn<Food, Integer> foodAmount1;

    @FXML
    private TableColumn<Food, String> foodCategoryColumn1;
    @FXML
    private TableColumn<Food, String> foodNameColumn1;
    @FXML
    private AnchorPane confirmed;
    @FXML
    private TableColumn<Food, Double> confirmedFoodPrice;

    List<Food> tempconfirmedFoods;

    @FXML
    void confirmedFoodtable() {
        confirmed.setVisible(true);
        resHome.setVisible(false);
        ResMenu.setVisible(false);
        AddFoodList.setVisible(false);
        PendingReqList.setVisible(false);
        HomeButton.setStyle("-fx-background-color:#c0c0c0 ");
        MenuButton.setStyle("-fx-background-color:#c0c0c0 ");
        PendingButton.setStyle("-fx-background-color:#c0c0c0 ");
        AddFoodButton.setStyle("-fx-background-color:#c0c0c0 ");
        ConfirmedButton.setStyle("-fx-background-color:#a5bfe8 ");
        tempconfirmedFoods.clear();
        // System.out.println("temp confirmed foods");
        confirmedTable.refresh();
        for (Food f : confirmedFoods) {
            if (f.getOrderedCount() > 0) {
                tempconfirmedFoods.add(f);
                totalEarned += f.getOrderedCount() * f.getFoodPrice();
                // System.out.println(f.getFoodName()+" "+f.getOrderedCount());
            }
        }

        ObservableList<Food> products2 = FXCollections.observableArrayList(tempconfirmedFoods);
        foodNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));
        foodCategoryColumn1
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodCategory()));
        foodAmount1.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderedCount()).asObject());
        confirmedFoodPrice.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getFoodPrice()).asObject());

        confirmedTable.setItems(products2);
        DecimalFormat df = new DecimalFormat("#.00");

        // Format the double value
        String formattedNumber = df.format(totalEarned);

        Cost.setText(formattedNumber + "$");

    }

    @FXML
    void signOut(ActionEvent event) throws Exception {
        main.closeConnection();
    }

    void setMain(Main main) {
        this.main = main;
    }

}
