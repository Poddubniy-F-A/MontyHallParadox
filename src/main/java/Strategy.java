import java.util.HashSet;

public interface Strategy {
    int getFinalChoice(HashSet<Integer> voidDoors, int playerChoice);
}
