/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import POJOs.User;
import POJOs.Patient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    
  
    public PatientServerCommunication (String serverAddress,int serverPort){
        
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;//9000
        try {
            this.socket=new Socket(serverAddress,serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            
            //el patient debe poder recibir feedback del server mientras manda las solicitudes 
            // Thread receiveThread=new Thread(new Receive());
            //receiveThread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public class Send {

        /**
         * Calls the server so the patient registers in the app and, therefore,
         * it is saved in the database
         *
         * @param username
         * @param password
         * @param patient
         */
        public void register(String username, String password, Patient patient) {
            try {
                out.writeObject("register"); // Acción de registro
                out.writeObject(new User(username, password));//envía los datos de registro al server
                out.writeObject(patient);
                
                out.flush();
                
                System.out.println("Registering.....");
                String confirmation=(String) in.readObject();
                System.out.println(confirmation);//muestra la confirmación del server de que se ha registrado
                
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
         */
        public void login(String username, String password) {

            try {
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                out.writeObject(password);
                System.out.println(in.readObject());//mensaje del server de que se ha recibido
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        /**
         * Logs out of the app by closing all connections from that patient to the server
         */
        public void logout(){
            try {
                out.writeObject("logout");
                System.out.println(in.readObject());
                //ahora mismo lo hace el server cuando recive esta opcion
                //releaseResources(in, out, socket);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Changes the current password of the patient
         *
         * @param username
         * @param oldPassword
         * @param newPassword
         */
        public void changePassword(String username, String oldPassword, String newPassword) {

            try {
                out.writeObject("changePassword"); // Acción de cambio de contraseña
                out.writeObject(username);
                out.writeObject(oldPassword);
                out.writeObject(newPassword);
                System.out.println(in.readObject());
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Method used to find the patient corresponding to the username and
         * password in the database
         *
         * @param username
         * @param password
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public void findPatient(String username, String password) throws IOException, ClassNotFoundException {

            out.writeObject("findPatient"); // Acción para buscar al paciente
            out.writeObject(username);
            out.writeObject(password);

            // Lee y muestra la respuesta del servidor
            Object response = in.readObject();
            if (response instanceof Patient) {
                patient = (Patient) response; // Guarda el objeto `Patient`
                //System.out.println("Patient found and stored: " + patient);
            } else {
                System.out.println(response); // Imprimir mensaje de error si es un String
            }

        }

        public void sendECGSignals() {
            //TODO manda la señales al server
        }

        public void sendEMGSignals() {
            //TODO manda las señales al server
        }

        public Patient getPatient() {
            return patient;
        }
        
        private static void releaseResources(ObjectInputStream in, ObjectOutputStream out, Socket socket){
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
        
        private boolean running=true;
        
        public void stop() {
        running = false;
        }
        
        @Override
        public void run() {
            try {
                while (running) {
                    // Continuously listens for incoming messages from the server
                    Object feedback = in.readObject();
                    handleFeedbackFromServer(feedback);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void handleFeedbackFromServer(Object message) {
            // Process messages received from the server
            System.out.println("Received from server: " + message);
        }
    }
    
    

}
