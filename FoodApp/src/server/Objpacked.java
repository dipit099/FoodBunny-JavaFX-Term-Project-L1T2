package server;
import java.io.Serializable;
import java.util.List;

import data.Food;
import data.Restaurant;
public class Objpacked implements Serializable
{
    List<Food> foodListObj;
    List<Restaurant> restaurantListObj;
    List<Food> cartFoods;
    String name1;
    String name2;
    String customerName;
    Integer orderNumber;

    public Objpacked()
    {
        this.name1 = "";
        this.name2 = "";
        customerName = "";
    }
    public void setFoodList(List<Food> foodList)
    {
        this.foodListObj = foodList;
    }
    void setRestaurantList(List<Restaurant> restaurantList)
    {
        this.restaurantListObj = restaurantList;
    }
    public void setCartFoods(List<Food> cartFoods)
    {
        this.cartFoods = cartFoods;
    }
    public void setName1(String name1)
    {
        this.name1 = name1;
    }
    public void setName2(String name2)
    {
        this.name2 = name2;
    }
    List<Food> getFoodList()
    {
        return this.foodListObj;
    }
    List<Food> getCartFoods()
    {
        return this.cartFoods;
    }
    List<Restaurant> getRestaurantList()
    {
        return restaurantListObj;
    }
    String getName1()
    {
        return name1;
    }
    String getName2()
    {
        return name2;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public void setOrderNumber(Integer orderNumber)
    {
        this.orderNumber = orderNumber;
    }
    public Integer getOrderNumber()
    {
        return orderNumber;
    }


   
}