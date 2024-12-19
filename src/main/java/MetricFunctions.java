import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MetricFunctions {
    public static double getWeightedAverageOf(HashMap<Integer, Double> map) {
        double amount = 0;
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            amount += entry.getKey() * entry.getValue();
        }
        return amount / getAmountOf(map.keySet());
    }

    public static double getWeightedDispersionOf(HashMap<Integer, Double> map, double value) {
        double amount = 0;
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            amount += entry.getKey() * Math.pow(entry.getValue() - value, 2);
        }
        return Math.sqrt(amount / getAmountOf(map.keySet()));
    }

    private static int getAmountOf(Collection<Integer> collection) {
        int amount = 0;
        for (Integer value : collection) {
            amount += value;
        }
        return amount;
    }
}
