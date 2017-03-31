import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public static List<List<Integer>> permutations(List<Integer> A) {
        return pickPlace(new ArrayList<>(), new ArrayList<Integer>(A), new ArrayList<>());
    }

    public static List<List<Integer>> pickPlace(List<Integer> currList, ArrayList<Integer> notPickedYet, List<List<Integer>> allLists) {
        if (notPickedYet.size() == 0) {
            allLists.add(currList);
            return allLists;
        }
        for (Integer unpicked: new ArrayList<>(notPickedYet)) {
            List<Integer> newCurrList = new ArrayList<>(currList); //don't want to overwrite it for next iteration
            notPickedYet.remove(unpicked); //has now been picked
            newCurrList.add(unpicked);
            allLists = pickPlace(newCurrList, notPickedYet, allLists);
            notPickedYet.add(unpicked);
        }
        return allLists;
    }
}
