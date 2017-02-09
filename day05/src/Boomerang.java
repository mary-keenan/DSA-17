import java.util.Set;

public class Boomerang {

    public static int numberOfBoomerangs(int[][] points) {
        int numBoomerangs = 0;
        MyHashMap<int[], MyHashMap> pointMap = new MyHashMap();
        for (int[] point: points) { //run through each point in points array
            MyHashMap distanceMap = new MyHashMap();
            for (int[] otherPoint:points) { //now run through every other point
                int distance = getSquaredDistance(point, otherPoint);
                if (distanceMap.get(distance) != null) { //if distance is already in map
                    int updatedVal = (int) distanceMap.get(distance) + 1; //update the value
                    distanceMap.put(distance, updatedVal);
                } else {
                    distanceMap.put(distance, 1); //otherwise, add it with value of 1
                }
            }
            pointMap.put(point, distanceMap);
        }

        for (int[] point: points) { //run through each point in points array again
            MyHashMap distanceMap = pointMap.get(point);
            Set keySet = distanceMap.keySet();
            for (Object key: keySet) {
                int numPoints = (int) distanceMap.get(key);
                numBoomerangs += numPoints * (numPoints -1);
            }
        }
        return numBoomerangs;
    }

    private static int getSquaredDistance(int[] a, int[] b) {
        int dx = a[0] - b[0];
        int dy = a[1] - b[1];

        return dx*dx + dy*dy;
    }
}
