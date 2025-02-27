import java.util.ArrayList;
import java.util.List;

public class Building {
    private String buildingName;
    private double energyConsumption;
    private List<EnergySource> energySources;

    public Building(String buildingName, double energyConsumption) {
        this.buildingName = buildingName;
        this.energyConsumption = energyConsumption;
        this.energySources = new ArrayList<>();
    }

    public String getBuildingName() {

        return buildingName;
    }

    public double getEnergyConsumption() {

        return energyConsumption;
    }

    public List<EnergySource> getEnergySources() {
        return energySources;
    }

    public void addEnergySource(EnergySource source) {

        energySources.add(source);
    }
}