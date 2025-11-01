package set;

import java.util.TreeSet;

public class KEmptySlotsUsingTreeSet {

    /**
     * Earliest day when there exist two ON bulbs with exactly k OFF bulbs between them.
     *
     * Approach:
     * - Maintain a sorted set (TreeSet) of positions that are currently ON.
     * - For each day, turn on position x, then:
     *     - Check the nearest ON bulb to the left (lower(x)).
     *     - Check the nearest ON bulb to the right (higher(x)).
     *   If either neighbor forms a gap of exactly k (i.e., x - lower - 1 == k or higher - x - 1 == k),
     *   return the current day (1-indexed).
     *
     * Correctness intuition:
     * - TreeSet gives immediate neighbors. If a closer ON bulb existed between a candidate pair,
     *   it would be the neighbor instead, so all bulbs strictly between the neighbors are OFF.
     *
     * Time Complexity: O(n log n)
     *   - For n days, each insert is O(log n) and each neighbor query (lower/higher) is O(log n).
     *   - Per day work is O(log n), total O(n log n).
     *
     * Space Complexity: O(n)
     *   - Up to n positions stored in the TreeSet.
     *
     * @param bulbs permutation where bulbs[i] is the position turned on on day i+1 (1-indexed positions)
     * @param k     required number of OFF bulbs between two ON bulbs
     * @return earliest day (1-indexed) satisfying the condition, or -1 if none exists
     */
    public static int kEmptySlots(int[] bulbs, int k){
        TreeSet<Integer> on = new TreeSet<>();
        for(int day = 0; day < bulbs.length; day++){
            int x = bulbs[day];

            on.add(x);

            Integer lower = on.lower(x);

            if(lower !=null && x-lower-1==k){
                return day+1;
            }

            Integer higher = on.higher(x);

            if(higher !=null && higher-x-1==k){
                return day+1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // Bigger example from our walkthrough
        int[] bulbs = {6, 2, 9, 4, 1, 8, 3, 7, 5, 10};
        int k = 1;

        int dayDebug = kEmptySlots(bulbs, k);
        System.out.println("Earliest day : " + dayDebug);
    }
}
