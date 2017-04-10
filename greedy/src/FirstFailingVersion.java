public class FirstFailingVersion {

    public static long firstBadVersion(long n, IsFailingVersion isBadVersion) {
        long firstBad = mergeSearch(0, n, isBadVersion);

        return firstBad;
    }

    public static long mergeSearch(long lowerBound, long upperBound, IsFailingVersion isBadVersion) {
        //base case
        if (upperBound - lowerBound <= 1) { //we're donesies
            if (isBadVersion.isFailingVersion(lowerBound)) {
                return lowerBound;
            } else {
                return upperBound;
            }
        }

        long firstBad;
        long middle = (upperBound + lowerBound)/2; //rounds down
        Boolean isBad = isBadVersion.isFailingVersion(middle);

        if (isBad) { //if it's bad, go left
            if (!isBadVersion.isFailingVersion(middle - 1)) { //if the middle number is the first bad number, return it
                return middle;
            } else {
                firstBad = mergeSearch(lowerBound, middle - 1, isBadVersion);
            }
        } else { //if it's good, go right
            firstBad = mergeSearch(middle + 1, upperBound, isBadVersion);
        }
        return firstBad;
    }
}
