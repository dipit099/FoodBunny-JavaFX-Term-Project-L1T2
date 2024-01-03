# FoodBunny - Java and JavaFX Food Delivery App

## Project Overview

FoodBunny is a food delivery application developed using Java for the backend and JavaFX for the graphical user interface (GUI). This project facilitates users to order food from restaurants while providing a platform for restaurants to manage their orders efficiently.

## Short Video Preview

Watch a short video preview of the FoodBunny app [here](https://youtu.be/ypb6dg9CDvA).

## Setup Instructions

1. Create a Java project in VS Code.
2. Download the full Project from Github.
3. Replace the `src` folder with the downloaded'src' folder.
4. Add the "lib" folder of JDK-20 into the Reference Library of the Java project.<br/><br/>
   ![Reference Library Setup](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/310c651d-b50e-42c1-a2ff-64972bf551a4)
   
5. Add the following VM arguments in the ```configuration``` of server and main also:
```bash
   "vmArgs": "--module-path \"C:/javafx-sdk-20.0.2/lib\" --add-modules javafx.controls,javafx.fxml"
```


   ![VM Arguments Setup](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/6c89ffb8-5e7c-4a3f-ab70-cdf7ab8799e0)


   

# How to Run

**Remember to Change the ```FILE PATH``` of password.txt, restaurant.txt and menu.txt . Check server.java, fileoperations.java for this.**

1. Run the `Server.java` file in the 'server' folder.
2. Run the `Main.java` file in the 'sample' folder.
3. Once the app is running, log in as a customer or a restaurant.
4. While login as customer, you can use any name and password.
5. While login as restaurant owner, please check the **password.txt** file for credentials.
6. If logging in as a customer, you can order food from restaurants and track your orders.
7. If logging in as a restaurant, you can manage your orders, accept or reject them .

 **Notice:** Before running `Main.java`, ensure that `Server.java` is already running.


<br/>

# User Interface

![Screenshot_3](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/6c49b0e8-ee0f-404e-b3f4-54bb35960571)

![Screenshot_8](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/598a824c-10c6-4f2b-b4ce-99afc8e32de4)

![Screenshot_13](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/de94abd3-59d3-4367-9545-6860cb35758a)

![Screenshot_7](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/054e0014-6c07-4c5c-a5f6-6adba2e5637a)

<br/>

# Restaurant Manager Interface

![Screenshot_4](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/e2d30230-2e42-4b69-8962-8364b0fdb4fe)

![Screenshot_5](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/5daceacf-6376-486f-af03-aab4cce71a80)

![Screenshot_6](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/48f15510-b0e5-4cf0-944a-ce1512ede3c7)

![Screenshot_11](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/c028bc08-39e0-40f1-91b9-068be175d8ea)

![Screenshot_10](https://github.com/dipit099/FoodBunny-JavaFX-Term-Project-L1T2/assets/112118531/efd30985-8a96-4b3c-9352-c6dd05249d2e)


# Fun Fact

If you want to run the app on two different devices, change the IP address in the `Main.java` file on the client system to your system's IP address (found using the `ipconfig` command in the command prompt). Run the server side on one system and the client side on another system, both connected to the same network.

Feel free to explore and contribute to this FoodBunny project!

---

*Note: Adjust paths and configurations based on your system environment.*
