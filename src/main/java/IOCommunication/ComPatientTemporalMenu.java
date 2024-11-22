/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import Menu.Utilities.Utilities;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.User;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to test all the method in the communication
 * @author maipa
 */
public class ComPatientTemporalMenu {
    
    public static void main(String[] args) {
        PatientServerCommunication com = new PatientServerCommunication("localhost", 1027);
        PatientServerCommunication.Send send = com.new Send();
        //register(send);
        //login(send);
        updateInfo(send);
    }

    public static void register(PatientServerCommunication.Send send) {
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
    
    public static void login(PatientServerCommunication.Send send){
       Patient patient=send.login("maipat1310@gmail.com", "Password123");
       System.out.println(patient);
    }
    
    public static void updateInfo(PatientServerCommunication.Send send){
        Patient patient=send.login("maipat1310@gmail.com", "Password123");
        System.out.println(patient);
        User user=patient.getUser();
        System.out.println("user\n" + user);
        user.setPassword("NEW");
        send.updateInformation(user);
    }
}
