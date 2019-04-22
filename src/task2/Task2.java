package task2;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


/**
 * @author chris
 */

public class Task2 {

 public static Statement stm = null;
 public static ResultSet result = null;
 public static Connection conn = null;
 public static String department = null;
 public static String pay_type = null;
 public static String education_Level = null;

    public static void main(String[] args) {
        
    // connection to the Database
    String username = "technical_test";
    String password = "HopefullyProspectiveDevsDontBreakMe";
    String hostname = "jdbc:mysql://mysql-technical-test.cq5i4y35n9gg.eu-west-1.rds.amazonaws.com:3306/foodmart";      
    Properties info = new Properties(); info.put("user", "technical_test"); info.put("password", "HopefullyProspectiveDevsDontBreakMe"); 
   
    try {
    conn = DriverManager.getConnection(hostname,info);
    
    
    Menu();
 
   
    } catch (SQLException ex) {
    // handle any errors
    System.out.println("Failed to connect");
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());
    System.out.println("VendorError: " + ex.getErrorCode());
}
    
    }
    //Method for specifying the Department 
public static void selectDistinct() throws SQLException
{
    
     stm = conn.createStatement();
    
    result = stm.executeQuery("Select DISTINCT department_description\n" +
"From department");
    
    ArrayList<String> names2 = new ArrayList<>();
    int i = 1;
    while (result.next()){
        
        
        System.out.println(i++ + " " + result.getString("department_description"));
        
        names2.add(result.getString("department_description"));
        
    }
    
    Scanner input = new Scanner(System.in);

    System.out.println("Select department :");
     int number = input.nextInt(); 
     
    if (number <= names2.size() && number != 0)
    {
   
    System.out.println(names2.get(number-1));
    department = names2.get(number-1);
    selectPayType() ;
    }
    else{
        
       System.out.println("Wrong number");
      selectDistinct();
    }

}

// Method for specifying the Pay Type    
public static void selectPayType() throws SQLException
{
    
     stm = conn.createStatement();
    
    result = stm.executeQuery("Select DISTINCT pay_type\n" +
"From position");
    
    String[] names = null;
    ArrayList<String> names2 = new ArrayList<String>();
    int i = 1;
    while (result.next()){
        
        
        System.out.println(i++ + " " + result.getString("pay_type"));
        
        names2.add(result.getString("pay_type"));
       
    }
    
    Scanner input = new Scanner(System.in);

    System.out.println("Select Pay Type:");
    int number = input.nextInt(); 
     if (number <= names2.size() && number != 0)
    {
   
    
    System.out.println(names2.get(number-1));
    pay_type = names2.get(number-1);
    educationLevel();
    }
     else {
         
         System.out.println("Wrong number");
         selectPayType();
     }
     
     
   
}


// Method for specifying the Education Level
public static void educationLevel() throws SQLException
{
    stm = conn.createStatement();
    
    result = stm.executeQuery("Select DISTINCT education_Level\n" +
"From employee");
    
    String[] names = null;
    ArrayList<String> names2 = new ArrayList<String>();
    int i = 1;
    while (result.next()){
        
        
        System.out.println(i++ + " " + result.getString("education_Level"));
        
        names2.add(result.getString("education_Level"));
       
    }
    
    Scanner input = new Scanner(System.in);

    System.out.println("Select Education Level:");
    int number = input.nextInt(); 
     if (number <= names2.size() && number != 0)
    {
   
    
    System.out.println(names2.get(number-1));
    education_Level = names2.get(number-1);
    finalQuery();
    
    }
     else {
         
         System.out.println("Wrong number");
         
         educationLevel();
     }

     
}

// Method for doing the final query and shows the results from the Database.
public static void finalQuery() throws SQLException
{
    
       stm = conn.createStatement();
    
    result = stm.executeQuery("SELECT p.employee_id, p.full_name, p.first_name, p.last_name, p.position_id, p.position_title, p.store_id, p.department_id, p.birth_date, p.hire_date, p.end_date, p.salary, p.supervisor_id, p.education_level, p.marital_status, p.gender, p.management_role, d.department_id, d.department_description, a.position_id, a.position_title, a.pay_type, a.min_scale, a.max_scale, a.management_role" + "\n"+
    "FROM employee p" + "\n" +
    "LEFT JOIN department d ON p.department_id = d.department_id" + "\n"+
    "LEFT JOIN position a ON p.position_id = a.position_id" + "\n"+
    "Where p.education_level ='" + education_Level + "'\n" +
    "AND a.pay_type ='" + pay_type  + "'\n" +
    "AND d.department_description ='" + department + "'");
    
    
if (result.next() != false)
{
    int i = 1;
   
        while (result.next()){
        
        System.out.println(i++ + ") " + result.getString("full_name") + ", Salary: " +result.getString("salary") + ", Hire Date: " + result.getString("hire_date") );
      
        }
  
}
else{
    System.out.println ("No data found");
}
  
    Menu();
}


public static void Menu() throws SQLException
{
    System.out.println("Choose from these choices");
        System.out.println("-----------------------\n");
        System.out.println("1 - Search Database");
        System.out.println("2 - Quit");
        System.out.println("------> ");
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();

    switch (choice) {
        case 1:
           selectDistinct();
            break;
        case 2:
            break;
        default:
            System.out.println("Wrong Choice");
            Menu();
    }
  
    
}


}
