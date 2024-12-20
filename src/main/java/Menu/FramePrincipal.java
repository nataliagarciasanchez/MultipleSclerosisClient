/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;
import IOCommunication.PatientServerCommunication;
import javax.swing.*;

/**
 *
 * @author nataliagarciasanchez
 */
public class FramePrincipal extends JFrame{
    public FramePrincipal(PatientServerCommunication.Send send, PatientServerCommunication.Receive receive) {
        
        setTitle("Multiple Sclerosis Monitoring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLocationRelativeTo(null); 

       
        add(new PanelPrincipal(send, receive));
    }
    
}
