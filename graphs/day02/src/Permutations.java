import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public static List<List<Integer>> permutations(List<Integer> A) {
        return pickPlace(new ArrayList<>(), A, new ArrayList<>());
    }

    //TODO: don't copy lists -- add then remove unpicked
    public static List<List<Integer>> pickPlace(List<Integer> currList, List<Integer> notPickedYet, List<List<Integer>> allLists) {
        if (notPickedYet.size() == 0) {
            allLists.add(currList);
            return allLists;
        }
        for (Integer unpicked: notPickedYet) {
            List<Integer> newNotPickedYet = new ArrayList<>(notPickedYet); //don't want to overwrite it for next iteration
            List<Integer> newCurrList = new ArrayList<>(currList); //don't want to overwrite it for next iteration
            newNotPickedYet.remove(unpicked); //has now been picked
            newCurrList.add(unpicked);
            allLists = pickPlace(newCurrList, newNotPickedYet, allLists);
//            notPickedYet.add(unpicked);
        }
        return allLists;
    }
}
