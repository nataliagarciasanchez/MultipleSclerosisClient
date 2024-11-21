/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import javax.swing.*;
import java.awt.*;

import IOCommunication.PatientServerCommunication;
import POJOs.Patient;

/**
 *
 * @author nataliagarciasanchez
 */
public class PanelPrincipal extends JPanel {
    private JPanel dynamicPanel; // Panel para contenido dinámico
    private JButton cancelButton;
    private JButton okButton;
    private JButton logInButton;
    private JButton signUpButton;
    private PatientServerCommunication patientServerCom;

    public PanelPrincipal(PatientServerCommunication patientServerCom) {
        this.patientServerCom = patientServerCom;

        setBackground(new Color(153, 153, 153));
        setLayout(new BorderLayout());

        // Panel izquierdo con el logo
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(getClass().getResource("/images/LogoNeuroTrack.png")));
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(153, 153, 153));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 50));
        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // Panel derecho para el título y contenido dinámico
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(153, 153, 153));
        rightPanel.setLayout(new BorderLayout());

        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(153, 153, 153));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JLabel titleLabel = new JLabel("Multiple Sclerosis Monitoring");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel dinámico
        dynamicPanel = new JPanel();
        dynamicPanel.setBackground(new Color(153, 153, 153));
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS));

        rightPanel.add(dynamicPanel, BorderLayout.CENTER);

        // Botones iniciales
        logInButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        cancelButton = new JButton("Cancel");
        okButton = new JButton("OK");

        logInButton.addActionListener(e -> showLogInForm());
        signUpButton.addActionListener(e -> showSignUpForm());
        cancelButton.addActionListener(e -> showDefaultContent());

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        showDefaultContent(); // Mostrar contenido inicial
    }

    private void showDefaultContent() {
        dynamicPanel.removeAll();

        JLabel questionLabel = new JLabel("What do you want to do?");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setBackground(new Color(153, 153, 153));
        topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        topButtonPanel.add(signUpButton);
        topButtonPanel.add(logInButton);

        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 150)));
        dynamicPanel.add(questionLabel);
        dynamicPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        dynamicPanel.add(topButtonPanel);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void showLogInForm() {
        dynamicPanel.removeAll();

        JLabel logInLabel = new JLabel("Log In");
        logInLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logInLabel.setForeground(Color.WHITE);
        logInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JButton okLogInButton = new JButton("OK");
        okLogInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                patientServerCom.new Send().login(username, password);
                JOptionPane.showMessageDialog(this, "Login Successful!");
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new SecondPanel());
                mainFrame.revalidate();
                mainFrame.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                showDefaultContent();
            }
        });

        dynamicPanel.add(logInLabel);
        dynamicPanel.add(usernameField);
        dynamicPanel.add(passwordField);
        dynamicPanel.add(okLogInButton);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void showSignUpForm() {
        dynamicPanel.removeAll();

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JButton okSignUpButton = new JButton("OK");
        okSignUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Patient patient = new Patient(); // Crear un objeto paciente con datos adicionales si es necesario
            try {
                patientServerCom.new Send().register(username, password, patient);
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                showDefaultContent();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Registration Failed.", "Error", JOptionPane.ERROR_MESSAGE);
                showDefaultContent();
            }
        });

        dynamicPanel.add(signUpLabel);
        dynamicPanel.add(usernameField);
        dynamicPanel.add(passwordField);
        dynamicPanel.add(okSignUpButton);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }
}
