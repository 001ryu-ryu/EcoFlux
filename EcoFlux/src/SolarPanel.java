import java.util.Random;

public class SolarPanel extends EnergySource {
    private double panelArea;   // in square meters
    private double efficiency;  // efficiency (0-1)
    private double sunHours;    // average sun hours per day

    public SolarPanel(String name, double panelArea, double efficiency, double sunHours) {
        super(name);
        this.panelArea = panelArea;
        this.efficiency = efficiency;
        this.sunHours = sunHours;
    }

    @Override
    public double calculateEnergyOutput() {
        // Base output: area * efficiency * sunHours * 1000 (W/m²).
        return panelArea * efficiency * sunHours * 1000;
    }

    @Override
    public double simulateDailyProduction(Random random) {
        // Variation: sun hours vary between 80% and 120% of the base value.
        double variationFactor = 0.8 + random.nextDouble() * 0.4;
        return panelArea * efficiency * (sunHours * variationFactor) * 1000;
    }
}