public class PeakFinding {

    // Return -1 is left is higher, 1 if right is higher, 0 if peak
    private static int peak(int i, int[] nums) {
        if (i > 0 && nums[i] < nums[i - 1])
            return -1;
        if (i < nums.length - 1 && nums[i] < nums[i + 1])
            return 1;
        return 0;
    }

    // Return -1 is left is higher, 1 if right is higher, 0 if peak
    private static int peakX(int x, int y, int[][] nums) {
        if (x > 0 && nums[y][x] < nums[y][x - 1])
            return -1;
        if (x < nums[0].length - 1 && nums[y][x] < nums[y][x + 1])
            return 1;
        return 0;
    }

    // Return -1 is up is higher, 1 if down is higher, 0 if peak
    private static int peakY(int x, int y, int[][] nums) {
        if (y > 0 && nums[y][x] < nums[y - 1][x])
            return -1;
        if (y < nums.length - 1 && nums[y][x] < nums[y + 1][x])
            return 1;
        return 0;
    }

    // These two functions return the index of the highest value along the X or Y axis, with the given
    // value for the other axis. Searches between hi (exclusive) and lo (inclusive)
    private static int maxXIndex(int y, int lo, int hi, int[][] nums) {
        int maxIndex = -1;
        for (int x = lo; x < hi; x++) {
            if (maxIndex == -1 || nums[y][x] > nums[y][maxIndex])
                maxIndex = x;
        }
        return maxIndex;
    }

    private static int maxYIndex(int x, int lo, int hi, int[][] nums) {
        int maxIndex = -1;
        for (int y = lo; y < hi; y++) {
            if (maxIndex == -1 || nums[y][x] > nums[maxIndex][x])
                maxIndex = y;
        }
        return maxIndex;
    }


    public static int findOneDPeak(int[] nums) {
        int lo = 0;
        int hi = nums.length;
        while (lo < hi) {
            int mid = (hi + lo) / 2;
            int direction = peak(mid, nums);
            if (direction == 0) return mid;
            else if (direction == -1) hi = mid;
            else if (direction == 1) lo = mid + 1;
        }
        return -1;
    }

    public static int maxIndex(int[] col){
        int max = (int) Double.NEGATIVE_INFINITY;
        int maxIndex = 0;
        for (int n = 0; n < col.length; n++){
            if (col[n] > max) {
                maxIndex = n;
                max = col[n];
            }
        }
        return maxIndex;
    }

    public static int[] findTwoDPeak(int[][] nums) {
        int[] peakIndex = checkMiddle2D(nums, 0, nums.length);
        return peakIndex;
    }

    public static int[] checkMiddle2D(int[][] nums, int low, int high) { //[col][row]

        //FIND COLUMN PEAK
        int midColInd = (low + high)/ 2; //number of cols assuming first array is num of cols
        int midColPeakInd = maxYIndex(midColInd, 0, nums.length, nums);
        int peak = peakX(midColInd, midColPeakInd, nums);
        if (peak==1) {
            return checkMiddle2D(nums, midColInd+1, high);
        } else if (peak==-1){
            return checkMiddle2D(nums, low, midColInd);
        } else if (peak==0){
            return new int[]{midColPeakInd, midColInd};
        }
        return null;
    }
}