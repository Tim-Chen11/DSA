import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    public int calculate(String s) {
        char[] a = s.toCharArray();
        int[] i = new int[]{0};             // mutable index
        return eval(a, i);
    }

    // Evaluate until ')' or end; advances i[0] accordingly.
    private int eval(char[] a, int[] i) {
        Deque<Integer> st = new ArrayDeque<>();
        long num = 0;                        // build numbers safely
        char op = '+';                       // previous operator

        while (i[0] < a.length) {
            char c = a[i[0]];

            if (c == ' ') {
                i[0]++;
                continue;
            }

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
                i[0]++;
                continue;
            }

            if (c == '(') {
                i[0]++;                      // skip '('
                num = eval(a, i);            // value inside parentheses
                // do not advance here; eval stops at ')', i[0] is at ')'
            }

            // If current char is an operator or ')', or we've just returned from ')'
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == ')') {
                // apply previous operator `op` to num
                if (op == '+') {
                    st.push((int) num);
                } else if (op == '-') {
                    st.push((int) -num);
                } else if (op == '*') {
                    st.push(st.pop() * (int) num);
                } else if (op == '/') {
                    st.push(st.pop() / (int) num); // trunc toward 0
                }
                num = 0;
                op = c;                      // set new previous op

                if (c == ')') {              // end of this subexpression
                    i[0]++;                  // consume ')'
                    break;
                }
                i[0]++;                      // consume operator
                continue;
            }

            // Any other char (shouldn't happen per problem constraints)
            i[0]++;
        }

        // End-of-input or just closed a ')': if last token was a number, it was already applied.
        int res = 0;
        while (!st.isEmpty()) res += st.pop();
        return res;
    }
}
