/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import BITalino.BITalino;
import BITalino.Frame;
import POJOs.Bitalino;
import POJOs.User;
import POJOs.Patient;
import POJOs.SignalType;
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
        this.serverPort = serverPort;//9000
        try {
            this.socket = new Socket(serverAddress, serverPort);
            
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
    
    /*public void start(){
        try {
            this.out=new ObjectOutputStream(socket.getOutputStream());
            this.in=new ObjectInputStream(socket.getInputStream());
            System.out.println("Patient connected to server");
            //new Thread(new Receive(in)).start();
        } catch (IOException ex) {
            Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public class Send{
        

        /**
         * Calls the server so the patient registers in the app and, therefore,
         * it is saved in the database
         *
         * @param patient
         */
        public void register(Patient patient) {
            try {
                out.writeObject("register"); // Acción de registro
                out.writeObject(patient.getUser());//envía los datos de registro al server
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
         * @return message
         */
        public Patient login(String username, String password) {
            
            try {
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                out.writeObject(password);
                System.out.println("Logging in.....");
                patient= (Patient) in.readObject();
  
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
         */
        public void updateInformation(User user) {

            try {
                out.writeObject("updateInformation");
                out.writeObject(user);
                System.out.println(in.readObject());
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                releaseResources(in,out,socket);//TODO quitar el cerrar, solo para la prueba
            }

        }
        

        public void sendECGSignals(Bitalino bitalino, BITalino bitalinoDevice) {
            try {
                List<Frame> ecgFrames=bitalino.storeRecordedSignals(bitalinoDevice, SignalType.ECG);
                // Send the ECG frames to the server
                out.writeObject("sendECGSignals");
                out.writeObject(ecgFrames);
                out.flush();
                System.out.println("ECG Signals sent.");

                // Read the server's response
                String response = (String) in.readObject();
                System.out.println("Server response: " + response);
            } catch (Throwable ex) {
                Logger.getLogger(PatientServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        public void sendEMGSignals(Bitalino bitalino, BITalino bitalinoDevice) {
            try {
                List<Frame> emgFrames=bitalino.storeRecordedSignals(bitalinoDevice, SignalType.EMG);
                // Send the ECG frames to the server
                out.writeObject("sendEMGSignals");
                out.writeObject(emgFrames);
                out.flush();
                System.out.println("EMG Signals sent.");

                // Read the server's response
                String response = (String) in.readObject();
                System.out.println("Server response: " + response);
                
            } catch (Throwable ex) {
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
