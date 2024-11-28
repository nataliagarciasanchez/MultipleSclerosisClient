/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import BITalino.BITalino;
import Menu.Utilities.Utilities;
import POJOs.Bitalino;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.Role;
import POJOs.SignalType;
import POJOs.User;
import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to test all the method in the communication
 * @author maipa
 */
public class ComPatientTemporalMenu {
    
    public static PatientServerCommunication com; 
    public static PatientServerCommunication.Send send;
    public static String macAddress = "98:D3:41:FD:4E:E8";
    public static Role role;
    
    public static void main(String[] args) {
        com= new PatientServerCommunication("localhost", 9000);
        //com.start();
        send= com.new Send();
        role=new Role();
        //register();
        //login();
        //updateInfo();
        sendSignals(SignalType.ECG);
        //sendSignals(send, SignalType.EMG);
    }

    public static void register() {
        try {
            java.sql.Date dob = Utilities.convertString2SqlDate("14/10/2003");
            //Doctor doctor=new Doctor("Dr.Garcia", "NEUROLOGY", new User("doctor.garcia@multipleSclerosis.com","Password456"));
            Patient maite = new Patient("noelia", "gomez", "05459423F", dob, Gender.FEMALE, "609526931");
            User user=new User("noelia@gmail.com", "Password123", role);
            maite.setUser(user);
            
            send.register(maite);
        } catch (ParseException ex) {
            Logger.getLogger(ComPatientTemporalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void login(){
       Patient patient=send.login("noelia@gmail.com", "Password123");
       System.out.println(patient);
    }
    
    public static void updateInfo() {
        Patient patient = send.login("noelia@gmail.com", "Password123");
        System.out.println(patient);
        User user = patient.getUser();
        user.setRole(role);
        System.out.println("user\n" + user);
        String newPass = "Password456";
        if (Utilities.isValidPassword(newPass)) {
            user.setPassword("Password456");
            System.out.println("This is to check if the setter is working correctly: " + user.getPassword());
            send.updateInformation(user);
        }

    }
    
    public static void sendSignals(SignalType signal_type){
        try {
            BITalino bitalinoDevice=new BITalino();
            
            bitalinoDevice.open(macAddress);
            
            Patient patient=send.login("noelia@gmail.com", "Password123");
            System.out.println(patient);
            Date date=new Date(2024,11,25);
            Bitalino bitalinoECG=new Bitalino(date,SignalType.ECG);
            Bitalino bitalinoEMG=new Bitalino(date,SignalType.EMG);
            if(signal_type==SignalType.ECG){
                sendECG(date, bitalinoECG, bitalinoDevice);
            }else{
                sendEMG(date, bitalinoEMG, bitalinoDevice);
            }} catch (Throwable ex) {
            Logger.getLogger(ComPatientTemporalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendECG(Date date, Bitalino bitalino, BITalino bitalinoDevice){
        send.sendECGSignals(bitalino, bitalinoDevice);
    }
    
    public static void sendEMG(Date date, Bitalino bitalino, BITalino bitalinoDevice){
       send.sendEMGSignals(bitalino, bitalinoDevice);
    }
}
