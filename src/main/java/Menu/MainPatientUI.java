/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import IOCommunication.PatientServerCommunication;
import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nataliagarciasanchez
 */
public class MainPatientUI {
    private static PatientServerCommunication patientServerCom;
    private static PatientServerCommunication.Send send;

    public static void main(String[] args) {
        // Pedir al usuario el IP del servidor y el puerto
        try {
            String serverAddress = JOptionPane.showInputDialog(null, "Enter the Server IP Address:", "Server Connection", JOptionPane.QUESTION_MESSAGE);
            String portInput = JOptionPane.showInputDialog(null, "Enter the Server Port:", "Server Connection", JOptionPane.QUESTION_MESSAGE);

            if (serverAddress == null || portInput == null || serverAddress.isEmpty() || portInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Server IP and Port are required. Exiting the application.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir si no se proporciona la información del servidor
            }

            int port = Integer.parseInt(portInput);

            // Inicializar la conexión al servidor
            patientServerCom = new PatientServerCommunication(serverAddress, port);
            send = patientServerCom.new Send();

            JOptionPane.showMessageDialog(null, "Connected to the server successfully!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

            // Iniciar la interfaz gráfica
            SwingUtilities.invokeLater(() -> {
                FramePrincipal mainFrame = new FramePrincipal(patientServerCom);
                mainFrame.setVisible(true);
            });

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid port number. Exiting the application.", "Error", JOptionPane.ERROR_MESSAGE);
        }/*catch (IOException e) {
            Logger.getLogger(MainPatientUI.class.getName()).log(Level.SEVERE, "Error connecting to server", e);
            JOptionPane.showMessageDialog(null, "Could not connect to the server. Please check the server IP and port.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }
}
