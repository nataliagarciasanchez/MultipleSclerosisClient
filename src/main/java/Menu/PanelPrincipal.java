/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import javax.swing.*;
import java.awt.*;

import IOCommunication.PatientServerCommunication;
import IOCommunication.PatientServerCommunication.Send;
import Menu.Utilities.Utilities;
import static Menu.Utilities.Utilities.convertString2SqlDate;
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

    public PanelPrincipal(PatientServerCommunication.Send send) {
        this.send = send;

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
                        mainFrame.add(new SecondPanel(patient, send)); // Pass patient details to the next panel
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void showSignUpForm() {
        dynamicPanel.removeAll();

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear panel de formulario con GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Fondo transparente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10); // Espaciado reducido entre filas

        // Dimensiones ajustadas para campos más grandes
        Dimension labelSize = new Dimension(220, 40);
        Dimension fieldSize = new Dimension(350, 40);

        // Campos de texto y etiquetas
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setPreferredSize(labelSize);
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(fieldSize);
        nameField.setMaximumSize(fieldSize);
        nameLabel.setToolTipText("Enter your full first name. Example: John");

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        surnameLabel.setForeground(Color.WHITE);
        surnameLabel.setPreferredSize(labelSize);
        JTextField surnameField = new JTextField();
        surnameField.setPreferredSize(fieldSize);
        surnameField.setMaximumSize(fieldSize);
        surnameLabel.setToolTipText("Enter your last name. Example: Doe");

        JLabel nifLabel = new JLabel("NIF:");
        nifLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        nifLabel.setForeground(Color.WHITE);
        nifLabel.setPreferredSize(labelSize);
        JTextField nifField = new JTextField();
        nifField.setPreferredSize(fieldSize);
        nifField.setMaximumSize(fieldSize);
        nifLabel.setToolTipText("Enter a valid NIF including the letter. Example: 12345678A");

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        dobLabel.setForeground(Color.WHITE);
        dobLabel.setPreferredSize(labelSize);
        JTextField dobField = new JTextField();
        dobField.setPreferredSize(fieldSize);
        dobField.setMaximumSize(fieldSize);
        dobLabel.setToolTipText("Enter your birth date in dd/MM/yyyy format. Example: 10/10/2003");

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setPreferredSize(labelSize);
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(fieldSize);
        phoneField.setMaximumSize(fieldSize);
        phoneLabel.setToolTipText("Enter your phone number. With or without country code. Example: +34123456789");


        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        genderLabel.setForeground(Color.WHITE);
        genderLabel.setPreferredSize(labelSize);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"MALE", "FEMALE"});
        genderComboBox.setPreferredSize(fieldSize);
        genderComboBox.setMaximumSize(fieldSize);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setPreferredSize(labelSize);
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(fieldSize);
        usernameField.setMaximumSize(fieldSize);
        usernameLabel.setToolTipText("Enter a unique username. Example: johndoe@gmail.com");

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setPreferredSize(labelSize);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);
        passwordLabel.setToolTipText("Enter a strong password with at least 8 characters, including uppercase, lowercase, and a number.");

        // Agregar filas al panel de formulario
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(surnameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(nifLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nifField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(dobLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(genderLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(genderComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Botones
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
                JOptionPane.showMessageDialog(this, "Error during registration. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        // Añadir componentes al dynamicPanel
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        dynamicPanel.add(signUpLabel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dynamicPanel.add(formPanel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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
        label.setPreferredSize(new Dimension(150, 40));

        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));

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