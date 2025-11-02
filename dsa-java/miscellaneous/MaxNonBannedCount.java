package miscellaneous;

import java.util.*;

/**
 * MaxNonBannedCount
 *
 * Provides:
 *  - Greedy (optimized) solution: O(n + |banned|) time, O(|banned|) space
 *  - Brute force (backtracking) solution: O(2^m) time, O(m) space, for validation
 *
 * Problem:
 * Choose the maximum count of integers from [1..n] not in 'banned' such that their sum <= maxSum.
 */
public class MaxNonBannedCount {

    /**
     * Greedy (Optimized) solution.
     *
     * Rationale:
     *  To maximize the number of selected integers under a sum cap, always choose the smallest allowed numbers first.
     *  If adding the current smallest exceeds maxSum, larger ones will also exceed; hence we can stop early.
     *
     * Time Complexity:  O(n + |banned|)
     * Space Complexity: O(|banned|)
     */
    public static int maxCountGreedy(int[] banned, int n, int maxSum) {
        HashSet<Integer> bannedSet = new HashSet<>();
        for (int b : banned) bannedSet.add(b);

        long sum = 0;
        int count = 0;

        for (int x = 1; x <= n; x++) {
            if (bannedSet.contains(x)) continue;
            if (sum + x > maxSum) break; // later numbers are larger; stop
            sum += x;
            count++;
        }
        return count;
    }

    /**
     * Brute Force (Backtracking) solution with pruning.
     *
     * Steps:
     *  1) Build 'allowed' from [1..n] \ banned
     *  2) DFS choose/skip each allowed element while keeping sum <= maxSum
     *  3) Use pruning: (a) stop when sum exceeds maxSum
     *                  (b) bound: if currCount + remaining <= best, prune
     *
     * Time Complexity:  O(2^m) in the worst case (m = allowed.size())
     * Space Complexity: O(m) recursion depth for call stack
     */
    public static int maxCountBruteForce(int[] banned, int n, int maxSum) {
        boolean[] isBanned = new boolean[n + 1];
        for (int b : banned) {
            if (b >= 1 && b <= n) isBanned[b] = true;
        }

        List<Integer> allowed = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!isBanned[i]) allowed.add(i);
        }

        int[] best = new int[1];
        backtrack(allowed, 0, 0, 0, maxSum, best);
        return best[0];
    }

    private static void backtrack(List<Integer> allowed, int idx, int currSum, int currCount,
                                  int maxSum, int[] best) {
        if (currSum > maxSum) return; // infeasible
        if (currCount > best[0]) best[0] = currCount;
        if (idx == allowed.size()) return;

        // bound: even taking all remaining won't beat best → prune
        int remaining = allowed.size() - idx;
        if (currCount + remaining <= best[0]) return;

        int val = allowed.get(idx);

        // Option 1: take if it fits
        if (currSum + val <= maxSum) {
            backtrack(allowed, idx + 1, currSum + val, currCount + 1, maxSum, best);
        }
        // Option 2: skip
        backtrack(allowed, idx + 1, currSum, currCount, maxSum, best);
    }

    // ------------------- Demo -------------------
    public static void main(String[] args) {
        // Demo 1
        int[] banned1 = {1, 6, 5};
        int n1 = 10;
        int maxSum1 = 11;
        System.out.println("Greedy Result       : " + maxCountGreedy(banned1, n1, maxSum1));
        System.out.println("Brute Force Result  : " + maxCountBruteForce(banned1, n1, maxSum1));
        System.out.println("----");

        // Demo 2
        int[] banned2 = {2, 5};
        int n2 = 8;
        int maxSum2 = 15;
        System.out.println("Greedy Result       : " + maxCountGreedy(banned2, n2, maxSum2));
        System.out.println("Brute Force Result  : " + maxCountBruteForce(banned2, n2, maxSum2));
    }
}

