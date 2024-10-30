/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import ClientInterfaces.PatientManager;
import ClientJDBC.ConnectionManager;
import ClientJDBC.JDBCPatientManager;
import Menu.Utilities.Utilities;
import java.io.IOException;
import javax.management.relation.Role;

/**
 *
 * @author maipa
 */
public class ClientMenu {
    
    private static PatientManager patientMan;
    
    public static void main(String[] args) {

        ConnectionManager conMan = new ConnectionManager();

        patientMan = new JDBCPatientManager(conMan.getConnection());
        userMan = new JPAUserManager();

        int choice = -1;
        do {
            try {
                System.out.println("\nWelcome to the MultipleSclerosis app!");
                System.out.println("What do you want to do? :");
                System.out.println("1. Register");
                System.out.println("2. Log in");
                System.out.println("0. Exit");
                choice = Utilities.readInteger();
                switch (choice) {
                    case 1: {
                        registerMenu();
                        break;
                    }
                    case 2: {
                        login();
                        break;
                    }
                    case 0: {
                        conMan.closeConnection();
                        System.out.println("Thank you for using the database! Goodbye.");
                        userMan.disconnect();
                        return;
                    }
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("You didn't type a valid option! Try an integer number between 0 and 2");
                choice = -1;
            } catch (IOException e) {
                System.out.println("I/O Exception.");
                e.printStackTrace();
            }
        } while (!Utilities.validMenu(2, choice) || choice != 0);
    }

    public static void registerMenu() {

        System.out.println("Please, introduce the following information: ");
        
        
        System.out.println("Name: ");
        String name = Utilities.readString();
        System.out.println("Surname: ");
        String surname = Utilities.readString();
        
        String NIF = "";
        do {
            System.out.println("NIF number: ");
            NIF = Utilities.readString();
        } while (!Utilities.validateID(NIF));
        
        //TODO:     preguntar por dob y genero
        
        
        String email = "";
        do {
            System.out.println("Email: ");
            email = Utilities.readString();
            
        } while (!Utilities.isValidEmail(email));
        
        String phone_number;
        do {
            System.out.println("Number of mobile phone: ");
            phone_number = Utilities.readString();
            
        } while (!Utilities.isValidPhone(phone_number));
        
        
        System.out.println("Password: ");
        String password = Utilities.readString();
        
        Role role = userMan.getRole("patient");
        userMan.assignRole(user, role);

        System.out.println("You have registered as a patient!\n");
        patientMenu(user.getEmail());

    }

    public static void login() throws IOException {
        String username = "";
        do {
            System.out.println("\n Press '0' to go back to menu\n");

            System.out.println("Username: ");
            username = Utilities.readString();
            if (username.equals("0")) {
                System.out.println("\n");
                break;
            }
            System.out.println("Password: ");
            String password = Utilities.readString();
            User user = userMan.login(username, password);
            if (user != null) {
                patientMenu(user.getEmail()); 
            } else {
                System.out.println("Wrong username/password combination");
            }
        } while (username.equals(""));
    }
}
