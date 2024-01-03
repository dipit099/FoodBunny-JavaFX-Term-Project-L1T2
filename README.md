# FoodBunny - Java and JavaFX Food Delivery App

## Project Overview

FoodBunny is a food delivery application developed using Java for the backend and JavaFX for the graphical user interface (GUI). This project facilitates users to order food from restaurants while providing a platform for restaurants to manage their orders efficiently.

## Short Video Preview

Watch a short video preview of the FoodBunny app [here](https://youtu.be/ypb6dg9CDvA).

## Setup Instructions

1. Create a Java project in VS Code.
2. Replace the `src` folder with the provided one.
3. Add the "lib" folder of JDK-20 into the Reference Library of the Java project.<br/><br/>
   ![Reference Library Setup](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/310c651d-b50e-42c1-a2ff-64972bf551a4)
   
5. Add the following VM arguments in the configuration:
```bash
   vmArgs": "--module-path \"C:/javafx-sdk-20.0.2/lib\" --add-modules javafx.controls,javafx.fxml
```
   ![VM Arguments Setup](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/6c89ffb8-5e7c-4a3f-ab70-cdf7ab8799e0)

# How to Run

1. Run the `Server.java` file in the 'server' package.
2. Run the `Main.java` file in the 'sample' package.
3. Once the app is running, log in as a customer or a restaurant.
4. If logging in as a customer, you can order food from restaurants and track your orders.
5. If logging in as a restaurant, you can manage your orders.
6. Before running `Main.java`, ensure that `Server.java` is already running.

# Restaurant Manager Interface

# User Interface

# Fun Fact

If you want to run the app on two different devices, change the IP address in the `Main.java` file on the client system to your system's IP address (found using the `ipconfig` command in the command prompt). Run the server side on one system and the client side on another system, both connected to the same network.

Feel free to explore and contribute to this FoodBunny project!

---

*Note: Adjust paths and configurations based on your system environment.*
