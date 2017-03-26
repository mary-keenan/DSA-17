
import java.util.*;

public class ShortestPath {

    public static List<Integer> shortestPath(Graph g, int v, int w) {
        List checkedAlready = new ArrayList();
        checkedAlready.add(v); //shouldn't go back to start point, gets stuck otherwise and returns null
        List pathList = new ArrayList();
        pathList.add(v);
        List path = pathFinder(g, v, w, checkedAlready, pathList);
        return path;
    }

    public static List<Integer> pathFinder(Graph g, int currVert, int w, final List checkedAlready, final List pathList) {
        LinkedList neighbors = (LinkedList) g.getNeighbors(currVert);
        List<List> allPathLists = new ArrayList<>();
        for (int i = 0; i < neighbors.size(); i++) {
            List currCheckedAlready = new ArrayList(checkedAlready); //clean slate each go around
            List currPathList = new ArrayList(pathList);
            int neighbor = (int) neighbors.get(i);
            if (neighbor == w) { //base case #1 -- we've found the vertex and can return; should return, guaranteed to be shortest list
                currPathList.add(neighbor);
                return currPathList; //guaranteed to be shortest list here
            } else if (!currCheckedAlready.contains(neighbor)) { //if we haven't been here before, follow this path
                currPathList.add(neighbor);
                currCheckedAlready.add(neighbor); //we've been here now
                List path = pathFinder(g, neighbor, w, currCheckedAlready, currPathList);
                allPathLists.add(path);
            } else { //base case #2 -- this vertex is a dead end; should not necessarily return
                allPathLists.add(new ArrayList());
            }
        }

        int maxIndex = 0;
        for (int n = 0; n < allPathLists.size(); n++) {
            if (allPathLists.get(n).size() > allPathLists.get(maxIndex).size()) {
                maxIndex = n;
            }
        }

        List shortestPath = allPathLists.get(maxIndex); //start with longest list to make sure we don't end up with a list of length 0 unless that's the max
        for (int n = 0; n < allPathLists.size(); n++) {
            List currList = allPathLists.get(n);
            if (currList.size() > 0 && currList.size() < shortestPath.size()) { //if new path is shorter, update shortest path list
                shortestPath = allPathLists.get(n);
            }
        }
        return shortestPath;
    }

    public static int distanceBetween(Graph g, int v, int w) {
        List<Integer> shortestPath = shortestPath(g, v, w);
        if (shortestPath != null) {
            return shortestPath.size() - 1;
        }
        return -1;
    }
}