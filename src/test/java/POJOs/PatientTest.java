/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package POJOs;

import java.sql.Date;
import java.util.List;
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
public class PatientTest {
    
    public PatientTest() {
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
     * Test of getSerialVersionUID method, of class Patient.
     */
    @Test
    public void testGetSerialVersionUID() {
        System.out.println("getSerialVersionUID");
        long expResult = 0L;
        long result = Patient.getSerialVersionUID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Patient.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Patient instance = new Patient();
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Patient.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Patient instance = new Patient();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDob method, of class Patient.
     */
    @Test
    public void testGetDob() {
        System.out.println("getDob");
        Patient instance = new Patient();
        Date expResult = null;
        Date result = instance.getDob();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGender method, of class Patient.
     */
    @Test
    public void testGetGender() {
        System.out.println("getGender");
        Patient instance = new Patient();
        Gender expResult = null;
        Gender result = instance.getGender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPhone method, of class Patient.
     */
    @Test
    public void testGetPhone() {
        System.out.println("getPhone");
        Patient instance = new Patient();
        String expResult = "";
        String result = instance.getPhone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDoctor method, of class Patient.
     */
    @Test
    public void testGetDoctor() {
        System.out.println("getDoctor");
        Patient instance = new Patient();
        Doctor expResult = null;
        Doctor result = instance.getDoctor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReports method, of class Patient.
     */
    @Test
    public void testGetReports() {
        System.out.println("getReports");
        Patient instance = new Patient();
        List<Report> expResult = null;
        List<Report> result = instance.getReports();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Patient.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer id = null;
        Patient instance = new Patient();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Patient.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Patient instance = new Patient();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class Patient.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        Patient instance = new Patient();
        User expResult = null;
        User result = instance.getUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSurname method, of class Patient.
     */
    @Test
    public void testGetSurname() {
        System.out.println("getSurname");
        Patient instance = new Patient();
        String expResult = "";
        String result = instance.getSurname();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDob method, of class Patient.
     */
    @Test
    public void testSetDob() {
        System.out.println("setDob");
        Date dob = null;
        Patient instance = new Patient();
        instance.setDob(dob);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGender method, of class Patient.
     */
    @Test
    public void testSetGender() {
        System.out.println("setGender");
        Gender gender = null;
        Patient instance = new Patient();
        instance.setGender(gender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPhone method, of class Patient.
     */
    @Test
    public void testSetPhone() {
        System.out.println("setPhone");
        String phone = "";
        Patient instance = new Patient();
        instance.setPhone(phone);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDoctor method, of class Patient.
     */
    @Test
    public void testSetDoctor() {
        System.out.println("setDoctor");
        Doctor doctor = null;
        Patient instance = new Patient();
        instance.setDoctor(doctor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReports method, of class Patient.
     */
    @Test
    public void testSetReports() {
        System.out.println("setReports");
        List<Report> reports = null;
        Patient instance = new Patient();
        instance.setReports(reports);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Patient.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Patient instance = new Patient();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Patient.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Patient instance = new Patient();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Patient.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Patient instance = new Patient();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
