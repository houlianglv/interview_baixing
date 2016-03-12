package Question4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 公司需要报销发票1000元，平时积攒的发票数值不等
 * （比如会有10元发票，20元，50元，或者176元，461元这样面值的发票），
 * 问：设计一种算法，可以用最小金额凑满1000元发票（要求给出算法描述，写出代码，并有测试用例）
 */

/*
 * I think this problem is NP-completeness. 
 * Thus there is no known algorithm both correct and fast (polynomial-time) on all cases.
 */
public class Solution {
    
    // for simple, just use main function to test the solution.
    public static void main(String[] args) {
        Solution so = new Solution();
        int[] invoices = new int[] { 3000, 1, 100, 899, 400, 500, 101 };// 899,
                                                                        // 101
        System.out.println(so.findMinTotalExceedsOneThousand2(invoices));
        invoices = new int[] { 100, 910, 1001 }; // 1001
        System.out.println(so.findMinTotalExceedsOneThousand2(invoices));
        invoices = new int[] { 2000, 4000 }; // 2000
        System.out.println(so.findMinTotalExceedsOneThousand(invoices));
        invoices = new int[] { 100, 4000, 200, 700, 700, 1001 }; // 100, 200,
        // 700
        System.out.println(so.findMinTotalExceedsOneThousand(invoices));
        invoices = new int[] { 1000, 999, 1001 }; // 1000
        System.out.println(so.findMinTotalExceedsOneThousand(invoices));
        invoices = new int[] { 2, 100, 101, 500, 501, 800, 999 }; // 1001
        System.out.println(so.findMinTotalExceedsOneThousand(invoices));
        
    }
    
    /*
     * suppose that the input array is the invoices we have(unsorted)
     * 
     * naive solution: find all subsets of the set composed with all invoices
     * less than 1000. Find which subset is the smallest and has more than 1000
     * total value. If we have invoice whose value is more than 1000, we select
     * the smaller one as the solution.
     * 
     * the solution may not be unique.
     * 
     */
    public List<Integer> findMinTotalExceedsOneThousand(int[] invoices) {
        if (null == invoices || invoices.length == 0) {
            throw new RuntimeException("invalid input");
        }
        
        Arrays.sort(invoices);
        int bound = invoices.length;
        for (int i = 0; i < invoices.length; i++) {
            if (invoices[i] >= 1000) {
                bound = i;
                break;
            }
        }
        int min = Integer.MAX_VALUE;
        List<Integer> ans = null;
        List<List<Integer>> subsets = findSubSets(invoices, 0, bound);
        for (List<Integer> subset : subsets) {
            int sum = 0;
            for (int i : subset)
                sum += i;
            if (sum >= 1000 && sum < min) {
                ans = subset;
                min = sum;
            }
        }
        if (bound < invoices.length && invoices[bound] < min) {
            ans = new ArrayList<Integer>();
            ans.add(invoices[bound]);
            return ans;
        }
        
        return ans;
    }
    
    private List<List<Integer>> findSubSets(int[] invoices, int i, int bound) {
        if (i == bound) {
            List<List<Integer>> ans = new ArrayList<List<Integer>>();
            ans.add(new ArrayList<Integer>());
            return ans;
        }
        List<List<Integer>> subsubsets = findSubSets(invoices, i + 1, bound);
        List<List<Integer>> subsets = new ArrayList<List<Integer>>();
        for (List<Integer> subsubset : subsubsets) {
            List<Integer> subset = new ArrayList<Integer>(subsubset);
            subset.add(invoices[i]);
            subsets.add(subset);
        }
        subsets.addAll(subsubsets);
        return subsets;
    }
    
    public List<Integer> findMinTotalExceedsOneThousand2(int[] invoices) {
        // this problem is like a knap sack problem
        // we could consider that we are trying to fill a knapsack which could
        // contains at most invoices.length bag, and each bag's weight is
        // invoices[i],
        // the maximum weight that the knapsack could hold is sum(invoices) -
        // 1000;
        int sum = 0, minInv = Integer.MAX_VALUE;
        int[] smallInvoices = new int[invoices.length];
        int bound = 0;
        for (int i : invoices) {
            if (i < 1000) {
                sum += i;
                smallInvoices[bound++] = i;
            } else if (i < minInv) {
                minInv = i;
            }
        }
        
        int N = bound; // number of invoices
        int W = sum - 1000; // maximum weight of knapsack
        
        if (W < 0) {
            if (minInv < 1000)
                throw new RuntimeException("invalid input, cannot make it");
            List<Integer> ans = new ArrayList<Integer>();
            ans.add(minInv);
            return ans;
            
        }
        
        // use dynamic programming to solve this problem.
        // but it could use too much memory. Not a space friendly algorithm
        // the running time is O(N*W)
        int[][] dp = new int[N + 1][W + 1];
        boolean[][] solution = new boolean[N + 1][W + 1];
        
        for (int col = 0; col <= W; col++) {
            dp[0][col] = 0;
        }
        for (int row = 0; row <= N; row++) {
            dp[row][0] = 0;
        }
        
        // weight and value is the same thing.
        // weight cannot exceed sum - 1000
        for (int item = 1; item <= N; item++) {
            for (int weight = 1; weight <= W; weight++) {
                int op1 = dp[item - 1][weight];
                int op2 = Integer.MIN_VALUE;
                if (smallInvoices[item - 1] <= weight) {
                    op2 = smallInvoices[item - 1]
                            + dp[item - 1][weight - smallInvoices[item - 1]];
                }
                dp[item][weight] = Math.max(op1, op2);
                solution[item][weight] = op2 > op1;
            }
        }
        
        if (minInv < sum - dp[N][W]) {
            List<Integer> ans = new ArrayList<Integer>();
            ans.add(minInv);
            return ans;
        }
        
        // determine which invoice to take
        boolean[] take = new boolean[N + 1];
        for (int n = N, w = W; n > 0; n--) {
            if (solution[n][w]) {
                take[n] = true;
                w = w - smallInvoices[n - 1];
            } else {
                take[n] = false;
            }
        }
        
        // construct the solution
        List<Integer> ans = new ArrayList<Integer>();
        for (int n = 1; n <= N; n++) {
            if (!take[n])
                ans.add(smallInvoices[n - 1]);
        }
        
        return ans;
    }
}
