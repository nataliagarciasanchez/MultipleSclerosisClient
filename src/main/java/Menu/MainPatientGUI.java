/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import IOCommunication.PatientServerCommunication;
import java.awt.GridLayout;
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
            JPanel connectionPanel = new JPanel(new GridLayout(2,2,10,10));
            JTextField serverAddressField = new JTextField();
            JTextField portField = new JTextField();
            
            connectionPanel.add(new JLabel("Server IP Address: "));
            connectionPanel.add(serverAddressField);
            connectionPanel.add(new JLabel("Server Port: "));
            connectionPanel.add(portField);
            
            int result = JOptionPane.showConfirmDialog(null, connectionPanel, "Server connection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if(result != JOptionPane.OK_OPTION){
                JOptionPane.showMessageDialog(null, "Connection canceled. Exiting application.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String serverAddress = serverAddressField.getText().trim();
            String portInput = portField.getText().trim();
            
            if (serverAddress.isEmpty() || portInput.isEmpty()) {
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