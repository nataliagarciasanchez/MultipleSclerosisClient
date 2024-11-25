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
    
    public static void main(String[] args) {
        com= new PatientServerCommunication("localhost", 1027);
        send= com.new Send();
        
        //register();
        //login();
        //TODO updateInfo() no funciona
        //updateInfo();
        sendSignals(SignalType.ECG);
        //sendSignals(send, SignalType.EMG);
    }

    public static void register() {
        try {
            java.sql.Date dob = Utilities.convertString2SqlDate("14/10/2003");
            //Doctor doctor=new Doctor("Dr.Garcia", "NEUROLOGY", new User("doctor.garcia@multipleSclerosis.com","Password456"));
            Patient maite = new Patient("maite", "gomez", "05459423M", dob, Gender.FEMALE, "609526931");
            User user=new User("maipat1310@gmail.com", "Password123");
            maite.setUser(user);
            send.register(maite);
        } catch (ParseException ex) {
            Logger.getLogger(ComPatientTemporalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void login(){
       Patient patient=send.login("maipat1310@gmail.com", "Password123");
       System.out.println(patient);
    }
    
    public static void updateInfo(){
        Patient patient=send.login("maipat1310@gmail.com", "Password123");
        System.out.println(patient);
        User user=patient.getUser();
        System.out.println("user\n" + user);
        user.setPassword("NEW");
        send.updateInformation(user);
    }
    
    public static void sendSignals(SignalType signal_type){
        try {
            BITalino bitalinoDevice=new BITalino();
            String macAddress = "98:D3:41:FD:4E:E8";
            bitalinoDevice.open(macAddress);
            
            Patient patient=send.login("maipat1310@gmail.com", "Password123");
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
