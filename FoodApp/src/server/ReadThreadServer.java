package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import data.Database;
import data.Food;
import data.Restaurant;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    public static ConcurrentHashMap<String, SocketWrapper> userMap = new ConcurrentHashMap<String, SocketWrapper>();
    public static ConcurrentHashMap<String, SocketWrapper> customerMap = new ConcurrentHashMap<String, SocketWrapper>();

    public volatile ConcurrentHashMap<String, List<Food>> pendingListMap;
    public ConcurrentHashMap<String, String> passwordMap;
    public Database db;

    public ReadThreadServer(SocketWrapper socketWrapper, Database dbtemp, ConcurrentHashMap<String, String> passwordMap,
            ConcurrentHashMap<String, List<Food>> pendingMap) {
        this.passwordMap = passwordMap;
        this.pendingListMap = pendingMap;
        this.socketWrapper = socketWrapper;
        this.db = dbtemp;
        this.thr = new Thread(this);
        thr.start();
    }

   

    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    Object o = socketWrapper.read();
                    if (o != null) {
                        if (o instanceof String) {
                            String message = (String) o;
                            String[] parts = message.split(",", 0);

                            if (parts[0].equals("password")) {
                                String userName = parts[1];
                                String password = parts[2];

                                String actualpassword = passwordMap.get(userName);

                                if ((password.equals(actualpassword))) {
                                    List<Restaurant> arrayRestaurants = db.findByRestaurantName(userName);

                                    userMap.put(userName, socketWrapper);
                                    socketWrapper.write(arrayRestaurants.get(0));
                                } else {

                                    socketWrapper.write("passwordnotmatched");

                                }
                            } else if (parts[0].equals("userlogin")) {
                                // System.out.println("Size of res UserMap " + userMap.size());
                                customerMap.put(parts[1], socketWrapper);
                                socketWrapper.write("userlogin," + parts[1]); // writing the name of user
                            }

                            else if (parts[0].equals("addrestaurant")) {
                                addRestaurant();
                            } else if (parts[0].equals("foodlist")) {

                                if (parts[1].equals("name")) {
                                    String foodName = parts[2];
                                    String resName = parts[3];
                                    List<Food> foodDetails = db.findByFoodNameAndRestaurantName(foodName, resName);
                                    socketWrapper.write(foodDetails);
                                } else if (parts[1].equals("price")) {
                                    Double price1 = Double.parseDouble(parts[2]);
                                    Double price2 = Double.parseDouble(parts[3]);
                                    String resName = parts[4];
                                    List<Food> foodDetails = db.findByFoodPriceAndRestaurantName(price1, price2,
                                            resName);
                                    socketWrapper.write(foodDetails);
                                }

                                else if (parts[1].equals("category")) {
                                    String category = parts[2];
                                    String resName = parts[3];
                                    List<Food> foodDetails = db.findByFoodCategoryAndRestaurantName(category, resName);
                                    socketWrapper.write(foodDetails);
                                } else if (parts[1].equals("costliest")) {
                                    String resName = parts[2];
                                    resName = resName.toLowerCase();
                                    List<Food> foodDetails = db.findByCostliestFood(resName);
                                    // System.out.println("in server "+ foodDetails.size());
                                    socketWrapper.write(foodDetails);

                                } else // show food of selected Restaurant in user
                                {
                                    String resName = parts[1];
                                    resName = resName.toLowerCase();
                                    // System.out.println("in showing food of selected res"+ resName);
                                    List<Food> foodDetails = db.findByRestaurantName(resName).get(0).getFoodList();
                                    // System.out.println("in server "+ foodDetails.size());
                                    socketWrapper.write(foodDetails);
                                }

                            } else if (parts[0].equals("restaurantlist")) {
                                if (parts[1].equals("full")) {
                                    List<Restaurant> tempRestaurants = db.restaurantList;
                                    Objpacked objpacked = new Objpacked();
                                    objpacked.setRestaurantList(tempRestaurants);
                                    objpacked.setName1("user");
                                    objpacked.setName2("searchResController");
                                    socketWrapper.write(objpacked);
                                } else if (parts[1].equals("name")) {
                                    String resName = parts[2];
                                    List<Restaurant> restaurantDetails = db.findByRestaurantName(resName);
                                    Objpacked objpacked = new Objpacked();
                                    objpacked.setRestaurantList(restaurantDetails);
                                    objpacked.setName1("user");
                                    objpacked.setName2("searchResController");
                                    socketWrapper.write(objpacked);
                                } else if (parts[1].equals("price")) {
                                    String price = parts[2];
                                    List<Restaurant> restaurantDetails = db.findByRestaurantPrice(price);
                                    Objpacked objpacked = new Objpacked();
                                    objpacked.setRestaurantList(restaurantDetails);
                                    objpacked.setName1("user");
                                    objpacked.setName2("searchResController");
                                    socketWrapper.write(objpacked);
                                } else if (parts[1].equals("score")) {
                                    double a = Double.parseDouble(parts[2]);
                                    double b = Double.parseDouble(parts[3]);
                                    List<Restaurant> restaurantDetails = db.findByRestaurantScore(a, b);
                                    Objpacked objpacked = new Objpacked();
                                    objpacked.setRestaurantList(restaurantDetails);
                                    objpacked.setName1("user");
                                    objpacked.setName2("searchResController");
                                    socketWrapper.write(objpacked);
                                } else if (parts[1].equals("zipcode")) {
                                    String zipCode = parts[2];
                                    List<Restaurant> restaurantDetails = db.findByZipCode(zipCode);
                                    Objpacked objpacked = new Objpacked();
                                    objpacked.setRestaurantList(restaurantDetails);
                                    objpacked.setName1("user");
                                    objpacked.setName2("searchResController");
                                    socketWrapper.write(objpacked);
                                } else if (parts[1].equals("category")) {
                                    String category = parts[2];
                                    List<Restaurant> restaurantDetails = db.findByRestaurantCategory(category);
                                    Objpacked objpacked = new Objpacked();
                                    objpacked.setRestaurantList(restaurantDetails);
                                    objpacked.setName1("user");
                                    objpacked.setName2("searchResController");
                                    socketWrapper.write(objpacked);
                                }
                            } else if (parts[0].equals("pendinglist")) // TODO
                            {
                                String resName = parts[1];
                                resName = resName.toLowerCase();
                                List<Food> foodDetails = pendingListMap.get(resName);
                                // System.out.println("getting from map for "+resName);

                                Objpacked objpacked = new Objpacked();
                                objpacked.setFoodList(foodDetails);
                                objpacked.setName1("pendinglistfrombutton");
                                socketWrapper.write(objpacked);

                            }

                        }

                        else if (o instanceof Objpacked) {
                            Objpacked objpacked = (Objpacked) o;
                            String name1 = objpacked.getName1();
                            if (name1.equals("user")) {
                                String name2 = objpacked.getName2().toLowerCase();
                                List<Food> cartFoods = objpacked.getCartFoods();
                                List<Food> mapFoodList = pendingListMap.get(name2);                               
                                for (Food f : cartFoods) {
                                    for (Food food : mapFoodList) {
                                        if (f.getFoodName().equals(food.getFoodName())) {
                                            food.setOrderedCount(food.getOrderedCount() + f.getOrderedCount());                                            
                                        }
                                    }
                                }

                                System.out.println("Updated food list");
                                SocketWrapper tempSocketWrapper = userMap.get(name2);
                                objpacked.setName1("restaurantpendingfoods");

                                if (tempSocketWrapper == null) {
                                    System.out.println("null TEMPSOCKETWRAPPER");
                                } else {
                                    tempSocketWrapper.write(objpacked);
                                    // System.out.println("order sent from user to restaurant");
                                }

                            } else if (name1.equals("lowerfoodcount")) {
                                String resName = objpacked.getName2();
                                resName = resName.toLowerCase();
                                List<Food> tempFoodList = objpacked.getFoodList(); // one food present only
                                // System.out.println(tempFoodList.size());
                                for (Food f : tempFoodList) {
                                    for (Food food : pendingListMap.get(resName)) {
                                        if (f.getFoodName().equals(food.getFoodName())) {
                                            food.setOrderedCount(food.getOrderedCount() - f.getOrderedCount());
                                            System.out.println("lowering food count " + food.getFoodName());

                                        }
                                    }
                                }
                                // SocketWrapper tempSocketWrapper = customerMap.get(resName);
                                // pendingListMap.put(resName, mapFoodList);
                            } else if (name1.equals("addfood")) {
                                String resName = objpacked.getName2();
                                resName = resName.toLowerCase();

                                List<Food> mapFoodList = pendingListMap.get(resName);
                                System.out.println("size"+mapFoodList.size());
                                List<Food> tempFoodList = objpacked.getFoodList();
                                Food f = tempFoodList.get(0);
                                if (db.addFood(f, resName) == true) {
                                   // System.out.println("size"+mapFoodList.size());
                                     // mapFoodList.addAll(objpacked.getFoodList());
                                     System.out.println(mapFoodList.size());
                                    Objpacked objpacked2 = new Objpacked();
                                    objpacked2.setName1("addfood");                                    
                                    objpacked2.setFoodList(mapFoodList);

                                    socketWrapper.write(objpacked2);
                                } else {
                                    Objpacked objpacked2 = new Objpacked();
                                    objpacked2.setName1("addnofood");
                                    objpacked2.setFoodList(mapFoodList);
                                    socketWrapper.write(objpacked2);
                                }

                            }

                        }

                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
     synchronized void addRestaurant() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        passwordMap.put(username, password);
        System.out.println();

        String resName = username;
        resName = resName.substring(0, 1).toUpperCase() + resName.substring(1).toLowerCase();
        System.out.println("Enter Restaurant Name:  " + resName);
        System.out.print("Enter Restaurant Score:  ");
        double score = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Restaurant Price:($ or $$ or $$$):  ");
        String price = sc.nextLine();
        System.out.print("Enter Restaurant Zip Code:  ");
        String zipCode = sc.nextLine();

        int categoryCount;
        do {
            System.out.print("Enter Restaurant Category Numbers(1-3):  ");
            categoryCount = sc.nextInt();
        } while (categoryCount < 1 || categoryCount > 3);

        sc.nextLine();
        System.out.println("Enter Restaurant Category name:  ");
        String[] res_category = new String[categoryCount];
        for (int i = 0; i < categoryCount; i++) {
            res_category[i] = sc.nextLine();
        }
        Restaurant r = new Restaurant(resName, score, price, zipCode, res_category);
        System.out.println();
        try {
            if (db.addRestaurant(r, db.restaurantList.size() + 1)) {
                System.out.println("Restaurant added successfully!");
                pendingListMap.put(resName, new ArrayList<Food>());
                socketWrapper.write("restaurantadded");
            } else {
                System.out.println("Restaurant Name Already Exists!");
                socketWrapper.write("restaurantnotadded");

            }
        } catch (Exception e) {
            System.out.println("Cant add restaurant");
            System.out.println(e);
        }
        sc.close();

    }

}
