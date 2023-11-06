package data;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements java.io.Serializable {

    private int id;
    private String name;
    private double score;
    private String price;
    private String zipCode;
    private String[] category;
    List<Food> foodList = new ArrayList<>();

    public Restaurant(String name, double score, String price, String zipCode, String[] category) {

        
        this.name = name;

        this.score = score;
        this.price = price;
        this.zipCode = zipCode;
        int size = category.length;
        this.category = new String[size];

        this.category = category;

    }

    public String getRestaurantName() {
        return name;
    }

    public int getRestaurantId() {
        return id;
    }

    public double getRestaurantScore() {
        return score;
    }

    public String getRestaurantPrice() {
        return price;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String[] getRestaurantCategory() {
        return category;
    }

    public String getnewResCategory()
    {
        String s = category[0];
        for(int i=1;i<category.length;i++)
        {
            s = s + "," + category[i];
        }
        return s;
    }

    int getCategoryLength() {
        return category.length;
    }

    void setRestaurantId(int id) {
        this.id = id;
    }

    void print() {
        System.out.println();
        System.out.println("Restaurant Details__ ");
        System.out.println("Restaurant ID: " + id);
        System.out.println("Restaurant Name: " + name);
        System.out.println("Restaurant Score: " + score);
        System.out.println("Restaurant Price: " + price);
        System.out.println("Restaurant Zip Code: " + zipCode);
        System.out.print("Restaurant Category: " + category[0]);
        for (int j = 1; j < category.length; j++) {
            System.out.print(", " + category[j]);
        }

        System.out.println();
    }

    public boolean addFood(Food food, String resName, int resId) {
        food.setRestaurantName(resName);
        food.setRestaurantId(resId);
        for (Food f : foodList) {
            if (food.equals(f)) {
                return false;
            }
        }

        foodList.add(food);       
        return true;
    }

    public List<Food> findByFoodName(String name) {
        name = name.toLowerCase();
        name= name.trim();
        List<Food> arrayFood = new ArrayList<>();
        for (Food f : foodList) {
            String foodName = f.getFoodName().toLowerCase();

            if (foodName.contains(name)) {
                arrayFood.add(f);
            }
        }
        return arrayFood;
    }



    List<Food> findByFoodCategory(String categoryName) {
        categoryName = categoryName.toLowerCase();
        List<Food> arrayFood = new ArrayList<>();
        for (Food f : foodList) {
            String s = f.getFoodCategory().toLowerCase();
            if (s.contains(categoryName)) {
                arrayFood.add(f);

            }
        }
        return arrayFood;
    }

    List<Food> findByFoodPrice(double a, double b) {
        List<Food> arrayFood = new ArrayList<>();
        for (Food f : foodList) {
            if (f.getFoodPrice() >= a && f.getFoodPrice() <= b) {
                arrayFood.add(f);
            }
        }
        return arrayFood;
    }

    List<Food> findCostliestFood() {
        List<Food> arrayFood = new ArrayList<>();
        double cost = -1;
        for (Food f : foodList) {
            if (f.getFoodPrice() >= cost) {
                cost = f.getFoodPrice();

            }
        }
       
        for (Food f : foodList) {
            if (f.getFoodPrice() == cost) {
                arrayFood.add(f);
            }
        }
        return arrayFood;

    }

    int displayNoOfFoods() {
        return foodList.size();

    }

    void printAllFoods() {
        for (Food f : foodList) {
            f.printFood();
        }
    }

   public List<Food> getFoodList()
   {
            return foodList;
   }




}
