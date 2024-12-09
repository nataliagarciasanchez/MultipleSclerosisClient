/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import IOCommunication.PatientServerCommunication;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author nataliagarciasanchez
 */
public class PatientTempCommuUI {
    
    public static void main(String[] args) {
        try {
            // Configuración predeterminada del servidor (localhost y puerto 9000)
            String serverAddress = "127.0.0.1"; // Localhost
            int port = 9000; // Puerto predeterminado

            // Inicializar la conexión al servidor
            PatientServerCommunication patientServerCom = new PatientServerCommunication(serverAddress, port);
            patientServerCom.start(); // was missing, required to start communication
            PatientServerCommunication.Send send = patientServerCom.new Send();
            PatientServerCommunication.Receive receive = patientServerCom.new Receive();

            JOptionPane.showMessageDialog(null, "Connected to the server successfully!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

            // Iniciar la interfaz gráfica
            SwingUtilities.invokeLater(() -> {
                FramePrincipal mainFrame = new FramePrincipal(send, receive);
                mainFrame.setVisible(true);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Server no disponible", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
