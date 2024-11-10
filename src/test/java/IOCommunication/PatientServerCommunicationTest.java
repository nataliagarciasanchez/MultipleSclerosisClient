/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package IOCommunication;

import POJOs.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author maipa
 */
public class PatientServerCommunicationTest {
    
    public PatientServerCommunicationTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of register method, of class PatientServerCommunication.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        String username = "";
        String password = "";
        PatientServerCommunication instance = new PatientServerCommunication("localhost",9000);
        instance.register(username, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class PatientServerCommunication.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String username = "";
        String password = "";
        PatientServerCommunication instance = null;
        instance.login(username, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePassword method, of class PatientServerCommunication.
     */
    @Test
    public void testChangePassword() {
        System.out.println("changePassword");
        String username = "";
        String oldPassword = "";
        String newPassword = "";
        PatientServerCommunication instance = null;
        instance.changePassword(username, oldPassword, newPassword);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findPatient method, of class PatientServerCommunication.
     */
    @Test
    public void testFindPatient() throws Exception {
        System.out.println("findPatient");
        String username = "";
        String password = "";
        PatientServerCommunication instance = null;
        instance.findPatient(username, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendECGSignals method, of class PatientServerCommunication.
     */
    @Test
    public void testSendECGSignals() {
        System.out.println("sendECGSignals");
        PatientServerCommunication instance = null;
        instance.sendECGSignals();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendEMGSignals method, of class PatientServerCommunication.
     */
    @Test
    public void testSendEMGSignals() {
        System.out.println("sendEMGSignals");
        PatientServerCommunication instance = null;
        instance.sendEMGSignals();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPatient method, of class PatientServerCommunication.
     */
    @Test
    public void testGetPatient() {
        System.out.println("getPatient");
        PatientServerCommunication instance = null;
        Patient expResult = null;
        Patient result = instance.getPatient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
