class Solution {
    public int calculate(String s) {
        return eval(s.replaceAll(" ", ""), new int[]{0});
    }

    private int eval(String s, int[] i) {
        if (Character.isDigit(s.charAt(i[0]))) {
            // parse number
            int num = 0;
            while (i[0] < s.length() && Character.isDigit(s.charAt(i[0]))) {
                num = num * 10 + (s.charAt(i[0]) - '0');
                i[0]++;
            }
            return num;
        } else {
            // parse function name
            StringBuilder fn = new StringBuilder();
            while (i[0] < s.length() && Character.isLetter(s.charAt(i[0]))) {
                fn.append(s.charAt(i[0]++));
            }
            String func = fn.toString();
            i[0]++; // skip '('

            int arg1 = eval(s, i);
            i[0]++; // skip ','

            int arg2 = eval(s, i);
            i[0]++; // skip ')'

            switch (func) {
                case "Add": return arg1 + arg2;
                case "Sub": return arg1 - arg2;
                case "Mul": return arg1 * arg2;
                case "Div": return arg1 / arg2;
                case "Pow": return (int)Math.pow(arg1, arg2);
                default: throw new RuntimeException("Unknown func: " + func);
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.calculate("Div(10,Add(1,Pow(2,7)))"));
        // 10 / (1 + (2^7)) = 10 / (1+128) = 10 / 129 = 0
    }
}
