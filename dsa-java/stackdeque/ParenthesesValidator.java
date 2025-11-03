package stackdeque;

/*
 * Problem Statement:
 * ------------------
 * Implement a validator that determines whether a string contains balanced brackets.
 * The validator must support the three bracket types: (), {}, and [].
 * A string is "balanced" if every opening bracket has a corresponding closing bracket
 * of the same type and the pairs are properly nested. Non-bracket characters are ignored.
 *
 * Examples:
 *   "()"            -> true
 *   "()[]{}"        -> true
 *   "([{}])"        -> true
 *   "(]"            -> false
 *   "([)]"          -> false
 *   "abc{[()]}xyz"  -> true
 *   "{[("           -> false
 *
 * Approach:
 * ---------
 * Use a stack (Deque<Character> backed by ArrayDeque) to track expected closing brackets.
 * - When we see an opening bracket, push its matching closing bracket onto the stack.
 * - When we see a closing bracket, it must match the top of the stack; otherwise it's unbalanced.
 * - Ignore any characters that are not one of ()[]{}.
 * - At the end, the stack must be empty for the string to be balanced.
 *
 * Why Deque/ArrayDeque?
 * ---------------------
 * ArrayDeque provides a fast, unsynchronized stack implementation with push/pop/peek.
 * It is preferred over the legacy java.util.Stack for performance and modern API ergonomics.
 *
 * Correctness Notes & Edge Cases:
 * -------------------------------
 * - Null input is treated as balanced (no brackets present).
 * - An early unmatched closing bracket returns false immediately.
 * - Leftover expected closings in the stack at the end implies unmatched openings -> false.
 *
 * Complexity:
 * -----------
 * Let n be the length of the input string.
 * - Time  : O(n) — each character is processed once; each push/pop is O(1).
 * - Space : O(n) in the worst case (all characters are opening brackets, e.g., "(((([[[{{{").
 */

import java.util.ArrayDeque;
import java.util.Deque;

public class ParenthesesValidator {

    /**
     * Validates whether the given string has balanced brackets for the set: {}, [], ().
     * Non-bracket characters are ignored.
     *
     * @param s input string; may be null (treated as balanced)
     * @return true iff the brackets in the string are balanced
     */
    public static boolean isBalanced(String s) {
        // Treat null as balanced: no brackets to validate.
        if (s == null) return true;

        // Use Deque as a stack of the *expected* closing brackets.
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            switch (c) {
                // Opening brackets: push their corresponding expected closing bracket.
                case '(': stack.push(')'); break;
                case '[': stack.push(']'); break;
                case '{': stack.push('}'); break;

                // Closing brackets: must match the top of the stack.
                case ')':
                case ']':
                case '}':
                    if (stack.isEmpty() || stack.pop() != c) {
                        return false; // Either nothing to match, or mismatched type/order.
                    }
                    break;

                // Non-bracket characters are ignored.
                default:
                    // no-op
            }
        }

        // Balanced only if no unmatched openings remain.
        return stack.isEmpty();
    }

    // Quick demo / basic sanity checks.
    public static void main(String[] args) {
        System.out.println(isBalanced("()[]{}"));          // true
        System.out.println(isBalanced("([{}])"));          // true
        System.out.println(isBalanced("(]"));              // false
        System.out.println(isBalanced("([)]"));            // false
        System.out.println(isBalanced("abc{[()]}xyz"));    // true
        System.out.println(isBalanced("{[("));             // false
        System.out.println(isBalanced(null));              // true
        System.out.println(isBalanced(""));                // true
        System.out.println(isBalanced("]"));               // false
    }
}
