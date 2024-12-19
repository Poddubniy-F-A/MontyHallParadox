import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Main {
    private static final int MAX_ITERATIONS = 1000, DOORS_NUMBER = 3;

    public static void main(String[] args) {
        Strategy
                notChangeStrategy = (playerChoice, _) -> playerChoice,
                changeStrategy = (playerChoice, voidDoors) -> {
                    HashSet<Integer> doors = getDoorsSet();
                    doors.removeAll(voidDoors);
                    doors.remove(playerChoice);
                    return (int) doors.toArray()[0];
                };

        HashMap<Integer, Double>
                notChangeResult = new HashMap<>(MAX_ITERATIONS),
                changeResult = new HashMap<>(MAX_ITERATIONS);
        for (int i = 1; i <= MAX_ITERATIONS; i++) {
            notChangeResult.put(i, getWinRate(i, notChangeStrategy));
            changeResult.put(i, getWinRate(i, changeStrategy));
        }

        System.out.println("\nНе меняем выбор");
        showInfo(1.0 / DOORS_NUMBER, notChangeResult);

        System.out.println("\nМеняем выбор");
        showInfo(1 - 1.0 / DOORS_NUMBER, changeResult);
    }

    private static double getWinRate(int iterations, Strategy strategy) {
        int counter = 0;
        for (int i = 0; i < iterations; i++) {
            int
                    prizeDoor = new Random().nextInt(DOORS_NUMBER) + 1,
                    playerChoice = new Random().nextInt(DOORS_NUMBER) + 1;

            HashSet<Integer> voidDoors = getDoorsSet();
            voidDoors.remove(prizeDoor);
            voidDoors.remove(voidDoors.contains(playerChoice) ?
                    playerChoice : voidDoors.toArray()[new Random().nextInt(DOORS_NUMBER - 1)]
            );

            if (strategy.getFinalChoice(playerChoice, voidDoors) == prizeDoor) {
                counter++;
            }
        }
        return (double) counter / iterations;
    }

    private static HashSet<Integer> getDoorsSet() {
        HashSet<Integer> res = new HashSet<>();
        for (int i = 1; i <= DOORS_NUMBER; i++) {
            res.add(i);
        }
        return res;
    }

    private static void showInfo(double expectation, HashMap<Integer, Double> result) {
        System.out.println("Ожидаемая вероятность угадать: " + expectation);
        System.out.println("Взвешенное среднее значение процента угадываний на основе экспериментов: " +
                MetricFunctions.getWeightedAverageOf(result));
        System.out.println("Взвешенное среднеквадратичное отклонение от ожидаемого процента угадываний: " +
                MetricFunctions.getWeightedDispersionOf(result, expectation));
    }
}
