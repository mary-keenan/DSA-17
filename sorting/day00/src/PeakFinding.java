import java.util.Arrays;

/**
 * Created by sidd on 2/20/17.
 */
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

    public static int[] findTwoDPeak(int[][] nums){
    	// TODO



        int[] answer = {-1,-1};
        return answer;
    }

    public static int checkColumn (int[] nums) {
        int index = 0;



        return index;
    }

}
