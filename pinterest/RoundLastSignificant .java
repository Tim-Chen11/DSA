public class RoundLastSignificant {

    public static String roundLast(String s) {
        if (s == null || s.isEmpty()) return s;

        char[] ch = s.toCharArray();
        int n = ch.length;

        // 1. 找最后一个有效位（最后一个非 0 的数字）
        int lastSig = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (ch[i] == '.') continue;
            if (ch[i] != '0') {
                lastSig = i;
                break;
            }
        }

        // 全是 0 或者啥都没有，直接返回
        if (lastSig == -1) return s;

        int digit = ch[lastSig] - '0';

        // 2. 四舍五入逻辑
        if (digit < 5) {
            // 直接变成 0
            ch[lastSig] = '0';
            // 右边全部数字变 0（小数点保留）
            for (int i = lastSig + 1; i < n; i++) {
                if (ch[i] != '.') ch[i] = '0';
            }
            return new String(ch);
        } else {
            // >=5，需要进位
            ch[lastSig] = '0';
            int i = lastSig - 1;

            // 向左进位
            while (i >= 0) {
                if (ch[i] == '.') {
                    i--;
                    continue;
                }
                if (ch[i] == '9') {
                    ch[i] = '0';
                    i--;
                } else {
                    ch[i] = (char) (ch[i] + 1);
                    break;
                }
            }

            String result;
            if (i < 0) {
                // 比如 99.5 -> 100.0，需要在前面补一个 '1'
                StringBuilder sb = new StringBuilder();
                sb.append('1');
                sb.append(ch);
                result = sb.toString();
            } else {
                result = new String(ch);
            }

            // 把 lastSig 右边的数字全部变 0
            char[] ch2 = result.toCharArray();
            for (int j = (result.length() - (n - lastSig)); j < ch2.length; j++) {
                if (ch2[j] != '.') ch2[j] = '0';
            }
            return new String(ch2);
        }
    }

    public static void main(String[] args) {
        System.out.println(roundLast("14.5"));  // 15.0
        System.out.println(roundLast("150"));   // 200
        System.out.println(roundLast("14.4"));  // 14.0
        System.out.println(roundLast("99.5"));  // 100.0
        System.out.println(roundLast("140"));   // 140
        System.out.println(roundLast("149"));   // 150
    }
}
