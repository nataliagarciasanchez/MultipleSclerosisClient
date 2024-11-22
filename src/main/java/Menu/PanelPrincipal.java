/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import javax.swing.*;
import java.awt.*;

//import IOCommunication.PatientServerCommunication;
//import POJOs.Patient;


/**
 *
 * @author nataliagarciasanchez
 */
public class PanelPrincipal extends JPanel {
    private JPanel dynamicPanel; // Panel for dynamic content
    private JButton cancelButton;
    private JButton okButton;
    private JButton logInButton;
    private JButton signUpButton;

    public PanelPrincipal() {
        setBackground(new Color(153, 153, 153));
        setLayout(new BorderLayout());

        // Left panel for the logo
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(getClass().getResource("/images/LogoNeuroTrack.png")));
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(153, 153, 153));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 50));
        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // Right panel for the title and dynamic content
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(153, 153, 153));
        rightPanel.setLayout(new BorderLayout());

        // Title label with extra space above
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(153, 153, 153));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(Box.createRigidArea(new Dimension(0, 50))); // Add spacer above the title

        JLabel titleLabel = new JLabel("Multiple Sclerosis Monitoring");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 72)); // Original size restored
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacer below the title
        rightPanel.add(titlePanel, BorderLayout.NORTH);

        // Dynamic panel for question, buttons, and forms
        dynamicPanel = new JPanel();
        dynamicPanel.setBackground(new Color(153, 153, 153));
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS));

        rightPanel.add(dynamicPanel, BorderLayout.CENTER);

        // Initialize buttons
        logInButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        cancelButton = new JButton("Cancel");
        okButton = new JButton("OK");

        // Add action listeners for the buttons
        logInButton.addActionListener(e -> showLogInForm());
        signUpButton.addActionListener(e -> showSignUpForm());
        cancelButton.addActionListener(e -> showDefaultContent());
        okButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Action confirmed!"));

        // Add the left and right panels to the main layout
        add(leftPanel, BorderLayout.WEST); // Image on the left
        add(rightPanel, BorderLayout.CENTER);

        // Show default content on initialization
        showDefaultContent();
    }

    private void showDefaultContent() {
        dynamicPanel.removeAll(); // Clear existing content

        // Title for the default question
        JLabel questionLabel = new JLabel("What do you want to do?");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Bold and large font
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons for Sign Up and Log In
        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setBackground(new Color(153, 153, 153)); // Match background
        topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Center-align buttons
        topButtonPanel.add(signUpButton);
        topButtonPanel.add(logInButton);

        // Add components to the dynamic panel in the desired order
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 150))); // Spacer to lower everything
        dynamicPanel.add(questionLabel); // Add the question at the top
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Spacer (20 pixels)
        dynamicPanel.add(topButtonPanel); // Add buttons directly below the question

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void showLogInForm() {
        dynamicPanel.removeAll(); // Clear existing content

        // Title for the Log In form
        JLabel logInLabel = new JLabel("Log In");
        logInLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logInLabel.setForeground(Color.WHITE);
        logInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Define font size
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 24);
        int fontSize = fieldFont.getSize();
        int fieldHeight = fontSize + 10; // Height proportional to font size
        int fieldWidth = fontSize * 20; // Width proportional to font size

        // Username row
        JPanel usernameRow = new JPanel();
        usernameRow.setBackground(new Color(153, 153, 153));
        usernameRow.setLayout(new BoxLayout(usernameRow, BoxLayout.X_AXIS));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setPreferredSize(new Dimension(120, fieldHeight)); // Fixed width
        JTextField usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(new Dimension(fieldWidth, fieldHeight)); // Preferred size
        usernameField.setMaximumSize(new Dimension(fieldWidth, fieldHeight)); // Max size to ensure proper alignment
        usernameField.setMinimumSize(new Dimension(fieldWidth, fieldHeight)); // Min size to enforce proportionality

        usernameRow.add(usernameLabel);
        usernameRow.add(usernameField);

        // Password row
        JPanel passwordRow = new JPanel();
        passwordRow.setBackground(new Color(153, 153, 153));
        passwordRow.setLayout(new BoxLayout(passwordRow, BoxLayout.X_AXIS));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setPreferredSize(new Dimension(120, fieldHeight)); // Fixed width
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(fieldWidth, fieldHeight)); // Preferred size
        passwordField.setMaximumSize(new Dimension(fieldWidth, fieldHeight)); // Max size to ensure proper alignment
        passwordField.setMinimumSize(new Dimension(fieldWidth, fieldHeight)); // Min size to enforce proportionality

        passwordRow.add(passwordLabel);
        passwordRow.add(passwordField);

        // Buttons (Cancel and OK)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(153, 153, 153));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(cancelButton);
        okButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        //HOLA
        /*
        try {
            
            if ("Successful login") {
                // If login is successful, navigate to SecondPanel
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new SecondPanel());
                mainFrame.revalidate();
                mainFrame.repaint();
            } else {
                // If login fails, show an error message
                JOptionPane.showMessageDialog(this, "Incorrect username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while trying to log in. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }*/
        });
        buttonPanel.add(okButton);

        // Add components to the dynamic panel in the desired order
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Spacer
        dynamicPanel.add(logInLabel); // Title at the top
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        dynamicPanel.add(usernameRow); // Username row
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        dynamicPanel.add(passwordRow); // Password row
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        dynamicPanel.add(buttonPanel); // Buttons at the bottom

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void showSignUpForm() {
        dynamicPanel.removeAll(); // Clear existing content

        // Title for the Sign Up form
        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Define font size
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 24);
        int fontSize = fieldFont.getSize();
        int fieldHeight = fontSize + 10; // Height proportional to font size
        int fieldWidth = fontSize * 20; // Width proportional to font size

        // Username row
        JPanel usernameRow = new JPanel();
        usernameRow.setBackground(new Color(153, 153, 153));
        usernameRow.setLayout(new BoxLayout(usernameRow, BoxLayout.X_AXIS));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setPreferredSize(new Dimension(120, fieldHeight)); // Fixed width
        JTextField usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        usernameField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        usernameField.setMinimumSize(new Dimension(fieldWidth, fieldHeight));

        usernameRow.add(usernameLabel);
        usernameRow.add(usernameField);

        // Password row
        JPanel passwordRow = new JPanel();
        passwordRow.setBackground(new Color(153, 153, 153));
        passwordRow.setLayout(new BoxLayout(passwordRow, BoxLayout.X_AXIS));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setPreferredSize(new Dimension(120, fieldHeight)); // Fixed width
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setMinimumSize(new Dimension(fieldWidth, fieldHeight));

        passwordRow.add(passwordLabel);
        passwordRow.add(passwordField);

        // Buttons (Cancel and OK)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(153, 153, 153));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        // Add components to the dynamic panel in the desired order
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Spacer
        dynamicPanel.add(signUpLabel); // Title at the top
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        dynamicPanel.add(usernameRow); // Username row
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        dynamicPanel.add(passwordRow); // Password row
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        dynamicPanel.add(buttonPanel); // Buttons at the bottom

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
}