package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import server.ReadThreadClient;
import server.SocketWrapper;

import java.io.IOException;
import java.util.List;

import data.Food;
import data.Restaurant;

public class Main extends Application {

    private Stage stage;
    private SocketWrapper socketWrapper;
    private AdminController adminController;
    public ResController resController;
    public UserController userController;

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            connectToServer();
            showAdminPage();
        } catch (Exception e) {
            System.out.println("Cant Load Start of Main");
        }
    }

    public void showAdminPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("admin.fxml"));
        Parent root = loader.load();
        adminController = loader.getController();
        adminController.setMain(this);
        stage.setTitle("Admin");
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
    }

    public void showResLoginPage() throws Exception {
        resController = new ResController();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("loginRes.fxml"));
        Parent root = loader.load();
        resController = loader.getController();
        resController.setMain(this);
        stage.setTitle("Restaurant Login");
        stage.setScene(new Scene(root, 415, 300));
        stage.show();
    }

    public void showResHomePage(Restaurant r) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyRestaurant.fxml"));
        Parent root = loader.load();
        resController = loader.getController();
        resController.setResHome();
        resController.setRestaurantReference(r);
        resController.setAllNames(r);
        socketWrapper.write("pendinglist," + r.getRestaurantName()); // writing
        String resName = r.getRestaurantName();
        resController.setMain(this);
        stage.setTitle("Restaurant Home Page: " + resName);
        stage.setScene(new Scene(root, 650, 500));
        stage.show();

    }

    public void showUserLoginPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("loginUser.fxml"));
        Parent root = loader.load();
        userController = loader.getController();
        userController.setMain(this);
        stage.setTitle("User Login");
        stage.setScene(new Scene(root, 380, 240));
        stage.show();
    }

    public void showUserHomePage(String username) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MyUser.fxml"));
        Parent root = loader.load();
        userController = loader.getController();
        userController.setMain(this);
        userController.setUserName(username);
        userController.userHomePage.setVisible(true);

        userController.cartPage.setVisible(false);
        stage.setTitle("User Home Page: " + username);
        stage.setScene(new Scene(root, 760, 573));
        stage.show();

    }

    public void updateuserfood(List<Food> foodlist) {
        userController.setFoodList(foodlist);
        // userController.showFoodTable();

    }

    public void updateuserResDetails(List<Restaurant> reslist) {
        userController.setResDetailsinTable(reslist);
        userController.showResDetailsinTable();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials!");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public void showAddRestaurantAlert1() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Restaurant");
        alert.setHeaderText("Successfully added restaurant!");
        alert.showAndWait();
    }

    public void showAddRestaurantAlert2() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Restaurant");
        alert.setHeaderText("Restaurant already exists!Please enter Another Name.");
        alert.showAndWait();

    }

    public void showFoodAddAlert1() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Food Added");
        alert.setHeaderText(null);
        alert.setContentText("Food Added Successfully");
        alert.showAndWait();
        resController.setAllFoodNames();
    }

    public void showFoodAddAlert2() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Food Added");
        alert.setHeaderText(null);
        alert.setContentText("Food Already Exists");
        alert.showAndWait();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 40000;
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
        new ReadThreadClient(this, this.getSocketWrapper());
    }

    public Stage getStage() {
        return stage;
    }

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    public void closeConnection() {
        try {
            socketWrapper.closeConnection();
            stage.close();
        } catch (Exception e) {
            System.out.println("Cant close connection");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
