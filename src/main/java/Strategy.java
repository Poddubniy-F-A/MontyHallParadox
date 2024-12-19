import java.util.HashSet;

@FunctionalInterface
public interface Strategy {
    int getFinalChoice(int playerChoice, HashSet<Integer> voidDoors);
}
