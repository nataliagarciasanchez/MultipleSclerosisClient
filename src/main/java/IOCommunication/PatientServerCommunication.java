/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import Menu.Utilities.Utilities;
import POJOs.Feedback;
import POJOs.User;
import POJOs.Patient;
import POJOs.Report;
import POJOs.Symptom;
import Security.PasswordEncryption;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author maite
 */
public class PatientServerCommunication {
    
    private String serverAddress;
    private int serverPort; 
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Patient patient;
    
  
    public PatientServerCommunication(String serverAddress, int serverPort) {

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        
    }
    
    public void start(){
        try {
            this.socket=new Socket(serverAddress, serverPort);
            this.out=new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in=new ObjectInputStream(socket.getInputStream());
            System.out.println("Patient connected to server");
            String message = "PatientServerCommunication";
            out.writeObject(message);
            
        } catch (IOException ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
            "Connection to the server was lost. Please try again later.",
            "Connection Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        }
    }
    
    public class Send{
        
        /**
         * Calls the server so the patient registers in the app and, therefore,
         * it is saved in the database
         *
         * @param patient
         */
        public void register(Patient patient) {
            try {
                Utilities.validateRegisterPatient(patient);
                
                out.writeObject("register"); // Acción de registro
                out.flush();
                
                //TODO quitar souts
                System.out.println("Plain: " + patient.getUser().getPassword()); // para comprobar si el hash se hace bien
                String hashedPassword = PasswordEncryption.hashPassword(patient.getUser().getPassword()); 
                patient.getUser().setPassword(hashedPassword); // para comprobar si el hash se hace bien
                System.out.println("Hashed: " + patient.getUser().getPassword());
                
                out.writeObject(patient.getUser());//envía los datos de registro al server
                out.writeObject(patient);
                out.flush();
                
                System.out.println("Registering.....");
                String confirmation=(String) in.readObject();
                if (confirmation.contains("Username already in use") ){
                   System.out.println("Error: " + confirmation); 
                }else{
                    System.out.println(confirmation);//muestra la confirmación del server de que se ha registrado
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Logs in the app with the username and password and accesses the info
         * of that patient
         *
         * @param username
         * @param password
         * @return message
         */
        public Patient login(String username, String password) {
            
            try {
                User user = new User(username, password);
                Utilities.validateLogin(user); 
                
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                
                
                password = PasswordEncryption.hashPassword(password); // encriptamos
                out.writeObject(password);
                
                System.out.println("Logging in.....");
                Object response=in.readObject();
                if(response instanceof Patient){//si es de tipo patient es que las credenciales son correctas
                   patient= (Patient) response;
                   System.out.println("Successfull log in!");
                }else if (response instanceof String){
                   String errorMessage = (String) response; // Mensaje de error
                   System.out.println("Error: " + errorMessage); 
                }
                
  
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return patient;
        }
        
        /**
         * Logs out of the app by closing all connections from that patient to the server
         */
        public void logout(){
            try {
                out.writeObject("logout");
                System.out.println(in.readObject());
                //ahora mismo lo hace el server cuando recive esta opcion
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
              releaseResources(in, out, socket);  
            }
        }

        /**
         * Changes the current password of the patient
         *
         * @param user
         * @param patient
         */
        public void updateInformation(User user, Patient patient) {

            try {
                
                Utilities.validateUpdatePatient(patient);
                //Utilities.validateUpdatePassword(user);
                out.writeObject("updateInformation");
                System.out.println("Sending update request to the server...");
                
                // Gestionar el hash de la contraseña en el servidor
                String newHashedPassword = PasswordEncryption.hashPassword(user.getPassword());
                String existingHashedPassword = patient.getUser().getPassword();

                // Actualizar la contraseña si es necesario
                if (!existingHashedPassword.equals(newHashedPassword) && !user.getPassword().equals(existingHashedPassword)) {
                    Utilities.validateUpdatePassword(user);
                    System.out.println("Updating password...");
                    user.setPassword(newHashedPassword);
                    out.writeObject(user);
                }else{
                    System.out.println("Patient.getUser(): " + patient.getUser().getPassword());
                    out.writeObject(patient.getUser());}
                
                
                out.writeObject(patient);
                
                String response = (String) in.readObject();
                System.out.println("Server response: " + response);
                //System.out.println(in.readObject());
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        /**
         * Retrieves all the possible symptoms from the database that a patient with multiple sclerosis may have
         * @return list of symptoms
         */
        public List<Symptom> getSymptoms(){
            List <Symptom> symptoms = null;
            try {
                out.writeObject("viewSymptoms");
                symptoms=(List <Symptom>) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return symptoms;
        }

        /**
         * Sends the report to the server with all the info 
         * @param report 
         */
        public void sendReport(Report report){
            try {
                out.writeObject("sendReport");
                out.writeObject(report);
                out.flush();
                System.out.println("Report sent.");
                // Read the server's response
                String response = (String) in.readObject();
                System.out.println("Server response: " + response);
                System.out.println("Feedback from doctor will be sent shortly in View Feedbacks");
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        

        public Patient getPatient() {
            return patient;
        }
        
        
        private static void releaseResources(ObjectInputStream in, ObjectOutputStream out, Socket socket) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public class Receive {
        
        
        /**
         * Receives feedbacks from server corresponding to that patient
         * @return 
         */
        public List<Feedback> viewFeedbacks(Patient patient){
            List<Feedback> feedbacks = null;
            try {
                out.writeObject("receiveFeedbacks");
                out.flush();
                out.writeObject(patient.getId());
                feedbacks=(List<Feedback>) in.readObject();
                out.writeObject("Feedbacks received.");
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return feedbacks;
        }
 
    }

}
