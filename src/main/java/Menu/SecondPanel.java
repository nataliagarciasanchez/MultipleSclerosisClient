/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import IOCommunication.PatientServerCommunication;
import Menu.Utilities.Utilities;
import static Menu.Utilities.Utilities.isValidPassword;
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
import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JPanel whitePanel; 
    private JLabel titleLabel;
    private java.util.List<Symptom> symptomsList; 
    private Patient patient;
    private final Image backgroundImage; 
    private final PatientServerCommunication.Send send;
    private final PatientServerCommunication.Receive receive;
    private LocalDate date = LocalDate.now();
    public static String macAddress = "98:D3:91:FD:69:4F";
    private java.util.List<Bitalino> bitalinos = new ArrayList<>(); 
    public static Role role;
    private BITalino bitalinoDevice; 

    
    
    
    public SecondPanel(Patient patient, PatientServerCommunication.Send send, PatientServerCommunication.Receive receive) {
        this.send = send;
        this.receive = receive;
        this.patient = patient;
        this.role=new Role();
        this.symptomsList = new LinkedList<>(); 

        backgroundImage = new ImageIcon(getClass().getResource("/images/Fondo.jpg")).getImage();

        setLayout(new BorderLayout());

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setOpaque(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); 

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

        logoPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        logoPanel.add(appLabel);

        leftPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        JButton viewInfoButton = createStyledButton("View My Information");
        JButton viewDoctorButton = createStyledButton("View Doctor Information");
        JButton startMonitoringButton = createStyledButton("Start Monitoring");
        JButton settingsButton = createStyledButton("Settings");
        JButton viewReportsButton = createStyledButton("View Dr. Feedbacks");
        
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

        buttonPanel.add(viewInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewDoctorButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(startMonitoringButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewReportsButton);
        buttonPanel.add(Box.createVerticalGlue()); 
        buttonPanel.add(settingsButton);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        mainContainer.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        titleLabel = new JLabel("Welcome to the MultipleSclerosis Patient app!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); 
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); //(top, left, bottom, right)
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        JButton logoutButton = createStyledButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(120, 40)); 
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); 
            }
        });
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutPanel.setOpaque(false); 
        logoutPanel.add(logoutButton);

        titlePanel.add(logoutPanel, BorderLayout.CENTER);

        rightPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel whitePanelWrapper = new JPanel();
        whitePanelWrapper.setLayout(new BorderLayout());
        whitePanelWrapper.setOpaque(false); 
        whitePanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // (top, left, bottom, right)

        whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));

        whitePanelWrapper.add(whitePanel, BorderLayout.CENTER);

        rightPanel.add(whitePanelWrapper, BorderLayout.CENTER);

        mainContainer.add(rightPanel, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true); 
        button.setBackground(Color.WHITE); 
        button.setForeground(Color.BLACK); 
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        return button;
    }


    private void displayPatientInfo() {
        whitePanel.removeAll();

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        contentPanel.add(createInfoLine("Name: ", patient.getName() != null ? patient.getName() + " " + patient.getSurname() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("NIF: ", patient.getNIF() != null ? patient.getNIF() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Birth Date: ", patient.getDob() != null ? patient.getDob().toString() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Gender: ", patient.getGender() != null ? patient.getGender().toString() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Phone: ", patient.getPhone() != null ? patient.getPhone() : "N/A"));

        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(contentPanel);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    
    private JPanel createInfoLine(String labelText, String valueText) {
        JPanel linePanel = new JPanel();
        linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS)); 
        linePanel.setBackground(Color.WHITE);
        linePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18)); 
        linePanel.add(label);

        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 16)); 
        linePanel.add(value);

        return linePanel;
    }
    
    private void displayDoctorInfo() {
        whitePanel.removeAll();

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        contentPanel.add(createInfoLine("Name: ", patient.getDoctor().getName() != null ? patient.getDoctor().getName() + " " + patient.getDoctor().getSurname() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Specialty: ", patient.getDoctor().getSpecialty() != null ? patient.getDoctor().getSpecialty() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(contentPanel);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void showMonitoringIntroduction() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS)); 


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
        
        whitePanel.add(centerPanel); 

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void showSymptomsPanel() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("Select all the symptoms you are currently experiencing:");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(instructionLabel, BorderLayout.NORTH);

        JPanel symptomsPanel = new JPanel();
        symptomsPanel.setBackground(Color.WHITE);
        symptomsPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        symptomsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        java.util.List<Symptom> symptoms = receive.getSymptoms(); 
        if (symptoms != null) {
            ListIterator<Symptom> it = symptoms.listIterator();

            while (it.hasNext()) {
                Symptom symptom = it.next(); 

                JCheckBox symptomCheckBox = new JCheckBox(symptom.getName()); 
                symptomCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
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
        } else {
            System.out.println("No symptoms received from server.");
        }


        whitePanel.add(symptomsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showMonitoringIntroduction()); 

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        playButton.setBackground(Color.WHITE); 
        playButton.setForeground(Color.BLACK); 
        playButton.setFocusPainted(false);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(e -> {
            JDialog progressDialog = new JDialog((Window) null, "Recording", Dialog.ModalityType.APPLICATION_MODAL);
            progressDialog.setLayout(new BorderLayout());
            progressDialog.setSize(300, 100);
            progressDialog.setLocationRelativeTo(null);
            JLabel progressLabel = new JLabel("Capturing data, please wait...", SwingConstants.CENTER);
            progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            progressDialog.add(progressLabel, BorderLayout.CENTER);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                private boolean success = true; 
                @Override
                protected Void doInBackground() {
                    try {
                        bitalinoDevice = new BITalino();
                        bitalinoDevice.open(macAddress);

                        bitalinoDevice.start(new int[]{1});
                        Bitalino bitalinoECG = new Bitalino(java.sql.Date.valueOf(date), SignalType.ECG);
                        java.util.List<Frame> ecgFrames = bitalinoECG.storeRecordedSignals(bitalinoDevice, SignalType.ECG);
                        bitalinoECG.setSignalValues(ecgFrames, 1);
                        bitalinos.add(bitalinoECG);
                    } catch (Exception ex) {
                        success = false;
                        JOptionPane.showMessageDialog(null, "Error while recording ECG data ", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (Throwable ex) {
                        success = false;
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
                    progressDialog.dispose();
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Successfully recorded data!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            };

            worker.execute();
            progressDialog.setVisible(true);
        });

        buttonPanel.add(playButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton backButton = new JButton("Back");
        JButton finishButton = new JButton("Finish Monitoring");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> showEMGPhase()); 

        finishButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        finishButton.setBackground(Color.WHITE);
        finishButton.setForeground(Color.BLACK);
        finishButton.addActionListener(e -> showCompletionPhase()); 

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
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
       
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        playButton.setBackground(Color.WHITE); 
        playButton.setForeground(Color.BLACK); 
        playButton.setFocusPainted(false);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playButton.addActionListener(e -> {
            JDialog progressDialog = new JDialog((Window) null, "Recording", Dialog.ModalityType.APPLICATION_MODAL);
            progressDialog.setLayout(new BorderLayout());
            progressDialog.setSize(300, 100);
            progressDialog.setLocationRelativeTo(null);
            JLabel progressLabel = new JLabel("Capturing data, please wait...", SwingConstants.CENTER);
            progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            progressDialog.add(progressLabel, BorderLayout.CENTER);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                private boolean success = true; 
                @Override
                protected Void doInBackground() {
                    try {
                        bitalinoDevice = new BITalino();
                        bitalinoDevice.open(macAddress);

                        bitalinoDevice.start(new int[]{0}); 
                        Bitalino bitalinoEMG = new Bitalino(java.sql.Date.valueOf(date), SignalType.EMG);
                        java.util.List<Frame> emgFrames = bitalinoEMG.storeRecordedSignals(bitalinoDevice, SignalType.EMG);
                        bitalinoEMG.setSignalValues(emgFrames, 0);
                        bitalinos.add(bitalinoEMG);
                    } catch (Exception ex) {
                        success = false; 
                        JOptionPane.showMessageDialog(null, "Error while recording EMG data ", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (Throwable ex) {
                        success = false;
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
                    progressDialog.dispose();
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Successfully recorded data!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            };

            worker.execute();
            progressDialog.setVisible(true);
        });

        buttonPanel.add(playButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton doneButton = new JButton("Done");
        doneButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        doneButton.setBackground(Color.WHITE);
        doneButton.setForeground(Color.BLACK);
        doneButton.addActionListener(e -> showMonitoringIntroduction()); 

        doneButton.addActionListener(e -> {
            Report report = new Report( java.sql.Date.valueOf(date), patient, bitalinos, symptomsList);
            send.sendReport(report);
            showMonitoringIntroduction(); 
        });
        
        buttonPanel.add(doneButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void displayFeedbacks() {
        whitePanel.removeAll(); 
        whitePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("View Feedbacks");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel feedbackContainer = new JPanel(new BorderLayout());
        feedbackContainer.setBackground(Color.WHITE);

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

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(feedbackPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        feedbackContainer.add(scrollPane, BorderLayout.CENTER);
        whitePanel.add(feedbackContainer, BorderLayout.CENTER);

        java.util.List<Feedback> feedbacks = receive.viewFeedbacks(patient); 
        if (feedbacks != null && !feedbacks.isEmpty()) {
            updateFeedbackList(feedbacks, feedbackPanel); 
        } else {
            feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));

            JLabel noFeedbacksLabel = new JLabel("No feedbacks available for this patient.");
            noFeedbacksLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noFeedbacksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noFeedbacksLabel.setForeground(Color.GRAY);

            feedbackPanel.add(Box.createVerticalGlue()); 
            feedbackPanel.add(noFeedbacksLabel); 
            feedbackPanel.add(Box.createVerticalGlue()); 

            feedbackPanel.revalidate();
            feedbackPanel.repaint();
        }

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

    private void updateFeedbackList(java.util.List<Feedback> feedbacks, JPanel feedbackPanel) {
        feedbackPanel.removeAll(); 

        Dimension fixedSize = new Dimension(1000, 50); 

        for (Feedback feedback : feedbacks) {
            JPanel feedbackItemPanel = new JPanel(new BorderLayout());
            feedbackItemPanel.setBackground(Color.LIGHT_GRAY);
            feedbackItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            feedbackItemPanel.setPreferredSize(fixedSize); 
            feedbackItemPanel.setMaximumSize(fixedSize);
            feedbackItemPanel.setMinimumSize(fixedSize);

            JLabel feedbackLabel = new JLabel("Date: " + feedback.getDate().toString());
            feedbackLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton viewButton = new JButton("View Entire Message");
            viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            viewButton.setBackground(Color.WHITE);
            viewButton.setForeground(Color.BLACK);

            viewButton.addActionListener(e -> displayFeedbacksInUI(feedback));

            feedbackItemPanel.add(feedbackLabel, BorderLayout.CENTER);
            feedbackItemPanel.add(viewButton, BorderLayout.EAST);

            feedbackPanel.add(feedbackItemPanel);
            feedbackPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        }

        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private void displayFeedbacksInUI(Feedback feedback) {
        whitePanel.removeAll(); 
        whitePanel.setLayout(new BorderLayout());

        
        JPanel feedbackDetailsPanel = new JPanel();
        feedbackDetailsPanel.setBackground(Color.WHITE);
        feedbackDetailsPanel.setLayout(new BoxLayout(feedbackDetailsPanel, BoxLayout.Y_AXIS));
        feedbackDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        feedbackDetailsPanel.add(createInfoLine("Doctor: ", feedback.getDoctor().getName()));
        feedbackDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        feedbackDetailsPanel.add(createInfoLine("Date: ", feedback.getDate().toString()));
        feedbackDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        feedbackDetailsPanel.add(createInfoLine("Message: ", feedback.getMessage()));

        whitePanel.add(feedbackDetailsPanel, BorderLayout.CENTER);

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
 
    private void auxiliar() {
        whitePanel.removeAll();
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new GridBagLayout());
        borderedPanel.setBackground(Color.WHITE);
        borderedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Select one of the options",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

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

        changeInfoButton.addActionListener(e -> displayPatientInfoUpdate());
        changePasswordButton.addActionListener(e -> displayPatientPasswordUpdate());

        buttonPanel.add(changeInfoButton);
        buttonPanel.add(changePasswordButton);

        borderedPanel.add(buttonPanel);

        GridBagConstraints borderedGbc = new GridBagConstraints();
        borderedGbc.gridx = 0;
        borderedGbc.gridy = 0;
        borderedPanel.add(buttonPanel, borderedGbc);

        whitePanel.add(borderedPanel,  BorderLayout.CENTER);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void displayPatientInfoUpdate() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20)); 

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Update Personal Information",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension fieldSize = new Dimension(200, 30);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(patient.getName() != null ? patient.getName() : "");
        nameField.setPreferredSize(fieldSize);
        nameField.setToolTipText("Enter your full first name. Example: John");
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(surnameLabel, gbc);

        JTextField surnameField = new JTextField(patient.getSurname() != null ? patient.getSurname() : "");
        surnameField.setPreferredSize(fieldSize);
        surnameField.setToolTipText("Enter your last name. Example: Doe");
        gbc.gridx = 1;
        contentPanel.add(surnameField, gbc);

        JLabel nifLabel = new JLabel("DNI:");
        nifLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(nifLabel, gbc);

        JTextField nifField = new JTextField(patient.getNIF() != null ? patient.getNIF() : "");
        nifField.setPreferredSize(fieldSize);
        nifField.setToolTipText("Enter a valid NIF including the letter. Example: 12345678A");
        gbc.gridx = 1;
        contentPanel.add(nifField, gbc);

        JLabel dobLabel = new JLabel("Birth Date:");
        dobLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(dobLabel, gbc);

        JTextField dobField = new JTextField(patient.getDob() != null ? patient.getDob().toString() : "");
        dobField.setPreferredSize(fieldSize);
        dobField.setToolTipText("Enter your birth date in YYYY-MM-DD format. Example: 2003-10-10");
        gbc.gridx = 1;
        contentPanel.add(dobField, gbc);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(patient.getPhone() != null ? patient.getPhone() : "");
        phoneField.setPreferredSize(fieldSize);
        phoneField.setToolTipText("Enter your phone number. With or without country code. Example: +34123456789");
        gbc.gridx = 1;
        contentPanel.add(phoneField, gbc);

        whitePanel.add(contentPanel, BorderLayout.CENTER);

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
               patient.setName(nameField.getText());
                patient.setSurname(surnameField.getText());
                patient.setNIF(nifField.getText());
                patient.setPhone(phoneField.getText());
                patient.setDob(java.sql.Date.valueOf(dobField.getText()));
                User user = patient.getUser();
                user.setRole(role);
                String samePassword = user.getPassword();
                send.updateInformation(user, patient, samePassword); 

            JOptionPane.showMessageDialog(this, "Patient information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
         
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            });

        cancelButton.addActionListener(e -> auxiliar());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(newPasswordLabel, gbc);

        gbc.gridy++;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordField.setToolTipText("Enter a strong password with at least 8 characters, including uppercase, lowercase, and a number.");
        passwordPanel.add(newPasswordField, gbc);

        gbc.gridy++;
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setToolTipText("Enter a strong password with at least 8 characters, including uppercase, lowercase, and a number.");
        passwordPanel.add(confirmPasswordField, gbc);

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
            
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (newPassword.trim().isEmpty()|| confirmPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(whitePanel, "Password fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // salir antes de enviar datos al servidor
            }
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(whitePanel, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // salir antes de enviar datos al servidor
            }
            if (!isValidPassword(newPassword)) {
            JOptionPane.showMessageDialog(whitePanel, "Invalid password.\nIt must be at least 8 characters long, contain at least one uppercase letter, and include at least one number.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // salir antes de enviar datos al servidor
            }
            try{

                
                User user = new User();
                user.setId(patient.getUser().getId());
                user.setEmail(patient.getUser().getEmail());
                user.setPassword(newPassword);
                user.setRole(role);
                
                send.updateInformation(user, patient, confirmPassword); 
                
                JOptionPane.showMessageDialog(whitePanel, "Password successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                auxiliar();
            
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }catch(Exception ex){
                JOptionPane.showMessageDialog(whitePanel, "An error occurred:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
