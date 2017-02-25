public class PeakFinding {

    public static int findOneDPeak(int[] nums){ //O(log(n)) runtime
        int peakIndex = checkMiddle(nums, 0, nums.length - 1);
        return peakIndex;
    }

    public static int checkMiddle(int[] nums, int low, int high) {
        if (high - low == 0) {
            return -1;
        }
        int midIndex = low + (high - low)/2;
        int midNum = nums[midIndex];

        if (midNum >= nums[midIndex - 1] && midNum >= nums[midIndex + 1]) { //we found a peak, we can stop now
            return midIndex;
        }
        else if (midNum <= nums[midIndex -1]) { //less than num to left --> go with left (lower) array
            if (midIndex - 1 - low == 0) { //if it's alone, it's greater/equal
                return midIndex + 1;
            } else if (checkMiddle(nums, low, midIndex - 1) != -1){ //if we found a peak here, return it
                return checkMiddle(nums, low, midIndex - 1);
            } else { //otherwise, search other side
                return checkMiddle(nums, midIndex + 1, high);
            }
        } else { //less than num to right or neither
            if (high - midIndex - 1 <= 0) { //if it's alone, it's greater/equal
                return midIndex + 1;
            } else if (checkMiddle(nums, midIndex + 1, high) != -1){ //if we found a peak here, return it
                return checkMiddle(nums, midIndex + 1, high);
            } else { //otherwise, search other side
                return checkMiddle(nums, low, midIndex - 1);
            }
        }
    }

    // Return -1 is left is higher, 1 if right is higher, 0 if peak
    private static int peak(int i, int[] nums) {
        if (i>0 && nums[i] < nums[i-1])
            return -1;
        if (i<nums.length-1 && nums[i] < nums[i+1])
            return 1;
        return 0;
    }

    // Return -1 is left is higher, 1 if right is higher, 0 if peak
    private static int peakX(int x, int y, int[][] nums) {
        if (x>0 && nums[y][x] < nums[y][x-1])
            return -1;
        if (x<nums[0].length-1 && nums[y][x] < nums[y][x+1])
            return 1;
        return 0;
    }

    // Return -1 is up is higher, 1 if down is higher, 0 if peak
    private static int peakY(int x, int y, int[][] nums) {
        if (y>0 && nums[y][x] < nums[y-1][x])
            return -1;
        if (y<nums.length-1 && nums[y][x] < nums[y+1][x])
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

    public static int[] findTwoDPeak(int[][] nums){ //currently O(nlog(n))
        int[] peakIndex = checkMiddle2D(nums, 0, nums.length - 1);
        return peakIndex;
    }

    public static int[] checkMiddle2D (int[][] nums, int low, int high) { //[col][row]
        int[] answer;
        int rightVal;
        int leftVal;

        //BASE CASES -- if there's only one column, find a peak in that column and return
        if (high - low == 0) { //one column left
            answer = new int[]{0, findOneDPeak(nums[0])}; //TODO: will that add them to the answer array? Also, is the order correct?
            return answer;
        } else if (nums[0].length <= 1){ //if there's only one row, find a peak in that row and return
            int[] colArray = new int[]{nums[0][nums.length-1]};
            answer = new int[]{maxIndex(colArray), 0};
            return answer;
        }

        //FIND COLUMN PEAK
//        int midColInd = nums.length/2; //number of cols assuming first array is num of cols
        int midColInd = low + (high - low)/2; //number of cols assuming first array is num of cols
        int[] midCol = nums[midColInd]; //middle column
        int midColPeakInd = maxIndex(midCol); //found index of column max on midCol; this is a row index
        int midColPeak = midCol[midColPeakInd];

        //CHECK SIDES TO SEE IF THERE'S A LEFT AND/OR RIGHT
        if (midColPeakInd < midCol.length - 1) { //check to make sure you can go right (see if it's along right side)
            rightVal = nums[midColInd][midColPeakInd + 1];
        } else { //peak is along right side
            leftVal = nums[midColInd][midColPeakInd - 1]; //has to exist or would have returned already (checked earlier to see if only one col or row)
            if (leftVal <= midColPeak) { //we have a peak
                answer = new int[]{midColInd, midColPeakInd};
                return answer;
            }
            rightVal = (int) Double.NEGATIVE_INFINITY; //doesn't exist, make it as low as possible for comparing
        }
        if (midColPeakInd > 0) { //check to make sure you can go left (see if it's along left side)
            leftVal = nums[midColInd][midColPeakInd - 1];
        } else { //peak is along right side
            rightVal = nums[midColInd][midColPeakInd + 1];
            if (rightVal <= midColPeak) { //we have a peak
                answer = new int[]{midColInd, midColPeakInd};
                return answer;
            }
            leftVal = (int) Double.NEGATIVE_INFINITY;
        }

        //NOT AGAINST SIDE or AGAINST SIDE BUT NOT A PEAK
        if (leftVal <= midColPeak && rightVal <= midColPeak) { //found a peak, return it
            answer = new int[]{midColInd, midColPeakInd};
            return answer;
        } else if (leftVal > midColPeak) { //left is bigger than peak, so move to the left
            return (checkMiddle2D(nums, low , midColInd));

        } else { //right is bigger than peak, so move right
            return (checkMiddle2D(nums, midColInd , high));
        }
    }


    public static int checkColumn (int[] nums) {
        int index = 0;
        return index;
    }

}
