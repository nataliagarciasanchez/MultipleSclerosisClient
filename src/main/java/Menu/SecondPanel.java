/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import POJOs.Patient;
import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.*;

/**
 *
 * @author nataliagarciasanchez
 */
public class SecondPanel extends JPanel {
    private JPanel whitePanel; // Dynamic white panel
    private JLabel titleLabel; // Main title
    private java.util.List<String> symptomsList; // Symptom list
    private Patient patient;
    private final Image backgroundImage; // Background image
    
    public SecondPanel(Patient patient) {
        this.patient = patient;
        symptomsList = new LinkedList<>(); // Initialize symptoms list

        // Load the background image
        backgroundImage = new ImageIcon(getClass().getResource("/images/Fondo.jpg")).getImage();

        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Create a split container for left and right sections
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setOpaque(false);

        // Left panel with logo and buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false); // Transparent to allow background visibility
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // Reduce top padding

        // Create a panel for the logo and title alignment
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource("/images/LogoSmall.jpeg")));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appLabel = new JLabel("NeuroTrack");
        appLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        appLabel.setForeground(Color.WHITE);
        appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer to align with title
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between logo and app name
        logoPanel.add(appLabel);

        // Add the logo panel to the left panel
        leftPanel.add(logoPanel, BorderLayout.NORTH);

        // Buttons with pink rectangular background
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent to allow customization
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Espacio entre NeuroTrack y los botones

        // Create styled buttons
        JButton viewInfoButton = createStyledButton("View My Information");
        JButton viewDoctorButton = createStyledButton("View Doctor Information");
        JButton startMonitoringButton = createStyledButton("Start Monitoring");
        JButton settingsButton = createStyledButton("Settings");
        
        // Add ActionListener to "View My Information" button
        viewInfoButton.addActionListener(e -> displayPatientInfo());
        viewDoctorButton.addActionListener(e -> {
            if (patient.getDoctor() != null) {
                displayDoctorInfo();
            } else {
                JOptionPane.showMessageDialog(this, "No doctor assigned to this patient.", "Doctor Info", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add buttons to the button panel with spacing
        buttonPanel.add(viewInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewDoctorButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(startMonitoringButton);
        buttonPanel.add(Box.createVerticalGlue()); // Spacer before settings button
        buttonPanel.add(settingsButton);

        // Add the button panel to the left panel
        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add left panel to the split container
        mainContainer.add(leftPanel, BorderLayout.WEST);

        // Right panel for title and white panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        // Title label above the white panel
        titleLabel = new JLabel("Welcome to the MultipleSclerosis Patient app!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a wrapper panel for the title to adjust spacing and alignment
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); // Transparent to allow background visibility
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Adjust spacing (top, left, bottom, right)
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        rightPanel.add(titlePanel, BorderLayout.NORTH);

        // Wrapper panel to provide spacing for the white panel
        JPanel whitePanelWrapper = new JPanel();
        whitePanelWrapper.setLayout(new BorderLayout());
        whitePanelWrapper.setOpaque(false); // Transparent so the background image is visible
        whitePanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add spacing (top, left, bottom, right)

        // White panel for dynamic content
        whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));

        // Add the white panel to the wrapper
        whitePanelWrapper.add(whitePanel, BorderLayout.CENTER);

        // Add white panel wrapper to the right panel
        rightPanel.add(whitePanelWrapper, BorderLayout.CENTER);

        // Add right panel to the split container
        mainContainer.add(rightPanel, BorderLayout.CENTER);

        // Add the main container to the main panel
        add(mainContainer, BorderLayout.CENTER);

        // Add action to the "Start Monitoring" button
        startMonitoringButton.addActionListener(e -> showMonitoringIntroduction());
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true); // Asegurarse de que el botón sea opaco
        button.setBackground(Color.WHITE); // Fondo blanco para el botón
        button.setForeground(Color.BLACK); // Texto en negro
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Borde negro para el botón
        return button;
    }


    // Método para mostrar información del paciente en el panel blanco
    private void displayPatientInfo() {
        // Limpia el contenido del panel blanco
        whitePanel.removeAll();

        // Panel interno con padding
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado dentro del panel blanco

        // Añade la información del paciente con comprobación de nulos
        contentPanel.add(createInfoLine("Name: ", patient.getName() != null ? patient.getName() + " " + patient.getSurname() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("DNI: ", patient.getNIF() != null ? patient.getNIF() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Birth Date: ", patient.getDob() != null ? patient.getDob().toString() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Gender: ", patient.getGender() != null ? patient.getGender().toString() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Phone: ", patient.getPhone() != null ? patient.getPhone() : "N/A"));

        // Añade el panel interno al panel blanco (alineado a la izquierda)
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(contentPanel);

        // Actualiza el contenido del panel blanco
        whitePanel.revalidate();
        whitePanel.repaint();
    }

    
    // Método auxiliar para crear una línea con dos etiquetas
    private JPanel createInfoLine(String labelText, String valueText) {
        JPanel linePanel = new JPanel();
        linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS)); // Alinea horizontalmente
        linePanel.setBackground(Color.WHITE);
        linePanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinea el panel a la izquierda

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Negrita
        linePanel.add(label);

        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Normal
        linePanel.add(value);

        return linePanel;
    }
    
    private void displayDoctorInfo() {
        // Limpia el contenido del panel blanco
        whitePanel.removeAll();

        // Panel interno con padding
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado dentro del panel blanco

        // Añade la información del doctor con comprobación de nulos
        contentPanel.add(createInfoLine("Name: ", patient.getDoctor().getName() != null ? patient.getDoctor().getName() + " " + patient.getDoctor().getSurname() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Specialty: ", patient.getDoctor().getSpecialty() != null ? patient.getDoctor().getSpecialty() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("User: ", patient.getDoctor().getUser() != null ? patient.getDoctor().getUser().toString() : "N/A"));

        // Añade el panel interno al panel blanco
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(contentPanel);

        // Actualiza el contenido del panel blanco
        whitePanel.revalidate();
        whitePanel.repaint();
    }

    


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Ensure the background image fills the entire panel dynamically
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void showMonitoringIntroduction() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS)); // Reset layout to BoxLayout


        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel introLabel = new JLabel("<html><center>Welcome to your remote Multiple Sclerosis Clinic.<br>" +
            "We are now going to attend to your needs.<br>" +
            "To make sure we take care of all your needs and report to your doctor,<br>" +
            "we would like to ask you to select all the symptoms you are currently experiencing.</center></html>");
        introLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(150, 40));
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> showSymptomsPanel());

        whitePanel.add(Box.createVerticalGlue());
        whitePanel.add(introLabel);
        whitePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        whitePanel.add(startButton);
        whitePanel.add(Box.createVerticalGlue());
        
        whitePanel.add(centerPanel); // Add center panel to white panel

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void showSymptomsPanel() {
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco
        whitePanel.setLayout(new BorderLayout());

        // Texto reducido encima de las columnas
        JLabel instructionLabel = new JLabel("Select all the symptoms you are currently experiencing:");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(instructionLabel, BorderLayout.NORTH);

        // Panel para las casillas de verificación
        JPanel symptomsPanel = new JPanel();
        symptomsPanel.setBackground(Color.WHITE);
        symptomsPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columnas con espaciado
        symptomsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado interno

        // Añadir casillas de verificación para los 100 síntomas
        for (String symptom : IntStream.range(1, 51).mapToObj(i -> "Symptom " + i).toList()) {
            JCheckBox symptomCheckBox = new JCheckBox(symptom);
            symptomCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Tamaño más grande
            symptomCheckBox.setBackground(Color.WHITE);
            symptomsPanel.add(symptomCheckBox);
            symptomCheckBox.addActionListener(e -> {
                if (symptomCheckBox.isSelected()) {
                    symptomsList.add(symptom);
                } else {
                    symptomsList.remove(symptom);
                }
            });
        }

        whitePanel.add(symptomsPanel, BorderLayout.CENTER);

        // Panel con botones Cancel y OK
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showMonitoringIntroduction()); // Go back to Intro

        nextButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nextButton.setBackground(Color.WHITE);
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(e -> showECGPhase());

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void showECGPhase() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());

        JLabel ecgLabel = new JLabel("<html><center><b>ECG Instructions</b><br>" +
                "Follow these steps to record an ECG:<br>" +
                "1. Place the RED electrode below the right clavicle.<br>" +
                "2. Place the WHITE electrode on the lower left side of the torso.<br>" +
                "3. Place the BLACK electrode on the right side of the torso.<br>" +
                "<br>Once everything is ready, press Play on the device.</center></html>");
        ecgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        ecgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(ecgLabel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showSymptomsPanel()); // Go back to Symptoms

        nextButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nextButton.setBackground(Color.WHITE);
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(e -> showEMGPhase()); // Move to EMG Phase

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void showEMGPhase() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());

        JLabel emgLabel = new JLabel("<html><center><b>EMG Instructions</b><br>" +
                "Now we will perform an EMG.<br>" +
                "1. Place the RED electrode on the center of the biceps muscle.<br>" +
                "2. Place the YELLOW electrode 2–3 cm along the muscle, aligned with fibers.<br>" +
                "3. Place the BLACK electrode on a bony area like the elbow or wrist.<br>" +
                "<br>Once everything is ready, press Play on the device.</center></html>");
        emgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        emgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(emgLabel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton backButton = new JButton("Back");
        JButton finishButton = new JButton("Finish Monitoring");


        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showECGPhase()); // Go back to ECG

        finishButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        finishButton.setBackground(Color.WHITE);
        finishButton.setForeground(Color.BLACK);
        finishButton.addActionListener(e -> showCompletionPhase());
    
        buttonPanel.add(backButton);
        buttonPanel.add(finishButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void showCompletionPhase() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());

        JLabel completionLabel = new JLabel("<html><center><b>Monitoring Complete</b><br>" +
                "All the information has been sent to your doctor.<br>" +
                "A report will be sent to you as soon as possible.<br>" +
                "Thank you for your cooperation!</center></html>");
        completionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        completionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(completionLabel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton doneButton = new JButton("Done");
        doneButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        doneButton.setBackground(Color.WHITE);
        doneButton.setForeground(Color.BLACK);
        doneButton.addActionListener(e -> showMonitoringIntroduction()); // Return to Introduction

        buttonPanel.add(doneButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
}
