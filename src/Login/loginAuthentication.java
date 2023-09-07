package Login;

import java.util.Scanner;
        
public class loginAuthentication {
    
    String username, password;
    String filePath;
    
    public void loginDisplay(){
        
        System.out.println("=======================================================");
        System.out.println("User Login Interface");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("=======================================================");
    }
    
    public void loginAuthentication(){
        Scanner sc = new Scanner(System.in);
        
        username = sc.nextLine();      
        password = sc.nextLine();
        
        
    }
        
}
