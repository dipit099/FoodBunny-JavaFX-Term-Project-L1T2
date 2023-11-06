package data;
import java.io.BufferedReader;
import java.io.FileReader;


public class FileOperations {
    //TODO specify File paths
   private static final String INPUT_FILE_NAME_1 = "C:\\Users\\UseR\\OneDrive - BUET\\Coding 1-1\\FoodApp\\FoodApp\\src\\restaurant.txt";
   private static final String INPUT_FILE_NAME_2 = "C:\\Users\\UseR\\OneDrive - BUET\\Coding 1-1\\FoodApp\\FoodApp\\src\\menu.txt";

  //private static final String INPUT_FILE_NAME_1 = "C:\\Users\\Dipit\\OneDrive - BUET\\nn\\FoodApp\\FoodApp\\src\\restaurant.txt";   
  //  private static final String INPUT_FILE_NAME_2 = "C:\\Users\\Dipit\\OneDrive - BUET\\nn\\FoodApp\\FoodApp\\src\\menu.txt";
    
    
    //private static final String OUTPUT_FILE_NAME_1 = "restaurant.txt";
    //private static final String OUTPUT_FILE_NAME_2 = "menu.txt";

    public void readFileRes(Database db) throws Exception {
        // Database db = new Database();
        BufferedReader resFile = new BufferedReader(new FileReader(INPUT_FILE_NAME_1));
        while (true) {
            String line = resFile.readLine();
            if (line == null)
                break;
            String[] array = line.split(",", 0);
            // System.out.println(array.length);
            int size = array.length-5;
            String[] cat = new String[size];
            for (int i = 0; i < size; i++) {
                cat[i] = array[i + 5];
            }          
                Restaurant r = new Restaurant(array[1], Double.parseDouble(array[2]), array[3], array[4], cat);
                db.addRestaurant(r, Integer.parseInt(array[0]));            
            
        }
        resFile.close();
    
    }
    public void readFileFood(Database db) throws Exception
    {
        

        BufferedReader foodFile = new BufferedReader(new FileReader(INPUT_FILE_NAME_2));

        while (true) {
            String line = foodFile.readLine();
            if (line == null)
                break;
            // System.out.println("called");
            String[] array = line.split(",", 0);

            Food f = new Food(array[1], array[2], Double.parseDouble(array[3]));
            f.setRestaurantId(Integer.parseInt(array[0]));
            db.addFood(f);

        }
        foodFile.close();
    }
}

    // void writeFile(Database db) throws Exception {
    //     BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME_1));

    //     for (int i = 0; i < db.restaurantList.size(); i++) {
    //         int id = db.restaurantList.get(i).getRestaurantId();
    //         String idt = Integer.toString(id);

    //         bw.write(idt); // doesnt need "to string"
    //         bw.write("," + db.restaurantList.get(i).getRestaurantName());
    //         bw.write("," + db.restaurantList.get(i).getRestaurantScore());
    //         bw.write("," + db.restaurantList.get(i).getRestaurantPrice());
    //         bw.write("," + db.restaurantList.get(i).getZipCode());
    //         // int cat_number= db.restaurantList.get(i).getCategory().length;
    //         String[] arr = db.restaurantList.get(i).getRestaurantCategory();
    //         for (int j = 0; j < arr.length; j++) {
    //             if (arr[j] == null) {
    //                 break;
    //             } else
    //                 bw.write("," + arr[j]);
    //         }

    //         bw.write(System.lineSeparator());

    //     }
    //     bw.close(); // veryy important
    //     bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME_2));

    //     for (int i = 0; i < db.restaurantList.size(); i++) {

    //         for (int j = 0; j < db.restaurantList.get(i).foodList.size(); j++) {
    //             int id = db.restaurantList.get(i).foodList.get(j).getRestaurantId();
    //             String idt = Integer.toString(id);

    //             bw.write(idt); // doesnt need "to string"
    //             bw.write("," + db.restaurantList.get(i).foodList.get(j).getFoodCategory());
    //             bw.write("," + db.restaurantList.get(i).foodList.get(j).getFoodName());
    //             bw.write("," + db.restaurantList.get(i).foodList.get(j).getFoodPrice());
    //             bw.write(System.lineSeparator());
    //         }

    //     }
    //     bw.close(); // veryy important
    //     System.out.println();
    //     System.out.println("Program successfully closed!\n");
    // }
//}
