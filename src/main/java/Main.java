import java.util.*;

public class Main {
    private static final int MAX_ITERATIONS = 1000, DOORS_NUMBER = 3;

    public static void main(String[] args) {
        Strategy notChangeStrategy = (_, playerChoice) -> playerChoice,
                changeStrategy = (voidDoors, playerChoice) -> {
                    HashSet<Integer> doors = getDoorsSet();
                    doors.removeAll(voidDoors);
                    doors.remove(playerChoice);
                    return (int) doors.toArray()[0];
                };

        HashMap<Integer, Double> notChangeResult = new HashMap<>(MAX_ITERATIONS),
                changeResult = new HashMap<>(MAX_ITERATIONS);
        for (int i = 1; i <= MAX_ITERATIONS; i++) {
            notChangeResult.put(i, (double) getWins(i, notChangeStrategy) / i);
            changeResult.put(i, (double) getWins(i, changeStrategy) / i);
        }

        System.out.println("\nНе меняем выбор");
        double expectation = 1.0 / DOORS_NUMBER;
        System.out.println("Ожидаемая вероятность угадать: " + expectation);
        System.out.println("Взвешенное среднее значение процента угадываний на основе экспериментов: " +
                getWeightedAverageOf(notChangeResult));
        System.out.println("Взвешенное среднеквадратичное отклонение от ожидаемого процента угадываний: " +
                getWeightedDispersionOf(notChangeResult, expectation));

        System.out.println("\nМеняем выбор");
        expectation = 1 - expectation;
        System.out.println("Ожидаемая вероятность угадать: " + expectation);
        System.out.println("Взвешенное среднее значение процента угадываний на основе экспериментов: " +
                getWeightedAverageOf(changeResult));
        System.out.println("Взвешенное среднеквадратичное отклонение от ожидаемого процента угадываний: " +
                getWeightedDispersionOf(changeResult, expectation));
    }

    private static int getWins(int iterations, Strategy strategy) {
        int counter = 0;
        for (int i = 0; i < iterations; i++) {
            HashSet<Integer> voidDoors = getDoorsSet();
            int prizeDoor = new Random().nextInt(1, 4), playerChoice = new Random().nextInt(1, 4);

            voidDoors.remove(prizeDoor);
            if (voidDoors.contains(playerChoice)) {
                voidDoors.remove(playerChoice);
            } else {
                voidDoors.remove((int) voidDoors.toArray()[new Random().nextInt(DOORS_NUMBER - 1)]);
            }

            if (strategy.getFinalChoice(voidDoors, playerChoice) == prizeDoor) {
                counter++;
            }
        }
        return counter;
    }

    private static HashSet<Integer> getDoorsSet() {
        HashSet<Integer> res = new HashSet<>();
        for (int i = 1; i <= DOORS_NUMBER; i++) {
            res.add(i);
        }
        return res;
    }

    private static double getWeightedAverageOf(HashMap<Integer, Double> map) {
        double amount = 0;
        for (Map.Entry<Integer, Double> entry: map.entrySet()) {
            amount += entry.getKey() * entry.getValue();
        }
        return amount / getAmountOf(map.keySet());
    }

    private static double getWeightedDispersionOf(HashMap<Integer, Double> map, double value) {
        double amount = 0;
        for (Map.Entry<Integer, Double> entry: map.entrySet()) {
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
