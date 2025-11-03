package stringop;

/**
 * Problem:
 * Given a binary string `str` (no leading zeros; str[0] == '1'),
 * count the steps to reduce it to "1" with:
 *  - If even: divide by 2
 *  - If odd (and not 1): add 1
 *
 * Optimized Idea (O(n) time, O(1) space):
 * Scan right-to-left with a carry.
 * For each bit i from the end down to 1:
 *   b = (str[i] - '0') + carry
 *   - b == 0 → even → +1 step (/2)
 *   - b == 1 → odd  → +2 steps (+1 then /2), carry=1
 *   - b == 2 → even → +1 step (/2), carry=1
 * After loop, if carry == 1, add one final step.
 *
 * Time:  O(n)
 * Space: O(1)
 */
public class BinaryToOneStepCounter {

    /**
     * Counts how many operations are needed to reduce the binary string `str` to "1".
     * Uses an O(n) carry-propagation counting approach (no mutation of the input).
     */
    public static int countStepsToOne(String str) {
        if (str == null || str.isEmpty() || str.charAt(0) != '1') {
            throw new IllegalArgumentException("Invalid input: non-empty binary with str[0] == '1' required.");
        }
        if (str.equals("1")) return 0;

        int steps = 0;
        int carry = 0;

        // Process bits from LSB to just before MSB
        for (int i = str.length() - 1; i > 0; i--) {
            int b = (str.charAt(i) - '0') + carry; // 0, 1, or 2

            if (b == 0) {
                // even → /2
                steps += 1;
            } else if (b == 1) {
                // odd → +1 then /2
                steps += 2;
                carry = 1;
            } else { // b == 2
                // even (..10) → /2
                steps += 1;
                carry = 1;
            }
        }

        // Handle potential final carry at MSB
        steps += carry;
        return steps;
    }

    /* ----------------
       Demo / Quick Run
       ---------------- */
    public static void main(String[] args) {
        String[] tests = {
                "1",          // 0
                "10",         // 1
                "11",         // 3
                "101",        // 5
                "1101",       // 6
                "111",        // 4
                "1000",       // 3
                "1111011110000011100000110001011011110010111001010111110001"
        };

        for (String str : tests) {
            System.out.printf("str = %s -> steps = %d%n", str, countStepsToOne(str));
        }
    }
}
