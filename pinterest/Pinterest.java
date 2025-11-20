// Question 1
// "335555"
// 要求压缩成形如：

// 若干个：[出现次数的十进制][该数字] 拼在一起的编码串

// 比如：

// "335555" 有：

// 两个 3 → 2 + 3

// 四个 5 → 4 + 5

// 压缩后就是："23" + "45" = "2345"

import java.util.List;
class solution1{
    public String encode(String s) {
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();

        char last = s.charAt(0);
        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c != last) {
                // finalize previous block
                sb.append(count);
                sb.append(last);
                
                // start new block
                last = c;
                count = 1;
            } else {
                count++;
            }
        }

        // append last block
        sb.append(count);
        sb.append(last);

        return sb.toString();
    }
}



// Question 2
// 给一个字符串，只有数字在里面，要求输出所有符合条件的字符串，比如“2345”，可以是“335555”，也可以是234个5

class solution{
    static String s;
    static StringBuilder sb;
    static List<String> res;

    public static List<String> decodeAll(String str) {
        s = str;
        sb = new StringBuilder();
        res = new ArrayList<>();

        dfs(0);
        return res;
    }

    static void dfs(int pos) {
        // 用完所有字符：当前 sb 是一种合法解码
        if (pos == s.length()) {
            res.add(sb.toString());
            return;
        }

        long count = 0;
        int n = s.length();

        // 从 pos 开始取 number，至少留 1 位给 digit
        for (int i = pos; i < n - 1; i++) {
            char ch = s.charAt(i);
            // 如果不允许前导零，可加判断：
            // if (i > pos && s.charAt(pos) == '0') break;

            // 累积 count
            count = count * 10 + (ch - '0');

            int digitPos = i + 1;
            char digit = s.charAt(digitPos);

            // 记录 append 前长度
            int lenBefore = sb.length();

            // 追加 count 个 digit
            // 注意：count 可能很大，真实题目里要根据约束控制
            for (long k = 0; k < count; k++) {
                sb.append(digit);
            }

            // 递归，从 digit 后面继续
            dfs(digitPos + 1);

            // 回溯：删掉刚刚 append 的这一段
            sb.delete(lenBefore, sb.length());
        }
    }
}

