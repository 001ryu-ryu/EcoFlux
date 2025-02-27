import java.util.Random;

public class WindTurbine extends EnergySource {
    private double bladeLength; // in meters
    private double windSpeed;   // in m/s

    public WindTurbine(String name, double bladeLength, double windSpeed) {
        super(name);
        this.bladeLength = bladeLength;
        this.windSpeed = windSpeed;
    }

    @Override
    public double calculateEnergyOutput() {
        double sweptArea = Math.PI * bladeLength * bladeLength;
        return sweptArea * Math.pow(windSpeed, 3);
    }

    @Override
    public double simulateDailyProduction(Random random) {
        // Fluctuates between 80% and 120%
        double variationFactor = 0.8 + random.nextDouble() * 0.4;
        double sweptArea = Math.PI * bladeLength * bladeLength;
        return sweptArea * Math.pow(windSpeed * variationFactor, 3);
    }
}