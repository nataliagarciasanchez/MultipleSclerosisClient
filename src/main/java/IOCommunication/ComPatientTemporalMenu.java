/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import Menu.Utilities.Utilities;
import POJOs.Doctor;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.User;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maipa
 */
public class ComPatientTemporalMenu {
    
    public static void main(String[] args) {
        try {
            PatientServerCommunication com=new PatientServerCommunication("localhost", 9000);
            PatientServerCommunication.Send send=com.new Send();
            java.sql.Date dob=Utilities.convertString2SqlDate("14/10/2003");
            Doctor doctor=new Doctor("Dr.Garcia", "NEUROLOGY", new User("doctor.garcia@multipleSclerosis.com","Password456"));
            Patient maite=new Patient("maite", "gomez", "05459423M",dob,Gender.FEMALE, "609526931",doctor );
            send.register("maipat1310@gmail.com", "Password123", maite);
            System.out.println("Ha llegado al final del main del patient");
            //send.login("maipat1310@gmail.com", "Password123");
        } catch (ParseException ex) {
            Logger.getLogger(ComPatientTemporalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
