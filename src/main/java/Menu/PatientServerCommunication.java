/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import POJOs.Patient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author maipa
 */
public class PatientServerCommunication {
    
    private String serverAddress = "localhost";//TODO aquí habrá que crear un constructor para cuando 
    //preguntemos por el IP address del server
    private int serverPort = 9000;//es 9000 porque es el puerto vacío por defecto
    private Patient patient;

    public void register(String username, String password) throws IOException, ClassNotFoundException {
        
        //crea un nuevo socket 
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("register"); // Acción de registro
            out.writeObject(new User(username, password));//envía los datos de registro al server
            System.out.println(in.readObject());//
        }
    }

    public void login(String username, String password) throws IOException, ClassNotFoundException {
        
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("login"); // Acción de inicio de sesión
            out.writeObject(username);
            out.writeObject(password);
            System.out.println(in.readObject());
        }
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("changePassword"); // Acción de cambio de contraseña
            out.writeObject(username);
            out.writeObject(oldPassword);
            out.writeObject(newPassword);
            System.out.println(in.readObject());
        }
    }
    
    /**
     * findPatient es un método utilizado para buscar un paciente en la base de datos usando usuario y contraseña
     * Busca y almacena un objeto de tipo Patient
     * @param username
     * @param password
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void findPatient(String username, String password) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("findPatient"); // Acción para buscar al paciente
            out.writeObject(username);
            out.writeObject(password);

            // Lee y muestra la respuesta del servidor
            Object response = in.readObject();
            if (response instanceof Patient) {
                
                patient = (Patient) response; // Guarda el objeto `Patient`
                //System.out.println("Patient found and stored locally: " + patient);
            } else {
                System.out.println(response); // Imprimir mensaje de error si es un String
            }
        }
    }

    public Patient getPatient() {
        return patient;
    }
    
    
}
