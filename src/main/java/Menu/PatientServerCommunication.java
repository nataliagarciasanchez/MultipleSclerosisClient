/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author maipa
 */
public class PatientServerCommunication {
    
    private String serverAddress = "localhost";
    private int serverPort = 12345;

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
}
