package slidingWindow;

public class KEmptySlotsLinear {

    /**
     * Returns the earliest day when there exist two ON bulbs with exactly k OFF bulbs between them,
     * or -1 if no such day exists. O(n) time, O(n) space.
     *
     * Sliding-window idea:
     * 1) Build dayAt[pos-1] = day (1..n) when position 'pos' turns ON.
     * 2) For each window [left, right] with right = left + k + 1, let maxDay = max(dayAt[left], dayAt[right]).
     *    The window is valid iff every inner index i in (left, right) has dayAt[i] > maxDay.
     * 3) If broken at i (dayAt[i] < maxDay), jump left = i; else record candidate (maxDay) and set left = right.
     */

    public static int kEmptySlots(int[] bulbs,int k){
        int n = bulbs.length;

        if(n==0 || k+1 > n-1) return -1;

        // dayAt[pos-1] = day when position 'pos' turns ON (days are 1..n)
        int[] dayAt = new int[n];

        for(int day=1; day<=n;day++){
            int pos= bulbs[day-1];

            dayAt[pos-1] = day;
        }

        // window over positions (0-indexed)
        int left =0;
        int right =left+k+1;

        int ans = Integer.MAX_VALUE;


        while(right < n){
            int boundaryMax = Math.max(dayAt[left],dayAt[right]);
            boolean valid = true;
            // Check inner positions; break early if any inner turns on too soon
            for(int i = left+1;i<right;i++){
                if(dayAt[i] < boundaryMax){
                    // Jump left to the breaking index (linear-time skip)
                    left = i;
                    right =left+k+1;
                    valid = false;
                    break;
                }
            }
            if(valid){
                ans = Math.min(ans,boundaryMax);
                // Advance window to start at the previous right boundary
                left = right;
                right = left+k+1;
            }
        }

        return ans==Integer.MAX_VALUE ? -1:ans;
    }

    public static void main(String[] args) {
        int[] bulbs = {6, 2, 9, 4, 1, 8, 3, 7, 5, 10};
        int k = 2;
        System.out.println(kEmptySlots(bulbs, k)); // prints earliest day or -1
    }
}
