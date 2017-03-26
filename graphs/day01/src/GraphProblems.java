import java.util.*;

public class GraphProblems {

    public static boolean connected(Graph g, int v, int u) {
        return pathFinder(g, v, u, new ArrayList());
    }

    public static boolean pathFinder(Graph g, int currVert, int u, List vis) {
        LinkedList neighbors = (LinkedList) g.getNeighbors(currVert);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = (int) neighbors.get(i);
            if (neighbor == u) {
                return true;
            } else if (!vis.contains(neighbor)) {
                vis.add(neighbor);
                Boolean possiblePath = pathFinder(g, neighbor, u, vis);
                if (possiblePath) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Integer> topologicalOrder(Digraph g) { //guaranteed to be acyclic
        List<Integer> order = new LinkedList<>();
        List<Integer> vis = new LinkedList<>();
        for (int j = 0; j < g.numVertices(); j++) { //run through every vertex
            if (!vis.contains(g.vertices().get(j))) { //only sort unvisited vertices
                vis.add(g.vertices().get(j));
                List<LinkedList> lists = topoSorter(g, g.vertices().get(j), (LinkedList) vis, (LinkedList) order);
                order = lists.get(0);
                vis = lists.get(1);
            }
        }
        return order;
    }

    public static List<LinkedList> topoSorter(Digraph g, int currVert, LinkedList vis, LinkedList order) {
        LinkedList neighbors = (LinkedList) g.getNeighbors(currVert);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = (int) neighbors.get(i);
            if (!vis.contains(neighbor)) { //haven't been there done that
                vis.add(neighbor);
                List<LinkedList> topoLists = topoSorter(g, neighbor, vis, order);
                order = topoLists.get(0);
                vis = topoLists.get(1);
            }
        }
        order.add(0, currVert);

        //COMPILE AND RETURN THE LISTS
        List<LinkedList> lists = new LinkedList<>();
        lists.add(order);
        lists.add(vis);
        return lists;
    }

    public static boolean hasCycle(UndirectedGraph g) {
        HashMap<Integer, List> vis = new HashMap();
        for (int i = 0; i < g.numVertices(); i++) {
            int vert = g.vertices().get(i);
            if (!vis.keySet().contains(vert)) {
                if (cycling(g, vert, vis)) { //if true, return now
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean cycling(UndirectedGraph g, int currVert, HashMap<Integer, List> vis) {
        List<Integer> neighbors = (List) g.getNeighbors(currVert);
        for (int neigh: neighbors) {
            List relations = vis.get(currVert);
            if (relations == null) {
                relations = new ArrayList();
            }
            else if (relations.contains(neigh)) { //check to see if that edge has been seen before
                return true;
            }
            relations.add(neigh); //update map with new value
            vis.put(currVert, relations);
            List neighRelations = vis.get(neigh);
            if (neighRelations == null || !neighRelations.contains(currVert)) { //only go through edges we haven't seen before
                Boolean cycle = cycling(g, neigh, vis);
                if (cycle) {
                    return true;
                }
            }
        }
        return false;
    }

}