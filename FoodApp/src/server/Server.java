package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import java.util.concurrent.ConcurrentHashMap;

import data.Database;
import data.FileOperations;
import data.Food;
public class Server {

    private ServerSocket serverSocket;   
    public ConcurrentHashMap<String, String> passwordMap;
    public volatile ConcurrentHashMap<String, List<Food>> pendingListMap ;
    Database db;
    

    Server() {
        System.out.println("Server starts running!");
        passwordMap = new ConcurrentHashMap<String, String>();
        pendingListMap = new ConcurrentHashMap<String, List<Food>>();
        db = new Database();              
        readData();
        readPassword();        
        try {
            serverSocket = new ServerSocket(40000);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server problem:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        System.out.println("Client Connected");        
        new ReadThreadServer(socketWrapper,db, passwordMap,pendingListMap);
    }

    void readPassword()
    {
       
        
       String filePath = "C:\\Users\\UseR\\OneDrive - BUET\\Coding 1-1\\FoodApp\\FoodApp\\src\\password.txt"; // Replace with the actual file path
       // String  filePath = "C:\\Users\\Dipit\\OneDrive - BUET\\nn\\FoodApp\\FoodApp\\src\\password.txt"; // Replace with the actual file path
        try {            
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {               
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();                    
                    passwordMap.put(key, value);
                    List<Food> pendingList = db.getOneFoodList(key); //got the list from passwordMap res names
                    pendingListMap.put(key, pendingList);       //key is the resName
                   
                    
                }
            }
           
            bufferedReader.close();
            fileReader.close();           
            // for (Map.Entry<String, String> entry : passwordMap.entrySet()) {
            //     System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            // }
       }  
       catch(IOException e)  
       {  
         System.out.println("cant find password file");  
       }
    }  
   
    void readData()
    {
        try{
        FileOperations fo = new FileOperations();
        fo.readFileRes(db);
        fo.readFileFood(db);
        //db.printAllRestaurants();
        }
        catch(Exception e)
        {
            System.out.println("cant find data file");
        }

    }

    public static void main(String[] args) {
        new Server();
    }
}
