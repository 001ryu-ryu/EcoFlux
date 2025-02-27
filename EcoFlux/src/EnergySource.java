import java.util.Random;

public abstract class EnergySource {
    private String name;

    public EnergySource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double calculateEnergyOutput();

    public abstract double simulateDailyProduction(Random random);
}
