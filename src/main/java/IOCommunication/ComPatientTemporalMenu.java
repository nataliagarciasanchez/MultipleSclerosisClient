/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

/**
 *
 * @author maipa
 */
public class ComPatientTemporalMenu {
    public static void main(String[] args) {
        PatientServerCommunication com=new PatientServerCommunication("localhost", 9000);
        PatientServerCommunication.Send send = null;
        send.register("maipat1310@gmail.com", "Password123");
        //send.login("maipat1310@gmail.com", "Password123");
    }
}
