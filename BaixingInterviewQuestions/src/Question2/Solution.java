package Question2;

/*
 * 给定一个数组，找出出现次数超过一半的数字，默认该数组中一定有一个数字出现次数超过一半
 * 请写出代码。
 * Some assumptions: the input type of numbers is int.
 * based on the description, the input array cannot be empty, so I will not check null or length of the input.
 */
public class Solution {
    
    public int findMajority(int[] nums) {
        int majority = nums[0], cnt = 1;
        for (int i = 1; i < nums.length;) {
            if (nums[i] == majority) {
                ++cnt;
            } else {
                --cnt;
            }
            if (cnt == 0) {
                majority = nums[++i];
                ++cnt;
            }
            ++i;
        }
        
        return majority;
    }
}
