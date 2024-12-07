/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;


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
            //new Thread(new Receive(in)).start();
        } catch (IOException ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public class Send{
        /**
         * Logs out of the app by closing all connections from that patient to the server
         */
        public void obtainAddressandPort(){
            try {
                out.writeObject("obtainAddressandPort");
                System.out.println(in.readObject());
                //ahora mismo lo hace el server cuando recive esta opcion
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Calls the server so the patient registers in the app and, therefore,
         * it is saved in the database
         *
         * @param patient
         */
        public void register(Patient patient) {
            try {
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
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                
                System.out.println("Plain: " + password);
                password = PasswordEncryption.hashPassword(password); // encriptamos
                System.out.println("Hashed: "+ password);
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
                out.writeObject("updateInformation");
                System.out.println("Updating info");
                System.out.println("user.getPassword(): " + user.getPassword()); //deberia ser nueva contraseña plana
                System.out.println("patient.getUser().getPassword(): " + patient.getUser().getPassword()); //contraseña antigua encriptada
                String hashedPassword = PasswordEncryption.hashPassword(user.getPassword());
                
                
                if (!patient.getUser().getPassword().equals(hashedPassword)){ // comprobamos encriptadas para ver si la contraseña ha cambiado
                    patient.getUser().setPassword(hashedPassword); 
                    System.out.println("Hashed patient.getUser().getPassword(): " + patient.getUser().getPassword());// para comprobar si el hash se hace bien
                
                }
                
                out.writeObject(user);
                out.writeObject(patient);
                System.out.println(in.readObject());
                
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
                
                //When we send the report we create a thread that listens to the feedback and 
                //after receiving it closes the connection
                Thread receiveThread=new Thread(new Receive(in));
                receiveThread.start();
                
            } catch (IOException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
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

    class Receive implements Runnable {
        
        private ObjectInputStream in;
        private boolean running=true;
        
        public Receive(ObjectInputStream in){
            this.in=in;
        }
        
        public void stop() {
        running = false;
        try{
            in.close();
        }catch (IOException ex){
           Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        
        @Override
        public void run() {
            while (running) {
                // Continuously listens for incoming messages from the server
                handleAddressandPortFromServer();
                handleFeedbackFromServer();
            }
        }

        private void handleFeedbackFromServer() {
            try {
                List<Feedback> feedbacks= (List<Feedback>) in.readObject();
                out.writeObject("Feedback recedived!.");
                // message is shown in the swing interface
                //TODO debería msotrar todos los mensajes de los feedbacks por orden de fecha
                this.stop();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void handleAddressandPortFromServer() {
            try {
                String IpAddress= (String) in.readObject();
                int port= (int) in.readObject();
                out.writeObject("Server Address and Server Port recedived!");
                if(IpAddress != null && IpAddress.equals(serverAddress)){//IpAddress correcta
                    System.out.println("Correct ServerAddress:" +IpAddress);
                } else{
                    System.out.print("Incorrect ServerAddress to connect");}
                
                if (port == serverPort){
                     System.out.println("Correct ServerPort");
                } else { 
                    System.out.print("Not correct ServerPort to connect");
                }
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

}
