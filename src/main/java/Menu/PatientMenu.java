/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;


import IOCommunication.PatientServerCommunication;
import POJOs.User;
import Menu.Utilities.Utilities;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.SignalType;
import POJOs.Symptom;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author maipa
 */
public class PatientMenu {
    
    
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
                Logger.getLogger(PatientMenu.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PatientMenu.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PatientMenu.class.getName()).log(Level.SEVERE, null, ex);
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
                        servicesMenu(patient, psCommunication);
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
    
    public static void servicesMenu(Patient patient, PatientServerCommunication psCommunication){
        //TODO here we should ask for symptoms, connect to the bitalino and so on
        
        System.out.println("Welcome to your remote Multiple Sclerosis clinic. We are now going to attend your needs.");
        System.out.println("To make sure we take care of all your needs and report to your doctor we would "
                + "like to ask you to select all the symptoms you are currently experiencing. ");
        
        //checklist with all the symptoms will appear 
        showSymptoms();
        
        //patient selects all the symptoms she/he has 
        List <Symptom> symptomsList=new LinkedList<>();
        //symptomsList.add(s1);
        
        System.out.println("We are sorry you are experiencing all those symptoms. Do not worry we will make sure you feel better!");
        recordECG(psCommunication);
        recordEMG(psCommunication);
        
    }
    
    public static void showSymptoms(){
        
        System.out.println("SÍNTOMAS MUSCULARES: ");
        System.out.println("1. Pérdida del equilibrio");
        System.out.println("2. Espasmos musculares");
        System.out.println("3. Entumecimiento o sensación anormal en cualquier zona");
        System.out.println("4. Problemas para mover los brazos y las piernas");
        System.out.println("5. Problemas para caminar");
        System.out.println("6. Problemas con la coordinación y para hacer movimientos pequeños");
        System.out.println("7. Temblor en uno o ambos brazos o piernas");
        System.out.println("8. Debilidad en uno o ambos brazos o piernas");
        
        
        System.out.println("SÍNTOMAS VESICALES E INTESTINALES: ");
        System.out.println("1. Estreñimiento y escape de heces");
        System.out.println("2. Dificultad para comenzar a orinar");
        System.out.println("3. Necesidad frecuente de orinar");
        System.out.println("4. Urgencia intensa de orinar");
        System.out.println("5. Escape de orina (incontinencia)");
        
        System.out.println("SÍNTOMAS OCULARES: ");
        System.out.println("1. Visión doble");
        System.out.println("2. Molestia en los ojos");
        System.out.println("3. Movimientos oculares incontrolables");
        System.out.println("4. Pérdida de visión (usualmente afecta un ojo a la vez)");
        
        
        System.out.println("ESTUMECIMIENTO, HORMIGUEO O DOLOR: ");
        System.out.println("1. Dolor facial");
        System.out.println("2. Espasmos musculares dolorosos");
        System.out.println("3. Hormigueo, sensación de picazón o ardor en los brazos y las piernas");
        
        
        System.out.println("OTROS SÍNTOMAS CEREBRALES Y NEUROLÓGICOS: ");
        System.out.println("1. Disminución del período de atención, de la capacidad de discernir y pérdida de la memoria");
        System.out.println("2. Dificultad para razonar y resolver problemas");
        System.out.println("3. Depresión o sentimientos de tristeza");
        System.out.println("4. Mareos o pérdida del equilibrio");
        System.out.println("5. Pérdida de la audición");
        
        
        System.out.println("SÍNTOMAS SEXUALES: ");
        System.out.println("1. Problemas de erección");
        System.out.println("2. Problemas con la lubricación vaginal");
        
        System.out.println("SÍNTOMAS DEL HABLA Y LA DEGLUCIÓN: ");
        System.out.println("1. Lenguaje mal articulado o difícil de entender");
        System.out.println("2. Problemas para masticar y tragar");

    }
    
    public static void recordECG(PatientServerCommunication psCommunication){
        
        SignalType signal=SignalType.ECG;
        
        System.out.println("First we would like to perform an ECG. For that you must do exactly as the following intructions tell you:  ");
        System.out.println("Currently there are 3 electrodes placed next to the recording device. ");
        System.out.println("1. First, place the positive (+)- RED electrode just below the right "
                + "clavicle (collarbone) on the right side of the chest. "
                + "This location is close to Lead I placement in a standard ECG setup");
        System.out.println("2. Second, place the negative (-)-WHITE electrode on the lower left side of the torso, below the left pectoral muscle. "
                + "This location is typically near the bottom of the rib cage or just above the hip bone on the left side.");
        System.out.println("3. Third, place the BLACK electrode on a neutral location, ideally on the right side of the torso, near the lower right abdomen or hip.");
        
        System.out.println("\n Once everything is correctly placed hit play on the program that will appear on screen.");
        //TODO aparece el programa de Shark en pantalla? o se debe abrir desde el programa
        //TODO the recording will finish after 1 minute
        
        System.out.println("Great!. Signals have been recorded correctly. ");
        
        psCommunication.sendECGSignals();
    }
    
    public static void recordEMG(PatientServerCommunication psCommunication){
        
        SignalType signal=SignalType.EMG;
        System.out.println("Now we will perform an EMG. Please follow the instructions. ");
        System.out.println("1. Positive electrode (RED): Center of biceps muscle. ");
        System.out.println("2. Negative electrode (YELLOW): 2–3 cm along the muscle, aligned with fibers.");
        System.out.println("3. Ground electrode (BLACK): On a bony area like the elbow or wrist. ");
        
        System.out.println("\n Once everything is correctly placed hit play on the program that will appear on screen.");
        //TODO aparece el programa de Shark en pantalla? o se debe abrir desde el programa
        //TODO the recording will finish after 1 minute
        System.out.println("Great! EMG performed correctly. ");
        
        psCommunication.sendEMGSignals();
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
                Logger.getLogger(PatientMenu.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }while(!Utilities.validMenu(2, choice) || choice != 0);
    }
}
    
