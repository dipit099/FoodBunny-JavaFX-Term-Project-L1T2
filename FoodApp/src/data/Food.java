package data;
public class Food implements java.io.Serializable{

    private int restaurantId;
    private String category;
    private String name;
    private double price;
    private String resName;
    private int orderedcount;
    Food() {
    }

    public Food(String category, String name, double price) {

        this.category = category;
        this.name = name;
        this.price = price;
        orderedcount=0;
        // // DELETE THIS LATER
        // if(category.equals("A la Carte"))
        //     orderedcount=1;
    }

    void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    void setRestaurantName(String resName) {
        this.resName = resName;
    }

    int getRestaurantId() {
        return restaurantId;
    }

    public String getFoodCategory() {
        return category;
    }

    public String getFoodName() {
        return name;
    }

    public double getFoodPrice() {
        return price;
    }
    public int getOrderedCount()
    {
        return orderedcount;
    }
    public String getFoodRestaurantName() {
        return resName;
    }
    public void setOrderedCount(int ans)
    {        
        this.orderedcount = ans;
    }

    @Override
    public boolean equals(Object o) {        
        Food p = (Food) o;
        if(this.name.equalsIgnoreCase(p.name) && this.price == p.price && this.category.equalsIgnoreCase(p.category))
            return true;
        return false;
    }

    void printFood() {
        System.out.println();
        System.out.println("Food Details__ ");
        System.out.println("Food Name: " + name);
        System.out.println("Food Price: " + price);
        System.out.println("Food Category: " + category);
        System.out.println("Found in \"" + resName + "\" restaurant!");
        System.out.println();
    }

    public String getFoodDetails()
    {
        String s = "";
        s = s +  category+ "   ,  "  + name + "   ,  " + price + "   ,  " +  resName ;        
        return s;
    }

}
