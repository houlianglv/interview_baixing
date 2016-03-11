package Question3;

import java.util.ArrayList;
import java.util.List;
/*
 * 从一个含有n个正整数的数组找到其中最小和第三小的值，请写出代码
 */

public class Solution {
    
    public List<Integer> findMinAndThirdMin(int[] nums) {
        if (null == nums || nums.length < 3) {
            throw new RuntimeException(
                    "the input is invalid. The length of input array is less than 3");
        }
        
        int[] candidates = new int[4];
        candidates[0] = nums[0];
        candidates[1] = nums[1];
        candidates[2] = nums[2];
        for (int i = 1; i < 3; i++) {
            int j = i - 1;
            while (j >= 0) {
                if (candidates[j] > candidates[j + 1]) {
                    int tmp = candidates[j];
                    candidates[j] = candidates[j + 1];
                    candidates[j + 1] = tmp;
                    --j;
                } else {
                    break;
                }
            }
        }
        for (int i = 3; i < nums.length; i++) {
            candidates[3] = nums[i];
            int j = 2;
            while (j >= 0) {
                if (candidates[j] > candidates[j + 1]) {
                    int tmp = candidates[j];
                    candidates[j] = candidates[j + 1];
                    candidates[j + 1] = tmp;
                    --j;
                } else {
                    break;
                }
            }
        }
        List<Integer> ans = new ArrayList<Integer>();
        ans.add(candidates[0]);
        ans.add(candidates[2]);
        return ans;
    }
}
