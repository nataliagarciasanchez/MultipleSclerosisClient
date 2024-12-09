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
public class MainPatientGUI {
    private static PatientServerCommunication patientServerCom;
    private static PatientServerCommunication.Send send;
    private static PatientServerCommunication.Receive receive;

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
            patientServerCom.start();
            send = patientServerCom.new Send();
            receive = patientServerCom.new Receive();

            JOptionPane.showMessageDialog(null, "Connected to the server successfully!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

            // Iniciar la interfaz gráfica
            SwingUtilities.invokeLater(() -> {
                FramePrincipal mainFrame = new FramePrincipal(send, receive);
                mainFrame.setVisible(true);
            });

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid port number. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            Logger.getLogger(MainPatientGUI.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            JOptionPane.showMessageDialog(null, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}