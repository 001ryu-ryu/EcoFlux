import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Random;

public class EnergyManagementSystemGUI extends JFrame {

    private JTextPane outputPane;
    private JButton runButton;

    public EnergyManagementSystemGUI() {
        super("EcoFlux Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Create a JTextPane for simulation output with a terminal-like style.
        outputPane = new JTextPane();
        outputPane.setEditable(false);
        outputPane.setBackground(Color.BLACK);
        outputPane.setForeground(Color.GREEN);
        outputPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        centerAlignText(outputPane);

        // Put the text pane in a scroll pane in case output exceeds window size.
        JScrollPane scrollPane = new JScrollPane(outputPane);
        add(scrollPane, BorderLayout.CENTER);

        // Create a button to trigger the simulation at the bottom.
        runButton = new JButton("Run Simulation");
        runButton.addActionListener(e -> runSimulation());
        add(runButton, BorderLayout.SOUTH);
    }

    // Center-align text in the JTextPane.
    private void centerAlignText(JTextPane pane) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    // Append text to the JTextPane on the Event Dispatch Thread (EDT).
    private void appendText(String text) {
        SwingUtilities.invokeLater(() -> {
            outputPane.setText(outputPane.getText() + text + "\n");
            centerAlignText(outputPane);
        });
    }

    // Runs the simulation with a countdown and gradual, line-by-line output.
    private void runSimulation() {
        // Clear previous output.
        outputPane.setText("");

        // Run in a separate thread so the GUI remains responsive.
        new Thread(() -> {
            try {
                // Countdown
                appendText("Running Simulation in 3...");
                Thread.sleep(1000);
                appendText("2...");
                Thread.sleep(1000);
                appendText("1...");
                Thread.sleep(1000);
                appendText("Go!\n");

                Random random = new Random();

                // Create the building with a base daily consumption of 50,000 Wh.
                Building building = new Building("Green Campus Building", 50000);

                // Create renewable energy sources.
                SolarPanel solarPanel = new SolarPanel("Solar Panel 1", 10, 0.15, 5);
                WindTurbine windTurbine = new WindTurbine("Wind Turbine 1", 5, 3);
                building.addEnergySource(solarPanel);
                building.addEnergySource(windTurbine);

                appendText("Simulating energy production and consumption over 30 days for "
                        + building.getBuildingName() + "...\n");

                double totalProduction = 0;
                double totalConsumption = 0;

                // Simulate 30 days, outputting each day's results with a short delay.
                for (int day = 1; day <= 30; day++) {
                    double dailyProduction = 0;
                    for (EnergySource source : building.getEnergySources()) {
                        dailyProduction += source.simulateDailyProduction(random);
                    }

                    // Daily consumption: fluctuate between 90% and 110% of the base.
                    double consumptionVariation = 0.9 + random.nextDouble() * 0.2;
                    double dailyConsumption = building.getEnergyConsumption() * consumptionVariation;

                    totalProduction += dailyProduction;
                    totalConsumption += dailyConsumption;

                    String status = (dailyProduction >= dailyConsumption) ? "Surplus" : "Deficit";
                    String line = String.format("Day %2d: Produced: %.2f Wh, Consumption: %.2f Wh, %s",
                            day, dailyProduction, dailyConsumption, status);
                    appendText(line);

                    // Delay between each day's output (e.g., 500ms).
                    Thread.sleep(300);
                }

                double averageProduction = totalProduction / 30;
                double averageConsumption = totalConsumption / 30;

                appendText("\n--- Overall Energy Evaluation ---");
                appendText(String.format("Average daily production: %.2f Wh", averageProduction));
                appendText(String.format("Average daily consumption: %.2f Wh", averageConsumption));
                if (averageProduction >= averageConsumption) {
                    appendText("Overall Status: Energy surplus. Renewable sources meet or exceed the building's needs!");
                } else {
                    appendText("Overall Status: Energy deficit. Consider adding more renewable sources or reducing consumption.");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}