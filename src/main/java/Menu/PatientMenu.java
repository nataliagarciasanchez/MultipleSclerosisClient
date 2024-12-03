/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;


import BITalino.BITalino;
import BITalino.Frame;
import IOCommunication.PatientServerCommunication;
import POJOs.User;
import Menu.Utilities.Utilities;
import POJOs.Bitalino;
import POJOs.Doctor;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.Report;
import POJOs.Role;
import POJOs.SignalType;
import POJOs.Symptom;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author maite
 */
public class PatientMenu {
    
    
    private static User user;
    private static PatientServerCommunication patientServerCom;
    private static PatientServerCommunication.Send send;
    private static BITalino bitalinoDevice;
    private static Date date;
    private static Role role=new Role();
    
    public static void main(String[] args) {
        
        //TODO deberá preguntar por el IP address del paciente
        int choice = 0;
        do {
            try {
                System.out.println("Introduce the IP Adress of the server you are trying to connect: ");
                String serverAddress=Utilities.readString();
                System.out.println("Introduce the port: ");
                int port=Utilities.readInteger();
                
                patientServerCom=new PatientServerCommunication(serverAddress, port);
                send=patientServerCom.new Send();//llama a la clase anidada de send en PatientServerCommunication

                System.out.println("");
                System.out.println("\nWelcome to the MultipleSclerosis Patient app!");
                
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

        Patient patient=registerPatientInfo();//only the info from the POJO not the IO info
        User user=getUserInfo();
        patient.setUser(user);
        send.register(patient);
        patientMenu(patient);
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
        
        String date_str;
        Date sqlDob=null;
        boolean validDate=false;
        
        
        while(!validDate){
            
            try{
                System.out.println("Date of birth (dd/MM/yyyy): ");
                date_str=Utilities.readString();
                sqlDob=Utilities.convertString2SqlDate(date_str);
                validDate=true;
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
        
        Doctor doctor=new Doctor();//TODO crear un método que asigne al patient un doctor aleatoriamente 
        
        return new Patient(name,surname,NIF,sqlDob,gender,phone_number, doctor,user);
    }

    public static User getUserInfo(){
        
        String email = "";
        do {
            System.out.println("Email: ");
            email = Utilities.readString();
            
        } while (!Utilities.isValidEmail(email));

        String password;
        while (true) {
            
            System.out.println("Password. NOTE: Password must have at least:  \n 8 characters \n 1 capital letter \n 1 number");
            password = Utilities.readString();
            
            if (Utilities.isValidPassword(password)) {
                System.out.println("Valid password.");
                break; 
            } else {
                System.out.println("Not valid password.");
            }
        }
        
        
        
        return new User(email,password, role);
    }
    
    public static void loginMenu() throws IOException {
        
        try {
            
            bitalinoDevice=new BITalino();
            String macAddress = "98:D3:41:FD:4E:E8";
            bitalinoDevice.open(macAddress);
            LocalDate localdate=LocalDate.now();
            date=Date.valueOf(localdate);

            System.out.println("\n Press '0' to go back to menu\n");
            user = getUserInfo();
            
            if (user != null) {
                send.login(user.getEmail(), user.getPassword());
                //comunicarme con el server para obtener el patient
                Patient patient = send.getPatient();
                if (patient != null) {
                    System.out.println("Successful login");
                    patientMenu(patient);
                } else {
                    System.out.println("Incorrect username and/or password");
                }
                
            }
        } catch (Throwable ex) {
            Logger.getLogger(PatientMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void patientMenu(Patient patient){
        
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
                        servicesMenu(patient);
                        break;
                    }
                    case 2: {
                        configurationMenu(patient);
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
    
    public static void servicesMenu(Patient patient){
        
        
        System.out.println("Welcome to your remote Multiple Sclerosis clinic. We are now going to attend your needs.");
        System.out.println("To make sure we take care of all your needs and report to your doctor we would "
                + "like to ask you to select all the symptoms you are currently experiencing. ");
        
        //checklist with all the symptoms will appear 
        showSymptoms();
        
        //patient selects all the symptoms she/he has 
        List <Symptom> symptomsList=new LinkedList<>();
        //symptomsList.add(s1);
        
        System.out.println("We are sorry you are experiencing all those symptoms. Do not worry we will make sure you feel better!");
        Bitalino bitalinoECG=recordECG();
        Bitalino bitalinoEMG=recordEMG();
        List<Frame> emgFrames=bitalinoEMG.storeRecordedSignals(bitalinoDevice, SignalType.EMG);
        bitalinoEMG.setSignalValues(emgFrames, 1);
        List<Frame> ecgFrames=bitalinoEMG.storeRecordedSignals(bitalinoDevice, SignalType.ECG);
        bitalinoEMG.setSignalValues(ecgFrames, 2);
        
        List<Bitalino> bitalinos = new ArrayList();
        bitalinos.add(bitalinoEMG);
        bitalinos.add(bitalinoECG);
        Report report=new Report(date,patient,bitalinos,symptomsList);
        

        diagnosisFromDoctor(report);
    }
    
    public static void showSymptoms() {
        
        System.out.println("MUSCLE SYMPTOMS: ");
        System.out.println("1. Loss of balance");
        System.out.println("2. Muscle spasms");
        System.out.println("3. Numbness or abnormal sensation in any area");
        System.out.println("4. Trouble moving arms and legs");
        System.out.println("5. Difficulty walking");
        System.out.println("6. Problems with coordination and making small movements");
        System.out.println("7. Tremor in one or both arms or legs");
        System.out.println("8. Weakness in one or both arms or legs");
        
        
        System.out.println("BLADDER AND BOWEL SYMPTOMS: ");
        System.out.println("1. Constipation and stool leakage");
        System.out.println("2. Difficulty starting to urinate");
        System.out.println("3. Frequent need to urinate");
        System.out.println("4. Intense urgency to urinate");
        System.out.println("5. Urine leakage (incontinence)");
        
        System.out.println("EYE SYMPTOMS: ");
        System.out.println("1. Double vision");
        System.out.println("2. Eye discomfort");
        System.out.println("3. Uncontrolled eye movements");
        System.out.println("4. Vision loss (usually affects one eye at a time)");
        
        
        System.out.println("NUMBNESS, TINGLING, OR PAIN: ");
        System.out.println("1. Facial pain");
        System.out.println("2. Painful muscle spasms");
        System.out.println("3. Tingling, itching, or burning sensation in arms and legs");
        
        
        System.out.println("OTHER BRAIN AND NEUROLOGICAL SYMPTOMS: ");
        System.out.println("1. Reduced attention span, ability to discern, and memory loss");
        System.out.println("2. Difficulty with reasoning and problem-solving");
        System.out.println("3. Depression or feelings of sadness");
        System.out.println("4. Dizziness or loss of balance");
        System.out.println("5. Hearing loss");
        
        
        System.out.println("SEXUAL SYMPTOMS: ");
        System.out.println("1. Erectile problems");
        System.out.println("2. Problems with vaginal lubrication");
        
        System.out.println("SPEECH AND SWALLOWING SYMPTOMS: ");
        System.out.println("1. Poorly articulated or difficult-to-understand speech");
        System.out.println("2. Trouble chewing and swallowing");

    }

    
    public static Bitalino recordECG(){
        
        SignalType signal=SignalType.ECG;
        Bitalino bitalino=new Bitalino(date,signal);
        
        System.out.println("First we would like to perform an ECG. For that you must do exactly as the following intructions tell you:  ");
        System.out.println("Currently there are 3 electrodes placed next to the recording device. ");
        System.out.println("1. First, place the positive (+)- RED electrode just below the right "
                + "clavicle (collarbone) on the right side of the chest. "
                + "This location is close to Lead I placement in a standard ECG setup");
        System.out.println("2. Second, place the negative (-)-WHITE electrode on the lower left side of the torso, below the left pectoral muscle. "
                + "This location is typically near the bottom of the rib cage or just above the hip bone on the left side.");
        System.out.println("3. Third, place the BLACK electrode on a neutral location, ideally on the right side of the torso, near the lower right abdomen or hip.");
        
        System.out.println("\n Once everything is correctly placed hit play on the program that will appear on screen.");
        
        
        System.out.println("Great!. Signals have been recorded correctly. ");
        
        return bitalino;
    }
    
    public static Bitalino recordEMG(){
        
        SignalType signal=SignalType.EMG;
        Bitalino bitalino=new Bitalino(date, signal);
        System.out.println("Now we will perform an EMG. Please follow the instructions. ");
        System.out.println("1. Positive electrode (RED): Center of biceps muscle. ");
        System.out.println("2. Negative electrode (WHITE): 2–3 cm along the muscle, aligned with fibers.");
        System.out.println("3. Ground electrode (BLACK): On a bony area like the elbow or wrist. ");
        
        System.out.println("\n Once everything is correctly placed hit play on the program that will appear on screen.");
        
        System.out.println("Great! EMG performed correctly. ");
        
        return bitalino;
    }
    
    public static void diagnosisFromDoctor(Report report){
        send.sendReport(report);
       /*TODO el feedback del doctor se va a mostrar automáticamente cuando el server 
       se ponga en contacto con este, una vez las señales del patient han sido mandadas con el hilo de la 
        clase Receive de la communication. */
    }
    
    public static void configurationMenu(Patient patient){
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
                        
                        System.out.println("Insert new password: ");
                        String new_password;
                        while (true) {
                            System.out.println("Password: ");
                            System.out.println("Password must have at least:  \n 8 characters \n 1 capital letter \n 1 number");
                            new_password = Utilities.readString();
                            
                            if (Utilities.isValidPassword(new_password)) {
                                user.setPassword(new_password);
                                System.out.println("Valid password.");
                                break;
                            } else {
                                System.out.println("Not valid password.");
                            }
                        }
                        
                        send.updateInformation(user);
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
}
    
