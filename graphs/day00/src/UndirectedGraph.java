import java.util.*;

public class UndirectedGraph implements Graph {
    Map<Integer, LinkedList> adjacencyMap;
    List<Integer> verticesList;
    int verticesNum;
    int edgesNum;

    public UndirectedGraph(int n) {
        //runtime is O(V), because we're adding all of the vertices to our map/list
        adjacencyMap = new HashMap<>();
        verticesList = new ArrayList<>();
        verticesNum = n;
        edgesNum = 0;
        for (int i = 0; i < n; i++) {
            adjacencyMap.put(i, new LinkedList<>());
            verticesList.add(i);
        }
    }

    @Override
    public void addEdge(int v, int w) {
        //runtime is O(1), because we're just adding two things to our adjacency map
        LinkedList vEdges = adjacencyMap.get(v);
        LinkedList wEdges = adjacencyMap.get(w);
        if (!vEdges.contains(w)) {
            vEdges.add(w); //automatically updates map?
            wEdges.add(v);
            edgesNum++;
        }
    }

    @Override
    public List<Integer> vertices() {
        //runtime is O(1), because we already have a list of the vertices
        return verticesList;
    }

    @Override
    public int numVertices() {
        //runtime is O(1), because we already have the number of vertices
        return verticesNum;
    }

    @Override
    public int numEdges() {
        //runtime is O(1), because we already have the number of edges
        return edgesNum;
    }

    @Override
    public Iterable<Integer> getNeighbors(int v) {
        //runtime is O(1), because we know the index of the linked list we're getting
        return adjacencyMap.get(v); //returns linked list but apparently that's okay
    }

    @Override
    public boolean hasEdgeBetween(int v, int w) {
        //runtime is O(E/V), because calling "contains" means searching the length of the linked list, which will have an average of E/V length
        LinkedList vEdges = adjacencyMap.get(v);
        if (vEdges.contains(w)) {
            return true;
        }
        return false;
    }
}
