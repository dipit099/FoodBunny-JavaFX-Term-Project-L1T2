package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.Objpacked;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import data.Food;
import data.Restaurant;

public class UserController implements Serializable, Initializable {

    public UserController() {
        cartFoods = new ArrayList<>();
        // foodList= new ArrayList<>();
        cost = 0;

    }

    double cost;

    private Main main;
    // String userName;

    @FXML
    public AnchorPane userHomePage;
    @FXML
    public AnchorPane cartPage;

    private String foodSearchList[] = { "Search By Name", "Search by Category", "Search by Food Price",
            "Search by Costliest Foods" };
    @FXML
    public ComboBox<String> myFoodComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            myFoodComboBox.getItems().addAll(foodSearchList);
            myFoodComboBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
             //System.out.println("Cant load the combobox");
             //System.out.println(e);
        }
    }

    @FXML
    TextField userText;

    @FXML
    private Label userName;

    @FXML
    private Label resName;
    String customerName;

    @FXML
    void loginAction(ActionEvent event) {
        String userName = userText.getText();
        customerName = userName;

        String message = "userlogin" + "," + userName;

        try {
            main.getSocketWrapper().write(message);
        } catch (IOException e) {
            System.out.println("Cant send the name of user to server");
        }
    }

    @FXML
    void backtoAdmin(ActionEvent event) throws Exception {
        main.showAdminPage();
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }

    void setUserName(String Name) {
        String finalName = " Hello " + Name + "!";
        userName.setText(finalName);
    }

    @FXML
    void foodItemSearch(ActionEvent event) throws IOException {
        String selectedSearch = myFoodComboBox.getSelectionModel().getSelectedItem();
        String input = firstName.getText();
        firstName.setText(null);
        if (selectedSearch.equals("Search By Name")) {
            main.getSocketWrapper().write("foodlist,name," + input + "," + restaurantName);
        } else if (selectedSearch.equals("Search by Food Price")) {
            main.getSocketWrapper().write("foodlist,price," + input + "," + restaurantName);
        } else if (selectedSearch.equals("Search by Category")) {
            main.getSocketWrapper().write("foodlist,category," + input + "," + restaurantName);
        }

        else if (selectedSearch.equals("Search by Costliest Foods")) {
            main.getSocketWrapper().write("foodlist,costliest," + restaurantName);
        }
    }

    @FXML
    private TableColumn<Food, String> foodCategoryColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;

    @FXML
    private TableColumn<Food, Double> foodPriceColumn;
    @FXML
    private TableView<Food> table;

    @FXML
    private TableColumn<Food, Integer> foodAmountColumn;

    @FXML
    private TextField firstName;

    String restaurantName;

    public List<Food> foodList;

    void setFoodList(List<Food> fullfoodList) {
        restaurantName = fullfoodList.get(0).getFoodRestaurantName();

        this.foodList = new ArrayList<>();
        for (Food f : fullfoodList) {
            Food copiedFood = new Food(f.getFoodCategory(), f.getFoodName(), f.getFoodPrice());
            copiedFood.setOrderedCount(0);
            this.foodList.add(copiedFood);
        }
        resName.setText(restaurantName);
        // System.out.println(foodList.size());
        showFoodTable();
    }

    void showFoodTable() {

        // foodList=fullfoodList;
        ObservableList<Food> products = FXCollections.observableArrayList(foodList);

        foodCategoryColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodCategory()));
        foodNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));
        foodPriceColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getFoodPrice()).asObject());
        // foodResColumn.setCellValueFactory(cellData -> new
        // SimpleStringProperty(cellData.getValue().getFoodRestaurantName()));

        foodAmountColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderedCount()).asObject());
        // table.setEditable(true); // set the table editable to true
        table.setItems(products);
        table.refresh();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void viewCart(ActionEvent e) {
        userHomePage.setVisible(false);
        cartPage.setVisible(true);
    }

    @FXML
    void backtoUserPage(ActionEvent e) {
        userHomePage.setVisible(true);
        cartPage.setVisible(false);

    }

    UserController userController2;

    @FXML
    private TableColumn<Food, String> cartFoodCategoryColumn;

    @FXML
    private TableColumn<Food, String> cartFoodNameColumn;
    @FXML
    private TableColumn<Food, Double> cartFoodPriceColumn;
    @FXML
    private TableColumn<Food, String> cartFoodResColumn;
    @FXML
    private TableColumn<Food, Integer> cartFoodCountColumn;

    @FXML
    private TableView<Food> carttable;

    void showFoodsinCart() {

        String costString = Double.toString(cost);
        price.setText(costString);
        ObservableList<Food> cartProducts = FXCollections.observableArrayList(cartFoods);
        cartFoodCategoryColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodCategory()));
        cartFoodNameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));
        cartFoodPriceColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getFoodPrice()).asObject());
        cartFoodCountColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderedCount()).asObject());

        // carttable.setEditable(true);
        carttable.setItems(cartProducts);
        carttable.refresh();
        carttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    private List<Food> cartFoods;
    @FXML
    private Label price;

    @FXML
    void addToCart(ActionEvent event) throws IOException {
        cartFoods.clear();
        cost = 0;
        List<Food> selectedFoods = table.getSelectionModel().getSelectedItems();

        for (Food f : selectedFoods) {
            for (Food food : foodList) {
                if (f.getFoodName().equals(food.getFoodName())) {
                    food.setOrderedCount(food.getOrderedCount() + 1);
                }
            }
        }
        for (Food f : foodList) {
            if (f.getOrderedCount() > 0) {
                cartFoods.add(f);
                cost += f.getFoodPrice() * f.getOrderedCount();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added to Cart!");
        alert.setHeaderText("Selected Food Items have been added to cart.");
        showFoodsinCart();
        showFoodTable();

    }

    @FXML
    void deleteItem() {
        if (cartFoods.size() != 0) {
            // delete from cartFoods
            List<Food> selectedFoods = carttable.getSelectionModel().getSelectedItems();
            for (Food f : selectedFoods) {
                for (Food food : foodList) {
                    if (f.getFoodName().equals(food.getFoodName())) {
                        food.setOrderedCount(Math.max(f.getOrderedCount() - 1, 0));
                    }
                }
            }
            for (Food f : selectedFoods) {
                // f.setOrderedCount(f.getOrderedCount() - 1);
                if (f.getOrderedCount() == 0) {
                    cartFoods.remove(f);
                }
                cost -= f.getFoodPrice();
            }

            DecimalFormat df = new DecimalFormat("#.00");

            // Format the double value
            String formattedNumber = df.format(cost);

            price.setText(formattedNumber + "$");

            ObservableList<Food> cartProducts = FXCollections.observableArrayList(cartFoods);
            cartFoodCategoryColumn
                    .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodCategory()));
            cartFoodNameColumn
                    .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));
            cartFoodPriceColumn.setCellValueFactory(
                    cellData -> new SimpleDoubleProperty(cellData.getValue().getFoodPrice()).asObject());
            cartFoodCountColumn.setCellValueFactory(
                    cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderedCount()).asObject());
            carttable.setItems(cartProducts);
            carttable.refresh();
            carttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        }

    }

    @FXML
    void confirmOrder(ActionEvent e) throws IOException {
        try {
            // restaurantName = foodList.get(0).getFoodRestaurantName();
           // System.out.println("restaurant name " + restaurantName);
            // System.out.println(foodList.size());

            Objpacked objPacked = new Objpacked();
            objPacked.setName1("user");
            objPacked.setName2(restaurantName);

            //System.out.println("cart foods");

            List<Food> sentFoods = new ArrayList<>();
            for (Food originalFood : cartFoods) {
                Food copiedFood = new Food(originalFood.getFoodCategory(), originalFood.getFoodName(),
                        originalFood.getFoodPrice());
                copiedFood.setOrderedCount(originalFood.getOrderedCount());
                sentFoods.add(copiedFood);
            }

            // objPacked.setFoodList(sentFoods);
            objPacked.setCartFoods(sentFoods);
            objPacked.setCustomerName(customerName);
            main.getSocketWrapper().write(objPacked);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order");
            alert.setHeaderText("Order Confirmed!");
            alert.setContentText("Your order has been confirmed");
            alert.showAndWait();
            for (Food f : foodList) {
                f.setOrderedCount(0);
            }
            cost = 0;
            DecimalFormat df = new DecimalFormat("#.00");

            // Format the double value
            String formattedNumber = df.format(cost);

            price.setText(formattedNumber + "$");
            cartFoods.clear();

            showFoodsinCart();
        } catch (Exception e1) {
            //System.out.println("Found!");
            System.out.println(e1);
        }
    }

    void setResDetailsinTable(List<Restaurant> resDetails) {
        searchResController.setRestaurantList(resDetails);

    }

    SearchResController searchResController;

    @FXML
    void searchResButton(ActionEvent e) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SearchRestaurant.fxml"));
            Parent root = loader.load();

            searchResController = loader.getController();

            searchResController.setsearchResMain(main);
            main.getSocketWrapper().write("restaurantlist,full");
            Stage stage = new Stage();
            stage.setTitle("Search Restaurant");
            stage.setScene(new Scene(root, 650, 400));
            searchResController.setStage(stage);
            stage.show();

        } catch (Exception e1) {
            System.out.println("Cant show the search restaurant page");
            System.out.println(e1);
        }

    }

    void showResDetailsinTable() {

        try {
            searchResController.showResDetailsinTable();
        } catch (Exception e) {
            System.out.println("Cant show the restaurant details in table");
            System.out.println(e);
        }

    }

    @FXML
    void signOut(ActionEvent event) throws Exception {
        main.closeConnection();
    }

}
