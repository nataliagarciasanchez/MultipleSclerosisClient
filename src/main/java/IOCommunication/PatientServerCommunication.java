
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
    
    private final String serverAddress;
    private final int serverPort; 
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Patient patient;
    
  
    public PatientServerCommunication(String serverAddress, int serverPort) {

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        
    }
    
    public boolean start(){
        boolean connection = false;
        try {
            this.socket=new Socket(serverAddress, serverPort);
            this.out=new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in=new ObjectInputStream(socket.getInputStream());
            
            String message = "PatientServerCommunication";
            out.writeObject(message);
            out.flush();
            
            Boolean serverResponse = (Boolean) in.readObject();
            if (!serverResponse) {
                connection = false;
                releaseResources(in, out, socket);
                
            }else{
            connection = true;
            }
            
          
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
            "Connection to the server was lost. Please try again later.",
            "Connection Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        }
        return connection;
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
                out.writeObject(patient.getUser());//envía los datos de registro al server
                out.writeObject(patient);
                out.flush();
                
                System.out.println("Registering.....");
                String hashedPassword = PasswordEncryption.hashPassword(patient.getUser().getPassword()); 
                patient.getUser().setPassword(hashedPassword); // para comprobar si el hash se hace bien
                
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
                if(response instanceof Patient patient1){//si es de tipo patient es que las credenciales son correctas
                   patient= patient1;
                   System.out.println("Successfull log in!");
                }else if (response instanceof String errorMessage){ // Mensaje de error
                    // Mensaje de error
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
         * @param confirmPassword
         */
        public void updateInformation(User user, Patient patient, String confirmPassword) {

            try {
                
                Utilities.validateUpdatePatient(patient);
                
                out.writeObject("updateInformation");
                System.out.println("Sending update request to the server...");
                
                // Obtain hash
                String newHashedPassword = PasswordEncryption.hashPassword(user.getPassword()); // new password
                String existingHashedPassword = patient.getUser().getPassword(); // current password

                // update password if it has been changed
                if (!existingHashedPassword.equals(newHashedPassword) && !user.getPassword().equals(existingHashedPassword)) { 
                    Utilities.validateUpdatePassword(user.getPassword(), confirmPassword);
                    System.out.println("Updating password...");
                    user.setPassword(newHashedPassword);
                    out.writeObject(user);
                }else{
                    System.out.println("Patient.getUser(): " + patient.getUser().getPassword());
                    System.out.println("Password remains the same.");
                    out.writeObject(patient.getUser());
                }
                
                
                out.writeObject(patient);
                
                String response = (String) in.readObject();
                System.out.println("Server response: " + response);
                
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        
        
      
        
    }

    public class Receive {
        
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
         * Receives feedbacks from server corresponding to that patient
         * @param patient
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
