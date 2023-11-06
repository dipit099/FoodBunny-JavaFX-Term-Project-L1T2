package server;

import sample.Main;
import java.io.IOException;
import java.util.List;

import data.Food;
import data.Restaurant;
import javafx.application.Platform;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    private Main main;

    public ReadThreadClient(Main main, SocketWrapper socketWrapper) {
        this.main = main;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    Object o = socketWrapper.read();

                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {

                                    if (o instanceof String) {
                                        String message = (String) o;
                                        String[] parts = message.split(",");

                                        if (parts[0].equals("passwordnotmatched")) {
                                            main.showAlert();
                                        } else if (parts[0].equals("userlogin")) {
                                            try {
                                                main.showUserHomePage(parts[1]);
                                            } catch (Exception e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        } else if (parts[0].equals("restaurantadded")) {
                                            main.showAddRestaurantAlert1();
                                        } else if (parts[0].equals("restaurantnotadded")) {
                                            main.showAddRestaurantAlert2();
                                        }

                                    } else if (o instanceof Restaurant) {
                                        Restaurant restaurant = (Restaurant) o;

                                        try {
                                            main.showResHomePage(restaurant);
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    }

                            else if (o instanceof List) {
                                        List<Food> tempfoodList = (List<Food>) o;

                                        try {

                                            // System.out.println("trying in ReadThreadClient updating foodlist");
                                            main.updateuserfood(tempfoodList);
                                        } catch (Exception e) {
                                            System.out.println("Cant show the FoodListPage");
                                        }

                                    }

                            else if (o instanceof Objpacked) {
                                        Objpacked objpacked = (Objpacked) o;

                                        String message = objpacked.getName1();
                                        if (message.equals("restaurantpendingfoods")) {
                                            // List<Food> tempfoodList = objpacked.getFoodList();

                                            List<Food> cartFoods = objpacked.getCartFoods();

                                            //System.out.println("cartfoods");
                                            for (Food f : cartFoods) {
                                                System.out.println(f.getFoodName() + " " + f.getOrderedCount());

                                            }

                                            try {
                                                main.resController.showleftRequest(cartFoods);
                                            } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }

                            else if (message.equals("pendinglistfrombutton")) {
                                            try {

                                                List<Food> tempfoodList = objpacked.getFoodList();
                                                //System.out.println("pendinglistfrombutton");
                                                for (Food f : tempfoodList) {
                                                    if (f.getOrderedCount() > 0) {
                                                        System.out.println(f.getFoodName() + " " + f.getOrderedCount());
                                                    }
                                                }
                                                // main.updateResPendingFoodList(tempfoodList);
                                                main.resController.setMainFoodList(tempfoodList);

                                            } catch (Exception e) {
                                                System.out.println("Cant show the FoodListPage in ResHomePage");
                                                System.out.println(e);
                                            }

                                        }

                            else if (message.equals("user")) {
                                            if (objpacked.getName2().equals("searchResController")) {
                                                try {

                                                    main.updateuserResDetails(objpacked.getRestaurantList());

                                                } catch (Exception e) {
                                                    System.out.println("Cant show the ResListPage in UserPage");
                                                }

                                            }
                                        } else if (message.equals("addfood")) {
                                            main.showFoodAddAlert1();
                                            List<Food> tempfoodList = objpacked.getFoodList();
                                            System.out.println(tempfoodList.size());
                                            try {
                                                main.resController.setMainFoodList(tempfoodList);
                                                //System.out.println("size"+tempfoodList.size());
                                            } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                            main.resController.setAllFoodNames();

                                        } else if (message.equals("addnofood")) {
                                            main.showFoodAddAlert2();
                                        }
                                    }

                                }

                            });
                }
            }
        }

        catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
