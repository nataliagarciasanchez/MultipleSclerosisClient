/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;


import POJOs.User;
import Menu.Utilities.Utilities;
import POJOs.Gender;
import POJOs.Patient;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author maipa
 */
public class ClientMenu {
    
    
    private static User user;
    
    public static void main(String[] args) {
        
        //TODO deberá preguntar por el IP address del paciente
        int choice = 0;
        do {
            try {
                System.out.println("\n Welcome to the MultipleSclerosis Patient app!");
                
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
                        loginMenu();
                        break;
                    }
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("You didn't type a valid option! Try an integer number between 0 and 2");
                choice = -1;
            } catch (IOException ex) {
                Logger.getLogger(ClientMenu.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } while (!Utilities.validMenu(2, choice) || choice != 0);
    }

    public static void registerMenu() {

        try {
            Patient patient=registerPatientInfo();//only the info from the POJO not the IO info
            
            User user=getUserInfo();
            
            PatientServerCommunication psCommunication=new PatientServerCommunication();
            psCommunication.register(user.getEmail(),user.getPassword());
           
            patientMenu(patient, user, psCommunication);
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Patient registerPatientInfo(){
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
        
        String date_str="";
        Date dob=null;
        boolean validDate=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        while(!validDate){
            System.out.println("Date of birth (dd/MM/yyyy): ");
            date_str=Utilities.readString();
            try{
               dob=(Date) dateFormat.parse(date_str);
            }catch(ParseException e){
                System.out.println("Invalid date. Please introduce date in correct format (dd/MM/yyyy) ");
            }
        }
        
        //crear dos botones: femenino o masculino
        Gender gender=null;
        while (gender == null) {
            System.out.print("Gender (MALE/FEMALE): ");
            String gender_str = Utilities.readString().trim().toUpperCase(); // Elimina espacios y convierte a mayúsculas

            try {
                gender = Gender.valueOf(gender_str); 
            } catch (IllegalArgumentException e) {
                System.out.println("Not valid gender. Please introduce MALE or FEMALE.");
            }
        }

        String phone_number;
        do {
            System.out.println("Number of mobile phone: ");
            phone_number = Utilities.readString();
            
        } while (!Utilities.isValidPhone(phone_number));
        
        return new Patient(name,surname,NIF,dob,gender,phone_number);
    }

    public static User getUserInfo(){
        
        String email = "";
        do {
            System.out.println("Email: ");
            email = Utilities.readString();
            
        } while (!Utilities.isValidEmail(email));

        String password;
        while (true) {
            System.out.println("Password: ");
            System.out.println("Password must have at least:  \n 8 characters \n 1 capital letter \n 1 number");
            password = Utilities.readString();
            
            if (Utilities.isValidPassword(password)) {
                System.out.println("Valid password.");
                break; 
            } else {
                System.out.println("Not valid password.");
            }
        }
        
        
        return new User(email,password);
    }
    
    public static void loginMenu() throws IOException {

        PatientServerCommunication psCommunication = new PatientServerCommunication();

        System.out.println("\n Press '0' to go back to menu\n");

        User user = getUserInfo();

        if (user != null) {
            try {
                psCommunication.login(user.getEmail(), user.getPassword());
                //comunicarme con el server para obtener el patient    
                psCommunication.findPatient(user.getEmail(), user.getPassword());
                Patient patient = psCommunication.getPatient();
                patientMenu(patient, user, psCommunication);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Wrong username/password combination");
        }

    }
    
    public static void patientMenu(Patient patient, User user, PatientServerCommunication psCommunication){
        
        int choice;
        do {
            try {
                
                System.out.println("What do you want to do? :");
                System.out.println("1. Use services");
                System.out.println("2. Profile Configuration");
                System.out.println("0. Exit");
                
                choice = Utilities.readInteger();
                switch (choice) {
                    case 1: {
                        servicesMenu();
                        break;
                    }
                    case 2: {
                        configurationMenu(patient, user, psCommunication);
                        break;
                    }
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("You didn't type a valid option! Try an integer number between 0 and 2");
                choice = -1;
            } 
        }while(!Utilities.validMenu(2, choice) || choice != 0);
    }    
    
    public static void servicesMenu(){
        //TODO here we should ask for symptoms, connect to the bitalino and so on
    }
    
    public static void configurationMenu(Patient patient, User user, PatientServerCommunication psCommunication){
        int choice = 0;
        do {
            try {
                
                System.out.println("What do you want to do? :");
                System.out.println("1. View personal info");
                System.out.println("2. Change password ");
                System.out.println("0. Exit");
                
                choice = Utilities.readInteger();
                switch (choice) {
                    case 1: {
                        System.out.println("Your personal info is: ");
                        System.out.println(patient.toString());
                        break;
                    }
                    case 2: {
                        System.out.println("Type your current password: ");
                        String actual_password=Utilities.readString();
                        if(actual_password.equals(user.getPassword())){
                            System.out.println("Insert new password: ");
                            String new_password;
                            while (true) {
                                System.out.println("Password: ");
                                System.out.println("Password must have at least:  \n 8 characters \n 1 capital letter \n 1 number");
                                new_password = Utilities.readString();

                                if (Utilities.isValidPassword(new_password)) {
                                    System.out.println("Valid password.");
                                    break;
                                } else {
                                    System.out.println("Not valid password.");
                                }
                            }
                            psCommunication.changePassword(user.getEmail(), actual_password, new_password);
                        }
                        break;
                    }
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("You didn't type a valid option! Try an integer number between 0 and 2");
                choice = -1;
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ClientMenu.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }while(!Utilities.validMenu(2, choice) || choice != 0);
    }
}
    
