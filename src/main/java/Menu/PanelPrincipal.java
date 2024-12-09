/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import javax.swing.*;
import java.awt.*;

import IOCommunication.PatientServerCommunication;
import Menu.Utilities.Utilities;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.Role;
import POJOs.User;
import java.sql.Date;


/**
 *
 * @author nataliagarciasanchez
 */

public class PanelPrincipal extends JPanel {
    private JPanel dynamicPanel; // Panel for dynamic content
    private final Image backgroundImage; // Background image
    private final PatientServerCommunication.Send send;
    public static PatientServerCommunication.Receive receive;

    public PanelPrincipal(PatientServerCommunication.Send send, PatientServerCommunication.Receive receive) {
        this.send = send;
        this.receive = receive; 

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("/images/Fondo.jpg")).getImage();

        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false); // Transparent background
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(Box.createRigidArea(new Dimension(0, 100))); // Spacer from the top

        JLabel titleLabel = new JLabel("Multiple Sclerosis Monitoring");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 52));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 40))); // Spacer below the title

        // Dynamic Panel for question and buttons
        dynamicPanel = new JPanel();
        dynamicPanel.setOpaque(false); // Transparent background
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS));

        showDefaultContent();

        add(titlePanel, BorderLayout.NORTH); // Add title panel
        add(dynamicPanel, BorderLayout.CENTER); // Add dynamic panel
    }

    private void showDefaultContent() {
        dynamicPanel.removeAll(); // Clear existing content

        JLabel questionLabel = new JLabel("What do you want to do?");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons for Sign Up and Log In
        JButton signUpButton = new JButton("Sign Up");
        JButton logInButton = new JButton("Log In");

        signUpButton.addActionListener(e -> showSignUpForm());
        logInButton.addActionListener(e -> showLogInForm());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(signUpButton);
        buttonPanel.add(logInButton);

        // Add components to the dynamic panel
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 150))); // Spacer to lower everything
        dynamicPanel.add(questionLabel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Spacer
        dynamicPanel.add(buttonPanel);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void showLogInForm() {
        dynamicPanel.removeAll();

        JLabel logInLabel = new JLabel("Log In");
        logInLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logInLabel.setForeground(Color.WHITE);
        logInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel usernamePanel = createRow("Username:", usernameField);
        JPanel passwordPanel = createRow("Password:", passwordField);

        JButton cancelButton = new JButton("Cancel");
        JButton okButton = new JButton("OK");

        cancelButton.addActionListener(e -> showDefaultContent());
        okButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();


            try {

                Patient patient = null;
                while (patient == null) {
                    patient = send.login(username, password); // Communicate with server  
                    if (patient != null) {
                        JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Proceed to the next panel with patient details
                        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        mainFrame.getContentPane().removeAll();
                        mainFrame.add(new SecondPanel(patient, send, receive)); // Pass patient details to the next panel
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error during login", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        dynamicPanel.add(logInLabel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dynamicPanel.add(usernamePanel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dynamicPanel.add(passwordPanel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dynamicPanel.add(buttonPanel);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
    
    private JPanel createRow(String labelText, JComponent field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(220, 40));
        label.setHorizontalAlignment(SwingConstants.LEFT); // Alineación a la izquierda

        field.setPreferredSize(new Dimension(350, 40));
        field.setMaximumSize(new Dimension(350, 40));

        row.add(label);
        row.add(Box.createRigidArea(new Dimension(10, 0))); 
        row.add(field);

        return row;
    }
    
    private void showSignUpForm() {
        dynamicPanel.removeAll();

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(350, 30));
        nameField.setMaximumSize(new Dimension(350, 30));
        JPanel namePanel = createAlignedRow("Name:", nameField);

        JTextField surnameField = new JTextField();
        surnameField.setPreferredSize(new Dimension(350, 30));
        surnameField.setMaximumSize(new Dimension(350, 30));
        JPanel surnamePanel = createAlignedRow("Surname:", surnameField);

        JTextField nifField = new JTextField();
        nifField.setPreferredSize(new Dimension(350, 30));
        nifField.setMaximumSize(new Dimension(350, 30));
        JPanel nifPanel = createAlignedRow("NIF:", nifField);

        JTextField dobField = new JTextField();
        dobField.setPreferredSize(new Dimension(350, 30));
        dobField.setMaximumSize(new Dimension(350, 30));
        JPanel dobPanel = createAlignedRow("Date of Birth:", dobField);

        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(350, 30));
        phoneField.setMaximumSize(new Dimension(350, 30));
        JPanel phonePanel = createAlignedRow("Phone:", phoneField);

        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"MALE", "FEMALE"});
        genderComboBox.setPreferredSize(new Dimension(350, 30));
        genderComboBox.setMaximumSize(new Dimension(350, 30));
        JPanel genderPanel = createAlignedRow("Gender:", genderComboBox);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(350, 30));
        usernameField.setMaximumSize(new Dimension(350, 30));
        JPanel usernamePanel = createAlignedRow("Username:", usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(350, 30));
        passwordField.setMaximumSize(new Dimension(350, 30));
        JPanel passwordPanel = createAlignedRow("Password:", passwordField);

        JButton cancelButton = new JButton("Cancel");
        JButton okButton = new JButton("OK");

        cancelButton.addActionListener(e -> showDefaultContent());
        okButton.addActionListener(e -> {
            try {
                Date dob = Utilities.convertString2SqlDate(dobField.getText().trim());
                Gender gender = Gender.valueOf((String) genderComboBox.getSelectedItem());
                Patient patient = new Patient(
                    nameField.getText().trim(),
                    surnameField.getText().trim(),
                    nifField.getText().trim(),
                    dob,
                    gender,
                    phoneField.getText().trim()
                );
                User user = new User(usernameField.getText().trim(), new String(passwordField.getPassword()).trim(), new Role());
                patient.setUser(user);
                send.register(patient);
                
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showDefaultContent();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error during registration:" +ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS)); 
        dynamicPanel.add(Box.createVerticalGlue());
        dynamicPanel.add(signUpLabel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 30))); 
        dynamicPanel.add(namePanel);
        dynamicPanel.add(surnamePanel);
        dynamicPanel.add(nifPanel);
        dynamicPanel.add(dobPanel);
        dynamicPanel.add(phonePanel);
        dynamicPanel.add(genderPanel);
        dynamicPanel.add(usernamePanel);
        dynamicPanel.add(passwordPanel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dynamicPanel.add(buttonPanel);
        dynamicPanel.add(Box.createVerticalGlue()); 

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private JPanel createAlignedRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0)); 
        row.setOpaque(false); 

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(220, 30)); 

        field.setPreferredSize(new Dimension(350, 30)); 
        field.setMaximumSize(new Dimension(350, 30));

        row.add(label);
        row.add(field);

        return row;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}