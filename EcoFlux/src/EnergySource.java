import java.util.Random;

public abstract class EnergySource {
    private String name;

    public EnergySource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Calculates the base energy output without random variation.
    public abstract double calculateEnergyOutput();

    // Simulates daily production including random variation.
    public abstract double simulateDailyProduction(Random random);
}
