package data;
import java.util.ArrayList;
import java.util.List;

public class Database implements java.io.Serializable {

    Database db;
    void setdatabase(Database db){
        this.db=db;
    }

    public List<Restaurant> restaurantList = new ArrayList<>();
    List<String> Categoryarray = new ArrayList<>();
    List<Food> fullfoodList = new ArrayList<>();

    public boolean addRestaurant(Restaurant restaurantObject, int resId) {
        for (Restaurant r : restaurantList) {
            if (restaurantObject.getRestaurantName().equalsIgnoreCase(r.getRestaurantName())) {
                return false;
            }
        }
        restaurantObject.setRestaurantId(resId);
        restaurantList.add(restaurantObject);
        CategoryList(restaurantObject);

        return true;
    }

    int findIdOfRestaurant(Restaurant r) {
        for (Restaurant rr : restaurantList) {
            if (rr.getRestaurantName().equalsIgnoreCase(r.getRestaurantName())) {
                return rr.getRestaurantId();
            }
        }
        return -1;
    }

    // from file reading
    boolean addFood(Food f) {
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantId() == f.getRestaurantId()) {
                // System.out.println("f.getRestaurantId() " + f.getRestaurantId());
                if (r.addFood(f, r.getRestaurantName(), r.getRestaurantId())){
                    fullfoodList.add(f);
                    return true;
                }
                else {
                    // System.out.println("didnt found");
                    return false;

                }

            }
        }
        // System.out.println(restaurantList.size());
        // System.out.println("gotcha");
        return false;

    }

    public boolean addFood(Food f, String resName) {
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().equalsIgnoreCase(resName)) {
                if (r.addFood(f, r.getRestaurantName(), r.getRestaurantId()))
                {
                     fullfoodList.add(f);
                    return true;
                }
                   
                else
                    return false;
            }
        }
        return false;

    }

    public List<Restaurant> findByRestaurantName(String name) {
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        name = name.toLowerCase();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().toLowerCase().contains(name)) {
                // r.print();
                arrayRestaurants.add(r);
            }
        }
        return arrayRestaurants;
    }

     public List<Restaurant> findByRestaurantScore(double a, double b) {
        List<Restaurant> arrayRestaurants = new ArrayList<>();

        for (Restaurant r : restaurantList) {
            double temp = r.getRestaurantScore();
            if (temp >= a && temp <= b) {
                arrayRestaurants.add(r);
            }
        }
        return arrayRestaurants;
    }

    public List<Restaurant> findByRestaurantPrice(String price) {
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantPrice().equals(price)) {
                arrayRestaurants.add(r);
                // r.print();
            }
        }
        return arrayRestaurants;
    }

    public List<Restaurant> findByZipCode(String zipCode) {
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getZipCode().equals(zipCode)) {
                arrayRestaurants.add(r);
                // r.print();
            }
        }
        return arrayRestaurants;
    }

    public List<Restaurant> findByRestaurantCategory(String categoryName) {
        categoryName = categoryName.toLowerCase();
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            int n = r.getCategoryLength();
            String[] cat = new String[n];
            cat = r.getRestaurantCategory();
            for (int j = 0; j < n; j++) {
                String s = cat[j].toLowerCase();
                if (s.contains(categoryName)) {
                    arrayRestaurants.add(r);
                }
            }

        }
        return arrayRestaurants;
    }

    void printCategorywiseRestaurants() {

        for (String s : Categoryarray) {

            System.out.print(s + ": ");
            List<Restaurant> arrayRestaurantss = new ArrayList<>();

            arrayRestaurantss = findByRestaurantCategory(s);
            for (int i = 0; i < arrayRestaurantss.size(); i++) {
                Restaurant rrr = arrayRestaurantss.get(i);

                if (i == arrayRestaurantss.size() - 1)
                    System.out.print(rrr.getRestaurantName());
                else
                    System.out.print(rrr.getRestaurantName() + ", ");
            }
            System.out.println();
        }
        return;
    }

    public List<Food> findByFoodNameAndRestaurantName(String foodName, String resName) {
        List<Food> arrayFood = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().equalsIgnoreCase(resName)) {
                arrayFood = r.findByFoodName(foodName);
            }
        }
        return arrayFood;
    }

    public List<Food> findByFoodCategoryAndRestaurantName(String foodCategory, String resName) {
        List<Food> arrayFood = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().equalsIgnoreCase(resName)) {
                arrayFood = r.findByFoodCategory(foodCategory);
            }
        }
        return arrayFood;

    }

    public List<Food> findByFoodPriceAndRestaurantName(double a, double b, String resName) {
        List<Food> arrayFood = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().equalsIgnoreCase(resName)) {               
                arrayFood=r.findByFoodPrice(a, b);
            }

        }
        return arrayFood;

    }

    public List<Food> findByCostliestFood(String resName) {
        List<Food> arrayFood = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().equalsIgnoreCase(resName)) {
                arrayFood = r.findCostliestFood();
                break;
            }

        }
        return arrayFood;

    }

    public void printAllRestaurants() {
        for (Restaurant r : restaurantList) {
            r.print();
        }
    }

    public void printAllFoods() {
        for (Restaurant r : restaurantList) {
            r.printAllFoods();
        }
    }

    void CategoryList(Restaurant restaurantObject) {

        int n = restaurantObject.getCategoryLength();
        String[] cat = new String[n];
        cat = restaurantObject.getRestaurantCategory();

        for (int i = 0; i < n; i++) {
            int check = 1;
            for (String s : Categoryarray) {

                if (s.equalsIgnoreCase(cat[i])) {

                    check = 0;
                    break;
                }

            }
            if (check == 1) {
                Categoryarray.add(cat[i]);

            }
        }
    }
    public List<Food> getWholeFoodList()
    {
        return fullfoodList;
    }
    public List<Food> getOneFoodList(String resName) {
        List<Food> arrayFood = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getRestaurantName().equalsIgnoreCase(resName)) {
                arrayFood = r.getFoodList();
            }
        }
        return arrayFood;
    }
}
