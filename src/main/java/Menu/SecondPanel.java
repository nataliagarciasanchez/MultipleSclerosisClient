/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import IOCommunication.PatientServerCommunication;
import static IOCommunication.PatientServerCommunicationTest.role;
import Menu.Utilities.Utilities;
import POJOs.Bitalino;
import POJOs.Doctor;
import POJOs.Feedback;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.Report;
import POJOs.Role;
import POJOs.SignalType;
import POJOs.Symptom;
import POJOs.User;
import Security.PasswordEncryption;
import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.*;
import java.time.LocalDate;
import java.util.stream.Collectors;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 *
 * @author nataliagarciasanchez
 */
public class SecondPanel extends JPanel {
    private JPanel whitePanel; // Dynamic white panel
    private JLabel titleLabel; // Main title
    private java.util.List<Symptom> symptomsList; // Symptom list
    private Patient patient;
    private final Image backgroundImage; // Background image
    private final PatientServerCommunication.Send send;
    private LocalDate date = LocalDate.now();
    public static String macAddress = "98:D3:41:FD:4E:E8";
    private java.util.List<Bitalino> bitalinos; // Symptom list
    public static Role role;
    private BITalino bitalinoDevice; // Variable global para mantener la conexión

    
    
    
    public SecondPanel(Patient patient, PatientServerCommunication.Send send) {
        this.send = send;
        this.patient = patient;
        this.role=new Role();
        this.symptomsList = new LinkedList<>(); // Initialize symptoms list

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
        JButton viewReportsButton = createStyledButton("View My Reports");
        
        settingsButton.addActionListener(e -> auxiliar());
        startMonitoringButton.addActionListener(e -> showMonitoringIntroduction());
        viewInfoButton.addActionListener(e -> displayPatientInfo());
        viewDoctorButton.addActionListener(e -> {
            if (patient.getDoctor() != null) {
                displayDoctorInfo();
            } else {
                JOptionPane.showMessageDialog(this, "No doctor assigned to this patient.", "Doctor Info", JOptionPane.WARNING_MESSAGE);
            }
        });
        viewReportsButton.addActionListener(e -> displayFeedbacks());

        // Add buttons to the button panel with spacing
        buttonPanel.add(viewInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewDoctorButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(startMonitoringButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewReportsButton);
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
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // Adjust spacing (top, left, bottom, right)
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        // Add "Log Out" button at the top-right corner
        JButton logoutButton = createStyledButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(120, 40)); // Slightly smaller size for top-right placement
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Close the application
            }
        });
        
        // Panel for positioning the logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutPanel.setOpaque(false); // Transparent to blend with the background
        logoutPanel.add(logoutButton);

        // Add the logout panel to the title panel
        titlePanel.add(logoutPanel, BorderLayout.CENTER);

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

        java.util.List<Symptom> symptoms = send.getSymptoms(); 
        if (symptoms != null) {
            ListIterator<Symptom> it = symptoms.listIterator();

            while (it.hasNext()) {
                Symptom symptom = it.next(); // Obtener el objeto Symptom actual

                JCheckBox symptomCheckBox = new JCheckBox(symptom.getName()); // Usar el nombre del síntoma
                symptomCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Ajustar fuente
                symptomCheckBox.setBackground(Color.WHITE);

                symptomsPanel.add(symptomCheckBox);

                // Agregar funcionalidad para añadir o quitar de la lista seleccionada
                symptomCheckBox.addActionListener(e -> {
                    if (symptomCheckBox.isSelected()) {
                        symptomsList.add(symptom); // Agregar el objeto Symptom completo
                    } else {
                        symptomsList.remove(symptom); // Eliminar el objeto Symptom completo
                    }
                });
            }
        } else {
            System.out.println("No symptoms received from server.");
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
        nextButton.addActionListener(e -> showEMGPhase());

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
                "1. Place the <b><span style='color:red;'>RED</span></b> electrode below the right clavicle.<br>" +
                "2. Place the <b><span style='color:#CCCCCC;'>WHITE</span></b> electrode on the lower left side of the torso.<br>" +
                "3. Place the <b><span style='color:black;'>BLACK</span></b> electrode on the right side of the torso.<br>" +
                "<br>Once everything is ready, press PLAY</center></html>");
        ecgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        ecgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(ecgLabel, BorderLayout.CENTER);

        // Crear un único panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Play Button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        playButton.setBackground(Color.WHITE); 
        playButton.setForeground(Color.BLACK); 
        playButton.setFocusPainted(false);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(e -> {
            // Crear un cuadro de diálogo no bloqueante para mostrar el progreso
            JDialog progressDialog = new JDialog((Window) null, "Recording", Dialog.ModalityType.APPLICATION_MODAL);
            progressDialog.setLayout(new BorderLayout());
            progressDialog.setSize(300, 100);
            progressDialog.setLocationRelativeTo(null);
            JLabel progressLabel = new JLabel("Capturing data, please wait...", SwingConstants.CENTER);
            progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            progressDialog.add(progressLabel, BorderLayout.CENTER);

            // Ejecutar la captura de datos en un SwingWorker para evitar bloquear el hilo principal
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        // Inicializar BITalino y empezar a grabar
                        bitalinoDevice = new BITalino();
                        bitalinoDevice.open(macAddress);

                        bitalinoDevice.start(new int[]{1}); // Canal ECG
                        Bitalino bitalinoECG = new Bitalino(java.sql.Date.valueOf(date), SignalType.ECG);
                        java.util.List<Frame> ecgFrames = bitalinoECG.storeRecordedSignals(bitalinoDevice, SignalType.ECG);
                        bitalinoECG.setSignalValues(ecgFrames, 1);
                        bitalinos.add(bitalinoECG);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error while recording ECG data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (Throwable ex) {
                        Logger.getLogger(SecondPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if (bitalinoDevice != null) {
                                bitalinoDevice.stop();
                                bitalinoDevice.close();
                            }
                        } catch (BITalinoException ex) {
                            ex.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // Cerrar el cuadro de diálogo de progreso y mostrar un mensaje de éxito
                    progressDialog.dispose();
                    JOptionPane.showMessageDialog(null, "Successfully recorded data!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            };

            // Mostrar el cuadro de diálogo de progreso y ejecutar la tarea en segundo plano
            worker.execute();
            progressDialog.setVisible(true);
        });

        // Añadir el botón "Play" al panel de botones
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado

        // Panel de navegación (Back y Finish Monitoring)
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton backButton = new JButton("Back");
        JButton finishButton = new JButton("Finish Monitoring");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showEMGPhase()); // Go back to Symptoms

        finishButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        finishButton.setBackground(Color.WHITE);
        finishButton.setForeground(Color.BLACK);
        finishButton.addActionListener(e -> showCompletionPhase()); // Move to EMG Phase

        navigationPanel.add(backButton);
        navigationPanel.add(finishButton);

        buttonPanel.add(navigationPanel);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    
    private void showEMGPhase() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());

        JLabel emgLabel = new JLabel("<html><center><b>EMG Instructions</b><br>" +
                "Now we will perform an EMG.<br>" +
                "1. Place the <b><span style='color:red;'>RED</span></b> electrode on the center of the biceps muscle.<br>" +
                "2. Place the <b><span style='color:#CCCCCC;'>WHITE</span></b> electrode 2–3 cm along the muscle, aligned with fibers.<br>" +
                "3. Place the <b><span style='color:black;'>BLACK</span></b>  electrode on a bony area like the elbow or wrist.<br>" +
                "<br>Once everything is ready, press PLAY.</center></html>");
        emgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        emgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(emgLabel, BorderLayout.CENTER);
        
        // Crear un único panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        // Play Button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        playButton.setBackground(Color.WHITE); 
        playButton.setForeground(Color.BLACK); 
        playButton.setFocusPainted(false);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playButton.addActionListener(e -> {
            // Crear un cuadro de diálogo no bloqueante para mostrar el progreso
            JDialog progressDialog = new JDialog((Window) null, "Recording", Dialog.ModalityType.APPLICATION_MODAL);
            progressDialog.setLayout(new BorderLayout());
            progressDialog.setSize(300, 100);
            progressDialog.setLocationRelativeTo(null);
            JLabel progressLabel = new JLabel("Capturing data, please wait...", SwingConstants.CENTER);
            progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            progressDialog.add(progressLabel, BorderLayout.CENTER);

            // Crear un SwingWorker para manejar la tarea en segundo plano
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        // Inicializar BITalino y empezar a grabar
                        bitalinoDevice = new BITalino();
                        bitalinoDevice.open(macAddress);

                        bitalinoDevice.start(new int[]{1}); // Canal ECG
                        Bitalino bitalinoECG = new Bitalino(java.sql.Date.valueOf(date), SignalType.ECG);
                        java.util.List<Frame> ecgFrames = bitalinoECG.storeRecordedSignals(bitalinoDevice, SignalType.ECG);
                        bitalinoECG.setSignalValues(ecgFrames, 1);
                        bitalinos.add(bitalinoECG);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error while recording ECG data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (Throwable ex) {
                        Logger.getLogger(SecondPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if (bitalinoDevice != null) {
                                bitalinoDevice.stop();
                                bitalinoDevice.close();
                            }
                        } catch (BITalinoException ex) {
                            ex.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // Mostrar el mensaje de éxito después de completar la captura
                    JOptionPane.showMessageDialog(null, "Successfully recorded data!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            };

            // Ejecutar la tarea en segundo plano
            worker.execute();
            progressDialog.setVisible(true);
        });


        // Añadir el botón "Play" al panel de botones
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado

        // Panel de navegación (Back y Finish Monitoring)
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");


        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showSymptomsPanel());

        nextButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nextButton.setBackground(Color.WHITE);
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(e -> showECGPhase());
    
        navigationPanel.add(backButton);
        navigationPanel.add(nextButton);
        
        buttonPanel.add(navigationPanel);

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

        doneButton.addActionListener(e -> {
            Report report = new Report( java.sql.Date.valueOf(date), patient, bitalinos, symptomsList);
            send.sendReport(report);
            try {
                if (bitalinoDevice != null) {
                    bitalinoDevice.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error while closing the BITalino device: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            showMonitoringIntroduction(); 
        });
        
        buttonPanel.add(doneButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void displayFeedbacks() {
    whitePanel.removeAll(); // Clear the white panel content
    whitePanel.setLayout(new BorderLayout());

    // Title at the top
    JLabel titleLabel = new JLabel("View Feedbacks");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    whitePanel.add(titleLabel, BorderLayout.NORTH);

    // Container for the search bar and feedback list
    JPanel feedbackContainer = new JPanel(new BorderLayout());
    feedbackContainer.setBackground(Color.WHITE);

    // Search panel for feedbacks
    JPanel searchPanel = new JPanel(new BorderLayout());
    searchPanel.setBackground(Color.WHITE);
    searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    JLabel searchLabel = new JLabel("Search Feedbacks by Date (YYYY-MM-DD):");
    searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

    JTextField searchField = new JTextField();
    searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

    searchPanel.add(searchLabel, BorderLayout.WEST);
    searchPanel.add(searchField, BorderLayout.CENTER);

    feedbackContainer.add(searchPanel, BorderLayout.NORTH);

    // Panel for the list of feedbacks
    JPanel feedbackPanel = new JPanel();
    feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
    feedbackPanel.setBackground(Color.WHITE);

    // Scrollable panel for feedbacks
    JScrollPane scrollPane = new JScrollPane(feedbackPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    feedbackContainer.add(scrollPane, BorderLayout.CENTER);
    whitePanel.add(feedbackContainer, BorderLayout.CENTER);

    // Retrieve feedbacks for the current patient
    java.util.List<Feedback> feedbacks = getMockFeedbacks(); // Replace with handleFeedbackFromServer() when ready
    if (feedbacks != null && !feedbacks.isEmpty()) {
        updateFeedbackList(feedbacks, feedbackPanel); // Display all feedbacks initially
    } else {
        System.out.println("No feedbacks available for this patient.");
    }

    // Add search functionality to filter feedbacks
    searchField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filterFeedbacks();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterFeedbacks();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterFeedbacks();
        }

        private void filterFeedbacks() {
            String searchText = searchField.getText().toLowerCase();
            if (feedbacks != null) {
                java.util.List<Feedback> filteredFeedbacks = feedbacks.stream()
                        .filter(feedback -> feedback.getDate().toString().contains(searchText)) // Filter by date
                        .collect(Collectors.toList());
                updateFeedbackList(filteredFeedbacks, feedbackPanel);
            }
        }
    });

    whitePanel.revalidate();
    whitePanel.repaint();
}


    // Método para actualizar la lista de feedbacks
    private void updateFeedbackList(java.util.List<Feedback> feedbacks, JPanel feedbackPanel) {
        feedbackPanel.removeAll(); // Clear the previous content

        Dimension fixedSize = new Dimension(1000, 50); // Fixed size for each feedback panel

        for (Feedback feedback : feedbacks) {
            JPanel feedbackItemPanel = new JPanel(new BorderLayout());
            feedbackItemPanel.setBackground(Color.LIGHT_GRAY);
            feedbackItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            feedbackItemPanel.setPreferredSize(fixedSize); // Apply fixed size
            feedbackItemPanel.setMaximumSize(fixedSize);
            feedbackItemPanel.setMinimumSize(fixedSize);

            JLabel feedbackLabel = new JLabel("Date: " + feedback.getDate().toString());
            feedbackLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton viewButton = new JButton("View Entire Message");
            viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            viewButton.setBackground(Color.WHITE);
            viewButton.setForeground(Color.BLACK);

            // Use displayFeedbacksInUI for a detailed view
            viewButton.addActionListener(e -> displayFeedbacksInUI(feedback));

            feedbackItemPanel.add(feedbackLabel, BorderLayout.CENTER);
            feedbackItemPanel.add(viewButton, BorderLayout.EAST);

            feedbackPanel.add(feedbackItemPanel);
            feedbackPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between feedbacks
        }

        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private void displayFeedbacksInUI(Feedback feedback) {
        whitePanel.removeAll(); // Clear the white panel content
        whitePanel.setLayout(new BorderLayout());

        // Panel for feedback details
        JPanel feedbackDetailsPanel = new JPanel();
        feedbackDetailsPanel.setBackground(Color.WHITE);
        feedbackDetailsPanel.setLayout(new BoxLayout(feedbackDetailsPanel, BoxLayout.Y_AXIS));
        feedbackDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add feedback details
        feedbackDetailsPanel.add(createInfoLine("Doctor: ", feedback.getDoctor().getName()));
        feedbackDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        feedbackDetailsPanel.add(createInfoLine("Date: ", feedback.getDate().toString()));
        feedbackDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        feedbackDetailsPanel.add(createInfoLine("Message: ", feedback.getMessage()));

        whitePanel.add(feedbackDetailsPanel, BorderLayout.CENTER);

        // Add a "Back" button to return to feedback list
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> displayFeedbacks());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(backButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }



    // Método de prueba para obtener feedbacks ficticios
    private java.util.List<Feedback> getMockFeedbacks() {
        java.util.List<Feedback> mockFeedbacks = new ArrayList<>();
        Patient mockPatient = new Patient("John", "Doe", "12345678A", java.sql.Date.valueOf("1990-01-01"), Gender.MALE, "123456789");

        // Crear doctores con el constructor correcto
        Doctor doctor1 = new Doctor(1, "Dr. Garcia", "Neurologist");
        Doctor doctor2 = new Doctor(2, "Dr. Lopez", "General Practitioner");
        Doctor doctor3 = new Doctor(3, "Dr. Martinez", "Physiotherapist");
        Doctor doctor4 = new Doctor(4, "Dr. Smith", "Rehabilitation Specialist");
        Doctor doctor5 = new Doctor(5, "Dr. Taylor", "Consultant");

        // Añadir feedbacks ficticios
        mockFeedbacks.add(new Feedback(1,"Great improvement!", java.sql.Date.valueOf("2023-12-01"), doctor1, mockPatient));
        mockFeedbacks.add(new Feedback(2,"Please schedule a follow-up.", java.sql.Date.valueOf("2023-11-15"), doctor2, mockPatient));
        mockFeedbacks.add(new Feedback(3,"Your condition is stable.", java.sql.Date.valueOf("2023-11-10"), doctor3, mockPatient));
        mockFeedbacks.add(new Feedback(4,"Keep up the good work with therapy.", java.sql.Date.valueOf("2023-10-25"), doctor4, mockPatient));
        mockFeedbacks.add(new Feedback(5,"I noticed some irregularities. Please visit.", java.sql.Date.valueOf("2023-10-10"), doctor5, mockPatient));

        return mockFeedbacks;
    }


    
    private void auxiliar() {
        // Clear the whitePanel
        whitePanel.removeAll();
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Panel with black border
        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new GridBagLayout());
        borderedPanel.setBackground(Color.WHITE);
        borderedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Select one of the options",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); // Add black border with title

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Buttons
        JButton changeInfoButton = new JButton("Change my personal information");
        changeInfoButton.setBackground(Color.WHITE);
        changeInfoButton.setForeground(Color.BLACK);
        changeInfoButton.setFocusPainted(false);
        changeInfoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton changePasswordButton = new JButton("Change my password");
        changePasswordButton.setBackground(Color.WHITE);
        changePasswordButton.setForeground(Color.BLACK);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Button actions
        changeInfoButton.addActionListener(e -> displayPatientInfoUpdate());
        changePasswordButton.addActionListener(e -> displayPatientPasswordUpdate());

        // Add buttons to the button panel
        buttonPanel.add(changeInfoButton);
        buttonPanel.add(changePasswordButton);

        // Add the button panel to the bordered panel
        borderedPanel.add(buttonPanel);

        // Constraints for the borderedPanel
        GridBagConstraints borderedGbc = new GridBagConstraints();
        borderedGbc.gridx = 0;
        borderedGbc.gridy = 0;
        borderedPanel.add(buttonPanel, borderedGbc);

        // Add borderedPanel to the whitePanel
        whitePanel.add(borderedPanel,  BorderLayout.CENTER);

        // Refresh the whitePanel
        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void displayPatientInfoUpdate() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20)); 

        // Main content panel with a titled border
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Update Personal Information",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 

        // Layout constraints for fields and labels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Consistent field size
        Dimension fieldSize = new Dimension(200, 30);

        // Add labels and text fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(patient.getName() != null ? patient.getName() : "");
        nameField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(surnameLabel, gbc);

        JTextField surnameField = new JTextField(patient.getSurname() != null ? patient.getSurname() : "");
        surnameField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(surnameField, gbc);

        JLabel nifLabel = new JLabel("DNI:");
        nifLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(nifLabel, gbc);

        JTextField nifField = new JTextField(patient.getNIF() != null ? patient.getNIF() : "");
        nifField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(nifField, gbc);

        JLabel dobLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        dobLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(dobLabel, gbc);

        JTextField dobField = new JTextField(patient.getDob() != null ? patient.getDob().toString() : "");
        dobField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(dobField, gbc);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(patient.getPhone() != null ? patient.getPhone() : "");
        phoneField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(phoneField, gbc);

        // Add the content panel to the white panel
        whitePanel.add(contentPanel, BorderLayout.CENTER);

        // Button panel for Save and Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);

        saveButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty() ||
                    nifField.getText().trim().isEmpty() || dobField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }
                if (!Utilities.validateID(nifField.getText().trim())) {
                    throw new IllegalArgumentException("Invalid NIF.");
                }
                if (!Utilities.isValidPhone(phoneField.getText().trim())) {
                    throw new IllegalArgumentException("Invalid phone number.");
                }
                if (!Utilities.validateDate(LocalDate.parse(dobField.getText()))) {
                    throw new IllegalArgumentException("Invalid Date.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            patient.setName(nameField.getText());
            patient.setSurname(surnameField.getText());
            patient.setNIF(nifField.getText());
            patient.setPhone(phoneField.getText());
            patient.setDob(java.sql.Date.valueOf(dobField.getText()));
            User user = patient.getUser();
            user.setRole(role);
            send.updateInformation(user, patient); // pasamos el user y patient con la info modificada

            JOptionPane.showMessageDialog(this, "Patient information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelButton.addActionListener(e -> auxiliar());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Refresh the whitePanel
        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void displayPatientPasswordUpdate() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20)); 

        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Change Password",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Etiquetas y campos
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        /*JLabel currentPasswordLabel = new JLabel("Current Password:");
        currentPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(currentPasswordLabel, gbc);*/

        //gbc.gridy++;
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(newPasswordLabel, gbc);

        gbc.gridy++;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(confirmPasswordLabel, gbc);

        // Campos para las contraseñas
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        /*JPasswordField currentPasswordField = new JPasswordField(20);
        passwordPanel.add(currentPasswordField, gbc);*/

        //gbc.gridy++;
        JPasswordField newPasswordField = new JPasswordField(20);
        passwordPanel.add(newPasswordField, gbc);

        gbc.gridy++;
        JPasswordField confirmPasswordField = new JPasswordField(20);
        passwordPanel.add(confirmPasswordField, gbc);

        // Botón para guardar cambios
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton savePasswordButton = new JButton("Save New Password");
        savePasswordButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        savePasswordButton.setBackground(Color.WHITE);
        savePasswordButton.setForeground(Color.BLACK);
        savePasswordButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        savePasswordButton.addActionListener(e -> {
            
            //String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            try{
                //String hashedCurrentPassword = PasswordEncryption.hashPassword(currentPassword);

                /*if (!hashedCurrentPassword.equals(patient.getUser().getPassword())) {
                    JOptionPane.showMessageDialog(whitePanel, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }*/

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(whitePanel, "Password fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(whitePanel, "New password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Utilities.isValidPassword(newPassword)) {
                    JOptionPane.showMessageDialog(whitePanel, "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //patient.getUser().setPassword(newPassword);
                User user = patient.getUser();
                user.setPassword(newPassword); // plana, se encripta en updateInformation
                user.setRole(role);
                send.updateInformation(user, patient); // pasamos user y patient con la informacion modificada

                JOptionPane.showMessageDialog(whitePanel, "Password successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                auxiliar(); 
            }catch(Exception ex){
                JOptionPane.showMessageDialog(whitePanel, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> auxiliar());
                
        buttonPanel.add(cancelButton);
        buttonPanel.add(savePasswordButton);

        whitePanel.add(passwordPanel, BorderLayout.CENTER);
        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

}
