/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.*;

/**
 *
 * @author nataliagarciasanchez
 */
public class SecondPanel extends JPanel {
    private JPanel whitePanel; // Panel blanco dinámico
    private JLabel titleLabel; // Título principal
    private java.util.List<String> symptomsList; // Cambiar la declaración


    public SecondPanel() {
        symptomsList = new LinkedList<>(); // Inicialización de la lista de síntomas
        setBackground(new Color(153, 153, 153)); // Fondo gris
        setLayout(new BorderLayout()); // Layout principal

        // Panel izquierdo con logo y botones
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(153, 153, 153));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacios generales en el panel izquierdo

        // Espaciador arriba para mover el logo hacia arriba
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource("/images/LogoSmall.jpeg")));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(logoLabel);

        // Etiqueta "NeuroTrack" debajo del logo
        JLabel appLabel = new JLabel("NeuroTrack");
        appLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        appLabel.setForeground(Color.WHITE);
        appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(appLabel);

        // Espaciador entre logo/texto y los botones
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Espaciador entre el logo y los botones

        // Botones principales
        JButton viewInfoButton = new JButton("View My Information");
        JButton viewDoctorButton = new JButton("View Doctor Information");
        JButton startMonitoringButton = new JButton("Start Monitoring");

        // Configuración de botones principales
        JButton[] mainButtons = {viewInfoButton, viewDoctorButton, startMonitoringButton};
        for (JButton button : mainButtons) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false); // Sin borde al hacer clic
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(300, 60)); // Tamaño más grande
            button.setPreferredSize(new Dimension(300, 60)); // Tamaño más grande
            button.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Texto más grande
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Espaciado interno
        }

        // Añadir botones con espaciadores entre ellos
        leftPanel.add(viewInfoButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciador
        leftPanel.add(viewDoctorButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciador
        leftPanel.add(startMonitoringButton);

        // Espaciador grande antes del botón de ajustes
        leftPanel.add(Box.createVerticalGlue()); // Empujar el botón de ajustes hacia la parte inferior

        // Botón de ajustes
        JButton settingsButton = new JButton("Settings");
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setFocusPainted(false);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setMaximumSize(new Dimension(300, 60)); // Tamaño más grande
        settingsButton.setPreferredSize(new Dimension(300, 60)); // Tamaño más grande
        settingsButton.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Texto más grande
        settingsButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Espaciado interno

        leftPanel.add(settingsButton); // Agregar el botón de ajustes al final

        // Panel derecho con texto de bienvenida y panel blanco dinámico
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(153, 153, 153));
        rightPanel.setLayout(new BorderLayout());

        // Título principal encima del panel blanco
        titleLabel = new JLabel("Welcome to the MultipleSclerosis Patient app!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espaciado arriba y abajo
        rightPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel blanco
        whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Borde negro
        whitePanel.setPreferredSize(new Dimension(600, 400)); // Tamaño ajustado
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS)); // Layout inicial

        rightPanel.add(whitePanel, BorderLayout.CENTER);

        // Añadir funcionalidad al botón "Start Monitoring"
        startMonitoringButton.addActionListener(e -> showMonitoringIntroduction());

        // Añadir ambos paneles al layout principal
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void showMonitoringIntroduction() {
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco

       // Crear un panel para centrar todo verticalmente
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Texto introductorio
        JLabel introLabel = new JLabel("<html><center>Welcome to your remote Multiple Sclerosis Clinic.<br>" +
            "We are now going to attend your needs.<br>" +
            "To make sure we take care of all your needs and report to your doctor,<br>" +
            "we would like to ask you to select all the symptoms you are currently experiencing.</center></html>");
        introLabel.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Texto más grande
        introLabel.setHorizontalAlignment(SwingConstants.CENTER); // Alinear horizontalmente al centro
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinear en el eje X del contenedor


        // Botón "Start"
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
        whitePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciador
        whitePanel.add(startButton);
        whitePanel.add(Box.createVerticalGlue());

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void showSymptomsPanel() {
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco
        whitePanel.setLayout(new BorderLayout());

        // Texto reducido encima de las columnas
        JLabel instructionLabel = new JLabel("Select all the symptoms you are currently experiencing:");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(instructionLabel, BorderLayout.NORTH);

        // Panel para las casillas de verificación
        JPanel symptomsPanel = new JPanel();
        symptomsPanel.setBackground(Color.WHITE);
        symptomsPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columnas con espaciado
        symptomsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado interno

        // Añadir casillas de verificación para los 100 síntomas
        for (String symptom : generateSymptoms()) {
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

        JButton cancelButton = new JButton("Cancel");
        JButton okButton = new JButton("OK");

        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.addActionListener(e -> resetWhitePanel());

        okButton.setBackground(Color.WHITE);
        okButton.setForeground(Color.BLACK);
        okButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        okButton.addActionListener(e -> saveSymptoms());

        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

   private java.util.List<String> generateSymptoms() {
    return IntStream.range(1, 101)
        .mapToObj(i -> "Symptom " + i)
        .toList();
    }

    private void resetWhitePanel() {
        whitePanel.removeAll();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void saveSymptoms() {
        JOptionPane.showMessageDialog(this, "Symptoms saved: " + symptomsList);
    }
}
